package com.discoverandchange.pornographycrisissupport.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * @author Stephen Nielson
 * @author Keith Higbee
 * @author John Okleberry
 */

public class ScoresProvider extends ContentProvider {

  private static final String AUTHORITY = "com.discoverandchange.pornographycrisissupport.db.scoresprovider";
  private static final String BASE_PATH = "pcs";
  public static final Uri CONTENT_URI =
      Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

  // Constant to identify the requested operation
  private static final int SCORE = 1;     // get the data
  private static final int SCORE_ID = 2;  // get only a single record

  private static final UriMatcher uriMatcher =
      new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(AUTHORITY, BASE_PATH, SCORE);
    uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SCORE_ID);
  }

  private SQLiteDatabase database;

  @Override
  public boolean onCreate() {
    ScoresDbOpenHelper helper = new ScoresDbOpenHelper(getContext());
    database = helper.getWritableDatabase();
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    if (uriMatcher.match(uri) == SCORE_ID) {
      selection = ScoresDbOpenHelper.SCORE_ID + "=" + uri.getLastPathSegment();
    }

    return database.query(ScoresDbOpenHelper.TBL_SCORES, ScoresDbOpenHelper.ALL_COLUMNS,
        selection, null, null, null,
        ScoresDbOpenHelper.DATE_CREATED + " DESC");
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    long id = database.insert(ScoresDbOpenHelper.TBL_SCORES,
        null, values);
    return Uri.parse(BASE_PATH + "/" + id);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return database.delete(ScoresDbOpenHelper.TBL_SCORES, selection, selectionArgs);
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return database.update(ScoresDbOpenHelper.TBL_SCORES,
        values, selection, selectionArgs);
  }
}
