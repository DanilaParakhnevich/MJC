import demo.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StringUtilsTest {
    @Test
    void isPositiveNumberTest(){
        assertTrue(StringUtils.isPositiveNumber("123"));
        assertFalse(StringUtils.isPositiveNumber("-123"));
        assertFalse(StringUtils.isPositiveNumber("0"));
        assertFalse(StringUtils.isPositiveNumber("asd"));
    }
}
