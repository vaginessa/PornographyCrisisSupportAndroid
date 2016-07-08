package com.discoverandchange.pornographycrisissupport.supportnetwork.controllers;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.IntentChecker;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContactsArrayAdapter;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the display, removal, and addition of support contacts.
 */
public class SupportNetworkListController extends BaseNavigationActivity {

  /**
   * Constant for identifying that we initiated the contact picker call.
   */
  private static final int CONTACT_PICKER_RESULT = 1001;

  /**
   * Wraps around the support contacts to be displayed in a list on the view.
   */
  private SupportContactsArrayAdapter contactArrayAdapter = null;

  /**
   * Refreshes the support network content on the page after a pause or startup.
   */
  public void onResume() {  // After a pause OR at startup
    super.onResume();
    //Refresh your stuff here
    setupSupportContactsList();
  }

  /**
   * Used saved state data to put support network list into a view.
   *
   * @param savedInstanceState This is necessary for displaying the support network list
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_support_network_list);
  }


  /**
   * Converts a list of support contacts into a list that is visible to the user and can be
   * interacted with.
   */
  private void setupSupportContactsList() {

    SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
    List<SupportContact> contactList = service.getSupportContactList();
    contactArrayAdapter = new SupportContactsArrayAdapter(getBaseContext(), contactList);
    ListView contactListView = (ListView) findViewById(R.id.lvSupportContact);
    contactListView.setAdapter(contactArrayAdapter);

    contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        Intent intent = new Intent(getBaseContext(), SupportNetworkEditController.class);
        intent.putExtra(Constants.SUPPORT_CONTACT_EDIT_MESSAGE,
            (SupportContact) parent.getItemAtPosition(position));
        startActivity(intent);
      }
    });
  }


  /**
   * Opens up the contact app or notifies the user that the app does not exist on the device.
   *
   * @param btn This is required for the contacts app to open
   */
  public void launchContactPicker(View btn) {

    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
        ContactsContract.Contacts.CONTENT_URI);
    IntentChecker checker = new IntentChecker(this);
    if (!checker.isIntentSafeToLaunch(contactPickerIntent)) {
      displayInstallContactsAppDialog();
    } else {
      startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

  }


  /**
   * Brings in data from the contacts application and stores it in the application data.
   *
   * @param cid This is a unique identifier used by the contacts application to specify a contact
   */
  private void createContactFromSelection(String cid) {
    // TODO: john,stephen This method is pretty heavy and should probably be moved outside the
    // controller and into a model method.

    ContentResolver cr = getContentResolver();

    String[] projection = new String[]{ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER};

    Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cid, null, null);

    // no phone number was available for the contact, or the contact id couldn't be found.
    if (cursor == null || cursor.getCount() < 1) {
      displayInvalidContactAlert();
      return;
    }

    // Locations of our name and phone number within the projection
    int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
    int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);


    // Our new Support Contact
    SupportContact contact = null;
    // Making a temporary list to store phone numbers to display to the user
    List<String> phoneList = new ArrayList<>();

    // A loop to collect all phone numbers for our contact into a list for display later
    while (cursor.moveToNext()) {

      // A place holder for whatever phone number is currently selected
      String number = cursor.getString(indexNumber);

      // A place holder for the name of the current contact
      String name = cursor.getString(indexName);

      contact = new SupportContact(cid, name, number);
      phoneList.add(number);
    }

    // some data structure to hold the contact && the list of phone numbers to pass back.

    cursor.close();

    if (phoneList.size() > 1) {
      displayPhoneSelectionDialog(contact, phoneList);
    } else {
      saveContactData(contact);
      setupSupportContactsList();
    }
  }


  /**
   * Allows a user to specify which phone number to save for a support network contact when multiple
   * numbers are associated with this contact in the Google Contacts application.
   *
   * @param contact         The specific contact who possesses multiple phone numbers in the
   *                        Contacts app
   * @param potentialPhones A list of phone numbers associated with our selected contact
   */
  private void displayPhoneSelectionDialog(final SupportContact contact,
                                           final List<String> potentialPhones) {

    AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
    builderSingle.setIcon(android.R.drawable.ic_dialog_alert);
    builderSingle.setTitle("Select the phone to contact:-");

    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        this,
        android.R.layout.select_dialog_singlechoice, potentialPhones);

    builderSingle.setNegativeButton(
        android.R.string.cancel,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });

    builderSingle.setAdapter(
        arrayAdapter,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // grab the contact they selected
            String phone = potentialPhones.get(which);
            contact.setPhoneNumber(phone);
            saveContactData(contact);
            setupSupportContactsList();
            dialog.dismiss();
          }
        });
    builderSingle.show();
  }

  /**
   * When a user selects a contact from the Contacts app contact picker, this
   * adds the contacts information as a new contact in our support network list.
   *
   * @param requestCode Requests submitted to the Contacts app
   * @param resultCode  Results from the Contacts app request
   * @param data        Data returned from the Contacts app
   */
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == RESULT_OK && requestCode == CONTACT_PICKER_RESULT) {
      Uri result = data.getData();

      Log.v(Constants.LOG_TAG, "uri is " + result.toString());

      String id = result.getLastPathSegment();
      createContactFromSelection(id);

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
   * Displays an Alert box representing an error. The error will contain the given title
   * and message passed in from the resources string file. IE R.string.message.
   *
   * @param titleStringId   The resource string id for the alert title
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

  /**
   * Persists the contact out through our network service.
   *
   * @param contact The contact we want to save.
   */
  private void saveContactData(SupportContact contact) {
    if (contact != null) {
      SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
      service.addSupportContact(contact);
    }
  }
}