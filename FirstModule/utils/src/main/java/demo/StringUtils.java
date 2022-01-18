package demo;

public class StringUtils {
    private StringUtils(){}

    public static boolean isPositiveNumber(String str) {
        return  str != null &&
                !str.equals("0") &&
                org.apache.commons.lang3.StringUtils.isNumeric(str)
                && org.apache.commons.lang3.StringUtils.containsNone("-");
    }
}
