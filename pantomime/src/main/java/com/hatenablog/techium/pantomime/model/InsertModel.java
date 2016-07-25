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

public class InsertModel extends FileModel {

    public static final String NAME = "insert";

    private HashMap<String, Object> values;

    private String newUri;

    private InsertModel(Builder builder) {
        super(builder.uri);
        values = builder.values;
        newUri = builder.newUri;
    }

    public String getName() {
        return NAME;
    }

    public ContentValues getValues() {
        return ContentValuesUtil.toContentValues(values);
    }

    public Uri getNewUri() {
        return Uri.parse(newUri);
    }

    public static class Builder {

        private String uri;

        private HashMap<String, Object> values;

        private String newUri;

        public Builder() {
        }

        public Builder uri(Uri uri) {
            this.uri = uri.toString();
            return this;
        }

        public Builder values(ContentValues values) {
            this.values = ContentValuesUtil.toHashMap(values);
            return this;
        }

        public Builder newUri(Uri newUri) {
            this.newUri = newUri.toString();
            return this;
        }

        public InsertModel build() {
            return new InsertModel(this);
        }
    }

}
