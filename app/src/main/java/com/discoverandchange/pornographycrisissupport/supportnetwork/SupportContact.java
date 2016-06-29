package com.discoverandchange.pornographycrisissupport.supportnetwork;

import java.io.Serializable;

/**
 * Information about a single support contact from our support network list such as name,
 * phone number, and cid from the Google Contacts application.
 *
 * @author Stephen Nielson
 */
public class SupportContact implements Serializable {

  /**
   * The phone number to contact for this contact.
   */
  private String phoneNumber;

  /**
   * The display name of this contact.
   */
  private String name;

  /**
   * The unique android contact id.
   */
  private String cid;

  /**
   * Whether this contact is the one to be called when the user is in crisis.
   */
  private boolean isCrisisContact;

  public SupportContact() {
  }

  /**
   * Constructos a support contact with the passed in parameters.
   * @param cid The unique android contact id.
   * @param name The display name of this contact.
   * @param number The phone number to contact for this contact.
   */
  public SupportContact(String cid, String name, String number) {
    this.setContactId(cid);
    this.setName(name);
    this.setPhoneNumber(number);
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContactId(String cid) {
    this.cid = cid;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public String getName() {
    return this.name;
  }

  public String getContactId() {
    return this.cid;
  }

  public void setIsCrisisContact(boolean isCrisis) {
    this.isCrisisContact = isCrisis;
  }

  public boolean isCrisisContact() {
    return this.isCrisisContact;
  }
}
