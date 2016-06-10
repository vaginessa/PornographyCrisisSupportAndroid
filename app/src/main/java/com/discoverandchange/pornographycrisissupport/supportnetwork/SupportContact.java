package com.discoverandchange.pornographycrisissupport.supportnetwork;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportContact {

  private String phoneNumber;
  private String name;
  private String cid;


  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContactID (String cid) { this.cid = cid; }

  public String getPhoneNumber() { return this.phoneNumber; }

  public String getName(String cid) { return this.name; }

  public String getContactID () { return this.cid; }
}
