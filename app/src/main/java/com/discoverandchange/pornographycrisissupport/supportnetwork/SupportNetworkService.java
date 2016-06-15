package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import com.discoverandchange.pornographycrisissupport.db.SupportContactOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.SupportContactProvider;

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

  public SupportContact addSupportContact(String name, String contactId, String phone) {
    return addSupportContact(new SupportContact(name, contactId, phone));
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

  /**
   * Sends a text message to all of our support network contacts.
   */
  public void contactNetwork() {
    this.sendMessageToSupportNetwork(getDefaultMessage());
  }

  /**
   * Returns the support network contact to call when the user is in crisis mode.
   * @return The support network to be called when in crisis mode.
   */
  public SupportContact getCrisisSupportContact() {
    for (SupportContact contact : getSupportContactList()) {
      if (contact.isCrisisContact()) {
        return contact;
      }
    }
    return null;
  }

  /**
   * Returns all of the support contacts that are being tracked by this service.
   * @return List of support contacts that have been added.
   */
  public List<SupportContact> getSupportContactList() {

    return new ArrayList<SupportContact>(this.contacts);
  }

  /**
   * Returns the default phone device number.  On Android a null value will use whatever the
   * device SIM card has set for the phone number.
   * @return The device phone number, null for the default device number.
   */
  public String getDevicePhoneNumber() {
    // if null stops being the default parameter for the device phone number
    // we will need to take action here.  All of the SMS handling treats null
    // as being the device number.
    return null;
  }

  public String getDefaultMessage() {
    return "I am experiencing severe cravings. Help!!!";
  }

  /**
   * Obtain the contactID that was given
   * Search the arrayList of contacts for the contact ID
   * If that contactID is not present, then it has been removed.
   * @param contactID The id of the contact to be removed
   * @return The contact that was removed.
   */
  public SupportContact removeSupportContact(String contactID){

    if (contactID == null) {
      throw new IllegalArgumentException("contactID must not be null");
    }

    SupportContact contact = getContactById(contactID);
    if (contact != null) {
      if (this.contacts.remove(contact)) {
        return contact;
      }
    }

    return null;
  }

  public SupportContact getContactById(String contactID) {
    for (SupportContact contact : getSupportContactList()) {
      if (contactID.equals(contact.getContactID())) {
        return contact;
      }
    }
    return null;
  }

  public void sendSMSTestMessage(String message) {
    sendMessageToSupportNetwork(message);
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

  /**
   * Given a message to send, it sends a SMS text to all of the phones for each support contact
   * we have.
   * @param message  The message to be sent.
   */
  private void sendMessageToSupportNetwork(String message) {
    for (SupportContact contact : getSupportContactList()) {
      if (contact.getPhoneNumber() != null) {
        smsManager.sendTextMessage(contact.getPhoneNumber(), getDevicePhoneNumber(), message
            , null, null);
      }
    }
  }
}
