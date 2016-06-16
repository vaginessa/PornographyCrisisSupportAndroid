package com.discoverandchange.pornographycrisissupport.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by snielson on 6/15/16.
 */
public class SupportContactStorageSystem {

  private Context context = null;

  private static final String IDENTITY_WHERE = SupportContactTable.COLUMN_NAME_ID + " = ?";

  private Uri databaseSupportContactUri;

  /** Contains the list of support contacts that have already been inserted into the database **/
  private Set<String> managedContactIds;

  public SupportContactStorageSystem(Context context, Uri contactUri) {
    this.context = context;
    this.databaseSupportContactUri = contactUri;
    this.managedContactIds = new HashSet<String>();
  }

  public void removeContact(SupportContact contact) {
    String[] deletionArgs = new String[] {contact.getContactID()};
    this.context.getContentResolver().delete(this.databaseSupportContactUri,IDENTITY_WHERE,deletionArgs);
    this.stopTrackingContact(contact);
  }

  public void persistContact(SupportContact contact) {
    // grab the contact from the database
    ContentValues values = new ContentValues();
    values.put(SupportContactTable.COLUMN_NAME_ID, contact.getContactID());
    values.put(SupportContactTable.COLUMN_NAME_NAME, contact.getName());
    values.put(SupportContactTable.COLUMN_NAME_PHONE, contact.getPhoneNumber());
    if (contact.isCrisisContact()) {
      values.put(SupportContactTable.COLUMN_NAME_IS_CRISIS, 1);
    }
    else {
      values.put(SupportContactTable.COLUMN_NAME_IS_CRISIS, 0);
    }

    if (this.isTrackingContact(contact)) {
      String[] updateArgs = new String[] {contact.getContactID()};
      this.context.getContentResolver().update(this.databaseSupportContactUri,values,
          IDENTITY_WHERE, updateArgs);
    }
    else {
      this.context.getContentResolver().insert(this.databaseSupportContactUri, values);
      this.trackContact(contact);
    }
  }

  public List<SupportContact> retrieveSupportContactsFromStorage() {
    Cursor cursor = this.context.getContentResolver().query(this.databaseSupportContactUri,
        SupportContactTable.ALL_COLUMNS, null, null, null);
    List<SupportContact> supportContacts = new ArrayList<SupportContact>();
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        SupportContact contact = hydrateContactFromCursor(cursor);
        supportContacts.add(contact);
        this.trackContact(contact);
        cursor.moveToNext();
      } while (!cursor.isAfterLast());
    }
    cursor.close();

    return supportContacts;
  }

  private SupportContact hydrateContactFromCursor(Cursor cursor) {
    SupportContact contact = new SupportContact();
    contact.setContactID(cursor.getString(cursor.getColumnIndex(SupportContactTable.COLUMN_NAME_ID)));
    contact.setName(cursor.getString(cursor.getColumnIndex(SupportContactTable.COLUMN_NAME_NAME)));
    contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SupportContactTable.COLUMN_NAME_PHONE)));
    // database holds boolean values as integers, 1 for true, 0 for false.
    int isCrisis = cursor.getInt(cursor.getColumnIndex(SupportContactTable.COLUMN_NAME_IS_CRISIS));
    contact.setIsCrisisContact(isCrisis == 1);
    return contact;
  }

  private void stopTrackingContact(SupportContact contact) {
    this.managedContactIds.remove(contact.getContactID());
  }

  private boolean isTrackingContact(SupportContact contact) {
    return this.managedContactIds.contains(contact.getContactID());
  }

  private void trackContact(SupportContact contact) {
    this.managedContactIds.add(contact.getContactID());
  }
}
