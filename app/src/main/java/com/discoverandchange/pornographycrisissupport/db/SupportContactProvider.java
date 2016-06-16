package com.discoverandchange.pornographycrisissupport.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by snielson on 6/15/16.
 */
public class SupportContactProvider extends ContentProvider {

  private static final String AUTHORITY = "com.discoverandchange.pornographycrisissupport"
      + ".SupportContactProvider";
  private static final String BASE_PATH = "SupportContactProvider";
  public static final Uri CONTENT_URI =
      Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

  private SQLiteDatabase database;

  @Override
  public boolean onCreate() {
    SupportContactOpenHelper helper = new SupportContactOpenHelper(getContext());
    database = helper.getWritableDatabase();
    return true;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    return database.query(SupportContactOpenHelper.TABLE_NAME, projection,
        selection, selectionArgs, null, null, sortOrder);
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    // returns mime type, which is not used.
    return null;
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    long id = database.insert(SupportContactOpenHelper.TABLE_NAME,
        null, values);
    return Uri.parse(BASE_PATH + "/" + id);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return database.delete(SupportContactOpenHelper.TABLE_NAME, selection, selectionArgs);
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return database.update(SupportContactOpenHelper.TABLE_NAME,
        values, selection, selectionArgs);
  }
}
