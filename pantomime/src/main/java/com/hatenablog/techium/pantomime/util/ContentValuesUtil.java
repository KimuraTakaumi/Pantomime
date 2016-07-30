/**
 *    Copyright 2016 Kimura Takaumi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public static boolean compare(ContentValues src, ContentValues dest) {
        if (src == null && dest == null) {
            return true;
        }

        for (String str : src.keySet()) {
            Object object = dest.get(str);

            if (object == null) {
                return false;
            }

            if (!src.get(str).equals(object)) {
                return false;
            }
        }
        return true;
    }
}
