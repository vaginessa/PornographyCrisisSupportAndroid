package com.discoverandchange.pornographycrisissupport;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
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

  public void retrieveContactData(String cid) {

    ContentResolver cr = getContentResolver();

    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cid, null, null);
      while (phones.moveToNext()) {
        String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
        switch (type) {
          case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
            // do something with the Home number here...

            Log.v(Constants.LOG_TAG, "Home Phone Number: " + number);
            Log.w(Constants.LOG_TAG, "Warning: Contact Home Phone Number not Handled Correctly");
            break;
          case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
            // do something with the Mobile number here...

            Log.v(Constants.LOG_TAG, "Mobile Phone Number: " + number);
            Log.w(Constants.LOG_TAG, "Warning: Contact Mobile Phone Number not Handled Correctly");
            break;
          case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
            // do something with the Work number here...

            Log.v(Constants.LOG_TAG, "Work Phone Number: " + number);
            Log.w(Constants.LOG_TAG, "Warning: Contact Work Phone Number not Handled Correctly");
            break;
        }

        // TO DO :  Create a Support Contact with Name, with phone number and cid stored

      }
      phones.close();

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case CONTACT_PICKER_RESULT: {

          Uri result = data.getData();

          Log.v(Constants.LOG_TAG, "uri is " + result.toString());

          String id = result.getLastPathSegment();
          retrieveContactData(id);
        }
          break;
      }

    } else {
      // gracefully handle failure
      Log.w(Constants.LOG_TAG, "Warning: activity result not ok");
    }
  }
}