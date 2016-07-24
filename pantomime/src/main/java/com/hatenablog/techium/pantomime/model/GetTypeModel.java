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
