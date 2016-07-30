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

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.HashMap;

public class CursorUtil {

    public static ArrayList<HashMap<String, Object>> toHashMap(Cursor cursor) {
        String[] names = cursor.getColumnNames();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();

            for (String name : names) {
                int index = cursor.getColumnIndex(name);
                int type = cursor.getType(index);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                        hashMap.put(name, cursor.getBlob(index));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        hashMap.put(name, cursor.getDouble(index));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        hashMap.put(name, cursor.getLong(index));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        hashMap.put(name, cursor.getString(index));
                        break;
                }
            }
            list.add(hashMap);
            cursor.moveToNext();
        }

        return list;
    }

    public static Cursor toCursor(ArrayList<HashMap<String, Object>> hashMapList) {
        String[] keys = hashMapList.get(0).keySet().toArray(new String[hashMapList.get(0).keySet().size()]);
        MatrixCursor cursor = new MatrixCursor(keys);
        cursor.moveToFirst();

        for (HashMap<String, Object> hashMap : hashMapList) {
            Object[] objects = new Object[keys.length];
            for (int i = 0; i < keys.length; i++) {
                objects[i] = hashMap.get(keys[i]);
            }
            cursor.addRow(objects);
        }
        return cursor;
    }

}
