package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.telephony.SmsManager;

/**
 * Created by snielson on 6/6/16.
 */
public class SupportNetworkService {

  private SmsManager smsManager;

  public SupportNetworkService(SmsManager smsManager) {
    this.smsManager = smsManager;
  }
  public void addSupportContact(String first, String last, String contactId, int phone) {}

  public void contactNetwork() {
    smsManager.sendTextMessage("8018558888", "8018558888", "some message", null, null);
    smsManager.sendTextMessage("8018888888", "8018888888", "some message", null, null);
    smsManager.sendTextMessage("8019999999", "8019999999", "some message", null, null);
  }

}
