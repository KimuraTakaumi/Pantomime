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

import android.database.Cursor;
import android.net.Uri;

import com.hatenablog.techium.pantomime.util.CursorUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryModel extends FileModel {

    public static final String NAME = "query";

    private String[] projection;

    private String selection;

    private String[] selectionArgs;

    private String sortOrder;

    private ArrayList<HashMap<String, Object>> cursor;

    private QueryModel(Builder builder) {
        super(builder.uri);
        projection = builder.projection;
        selection = builder.selection;
        selectionArgs = builder.selectionArgs;
        sortOrder = builder.sortOrder;
        cursor = builder.cursor;
    }

    public String getName() {
        return NAME;
    }

    public String[] getProjection() {
        return projection;
    }

    public String getSelection() {
        return selection;
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public Cursor getCursor() {
        return CursorUtil.toCursor(cursor);
    }

    public static class Builder {

        private String uri;

        private String[] projection;

        private String selection;

        private String[] selectionArgs;

        private String sortOrder;

        private ArrayList<HashMap<String, Object>> cursor;

        public Builder() {
        }

        public Builder uri(Uri uri) {
            this.uri = uri.toString();
            return this;
        }

        public Builder projection(String[] projection) {
            this.projection = projection;
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

        public Builder sortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder curor(Cursor cursor) {
            this.cursor = CursorUtil.toHashMap(cursor);
            return this;
        }

        public QueryModel build() {
            return new QueryModel(this);
        }
    }

}
