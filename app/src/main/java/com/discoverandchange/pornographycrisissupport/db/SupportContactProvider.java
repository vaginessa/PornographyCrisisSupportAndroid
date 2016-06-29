package com.discoverandchange.pornographycrisissupport.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Contact provider for CRUD on the SupportContact entities.
 * @author Stephen Nielson
 */
public class SupportContactProvider extends ContentProvider {

  /**
   * The authority to use for querying this data.
   */
  private static final String AUTHORITY = "com.discoverandchange.pornographycrisissupport"
      + ".SupportContactProvider";

  /**
   * The base query path for this entity.
   */
  private static final String BASE_PATH = "SupportContactProvider";

  /**
   * The public content uri.
   */
  public static final Uri CONTENT_URI =
      Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

  /**
   * The database we are reading / updating data in.
   */
  private SQLiteDatabase database;

  @Override
  public boolean onCreate() {
    PcsDbOpenHelper helper = new PcsDbOpenHelper(getContext());
    database = helper.getWritableDatabase();
    return true;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                      String sortOrder) {
    return database.query(SupportContactTable.TABLE_NAME, projection,
        selection, selectionArgs, null, null, sortOrder);
  }

  @Nullable
  @Override
  /**
   * We don't use mime types here so no type is returned.
   * @param uri the uri to return the type for.
   * @return null as we don't deal with mime types.
   */
  public String getType(Uri uri) {
    // returns mime type, which is not used.
    return null;
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    long id = database.insert(SupportContactTable.TABLE_NAME,
        null, values);
    return Uri.parse(BASE_PATH + "/" + id);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return database.delete(SupportContactTable.TABLE_NAME, selection, selectionArgs);
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return database.update(SupportContactTable.TABLE_NAME,
        values, selection, selectionArgs);
  }
}
