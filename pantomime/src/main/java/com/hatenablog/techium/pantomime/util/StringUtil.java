package com.hatenablog.techium.pantomime.util;

public class StringUtil {

    public static boolean compareArray(String[] src, String[] dest) {
        if (src == null && dest == null) {
            return true;
        }

        if (src.length != dest.length) {
            return false;
        }

        for (int i = 0; i < src.length; i++) {
            if (!src[i].equals(dest[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean compare(String src, String dest) {
        if (src == null && dest == null) {
            return true;
        }

        if (!src.equals(dest)) {
            return false;
        }

        return true;
    }

}
