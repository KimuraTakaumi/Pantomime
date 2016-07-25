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

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManager {

    private String mPath;

    public void setPath(String path) {
        mPath = path;
    }

    public void save(FileModel model) throws IOException {
        Gson gson = new Gson();
        File file = new File(mPath + "/" + model.getFileName());

        if (file.exists()) {
            return;
        }

        FileWriter filewriter;

        filewriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(filewriter);
        PrintWriter pw = new PrintWriter(bw);
        pw.write(gson.toJson(model));
        pw.close();
    }

    public ArrayList<FileModel> load(final String name, String uri) throws IOException {
        Gson gson = new Gson();
        File directory = new File(mPath);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.startsWith(name);
            }
        };

        File[] files = directory.listFiles(filter);
        ArrayList<FileModel> models = new ArrayList<>();
        for (File file : files) {
            FileInputStream input = new FileInputStream(mPath + "/" + file.getName());
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // Json読み込み
            String json = new String(buffer);
            FileModel model;
            switch (name) {
                case InsertModel.NAME:
                    model = gson.fromJson(json, InsertModel.class);
                    break;
                case DeleteModel.NAME:
                    model = gson.fromJson(json, DeleteModel.class);
                    break;
                case QueryModel.NAME:
                    model = gson.fromJson(json, QueryModel.class);
                    break;
                case GetTypeModel.NAME:
                    model = gson.fromJson(json, GetTypeModel.class);
                    break;
                case UpdateModel.NAME:
                    model = gson.fromJson(json, UpdateModel.class);
                    break;
                default:
                    continue;
            }
            models.add(model);
        }

        return models;
    }
}
