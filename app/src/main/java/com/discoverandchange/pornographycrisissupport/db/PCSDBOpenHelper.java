package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.discoverandchange.pornographycrisissupport.Constants;

/**
 * Created by snielson on 6/16/16.
 */
public class PCSDBOpenHelper extends SQLiteOpenHelper {

  public PCSDBOpenHelper(Context context) {
    super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(ScoresTable.TABLE_CREATE);
    db.execSQL(SupportContactTable.CREATE_TABLE);
  }


  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + ScoresTable.TBL_SCORES);
    db.execSQL("DROP TABLE IF EXISTS " + SupportContactTable.TABLE_NAME);
    onCreate(db);
  }
}
