package io.v.v23.vom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import io.v.v23.vdl.Types;
import io.v.v23.vdl.VdlArray;
import io.v.v23.vdl.VdlType;
import io.v.v23.vdl.VdlValue;
import io.v.v23.verror.VException;
import io.v.v23.vom.testdata.Constants;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryDecoderTest extends TestCase {
    private void assertEqual(Object expected, Object actual) {
        if (expected.getClass().isArray()) {
            Class<?> component = expected.getClass().getComponentType();
            if (component == Boolean.TYPE) {
                assertTrue(Arrays.equals((boolean[]) expected, (boolean[]) actual));
            } else if (component == Byte.TYPE) {
                assertTrue(Arrays.equals((byte[]) expected, (byte[]) actual));
            } else if (component == Short.TYPE) {
                assertTrue(Arrays.equals((short[]) expected, (short[]) actual));
            } else if (component == Integer.TYPE) {
                assertTrue(Arrays.equals((int[]) expected, (int[]) actual));
            } else if (component == Long.TYPE) {
                assertTrue(Arrays.equals((long[]) expected, (long[]) actual));
            } else if (component == Float.TYPE) {
                assertTrue(Arrays.equals((float[]) expected, (float[]) actual));
            } else if (component == Double.TYPE) {
                assertTrue(Arrays.equals((double[]) expected, (double[]) actual));
            } else {
                assertTrue(Arrays.equals((Object[]) expected, (Object[]) actual));
            }
        } else {
            assertEquals(expected, actual);
        }
    }

    public void testDecode() throws Exception {
        for (io.v.v23.vom.testdata.TestCase test : Constants.TESTS) {
            byte[] bytes = TestUtil.hexStringToBytes(test.getHex());
            Object value;
            if (test.getValue().getElem().getClass().isArray()) {
                value = TestUtil.decode(bytes, test.getValue().getElem().getClass());
            } else {
                value = TestUtil.decode(bytes);
            }
            assertEqual(test.getValue().getElem(), value);
        }
    }

    public void testDecodeEncode() throws Exception {
        for (io.v.v23.vom.testdata.TestCase test : Constants.TESTS) {
            byte[] bytes = TestUtil.hexStringToBytes(test.getHex());
            VdlValue value = (VdlValue) TestUtil.decode(bytes, VdlValue.class);
            assertEquals(test.getHex(), TestUtil.encode(value.vdlType(), value));
        }

        VdlType testsType = Types.getVdlTypeFromReflect(
                Constants.class.getDeclaredField("TESTS").getGenericType());
        String encoded = TestUtil.encode(testsType, Constants.TESTS);
        VdlValue decoded = (VdlValue) TestUtil.decode(
                TestUtil.hexStringToBytes(encoded));
        assertEquals(encoded, TestUtil.encode(decoded.vdlType(), decoded));
    }

    public void testDecodeVdlArray() throws Exception {
        VdlArray<Byte> v = new VdlArray<Byte>(Types.arrayOf(4, Types.BYTE), new Byte[]{1, 2, 3, 4});
        byte[] encoded = TestUtil.hexStringToBytes(TestUtil.encode(v));
        Object decoded = TestUtil.decode(encoded, VdlValue.class);
        assertNotNull(decoded);
    }

    public void testDecodeVException() throws Exception {
        final Serializable[] params = {
                1,
                "2",
                ImmutableList.<String>of("3"),
                ImmutableMap.<String, String>of("4", "")
        };
        final Type[] paramTypes = {
                Integer.class,
                String.class,
                new TypeToken<List<String>>(){}.getType(),
                new TypeToken<Map<String, String>>(){}.getType()
        };
        final VException v = new VException(new VException.IDAction(
                "io.v.v23.vom.Testing", VException.ActionCode.NO_RETRY),
                "1 2 [3] 4=", params, paramTypes);
        final byte[] encoded = TestUtil.hexStringToBytes(TestUtil.encode(VException.class, v));
        final Object decoded = TestUtil.decode(encoded);
        if (!v.deepEquals(decoded)) {
            fail(String.format("Expected error %s, got %s", v, decoded));
        }
    }

    public void testDecodeVExceptionBadParams() throws Exception {
        final Serializable[] params = {
                ImmutableList.<String>of("3"),
                ImmutableMap.<String, String>of("4", "")
        };
        final Type[] paramTypes = {
                List.class,
                Map.class
        };
        final VException v = new VException(new VException.IDAction(
                "io.v.v23.vom.Testing", VException.ActionCode.NO_RETRY),
                "1 2 [3] 4=", params, paramTypes);
        final byte[] encoded = TestUtil.hexStringToBytes(TestUtil.encode(VException.class, v));
        final Object decoded = TestUtil.decode(encoded);
        final VException expected = new VException(new VException.IDAction(
                "io.v.v23.vom.Testing", VException.ActionCode.NO_RETRY),
                "1 2 [3] 4=", null, new Type[0]);
        if (!expected.deepEquals(decoded)) {
            fail(String.format("Expected error %s, got %s", v, decoded));
        }
    }

    public void testDecodeEncodeVException() throws Exception {
        final Serializable[] params = {
                1,
                "2",
                ImmutableList.<String>of("3"),
                ImmutableMap.<String, String>of("4", "")
        };
        final Type[] paramTypes = {
                Integer.class,
                String.class,
                new TypeToken<List<String>>(){}.getType(),
                new TypeToken<Map<String, String>>(){}.getType()
        };
        final VException v = new VException(new VException.IDAction(
                "io.v.v23.vom.Testing", VException.ActionCode.NO_RETRY),
                "1 2 [3] 4=", params, paramTypes);
        final byte[] encoded = TestUtil.hexStringToBytes(TestUtil.encode(VException.class, v));
        final Object decoded = TestUtil.decode(encoded);
        if (!(decoded instanceof VException)) {
            fail(String.format("Decoded into %s, wanted %s", decoded.getClass(), VException.class));
        }
        final VException decodedV = (VException) decoded;
        final byte[] reEncoded = TestUtil.hexStringToBytes(
                TestUtil.encode(VException.class, decodedV));
        assertEquals(Arrays.toString(encoded), Arrays.toString(reEncoded));
    }

    private void assertDecodeEncode(Object value) throws Exception {
        final byte[] encoded = TestUtil.hexStringToBytes(TestUtil.encode(value.getClass(), value));
        final Object decoded = TestUtil.decode(encoded);
        assertEquals(value, decoded);
    }

    public void testDecodeEncodeTime() throws Exception {
        assertDecodeEncode(new DateTime(2015, 2, 18, 20, 34, 35, 997, DateTimeZone.UTC));
        assertDecodeEncode(new Duration(239017));
    }
}