package webdriver;

public class BoolUtils {

    public static boolean getBoolValueByCondition(boolean condition){
        boolean bool = false;
        if (condition) {
            bool = Boolean.TRUE;
        }
        return bool;
    }
}
