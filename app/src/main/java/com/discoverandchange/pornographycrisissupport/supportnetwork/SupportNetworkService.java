package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportNetworkService {

  private List<SupportContact> contacts;

  private SmsManager smsManager;

  private static SupportNetworkService service;

  public SupportNetworkService(SmsManager smsManager) {
    this.smsManager = smsManager;
    this.contacts = new ArrayList<SupportContact>();
    // TODO: eventually we'll load this from a database.
  }

  public static SupportNetworkService getInstance() {
    if (service == null) {
      service = new SupportNetworkService(SmsManager.getDefault());
    }
    return service;
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

}
