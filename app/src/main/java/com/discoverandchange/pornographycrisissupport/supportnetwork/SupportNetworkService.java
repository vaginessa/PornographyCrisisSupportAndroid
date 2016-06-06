package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.telephony.SmsManager;

import java.util.List;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportNetworkService {

  private SmsManager smsManager;

  public SupportNetworkService(SmsManager smsManager) {
    this.smsManager = smsManager;
  }
  public SupportContact addSupportContact(String first, String last, String contactId, String phone) {
    return null;
  }

  public void contactNetwork() {
    // TODO: go through all of our contact lists & send out the test message.
    smsManager.sendTextMessage("8018558888", "8018558888", "some message", null, null);
//    smsManager.sendTextMessage("8018888888", "8018888888", "some message", null, null);
//    smsManager.sendTextMessage("8019999999", "8019999999", "some message", null, null);
  }

  public List<SupportContact> getSupportContactList() {
    return null;
  }

  public String getDevicePhoneNumber() {
    return "888-888-8888";
  }

  public String getDefaultMessage() {
    return "I am experiencing severe cravings. Help!!!";
  }
}
