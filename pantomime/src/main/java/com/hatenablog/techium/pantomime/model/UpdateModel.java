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
package com.hatenablog.techium.pantomime.model;

import android.content.ContentValues;
import android.net.Uri;

import com.hatenablog.techium.pantomime.util.ContentValuesUtil;

import java.util.HashMap;

public class UpdateModel extends FileModel {

    public static final String NAME = "update";

    private String selection;

    private String[] selectionArgs;

    private HashMap<String, Object> values;

    private int rows;

    private UpdateModel(Builder builder) {
        super(builder.uri);
        selection = builder.selection;
        selectionArgs = builder.selectionArgs;
        values = builder.values;
        rows = builder.rows;
    }

    public String getName() {
        return NAME;
    }

    public String getSelection() {
        return selection;
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public ContentValues getValues() {
        return ContentValuesUtil.toContentValues(values);
    }

    public int getRows() {
        return rows;
    }

    public static class Builder {

        private String uri;

        private String selection;

        private String[] selectionArgs;

        private HashMap<String, Object> values;

        private int rows;

        public Builder() {
        }

        public Builder uri(Uri uri) {
            this.uri = uri.toString();
            return this;
        }

        public Builder selection(String selection) {
            this.selection = selection;
            return this;
        }

        public Builder selectionArgs(String[] selectionArgs) {
            this.selectionArgs = selectionArgs;
            return this;
        }

        public Builder values(ContentValues values) {
            this.values = ContentValuesUtil.toHashMap(values);
            return this;
        }

        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public UpdateModel build() {
            return new UpdateModel(this);
        }
    }

}
