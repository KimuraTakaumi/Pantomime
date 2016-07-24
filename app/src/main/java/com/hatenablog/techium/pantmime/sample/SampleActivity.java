package com.hatenablog.techium.pantmime.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hatenablog.techium.pantomime.Pantomime;

public class SampleActivity extends AppCompatActivity {

    private static int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(mOnClickListener);
        Pantomime.getInstance().record(Environment.getExternalStorageDirectory().getPath());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pantomime.getInstance().stop();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // 権限がない場合はリクエスト
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_STATE);
                    } else {
                        Cursor cursor = Pantomime.getInstance().query(
                                getContentResolver(),
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null);
                        showCursor(cursor);
                    }

                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_STATE == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 許可された
                Cursor cursor = Pantomime.getInstance().query(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                showCursor(cursor);
            } else {
                // 拒否された
                Toast.makeText(this, "拒否された", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showCursor(Cursor cursor){
        cursor.moveToFirst();
        Gson gson = new Gson();
        for(int i = 0; i < cursor.getCount(); i++){
            String[] names = cursor.getColumnNames();
            for(String name : names){
                int index = cursor.getColumnIndex(name);
                String value = "";
                switch (cursor.getType(index)){
                    case Cursor.FIELD_TYPE_BLOB:
                        value = gson.toJson(cursor.getBlob(index));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        value = gson.toJson(cursor.getDouble(index));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        value = gson.toJson(cursor.getLong(index));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        value = gson.toJson(cursor.getShort(index));
                        break;
                }
                Log.d("test", "key = " + name + ", value = " + value);
            }
            cursor.moveToNext();
        }
        cursor.close();
    }
}
