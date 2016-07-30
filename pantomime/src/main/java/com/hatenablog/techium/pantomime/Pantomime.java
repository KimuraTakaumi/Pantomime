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
package com.hatenablog.techium.pantomime;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.hatenablog.techium.pantomime.model.DeleteModel;
import com.hatenablog.techium.pantomime.model.FileManager;
import com.hatenablog.techium.pantomime.model.FileModel;
import com.hatenablog.techium.pantomime.model.GetTypeModel;
import com.hatenablog.techium.pantomime.model.InsertModel;
import com.hatenablog.techium.pantomime.model.QueryModel;
import com.hatenablog.techium.pantomime.model.UpdateModel;
import com.hatenablog.techium.pantomime.util.BuildConfigUtil;
import com.hatenablog.techium.pantomime.util.ContentValuesUtil;
import com.hatenablog.techium.pantomime.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;

public class Pantomime {

    private static Pantomime sInstance;

    private State mState = State.DISABLE;

    private FileManager mFileManager;

    private boolean mIsDebug;

    private Pantomime() {
        mFileManager = new FileManager();
    }

    public static Pantomime getInstance() {
        if (sInstance == null) {
            sInstance = new Pantomime();
        }
        return sInstance;
    }

    public Pantomime register(Context context){
        mIsDebug = BuildConfigUtil.isDebug(context);
        return this;
    }

    public Pantomime unregister(Context content) {
        mIsDebug = false;
        return this;
    }

    public Pantomime record(String path) {
        if (mState != State.DISABLE) {
            throw new IllegalStateException("Pantomime state run DISABLE state. This state is " + mState);
        }
        if (mIsDebug) {
            mState = State.RECORDING;
            mFileManager.setPath(path);
        }
        return this;
    }

    public Pantomime start(String path) {
        if (mState != State.DISABLE) {
            throw new IllegalStateException("Pantomime state run DISABLE state. This state is " + mState);
        }
        if (mIsDebug) {
            mState = State.PANTOMIME;
            mFileManager.setPath(path);
        }
        return this;
    }

    public Pantomime stop() {
        if (mIsDebug) {
            if (mState == State.DISABLE) {
                throw new IllegalStateException("Pantomime state run other DISABLE state. This state is " + mState);
            }
            mState = State.DISABLE;
        }
        return this;
    }

    public State getState() {
        return mState;
    }

    public Cursor query(ContentResolver resolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (mState) {
            case DISABLE: {
                return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
            }
            case RECORDING: {
                Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
                FileModel model = new QueryModel.Builder()
                        .uri(uri)
                        .projection(projection)
                        .selection(selection)
                        .selectionArgs(selectionArgs)
                        .sortOrder(sortOrder)
                        .curor(cursor).build();
                try {
                    mFileManager.save(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                cursor.moveToFirst();
                return cursor;
            }
            case PANTOMIME: {
                try {
                    FileModel model = getMeetQuery(mFileManager.load(QueryModel.NAME, uri.toString()),
                            uri, projection, selection, selectionArgs, sortOrder);
                    return ((QueryModel) model).getCursor();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            default:
                break;
        }
        return null;
    }

    public String getType(ContentResolver resolver, Uri uri) {
        switch (mState) {
            case DISABLE: {
                return resolver.getType(uri);
            }
            case RECORDING: {
                String mime = resolver.getType(uri);
                FileModel model = new GetTypeModel.Builder()
                        .uri(uri)
                        .mimeType(mime)
                        .build();
                try {
                    mFileManager.save(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return mime;
            }
            case PANTOMIME: {
                try {
                    FileModel model = getMeetGetType(mFileManager.load(GetTypeModel.NAME,
                            uri.toString()), uri);
                    return ((GetTypeModel) model).getMimeType();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            default:
                break;
        }
        return null;
    }

    public Uri insert(ContentResolver resolver, Uri uri, ContentValues values) {
        switch (mState) {
            case DISABLE:
                return resolver.insert(uri, values);
            case RECORDING: {
                Uri newUri = resolver.insert(uri, values);
                FileModel model = new InsertModel.Builder()
                        .uri(uri)
                        .newUri(newUri)
                        .values(values)
                        .build();
                try {
                    mFileManager.save(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return newUri;
            }
            case PANTOMIME: {
                try {
                    FileModel model = getMeetInsert(mFileManager.load(InsertModel.NAME,
                            uri.toString()), uri, values);
                    return ((InsertModel) model).getNewUri();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            default:
                break;
        }
        return null;
    }

    public int delete(ContentResolver resolver, Uri uri, String selection, String[] selectionArgs) {
        switch (mState) {
            case DISABLE:
                return resolver.delete(uri, selection, selectionArgs);
            case RECORDING: {
                int rows = resolver.delete(uri, selection, selectionArgs);
                FileModel model = new DeleteModel.Builder()
                        .uri(uri)
                        .selection(selection)
                        .selectionArgs(selectionArgs)
                        .rows(rows)
                        .build();
                try {
                    mFileManager.save(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
                return rows;
            }
            case PANTOMIME: {
                try {
                    FileModel model = getMeetDelete(mFileManager.load(DeleteModel.NAME,
                            uri.toString()), uri, selection, selectionArgs);
                    return ((DeleteModel) model).getRows();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            default:
                break;
        }
        return 0;
    }

    public int update(ContentResolver resolver, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (mState) {
            case DISABLE:
                return resolver.update(uri, values, selection, selectionArgs);
            case RECORDING: {
                int rows = resolver.update(uri, values, selection, selectionArgs);
                FileModel model = new UpdateModel.Builder()
                        .uri(uri)
                        .values(values)
                        .selection(selection)
                        .selectionArgs(selectionArgs)
                        .rows(rows)
                        .build();
                try {
                    mFileManager.save(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
                return rows;
            }
            case PANTOMIME: {
                try {
                    FileModel model = getMeetUpdate(mFileManager.load(UpdateModel.NAME,
                            uri.toString()), uri, values, selection, selectionArgs);
                    return ((UpdateModel) model).getRows();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            default:
                break;
        }
        return 0;
    }

    private FileModel getMeetQuery(ArrayList<FileModel> models, Uri uri, String[] projection,
                                   String selection, String[] selectionArgs, String sortOrder) {
        for (FileModel model : models) {
            QueryModel query = (QueryModel) model;

            if (!StringUtil.compare(query.getUri(), uri.toString())) {
                continue;
            }

            if (!StringUtil.compareArray(query.getProjection(), projection)) {
                continue;
            }

            if (!StringUtil.compare(query.getSelection(), selection)) {
                continue;
            }

            if (!StringUtil.compareArray(query.getSelectionArgs(), selectionArgs)) {
                continue;
            }

            if (!StringUtil.compare(query.getSortOrder(), sortOrder)) {
                continue;
            }

            return model;
        }

        return null;
    }

    private FileModel getMeetGetType(ArrayList<FileModel> models, Uri uri) {
        for (FileModel model : models) {
            GetTypeModel getType = (GetTypeModel) model;

            if (!StringUtil.compare(getType.getUri(), uri.toString())) {
                continue;
            }

            return model;
        }

        return null;
    }

    private FileModel getMeetInsert(ArrayList<FileModel> models, Uri uri, ContentValues values) {
        for (FileModel model : models) {
            InsertModel insert = (InsertModel) model;

            if (!StringUtil.compare(insert.getUri(), uri.toString())) {
                continue;
            }

            if (!ContentValuesUtil.compare(insert.getValues(), values)) {
                continue;
            }

            return model;
        }

        return null;
    }

    private FileModel getMeetDelete(ArrayList<FileModel> models, Uri uri, String selection,
                                    String[] selectionArgs) {
        for (FileModel model : models) {
            DeleteModel delete = (DeleteModel) model;

            if (!StringUtil.compare(delete.getUri(), uri.toString())) {
                continue;
            }

            if (!StringUtil.compare(delete.getSelection(), selection)) {
                continue;
            }

            if (!StringUtil.compareArray(delete.getSelectionArgs(), selectionArgs)) {
                continue;
            }

            return model;
        }

        return null;

    }

    private FileModel getMeetUpdate(ArrayList<FileModel> models, Uri uri, ContentValues values,
                                    String selection, String[] selectionArgs) {
        for (FileModel model : models) {
            UpdateModel update = (UpdateModel) model;

            if (!StringUtil.compare(update.getUri(), uri.toString())) {
                continue;
            }

            if (!ContentValuesUtil.compare(update.getValues(), values)) {
                continue;
            }

            if (!StringUtil.compare(update.getSelection(), selection)) {
                continue;
            }

            if (!StringUtil.compareArray(update.getSelectionArgs(), selectionArgs)) {
                continue;
            }

            return model;
        }

        return null;
    }

    public enum State {
        DISABLE,
        RECORDING,
        PANTOMIME
    }
}
