package io.veyron.veyron.veyron2.vom2;

import io.veyron.veyron.veyron2.vdl.VdlArray;
import io.veyron.veyron.veyron2.vdl.VdlComplex128;
import io.veyron.veyron.veyron2.vdl.VdlComplex64;
import io.veyron.veyron.veyron2.vdl.VdlEnum;
import io.veyron.veyron.veyron2.vdl.VdlList;
import io.veyron.veyron.veyron2.vdl.VdlMap;
import io.veyron.veyron.veyron2.vdl.VdlSet;
import io.veyron.veyron.veyron2.vdl.VdlType;
import io.veyron.veyron.veyron2.vdl.VdlValue;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * ReflectUtil provides helpers to get object properties and create class instances from reflection.
 */
final class ReflectUtil {
    /**
     * Creates an instance of java primitives, one of boolean, byte, short, int, long, float,
     * double, String. Handles java types and VDL types.
     *
     * @param target the target containing java class and VDL type information
     * @param value the value of primitive to be created
     * @return an instance of VDL primitive containing the provided value if the target class
     *         is inherited from {@code VdlValue}; returns provided value otherwise
     * @throws ConversionException if the instance of the target class can't be created
     */
    static Object createPrimitive(ConversionTarget target, Object value,
            Class<?> valueType) throws ConversionException {
        Class<?> targetClass = target.getTargetClass();
        try {
            if (targetClass.getSuperclass() == VdlValue.class) {
                return targetClass.getConstructor(VdlType.class, valueType)
                        .newInstance(target.getVdlType(), value);
            } else if (VdlValue.class.isAssignableFrom(targetClass)) {
                return targetClass.getConstructor(valueType).newInstance(value);
            } else {
                return value;
            }
        } catch (Exception e) {
            throw new ConversionException(value, targetClass, e.getMessage());
        }
    }

    /**
     * Creates an instance of VDL complex. The target class should be inherited from
     * {@code VdlValue}.
     */
    static VdlValue createComplex(ConversionTarget target, double real, double imag)
            throws ConversionException {
        Class<?> targetClass = target.getTargetClass();
        try {
            if (targetClass == VdlComplex64.class) {
                return new VdlComplex64(target.getVdlType(), (float) real, (float) imag);
            } else if (targetClass == VdlComplex128.class) {
                return new VdlComplex128(target.getVdlType(), real, imag);
            } else if (VdlComplex64.class.isAssignableFrom(targetClass)) {
                return (VdlValue) targetClass.getConstructor(Float.TYPE, Float.TYPE)
                        .newInstance((float) real, (float) imag);
            } else if (VdlComplex128.class.isAssignableFrom(targetClass)) {
                return (VdlValue) targetClass.getConstructor(Double.TYPE, Double.TYPE)
                        .newInstance(real, imag);
            }
        } catch (Exception e) {
            throw new ConversionException(
                    new VdlComplex128(real, imag), targetClass, e.getMessage());
        }
        throw new ConversionException(new VdlComplex128(real, imag), targetClass);
    }

    /**
     * Creates an instance of VDL enum. The target class should be inherited from {@code VdlEnum}.
     */
    static VdlEnum createEnum(ConversionTarget target, String label)
            throws ConversionException {
        Class<?> targetClass = target.getTargetClass();
        if (targetClass == VdlEnum.class) {
            return new VdlEnum(target.getVdlType(), label);
        }
        try {
            return (VdlEnum) targetClass.getMethod("valueOf", String.class)
                    .invoke(null, label);
        } catch (Exception e) {
            throw new ConversionException(label, targetClass, e.getMessage());
        }
    }

    private static Object createNamedGeneric(Class<?> targetClass, Object impl)
            throws ConversionException {
        try {
            if (VdlArray.class.isAssignableFrom(targetClass)) {
                return targetClass.getConstructor(impl.getClass()).newInstance(impl);
            } else if (VdlList.class.isAssignableFrom(targetClass)) {
                return targetClass.getConstructor(List.class).newInstance(impl);
            } else if (VdlMap.class.isAssignableFrom(targetClass)) {
                return targetClass.getConstructor(Map.class).newInstance(impl);
            } else if (VdlSet.class.isAssignableFrom(targetClass)) {
                return targetClass.getConstructor(Set.class).newInstance(impl);
            }
        } catch (Exception e) {
            throw new ConversionException(impl, targetClass, e.getMessage());
        }
        throw new ConversionException(impl, targetClass);
     }

    /**
     * Creates an instance of generic type, one of array, list, map, set.
     *
     * @param target the target containing java class and VDL type information
     * @param impl the implementation of generic type to be created
     * @return an instance of VDL generic containing wrapped around the provided implementation
     *         object if the target class is inherited from {@code VdlValue}; returns provided
     *         implementation object otherwise
     * @throws ConversionException if the instance of the target class can't be created
     */
    @SuppressWarnings("unchecked")
    static Object createGeneric(ConversionTarget target, Object impl)
            throws ConversionException {
        Class<?> targetClass = target.getTargetClass();
        if (targetClass == VdlArray.class) {
            return new VdlArray<Object>(target.getVdlType(), (Object[]) impl);
        } else if (targetClass == VdlList.class) {
            return new VdlList<Object>(target.getVdlType(), (List<Object>) impl);
        } else if (targetClass == VdlSet.class) {
            return new VdlSet<Object>(target.getVdlType(), (Set<Object>) impl);
        } else if (targetClass == VdlMap.class) {
            return new VdlMap<Object, Object>(target.getVdlType(), (Map<Object, Object>) impl);
        } else if (VdlValue.class.isAssignableFrom(targetClass)) {
            return createNamedGeneric(targetClass, impl);
        } else {
            return impl;
        }
    }

    /**
     * Returns a {@code Class} object that is represented by provided {@code Type} object.
     */
    static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Class<?> component = getRawClass(((GenericArrayType) type).getGenericComponentType());
            return Array.newInstance(component, 0).getClass();
        } else {
            return null;
        }
    }

    /**
     * Returns types of elements for provided generic or array type.
     *
     * @param type the generic type
     * @returns an array of element types; the returned array is empty if the provided type
     *          is not generic or array
     */
    static Type[] getElementTypes(Type type) {
        Type[] types;
        if (type instanceof Class) {
            Class<?> klass = (Class<?>) type;
            if (klass.isArray()) {
                types = new Type[1];
                types[0] = klass.getComponentType();
                return types;
            } else {
                return getElementTypes(klass.getGenericSuperclass());
            }
        } else if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        } else if (type instanceof GenericArrayType) {
            types = new Type[1];
            types[0] = ((GenericArrayType) type).getGenericComponentType();
            return types;
        }
        return new Type[0];
    }

    /**
     * Sets the value of the indexed element of the specified array object to the specified value.
     *
     * @param array the array
     * @param index the index into the array
     * @param value new value of the indexed element
     * @param elementClass the class of elements in the array
     */
    static void setArrayValue(Object array, int index, Object value, Class<?> elementClass) {
        if (elementClass == Boolean.TYPE) {
            Array.setBoolean(array, index, (Boolean) value);
        } else if (elementClass == Byte.TYPE) {
            Array.setByte(array, index, (Byte) value);
        } else if (elementClass == Double.TYPE) {
            Array.setDouble(array, index, (Double) value);
        } else if (elementClass == Float.TYPE) {
            Array.setFloat(array, index, (Float) value);
        } else if (elementClass == Integer.TYPE) {
            Array.setInt(array, index, (Integer) value);
        } else if (elementClass == Long.TYPE) {
            Array.setLong(array, index, (Long) value);
        } else if (elementClass == Short.TYPE) {
            Array.setShort(array, index, (Short) value);
        } else {
            Array.set(array, index, value);
        }
    }
}
