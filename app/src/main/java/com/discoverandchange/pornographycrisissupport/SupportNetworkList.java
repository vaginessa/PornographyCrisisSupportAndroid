package com.discoverandchange.pornographycrisissupport;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

public class SupportNetworkList extends BaseNavigationActivity {

  private static final int CONTACT_PICKER_RESULT = 1001;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_support_network_list);
  }



  public void launchContactPicker(View btn) {

    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
        ContactsContract.Contacts.CONTENT_URI);
    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case CONTACT_PICKER_RESULT: {

          Uri result = data.getData();

          Log.v(Constants.LOG_TAG, "uri is " + result.toString());

          String id = result.getLastPathSegment();
        }
          break;
      }

    } else {
      // gracefully handle failure
      Log.w(Constants.LOG_TAG, "Warning: activity result not ok");
    }
  }
}