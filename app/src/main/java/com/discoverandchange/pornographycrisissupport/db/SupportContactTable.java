package com.discoverandchange.pornographycrisissupport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.discoverandchange.pornographycrisissupport.Constants;

/**
 * Helper class representing column and sql commands for the support contact sqlite database table.
 */
public class SupportContactTable {


  /**
   * The column name for the contact id
   */
  public static final String COLUMN_NAME_ID = "id";

  /**
   * The column name for the contact name
   */
  public static final String COLUMN_NAME_NAME = "name";

  /**
   * The column name for the contact phone
   */
  public static final String COLUMN_NAME_PHONE = "phone";

  /**
   * The column name for the contact crisis field
   */
  public static final String COLUMN_NAME_IS_CRISIS = "is_crisis";

  /**
   * The name of the table the contact data is stored in.
   */
  public static final String TABLE_NAME = "SupportContact";

  /**
   * All of the columns in the contact table.
   */
  public static final String[] ALL_COLUMNS = {COLUMN_NAME_ID, COLUMN_NAME_NAME,
      COLUMN_NAME_PHONE, COLUMN_NAME_IS_CRISIS};

  /**
   * The Create table command for the support contact.
   */
  public static final java.lang.String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
      + "("
      + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
      + COLUMN_NAME_NAME + " VARCHAR(255) DEFAULT NULL, "
      + COLUMN_NAME_PHONE + " VARCHAR(20) NOT NULL, "
      + COLUMN_NAME_IS_CRISIS + " INTEGER DEFAULT 0 "
      + ")";
}
