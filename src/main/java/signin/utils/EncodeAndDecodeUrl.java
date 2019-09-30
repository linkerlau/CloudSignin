package signin.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeAndDecodeUrl {

    public static String encode(String value, String charset) {
        try {
            return URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            System.err.println("cookie解码失败！");
            e.printStackTrace();
        }
        return "";
    }

    public static String decode(String value,  String charset) {
        try {
            return URLDecoder.decode(value, charset);
        } catch (UnsupportedEncodingException e) {
            System.err.println("cookie解码失败！");
            e.printStackTrace();
        }
        return "";
    }
}
