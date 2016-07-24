package com.hatenablog.techium.pantomime;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.hatenablog.techium.pantomime.model.DeleteModel;
import com.hatenablog.techium.pantomime.model.FileManager;
import com.hatenablog.techium.pantomime.model.FileModel;
import com.hatenablog.techium.pantomime.model.GetTypeModel;
import com.hatenablog.techium.pantomime.model.InsertModel;
import com.hatenablog.techium.pantomime.model.QueryModel;
import com.hatenablog.techium.pantomime.model.UpdateModel;

import java.io.IOException;

public class Pantomime {

    private static Pantomime sInstance;

    private State mState = State.DISABLE;

    private FileManager mFileManager;

    private Pantomime() {
        mFileManager = new FileManager();
    }

    public static Pantomime getInstance() {
        if (sInstance == null) {
            sInstance = new Pantomime();
        }
        return sInstance;
    }

    public Pantomime record(String path) {
        if (mState != State.DISABLE) {
            throw new IllegalStateException("Pantomime state run DISABLE state. This state is " + mState);
        }
        if (BuildConfig.DEBUG) {
            mState = State.RECORDING;
            mFileManager.setPath(path);
        }
        return this;
    }

    public Pantomime start(String path) {
        if (mState != State.DISABLE) {
            throw new IllegalStateException("Pantomime state run DISABLE state. This state is " + mState);
        }
        if (BuildConfig.DEBUG) {
            mState = State.PANTOMIME;
            mFileManager.setPath(path);
        }
        return this;
    }

    public Pantomime stop() {
        if (BuildConfig.DEBUG) {
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
                    FileModel model = mFileManager.load(QueryModel.NAME, uri.toString());
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
                    FileModel model = mFileManager.load(GetTypeModel.NAME, uri.toString());
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
                    FileModel model = mFileManager.load(InsertModel.NAME, uri.toString());
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
                    FileModel model = mFileManager.load(DeleteModel.NAME, uri.toString());
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
                    FileModel model = mFileManager.load(UpdateModel.NAME, uri.toString());
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

    public enum State {
        DISABLE,
        RECORDING,
        PANTOMIME
    }
}
