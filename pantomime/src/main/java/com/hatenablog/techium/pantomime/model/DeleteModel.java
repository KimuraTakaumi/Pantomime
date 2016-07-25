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

import android.net.Uri;

public class DeleteModel extends FileModel {

    public static final String NAME = "delete";

    private String selection;

    private String[] selectionArgs;

    private int rows;

    private DeleteModel(Builder builder) {
        super(builder.uri);
        selection = builder.selection;
        selectionArgs = builder.selectionArgs;
        rows = builder.rows;
    }

    public String getName() {
        return NAME;
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public String getSelection() {
        return selection;
    }

    public int getRows() {
        return rows;
    }

    public static class Builder {

        private String uri;

        private String selection;

        private String[] selectionArgs;

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

        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public DeleteModel build() {
            return new DeleteModel(this);
        }
    }

}
