package com.discoverandchange.pornographycrisissupport;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContactsArrayAdapter;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkEdit;
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
    //service.addSupportContact(new SupportContact("1", "Test User 1", "888-888-8888"));
    //service.addSupportContact(new SupportContact("2", "Test User 2", "888-888-8889"));

    contactArrayAdapter = new SupportContactsArrayAdapter(getBaseContext(),
        service.getSupportContactList());
    ListView contactListView = (ListView)findViewById(R.id.lvSupportContact);
    contactListView.setAdapter(contactArrayAdapter);

    contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        Intent intent = new Intent(getBaseContext(), SupportNetworkEdit.class);
        intent.putExtra(Constants.SUPPORT_CONTACT_EDIT_MESSAGE, (SupportContact) parent.getItemAtPosition(position));
        startActivity(intent);
      }
    });

  }



  public void launchContactPicker(View btn) {

      Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
          ContactsContract.Contacts.CONTENT_URI);
    IntentChecker checker = new IntentChecker(this);
    if (!checker.isIntentSafeToLaunch(contactPickerIntent)) {
      displayInstallContactsAppDialog();
    }
    else {
      startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

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

    // no phone number was available for the contact, or the contact id couldn't be found.
    if (cursor == null || cursor.getCount() < 1) {
      return null;
    }

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
      contact.setIsCrisisContact(true);
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

    if (resultCode == RESULT_OK && requestCode == CONTACT_PICKER_RESULT) {
          Uri result = data.getData();

          Log.v(Constants.LOG_TAG, "uri is " + result.toString());

          String id = result.getLastPathSegment();
          SupportContact contact = retrieveContactData(id);
          if (contact != null) {
            saveContactData(contact);
            updateContactListWithContact(contact);
          }
          else {
            displayInvalidContactAlert();
          }
    } else {
      // gracefully handle failure
      Log.w(Constants.LOG_TAG, "Warning: activity result not ok");
    }
  }

  /**
   * Displays an alert box notifying the user that the contact they chose is invalid.
   */
  private void displayInvalidContactAlert() {
    displayErrorDialog(R.string.contact_add_invalid_title, R.string.contact_add_invalid);
  }

  /**
   * Displays an alert box notifying the user that they need to install a contacts app
   * so we can pick a contact.
   */
  private void displayInstallContactsAppDialog() {
    displayErrorDialog(R.string.contact_app_missing_title, R.string.contact_app_missing);
  }

  /**
   * Displays an Alert box representing an error. The error will containct the given title
   * and message passed in from the resources string file. IE R.string.<title|message>
   * @param titleStringId The resource string id for the alert title
   * @param messageStringId The resource string id for the alert message displayed.
   */
  private void displayErrorDialog(int titleStringId, int messageStringId) {
    new AlertDialog.Builder(this)
        .setTitle(titleStringId)
        .setMessage(messageStringId)
        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // right now we do nothing if they click the ok button.
          }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
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