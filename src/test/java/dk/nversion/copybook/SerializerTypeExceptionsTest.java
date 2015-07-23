package dk.nversion.copybook;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SerializerTypeExceptionsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("serialized bytes are to long");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeInteger.class);
        fieldTypeInteger test = new fieldTypeInteger();
        test.field = 100;
        byte[] testBytes = serializer.serialize(test);
    }

    @org.junit.Test
    public void testRightFieldTypeString() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("serialized bytes are to long");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeString.class);
        fieldTypeString test = new fieldTypeString();
        test.field = "ok1234";
        byte[] testBytes = serializer.serialize(test);
    }

    @org.junit.Test
    public void testRightFieldTypeDecimal() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("serialized bytes are to long");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeDecimal.class);
        fieldTypeDecimal test = new fieldTypeDecimal();
        test.field = new BigDecimal("100.01");
        byte[] testBytes = serializer.serialize(test);
    }
}