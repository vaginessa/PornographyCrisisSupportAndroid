package com.discoverandchange.pornographycrisissupport.supportnetwork;

import java.io.Serializable;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportContact implements Serializable {

  private String phoneNumber;
  private String name;
  private String cid;
  private boolean isCrisisContact;

  public SupportContact() {
  }

  public SupportContact (String cid, String name, String number){
    this.setContactID(cid);
    this.setName(name);
    this.setPhoneNumber(number);
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContactID (String cid) { this.cid = cid; }

  public String getPhoneNumber() { return this.phoneNumber; }

  public String getName() { return this.name; }

  public String getContactID () { return this.cid; }

  public void setIsCrisisContact(boolean isCrisis) {
    this.isCrisisContact = isCrisis;
  }

  public boolean isCrisisContact() {
    return this.isCrisisContact;
  }
}
