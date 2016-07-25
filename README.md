# Pantomime

# 使い方

PantomimeはAndroidのContentProviderに保存されているデータにアクセスしたデータをスキミングし保存します。
そして保存したデータをPantomimeモードで実行した時にそのスキミングデータをContentProviderの代わりに返却します。

下記のようにrecordを呼び出すとContentProviderへのアクセス時にデータをスキミングします。

```
Pantomime.getInstance().record("スキミングデータを保存するディレクトリパス");
```

止めるには下記のようにstopを呼び出します。

```
Pantomime.getInstance().stop();
```

ContentProviderを利用しないでスキミングデータを利用する場合は次のようにします。

```
Pantomime.getInstance().start("スキミングデータを保存するディレクトリパス");
```

ContentProviderへqueryでアクセスする場合は次のように実施します。
[例：query]
```
Pantomime.getInstance().query(
                getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
```

Pantomimeには次のメソッドが用意されています。
```
Cursor query(ContentResolver resolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);
```

# 制限事項

下記のAPIは開発中です。

```
String getType(ContentResolver resolver, Uri uri);
Uri insert(ContentResolver resolver, Uri uri, ContentValues values);
int delete(ContentResolver resolver, Uri uri, String selection, String[] selectionArgs);
int update(ContentResolver resolver, Uri uri, ContentValues values, String selection, String[] selectionArgs);
```

# License

Apache License 2.0