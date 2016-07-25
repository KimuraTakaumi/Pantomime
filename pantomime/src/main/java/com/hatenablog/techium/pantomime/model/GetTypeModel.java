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

public class GetTypeModel extends FileModel {

    public static final String NAME = "get_type";

    private String mimeType;

    private GetTypeModel(Builder builder) {
        super(builder.uri);
        mimeType = builder.mimeType;
    }

    public String getName() {
        return NAME;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static class Builder {

        private String uri;

        private String mimeType;

        public Builder() {
        }

        public Builder uri(Uri uri) {
            this.uri = uri.toString();
            return this;
        }

        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public GetTypeModel build() {
            return new GetTypeModel(this);
        }
    }

}
