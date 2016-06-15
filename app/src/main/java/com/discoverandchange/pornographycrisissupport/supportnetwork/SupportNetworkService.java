package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import com.discoverandchange.pornographycrisissupport.db.SupportContactOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.SupportContactProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportNetworkService {

  private List<SupportContact> contacts;

  private SmsManager smsManager;

  private Context context = null;

  private Uri databaseSupportContactUri;

  private static SupportNetworkService service;

  public SupportNetworkService(Context context, SmsManager smsManager, Uri dbContactURI) {
    this.context = context;
    this.smsManager = smsManager;
    this.databaseSupportContactUri = dbContactURI;
    this.contacts = new ArrayList<>();
  }

  public static SupportNetworkService getInstance(Context context) {
    if (service == null) {
      service = new SupportNetworkService(context, SmsManager.getDefault(),
          SupportContactProvider.CONTENT_URI);
      service.init();
    }
    return service;
  }

  /**
   * Initialization logic for the service, launching any asynchronous tasks, etc.
   */
  public void init() {
    this.contacts = this.retrieveSupportContactsFromDatabase();
  }

  public SupportContact addSupportContact(String first, String last, String contactId, String phone) {
    return addSupportContact(new SupportContact(first + " " + last, contactId, phone));
  }

  /**
   * Adds a support contact to the service and persists it.  The contact must have a contact id
   * in order to be saved.
   * @param contactToSave The contact to be saved with a valid id.
   * @return The contact that was saved.
   */
  public SupportContact addSupportContact(SupportContact contactToSave) {
    if (contactToSave.getContactID() == null) {
      throw new IllegalArgumentException("Contact must have a contact id in order to be saved");
    }

    this.contacts.add(contactToSave);
    return contactToSave;
  }

  public void contactNetwork() {
    // TODO: go through all of our contact lists & send out the test message.
    smsManager.sendTextMessage("8018558888", "8018558888", "some message", null, null);
//    smsManager.sendTextMessage("8018888888", "8018888888", "some message", null, null);
//    smsManager.sendTextMessage("8019999999", "8019999999", "some message", null, null);
  }

  public SupportContact getCrisisSupportContact() {
    SupportContact crisis = new SupportContact();
    crisis.setPhoneNumber("801-610-9014");
    crisis.setName("Stephen Nielson");
    return crisis;
  }

  /**
   * Returns all of the support contacts that are being tracked by this service.
   * @return List of support contacts that have been added.
   */
  public List<SupportContact> getSupportContactList() {

    return new ArrayList<SupportContact>(this.contacts);
  }

  public String getDevicePhoneNumber() {
    return "888-888-8888";
  }

  public String getDefaultMessage() {
    return "I am experiencing severe cravings. Help!!!";
  }

  // Obtain the contactID that was given
  // Search the arrayList of contacts for the contact ID
  // If that contactID is not present, then it has been removed
  public SupportContact removeSupportContact(String contactID){
    return null;
  }

  public void sendSMSTestMessage(String message) {

  }

  public boolean hasContact(String contactID) {
    boolean contactPresent = false;
    return contactPresent;
  }

  private void persistContact(SupportContact contact) {
    // grab the contact from the database

  }

  // TODO: stephen... I'd really rather this logic be in the SupportContactProvider
  private List<SupportContact> retrieveSupportContactsFromDatabase() {
    Cursor cursor = this.context.getContentResolver().query(this.databaseSupportContactUri,
        SupportContactOpenHelper.ALL_COLUMNS, null, null, null);
    List<SupportContact> supportContacts = new ArrayList<SupportContact>();
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        SupportContact contact = hydrateContactFromCursor(cursor);
        supportContacts.add(contact);
        cursor.moveToNext();
      } while (!cursor.isAfterLast());

    }

    return supportContacts;
  }

  private SupportContact hydrateContactFromCursor(Cursor cursor) {
    SupportContact contact = new SupportContact();
    contact.setContactID(cursor.getString(cursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_ID)));
    contact.setName(cursor.getString(cursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_NAME)));
    contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_PHONE)));
    // database holds boolean values as integers, 1 for true, 0 for false.
    int isCrisis = cursor.getInt(cursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_IS_CRISIS));
    contact.setIsCrisisContact(isCrisis == 1);
    return contact;
  }

}
