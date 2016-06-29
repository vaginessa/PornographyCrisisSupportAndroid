package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.discoverandchange.pornographycrisissupport.Constants;

/**
 * Executes creation of the database and tables.
 * @author Stephen Nielson
 */
public class PcsDbOpenHelper extends SQLiteOpenHelper {

  public PcsDbOpenHelper(Context context) {
    super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
  }

  /**
   * Creates the tables in the database.
   * @param db The reference to the database
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(ScoresTable.TABLE_CREATE);
    db.execSQL(SupportContactTable.CREATE_TABLE);
  }

  /**
   * Drops the tables and then recreates the tables in the database.
   * @param db The reference to the database
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + ScoresTable.TBL_SCORES);
    db.execSQL("DROP TABLE IF EXISTS " + SupportContactTable.TABLE_NAME);
    onCreate(db);
  }
}
