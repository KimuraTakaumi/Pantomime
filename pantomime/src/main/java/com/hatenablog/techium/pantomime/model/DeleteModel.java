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
