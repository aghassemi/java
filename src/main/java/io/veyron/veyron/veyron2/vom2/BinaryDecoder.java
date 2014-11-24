package io.veyron.veyron.veyron2.vom2;

import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;

import io.veyron.veyron.veyron2.vdl.Kind;
import io.veyron.veyron.veyron2.vdl.Types;
import io.veyron.veyron.veyron2.vdl.VdlAny;
import io.veyron.veyron.veyron2.vdl.VdlArray;
import io.veyron.veyron.veyron2.vdl.VdlOneOf;
import io.veyron.veyron.veyron2.vdl.VdlStruct;
import io.veyron.veyron.veyron2.vdl.VdlStructField;
import io.veyron.veyron.veyron2.vdl.VdlType;
import io.veyron.veyron.veyron2.vdl.VdlType.Builder;
import io.veyron.veyron.veyron2.vdl.VdlType.PendingType;
import io.veyron.veyron.veyron2.vdl.VdlTypeObject;
import io.veyron.veyron.veyron2.vdl.VdlValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BinaryDecoder reads a VDL value from {@code InputStream} encoded in binary VOM format.
 */
public class BinaryDecoder {
    private final InputStream in;
    private final Map<TypeID, VdlType> decodedTypes;
    private final Map<TypeID, VdlValue> wireTypes;
    private boolean binaryMagicByteRead;

    public BinaryDecoder(InputStream in) {
        this.in = in;
        this.decodedTypes = new HashMap<TypeID, VdlType>();
        this.wireTypes = new HashMap<TypeID, VdlValue>();
        this.binaryMagicByteRead = false;
    }

    /**
     * Decodes a VDL value. Returns an instance of provided {@code java.lang.reflect.Type}.
     *
     * @param targetType the type of returned object
     * @return the decoded value
     * @throws IOException
     * @throws ConversionException
     */
    public Object decodeValue(Type targetType) throws IOException, ConversionException {
        if (!binaryMagicByteRead) {
            if ((byte) in.read() != BinaryUtil.BINARY_MAGIC_BYTE) {
                throw new CorruptVomStreamException(
                        String.format("The input stream should start with byte %02x",
                        BinaryUtil.BINARY_MAGIC_BYTE));
            }
            binaryMagicByteRead = true;
        }
        VdlType actualType = decodeType();
        return readValueMessage(actualType, targetType);
    }

    /**
     * Decodes a VDL value. Returns an instance of {@code VdlValue}.
     *
     * @return the decoded value
     * @throws IOException
     * @throws ConversionException
     */
    public Object decodeValue() throws IOException, ConversionException {
        return decodeValue(VdlValue.class);
    }

    private Object readValueMessage(VdlType actualType, Type targetType) throws IOException,
            ConversionException {
        if (BinaryUtil.hasBinaryMsgLen(actualType)) {
            // Do nothing with this information for now.
            BinaryUtil.decodeUint(in);
        }
        return readValue(actualType, targetType);
    }

    private VdlType decodeType() throws IOException, ConversionException {
        while (true) {
            long typeId = BinaryUtil.decodeInt(in);
            if (typeId == 0) {
                throw new CorruptVomStreamException("Unexpected zero type ID");
            } else if (typeId > 0) {
                return getType(new TypeID(typeId));
            } else {
                VdlAny wireType = (VdlAny) readValueMessage(Types.ANY, VdlAny.class);
                wireTypes.put(new TypeID(-typeId), (VdlValue) wireType.getElem());
            }
        }
    }

    private VdlType lookupType(TypeID typeId) {
        VdlType type = BootstrapType.getBootstrapType(typeId);
        if (type != null) {
            return type;
        } else if (decodedTypes.containsKey(typeId)) {
            return decodedTypes.get(typeId);
        } else {
            return null;
        }
    }

    private VdlType getType(TypeID typeId) throws CorruptVomStreamException {
        VdlType type = lookupType(typeId);
        if (type != null) {
            return type;
        } else {
            WireToVdlTypeBuilder builder = new WireToVdlTypeBuilder();
            PendingType pendingType = builder.lookupOrBuildPending(typeId);
            builder.build();
            return pendingType.built();
        }
    }

    private Object readValue(VdlType actualType, Type targetType) throws IOException,
            ConversionException {
        ConversionTarget target;
        if (targetType == VdlValue.class) {
            Type bootstrapClass = BootstrapType.getBootstrapClass(actualType);
            if (bootstrapClass != null) {
                target = new ConversionTarget(bootstrapClass);
            } else {
                target = new ConversionTarget(actualType);
            }
        } else {
            target = new ConversionTarget(targetType);
        }

        // TODO(rogulenko): check compatibility and handle OneOf correctly
        if (actualType.getKind() != Kind.ANY && target.getKind() == Kind.ANY) {
            return new VdlAny((VdlValue) readValue(actualType, VdlValue.class));
        }
        switch (actualType.getKind()) {
            case ANY:
                return readVdlAny(target);
            case ARRAY:
            case LIST:
                return readVdlArrayOrVdlList(actualType, target);
            case BOOL:
                return readVdlBool(target);
            case BYTE:
                return readVdlByte(target);
            case COMPLEX64:
            case COMPLEX128:
                return readVdlComplex(target);
            case ENUM:
                return readVdlEnum(actualType, target);
            case FLOAT32:
            case FLOAT64:
                return readVdlFloat(target);
            case INT16:
            case INT32:
            case INT64:
                return readVdlInt(target);
            case MAP:
            case SET:
                return readVdlMapOrSet(actualType, target);
            case STRUCT:
                return readVdlStruct(actualType, target);
            case ONE_OF:
                return readVdlOneOf(target);
            case STRING:
                return readVdlString(target);
            case TYPEOBJECT:
                return readVdlTypeObject();
            case UINT16:
            case UINT32:
            case UINT64:
                return readVdlUint(target);
            default:
                throw new ConversionException(actualType, targetType);
        }
    }

    private VdlAny readVdlAny(ConversionTarget target) throws IOException, ConversionException {
        TypeID typeId = new TypeID(BinaryUtil.decodeUint(in));
        if (typeId.getValue() == 0) {
            return new VdlAny();
        }
        Type targetType;
        if (target.getKind() != Kind.ANY) {
            targetType = target.getTargetType();
        } else {
            targetType = VdlValue.class;
        }
        return new VdlAny((VdlValue) readValue(getType(typeId), targetType));
    }

    private Object readVdlArrayOrVdlList(VdlType actualType, ConversionTarget target)
            throws IOException, ConversionException {
        int len;
        if (actualType.getKind() == Kind.LIST) {
            len = (int) BinaryUtil.decodeUint(in);
        } else {
            len = actualType.getLength();
        }

        Class<?> targetClass = target.getTargetClass();
        if (!List.class.isAssignableFrom(targetClass) && !targetClass.isArray()) {
            return ConvertUtil.convertFromBytes(BinaryUtil.decodeBytes(in, len), target);
        }

        Type elementType = ReflectUtil.getElementType(target.getTargetType(), 0);
        if (targetClass.isArray() || VdlArray.class.isAssignableFrom(targetClass)) {
            int targetLen = len;
            if (target.getKind() == Kind.ARRAY) {
                if (len > target.getVdlType().getLength()) {
                    throw new ConversionException(actualType, target.getTargetType(),
                            "target array is too short");
                }
                targetLen = target.getVdlType().getLength();
            }
            Class<?> elementClass = ReflectUtil.getRawClass(elementType);
            Object array = Array.newInstance(elementClass, targetLen);
            for (int i = 0; i < len; i++) {
                ReflectUtil.setArrayValue(array, i,
                        readValue(actualType.getElem(), elementType), elementClass);
            }
            return ReflectUtil.createGeneric(target, array);
        } else {
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < len; i++) {
                list.add(readValue(actualType.getElem(), elementType));
            }
            return ReflectUtil.createGeneric(target, list);
        }
    }

    private Object readVdlBool(ConversionTarget target) throws IOException, ConversionException {
        return ReflectUtil.createPrimitive(target, BinaryUtil.decodeBoolean(in), Boolean.TYPE);
    }

    private Object readVdlByte(ConversionTarget target) throws IOException, ConversionException {
        return ConvertUtil.convertFromByte(BinaryUtil.decodeBytes(in, 1)[0], target);
    }

    private Object readVdlComplex(ConversionTarget target) throws IOException, ConversionException {
        return ConvertUtil.convertFromComplex(BinaryUtil.decodeDouble(in),
                BinaryUtil.decodeDouble(in), target);
    }

    private Object readVdlEnum(VdlType actualType, ConversionTarget target) throws IOException,
            ConversionException {
        int enumIndex = (int) BinaryUtil.decodeUint(in);
        byte[] bytes = actualType.getLabels().get(enumIndex).getBytes(BinaryUtil.UTF8_CHARSET);
        return ConvertUtil.convertFromBytes(bytes, target);
    }

    private Object readVdlFloat(ConversionTarget target) throws IOException, ConversionException {
        return ConvertUtil.convertFromDouble(BinaryUtil.decodeDouble(in), target);
    }

    private Object readVdlInt(ConversionTarget target) throws IOException, ConversionException {
        return ConvertUtil.convertFromInt(BinaryUtil.decodeInt(in), target);
    }

    private Type getMapElemOrStructFieldType(ConversionTarget target, Object key)
            throws ConversionException {
        Class<?> targetClass = target.getTargetClass();
        if (target.getKind() == Kind.MAP) {
            return ReflectUtil.getElementType(target.getTargetType(), 1);
        } else if (target.getKind() == Kind.SET) {
            return Boolean.class;
        } else if (targetClass == VdlStruct.class) {
            return VdlValue.class;
        } else {
            String fieldName = (String) key;
            for (Field field : targetClass.getDeclaredFields()) {
                SerializedName name = field.getAnnotation(SerializedName.class);
                if (name != null && name.value().equals(fieldName)) {
                    return field.getGenericType();
                }
            }
            return VdlValue.class;
        }
    }

    @SuppressWarnings("unchecked")
    private void setMapElemOrStructField(ConversionTarget target, Object data, Object key,
            Object elem, Type elemType) throws ConversionException {
        if (target.getKind() == Kind.MAP) {
            ((Map<Object, Object>) data).put(key, elem);
        } else if (target.getKind() == Kind.SET) {
            if ((Boolean) elem) {
                ((Set<Object>) data).add(key);
            }
        } else if (data instanceof VdlStruct) {
            ((VdlStruct) data).assignField((String) key, (VdlValue) elem);
        } else {
            if (elemType == VdlValue.class) {
                // no such field, just skip it
                return;
            }
            try {
                data.getClass().getDeclaredMethod("set" + (String) key,
                        ReflectUtil.getRawClass(elemType)).invoke(data, elem);
            } catch (Exception e) {
                throw new ConversionException("Can't set field " + key + " to " + elem + " of "
                        + target.getTargetType() + " : " + e.getMessage());
            }
        }
    }

    private Object createMapOrSetOrStruct(ConversionTarget target) throws ConversionException {
        if (target.getKind() == Kind.MAP) {
            return ReflectUtil.createGeneric(target, new HashMap<Object, Object>());
        } else if (target.getKind() == Kind.SET) {
            return ReflectUtil.createGeneric(target, new HashSet<Object>());
        } else {
            return ReflectUtil.createStruct(target);
        }
    }

    private Type getTargetKeyType(ConversionTarget target) throws ConversionException {
        if (target.getKind() == Kind.MAP || target.getKind() == Kind.SET) {
            return ReflectUtil.getElementType(target.getTargetType(), 0);
        } else {
            return String.class;
        }
    }

    private Object readVdlMapOrSet(VdlType actualType, ConversionTarget target)
            throws IOException, ConversionException {
        Object data = createMapOrSetOrStruct(target);
        Type targetKeyType = getTargetKeyType(target);
        int len = (int) BinaryUtil.decodeUint(in);
        for (int i = 0; i < len; i++) {
            Object key = readValue(actualType.getKey(), targetKeyType);
            Type targetElemType = getMapElemOrStructFieldType(target, key);
            Object elem;
            if (actualType.getKind() == Kind.SET) {
                elem = ReflectUtil.createPrimitive(new ConversionTarget(targetElemType),
                        true, Boolean.TYPE);
            } else {
                elem = readValue(actualType.getElem(), targetElemType);
            }
            setMapElemOrStructField(target, data, key, elem, targetElemType);
        }
        return data;
    }

    private Object readVdlStruct(VdlType actualType, ConversionTarget target)
            throws IOException, ConversionException {
        Object data = createMapOrSetOrStruct(target);
        Type targetKeyType = getTargetKeyType(target);
        while (true) {
            int index = (int) BinaryUtil.decodeUint(in);
            if (index == 0) {
                break;
            }
            VdlStructField field = actualType.getFields().get(index - 1);
            Type targetElemType = getMapElemOrStructFieldType(target, field.getName());
            Object key = ConvertUtil.convertFromBytes(BinaryUtil.getBytes(field.getName()),
                    new ConversionTarget(targetKeyType));
            Object elem = readValue(field.getType(), targetElemType);
            setMapElemOrStructField(target, data, key, elem, targetElemType);
        }
        return data;
    }
    private Object readVdlOneOf(ConversionTarget target) throws IOException, ConversionException {
        VdlOneOf result = ReflectUtil.createOneOf(target);
        TypeID typeId = new TypeID(BinaryUtil.decodeUint(in));
        if (typeId.getValue() == 0) {
            return result;
        }
        VdlType elemType = getType(typeId);
        Type elemReflectType = null;
        try {
            @SuppressWarnings("unchecked")
            List<TypeToken<?>> types =
                    (List<TypeToken<?>>) target.getTargetClass().getField("TYPES").get(null);
            for (TypeToken<?> typeToken : types) {
                Type type = typeToken.getType();
                if (Types.getVdlTypeFromReflect(typeToken.getType()).equals(elemType)) {
                    elemReflectType = type;
                    break;
                }
            }
        } catch (Exception e) {
            // fall through
        }
        if (elemReflectType != null) {
            return result.assignValue(elemReflectType,
                    (Serializable) readValue(elemType, elemReflectType));
        } else {
            return result.assignValue((VdlValue) readValue(elemType, VdlValue.class));
        }
    }

    private Object readVdlString(ConversionTarget target) throws IOException, ConversionException {
        int len = (int) BinaryUtil.decodeUint(in);
        byte[] bytes = BinaryUtil.decodeBytes(in, len);
        return ConvertUtil.convertFromBytes(bytes, target);
    }

    private Object readVdlUint(ConversionTarget target) throws IOException, ConversionException {
        return ConvertUtil.convertFromUint(BinaryUtil.decodeUint(in), target);
    }

    private Object readVdlTypeObject() throws IOException {
        return new VdlTypeObject(getType(new TypeID(BinaryUtil.decodeUint(in))));
    }

    /**
     * Builds VdlType from wire type.
     */
    private final class WireToVdlTypeBuilder {
        private final Builder builder;
        private final Map<TypeID, PendingType> pendingTypes;

        public WireToVdlTypeBuilder() {
            builder = new Builder();
            pendingTypes = new HashMap<TypeID, PendingType>();
        }

        public void build() {
            builder.build();
            for (Map.Entry<TypeID, PendingType> entry : pendingTypes.entrySet()) {
                BinaryDecoder.this.decodedTypes.put(entry.getKey(), entry.getValue().built());
            }
        }

        public PendingType lookupOrBuildPending(TypeID typeId) throws CorruptVomStreamException {
            PendingType vdlType = lookupType(typeId);
            if (vdlType != null) {
                return vdlType;
            }
            return buildPendingType(typeId);
        }

        private PendingType lookupType(TypeID typeId) {
            VdlType type = BinaryDecoder.this.lookupType(typeId);
            if (type != null) {
                return builder.builtPendingFromType(type);
            } else if (pendingTypes.containsKey(typeId)) {
                return pendingTypes.get(typeId);
            }
            return null;
        }

        private PendingType buildPendingType(TypeID typeId) throws CorruptVomStreamException {
            VdlValue wireType = BinaryDecoder.this.wireTypes.get(typeId);
            if (wireType == null) {
                throw new CorruptVomStreamException("Unknown wire type " + typeId);
            }
            PendingType pending = builder.newPending();
            pendingTypes.put(typeId, pending);

            if (wireType instanceof WireNamed) {
                WireNamed wireNamed = (WireNamed) wireType;
                return pending.setName(wireNamed.getName())
                        .assignBase(lookupOrBuildPending(wireNamed.getBase()));
            } else if (wireType instanceof WireArray) {
                WireArray wireArray = (WireArray) wireType;
                return pending.setName(wireArray.getName()).setKind(Kind.ARRAY)
                        .setLength((int) wireArray.getLen().getValue())
                        .setElem(lookupOrBuildPending(wireArray.getElem()));
            } else if (wireType instanceof WireEnum) {
                WireEnum wireEnum = (WireEnum) wireType;
                pending.setName(wireEnum.getName()).setKind(Kind.ENUM);
                for (String label : wireEnum.getLabels()) {
                    pending.addLabel(label);
                }
                return pending;
            } else if (wireType instanceof WireList) {
                WireList wireList= (WireList) wireType;
                return pending.setName(wireList.getName()).setKind(Kind.LIST)
                        .setElem(lookupOrBuildPending(wireList.getElem()));
            } else if (wireType instanceof WireMap) {
                WireMap wireMap = (WireMap) wireType;
                return pending.setName(wireMap.getName()).setKind(Kind.MAP)
                        .setKey(lookupOrBuildPending(wireMap.getKey()))
                        .setElem(lookupOrBuildPending(wireMap.getElem()));
            } else if (wireType instanceof WireOneOf) {
                WireOneOf wireOneOf = (WireOneOf) wireType;
                pending.setName(wireOneOf.getName()).setKind(Kind.ONE_OF);
                for (TypeID oneOfTypeId : wireOneOf.getTypes()) {
                    pending.addType(lookupOrBuildPending(oneOfTypeId));
                }
                return pending;
            } else if (wireType instanceof WireSet) {
                WireSet wireSet = (WireSet) wireType;
                return pending.setName(wireSet.getName()).setKind(Kind.SET)
                        .setKey(lookupOrBuildPending(wireSet.getKey()));
            } else if (wireType instanceof WireStruct) {
                WireStruct wireStruct = (WireStruct) wireType;
                pending.setName(wireStruct.getName()).setKind(Kind.STRUCT);
                for (WireField field : wireStruct.getFields()) {
                    pending.addField(field.getName(), lookupOrBuildPending(field.getType()));
                }
                return pending;
            } else {
                throw new CorruptVomStreamException("Unknown wire type: " + wireType.vdlType());
            }
        }
    }
}