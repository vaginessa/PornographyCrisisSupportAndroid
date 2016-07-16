package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Context;
import android.telephony.SmsManager;

import com.discoverandchange.pornographycrisissupport.db.SupportContactProvider;
import com.discoverandchange.pornographycrisissupport.db.SupportContactStorageSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tracks and manages support contacts.  Sends text messages and handles who the top crisis contact
 * is.
 *
 * @author Stephen Nielson
 */
public class SupportNetworkService {

  private List<SupportContact> contacts;

  /**
   * Contains the list of support contacts that have already been inserted into the database.
   **/
  private Set<String> managedContactIds;

  private SmsManager smsManager;

  /**
   * The singleton instance for the support contact.
   */
  private static SupportNetworkService service;

  /**
   * The persistance storage system for support contacts.
   */
  private SupportContactStorageSystem storageSystem;

  /**
   * Instantiate a support network service with the given storage system and SmsManager.
   * This method while public is used strictly for testing purposes and shouldn't be used.
   * Use getInstance(...) instead.
   *
   * @param storageSystem The storage system that persists support contacts
   * @param smsManager    The android sms manager system.
   */
  public SupportNetworkService(SupportContactStorageSystem storageSystem, SmsManager smsManager) {
    this.smsManager = smsManager;
    this.storageSystem = storageSystem;
    this.contacts = new ArrayList<>();
    this.managedContactIds = new HashSet<String>();
  }

  /**
   * Retrieves the singleton instance of the SupportNetworkService.  Note because this method is
   * synchronized care should be made to avoid repeated calls across multiple threads.
   * @param context The global android application context
   * @return The current/instantiated instance of SupportNetworkService.
   */
  public static synchronized SupportNetworkService getInstance(Context context) {
    if (service == null) {
      SupportContactStorageSystem storageSystem = new SupportContactStorageSystem(context,
          SupportContactProvider.CONTENT_URI);
      service = new SupportNetworkService(storageSystem, SmsManager.getDefault());
      service.init();
    }
    return service;
  }

  /**
   * Initialization logic for the service, launching any asynchronous tasks, etc.
   */
  public void init() {
    this.contacts = this.storageSystem.retrieveSupportContactsFromStorage();
  }

  /**
   * Shorthand for adding a support contact with a given name, contactId and phone number
   *
   * @param name      The display name of this support contact.
   * @param contactId The unique id that represents this contact
   * @param phone     The phone number to call / send a text message to for a contact.
   * @return A support contact that has been persisted.
   */
  public SupportContact addSupportContact(String name, String contactId, String phone) {
    return addSupportContact(new SupportContact(name, contactId, phone));
  }

  /**
   * Adds a support contact to the service and persists it.  The contact must have a contact id
   * in order to be saved.  If the contact has the same id as an already existing contact, the
   * contact is updated with the new contact's information.
   * If the contact is set to be a crisis contact, it replaces the current system crisis contact.
   *
   * @param contactToSave The contact to be saved with a valid id.
   * @return The contact that was saved.
   */
  public SupportContact addSupportContact(SupportContact contactToSave) {
    if (contactToSave.getContactId() == null) {
      throw new IllegalArgumentException("Contact must have a contact id in order to be saved");
    }

    if (contactToSave.isCrisisContact()) {
      // change the current crisis contact if we have one
      SupportContact crisisContact = this.getCrisisSupportContact();
      if (crisisContact != null && !crisisContact.getContactId().equals(
          contactToSave.getContactId())) {
        crisisContact.setIsCrisisContact(false);
        this.storageSystem.persistContact(crisisContact);
      }
    }

    SupportContact contact = getContactById(contactToSave.getContactId());
    if (contact != null) {
      int contactIndex = this.contacts.indexOf(contact);
      this.contacts.set(contactIndex, contactToSave);
    } else {
      this.contacts.add(contactToSave);
    }
    // save any changes to the contact.
    this.storageSystem.persistContact(contactToSave);
    return contactToSave;
  }

  /**
   * Saves the support contact and returns the contact that was saved.
   *
   * @param contactToSave The contact to be saved with a valid id.
   * @return The saved contact.
   */
  public SupportContact saveSupportContact(SupportContact contactToSave) {
    return addSupportContact(contactToSave);
  }

  /**
   * Sends a text message to all of our support network contacts.
   */
  public void contactNetwork() {
    this.sendMessageToSupportNetwork(getDefaultMessage());
  }

  /**
   * Sends a text message as part of the first time setup.
   */
  public void testSendMessage(String message) {
    sendMessageToSupportNetwork(message);
  }

  /**
   * Returns the support network contact to call when the user is in crisis mode.
   *
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
   *
   * @return List of support contacts that have been added.
   */
  public List<SupportContact> getSupportContactList() {

    return new ArrayList<SupportContact>(this.contacts);
  }

  /**
   * Returns the default phone device number.  On Android a null value will use whatever the
   * device SIM card has set for the phone number.
   *
   * @return The device phone number, null for the default device number.
   */
  public String getDevicePhoneNumber() {
    // if null stops being the default parameter for the device phone number
    // we will need to take action here.  All of the SMS handling treats null
    // as being the device number.
    return null;
  }

  /**
   * Returns the default sms text message to send when a person is in crisis mode.
   *
   * @return The default string message.
   */
  public String getDefaultMessage() {

    return "I'm really struggling with cravings right now.  Please help me!";
  }

  /**
   * Obtain the contactID that was given
   * Search the arrayList of contacts for the contact ID
   * If that contactID is not present, then it has been removed.
   *
   * @param contactId The id of the contact to be removed
   * @return The contact that was removed.
   */
  public SupportContact removeSupportContact(String contactId) {

    if (contactId == null) {
      throw new IllegalArgumentException("contactID must not be null");
    }

    SupportContact contact = getContactById(contactId);
    if (contact != null) {
      if (this.contacts.remove(contact)) { // remove in memory
        this.storageSystem.removeContact(contact); // remove in the database
        return contact;
      }
    }

    return null;
  }

  /**
   * Given a unique id of a contact, return the SupportContact if we have one for that id.
   * If we don't have one then null is returned
   *
   * @param contactId The unique id of the contact we want to retrieve.
   * @return The SupportContact saved in the system, or null if none was found.
   */
  public SupportContact getContactById(String contactId) {
    for (SupportContact contact : getSupportContactList()) {
      if (contactId.equals(contact.getContactId())) {
        return contact;
      }
    }
    return null;
  }

  /**
   * Sends out the passed in test sms message to all of our support network.
   * @param message The message we want sent out.
   */
  public void sendSmsTestMessage(String message) {
    sendMessageToSupportNetwork(message);
  }

  /**
   * Given a message to send, it sends a SMS text to all of the phones for each support contact
   * we have.
   *
   * @param message The message to be sent.
   */
  private void sendMessageToSupportNetwork(String message) {
    for (SupportContact contact : getSupportContactList()) {
      if (contact.getPhoneNumber() != null) {
        smsManager.sendTextMessage(contact.getPhoneNumber(), getDevicePhoneNumber(), message,
            null, null);
      }
    }
  }
}
