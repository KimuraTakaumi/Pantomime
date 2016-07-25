package com.hatenablog.techium.pantomime.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class FileModel {

    public static final String NAME = "File";

    private String uri;

    protected FileModel(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getFileName() throws UnsupportedEncodingException {
        String uri = URLEncoder.encode(getUri(), "utf-8");
        Date date = new Date();
        return getName() + "_" + date.getTime() + uri + ".json";
    }

    public String getName() {
        return NAME;
    }

}
