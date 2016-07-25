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
