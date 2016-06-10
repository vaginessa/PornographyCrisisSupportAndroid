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

import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    String[] projection    = new String[] { ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};

    Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cid, null, null);

    // Locations of our name and phone number within the projection
    int indexName   = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
    int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

    // Making a temporary list to store phone numbers to display to the user
    List phoneList = new ArrayList<String> ();

    // Our new Support Contact
    SupportContact contact;

    // A loop to collect all phone numbers for our contact into a list for display later
    while (cursor.moveToNext()) {

      // A place holder for whatever phone number is currently selected
      String number = cursor.getString(indexNumber);

      // A place holder for the name of the current contact
      String name = cursor.getString(indexName);

      phoneList.add(number);

      contact = new SupportContact(cid, name, number);
    }

    cursor.close();

    // Display the contact list here with something like "numberList.displayList();"
      // Receive User input on which number to store
      // store that number in the "number" variable

              // Also :   Lay groundwork for storing the supportContact data long-term


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