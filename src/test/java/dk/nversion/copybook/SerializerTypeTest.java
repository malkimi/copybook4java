package dk.nversion.copybook;

import java.math.BigDecimal;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SerializerTypeTest {

    @CopyBook()
    static public class fieldTypeInteger {
        @CopyBookLine("01 FIELD PIC 9(2).")
        public int field;
    }

    @CopyBook()
    static public class fieldTypeString {
        @CopyBookLine("01 FIELD PIC X(2).")
        public String field;
    }

    @CopyBook()
    static public class fieldTypeDecimal {
        @CopyBookLine("01 FIELD PIC 9(2)V9(2).")
        public BigDecimal field;
    }

    @org.junit.Test
    public void testRightFieldTypeInteger() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeInteger.class);
        fieldTypeInteger test = new fieldTypeInteger();
        test.field = 10;
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[]{(byte) '1', (byte) '0'});
        fieldTypeInteger test2 = serializer.deserialize(testBytes, fieldTypeInteger.class);
        assertEquals(10, test2.field);
    }

    @org.junit.Test
    public void testRightFieldTypeString() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeString.class);
        fieldTypeString test = new fieldTypeString();
        test.field = "ok";
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[] { (byte)'o', (byte)'k'});
        fieldTypeString test2 = serializer.deserialize(testBytes, fieldTypeString.class);
        assertEquals("ok", test2.field);
    }

    @org.junit.Test
    public void testRightFieldTypeDecimal() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeDecimal.class);
        fieldTypeDecimal test = new fieldTypeDecimal();
        test.field = new BigDecimal("10.01");
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[] { (byte)'1', (byte)'0', (byte)'0', (byte)'1'});
        fieldTypeDecimal test2 = serializer.deserialize(testBytes, fieldTypeDecimal.class);
        assertEquals(new BigDecimal("10.01"), test2.field);
    }
}