package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khigbee on 6/9/2016.
 */

public class ScoresDBOpenHelper extends SQLiteOpenHelper {

  // db name and version
  private static final String DATABASE_NAME = "pcs.db";
  private static final int DATABASE_VERSION = 1;

  // table
  public static final String SCORES = "scores";

  // columns
  public static final String SCORE_ID = "_id";
  public static final String SCORE =  "score";
  public static final String DATE_CREATED = "dateCreated";

  public static final String[] ALL_COLUMNS =
      {SCORE_ID, SCORE, DATE_CREATED};

  // SQL to create table
  private static final String TABLE_CREATE =
      "CREATE TABLE " + SCORES + " (" +
          SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          SCORE + " INTEGER, " +
          DATE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
          ")";

  public ScoresDBOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(TABLE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    // currently, any change to the database version will drop the table and recreate
    // need to update this section if data retention is necessary
    db.execSQL("DROP TABLE IF EXISTS " + SCORES);
    onCreate(db);
  }
}
