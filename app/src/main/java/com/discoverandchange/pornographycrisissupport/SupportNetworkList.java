package com.discoverandchange.pornographycrisissupport;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContactsArrayAdapter;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SupportNetworkList extends BaseNavigationActivity {

  private static final int CONTACT_PICKER_RESULT = 1001;
  private SupportContactsArrayAdapter contactArrayAdapter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_support_network_list);

    SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
    // TODO: stephen,john this is test code, remove it when we are done.
    service.addSupportContact(new SupportContact("1", "Test User 1", "888-888-8888"));
    service.addSupportContact(new SupportContact("2", "Test User 2", "888-888-8889"));

    contactArrayAdapter = new SupportContactsArrayAdapter(getBaseContext(),
        service.getSupportContactList());
    ListView contactListView = (ListView)findViewById(R.id.lvSupportContact);
    contactListView.setAdapter(contactArrayAdapter);
  }



  public void launchContactPicker(View btn) {

    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
        ContactsContract.Contacts.CONTENT_URI);
    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    //launchContactPickerWithPermission();

  }
//
//  private void launchContactPickerWithPermission() {
//    int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
//
//    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//      Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//          ContactsContract.Contacts.CONTENT_URI);
//      startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
//    }
//    else {
//      requestContactPermissionAndLaunch();
//    }
//  }
//
//  private void requestContactPermissionAndLaunch() {
//
//    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//        Manifest.permission.READ_CONTACTS)) {
//
//      // request permission and then launch again.
//    launchContactPickerWithPermission();
//  }

  private SupportContact retrieveContactData(String cid) {

    ContentResolver cr = getContentResolver();

    String[] projection    = new String[] { ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};

    Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cid, null, null);

    // TODO: stephen,john handle the case if there is nothing that came back.

    // Locations of our name and phone number within the projection
    int indexName   = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
    int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

    // Making a temporary list to store phone numbers to display to the user
    List phoneList = new ArrayList<String> ();

    // Our new Support Contact
    SupportContact contact = null;

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

    return contact;

    // Display the contact list here with something like "numberList.displayList();"
    // Receive User input on which number to store
    // store that number in the "number" variable

    // Also :   Lay groundwork for storing the supportContact data long-term


  }

  @Override
  /** This gets called when a user selects a contact from the contact picker **/
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case CONTACT_PICKER_RESULT: {

          Uri result = data.getData();

          Log.v(Constants.LOG_TAG, "uri is " + result.toString());

          String id = result.getLastPathSegment();
          SupportContact contact = retrieveContactData(id);
          saveContactData(contact);
          updateContactListWithContact(contact);
        }
          break;
      }

    } else {
      // gracefully handle failure
      Log.w(Constants.LOG_TAG, "Warning: activity result not ok");
    }
  }

  // Adds a Support contact from the support contacts list
  private void updateContactListWithContact(SupportContact contact) {
    contactArrayAdapter.add(contact);
  }

  // Removes a Support contact from the support contacts list
  private void updateContactListRemoveContact(SupportContact contact) {
    contactArrayAdapter.remove(contact);
  }

  private void saveContactData(SupportContact contact) {
    if (contact != null) {
      SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
      service.addSupportContact(contact);
    }
  }
}