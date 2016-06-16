package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.discoverandchange.pornographycrisissupport.Constants;

/**
 * Created by snielson on 6/14/16.
 */
public class SupportContactTable {


  public static final String COLUMN_NAME_ID = "id";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_PHONE = "phone";
  public static final String COLUMN_NAME_IS_CRISIS = "is_crisis";
  public static final String TABLE_NAME = "SupportContact";
  public static final String[] ALL_COLUMNS = {COLUMN_NAME_ID, COLUMN_NAME_NAME,
      COLUMN_NAME_PHONE, COLUMN_NAME_IS_CRISIS};
  public static final java.lang.String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
      + "("
      + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
      + COLUMN_NAME_NAME + " VARCHAR(255) DEFAULT NULL, "
      + COLUMN_NAME_PHONE + " VARCHAR(20) NOT NULL, "
      + COLUMN_NAME_IS_CRISIS + " INTEGER DEFAULT 0 "
      + ")";
}
