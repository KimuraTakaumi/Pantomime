package com.hatenablog.techium.pantomime.util;


import android.content.ContentValues;

import java.util.HashMap;

public class ContentValuesUtil {

    public static HashMap<String, Object> toHashMap(ContentValues values) {
        HashMap<String, Object> hashMap = new HashMap<>();

        for (String key : values.keySet()) {
            hashMap.put(key, values.get(key));
        }

        return hashMap;
    }

    public static ContentValues toContentValues(HashMap<String, Object> map) {
        ContentValues values = new ContentValues();
        for (String key : map.keySet()) {
            Object object = map.get(key);
            if (object instanceof Byte) {
                values.put(key, (Byte) object);
            } else if (object instanceof Integer) {
                values.put(key, (Integer) object);
            } else if (object instanceof Long) {
                values.put(key, (Long) object);
            } else if (object instanceof Short) {
                values.put(key, (Short) object);
            } else if (object instanceof Float) {
                values.put(key, (Float) object);
            } else if (object instanceof Double) {
                values.put(key, (Double) object);
            } else if (object instanceof Boolean) {
                values.put(key, (Boolean) object);
            } else if (object instanceof byte[]) {
                values.put(key, (byte[]) object);
            }
        }

        return values;
    }
}
