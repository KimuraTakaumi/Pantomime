package com.hatenablog.techium.pantomime.model;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

public class FileManager {

    private String mPath;

    public void setPath(String path) {
        mPath = path;
    }

    public void save(FileModel model) throws IOException {
        Gson gson = new Gson();
        File file = new File(mPath + "/" + model.getFileName());
        FileWriter filewriter;

        filewriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(filewriter);
        PrintWriter pw = new PrintWriter(bw);
        pw.write(gson.toJson(model));
        pw.close();
    }

    public FileModel load(String name, String uri) throws IOException {
        Gson gson = new Gson();
        String strUri = URLEncoder.encode(uri, "utf-8");
        String fileName = name + "_" + strUri + ".json";

        FileInputStream input = new FileInputStream(mPath + "/" + fileName);
        int size = input.available();
        byte[] buffer = new byte[size];
        input.read(buffer);
        input.close();

        // Json読み込み
        String json = new String(buffer);

        switch (name) {
            case InsertModel.NAME:
                return gson.fromJson(json, InsertModel.class);
            case DeleteModel.NAME:
                return gson.fromJson(json, DeleteModel.class);
            case QueryModel.NAME:
                return gson.fromJson(json, QueryModel.class);
            case GetTypeModel.NAME:
                return gson.fromJson(json, GetTypeModel.class);
            case UpdateModel.NAME:
                return gson.fromJson(json, UpdateModel.class);
            default:
                break;
        }

        return null;
    }
}
