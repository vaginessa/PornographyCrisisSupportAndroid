package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.App;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.library.LibraryController;

/**
 * TODO: stephen verify that the app context is what we want and it won't cause problems.
 * Created by snielson on 6/7/16.
 */
public class EndCallListener extends PhoneStateListener {

  //private Intent returnIntent;
  private boolean phoneCallInitiatedByApp = false;

//  public EndCallListener(Intent intent) {
//    returnIntent = intent;
//  }

  @Override
  public void onCallStateChanged(int state, String incomingNumber) {


    if(TelephonyManager.CALL_STATE_RINGING == state) {
      Log.i(Constants.LOG_TAG, "RINGING, number: " + incomingNumber);
    }
    if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
      //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
      phoneCallInitiatedByApp = true;
      Log.i(Constants.LOG_TAG, "OFFHOOK");
    }
    if(TelephonyManager.CALL_STATE_IDLE == state) {
      if (phoneCallInitiatedByApp) {
        Context context = App.getContext();
        TelephonyManager mTM = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        Intent intent = new Intent(context, LibraryController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
        mTM.listen(this, PhoneStateListener.LISTEN_NONE);
      }
      //when this state occurs, and your flag is set, restart your app
      Log.i(Constants.LOG_TAG, "IDLE");
    }
  }

}
