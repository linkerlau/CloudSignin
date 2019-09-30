package signin.utils;

import java.io.UnsupportedEncodingException;

public class ConvertEncoding {

    public static String toGBK(String string) {
        try {
            return new String(string.getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
