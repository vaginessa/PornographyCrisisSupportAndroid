package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Declares the structure of the scores table.
 * @author Keith Higbee
 */
public class ScoresTable {

  /**
   * Scores Table name.
   */
  public static final String TBL_SCORES = "tbl_scores";

  /**
   * Score Column Id name.
   */
  public static final String SCORE_ID = "_id";

  /**
   * Score Column score.
   */
  public static final String SCORE =  "score";

  /**
   * Score Column date.
   */
  public static final String DATE_CREATED = "dateCreated";

  /**
   * All of the columns in the Score table.
   */
  public static final String[] ALL_COLUMNS =
      {SCORE_ID, SCORE, DATE_CREATED};

  /**
   * SQL to create table.
   */
  public static final String TABLE_CREATE =
      "CREATE TABLE " + TBL_SCORES + " ("
          + SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + SCORE + " INTEGER, "
          + DATE_CREATED + " TEXT default CURRENT_TIMESTAMP"
          + ")";
}