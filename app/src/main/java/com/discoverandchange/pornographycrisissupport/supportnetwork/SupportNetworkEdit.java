package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;

/**
 * Handles the editing of a SupportContact.
 */
public class SupportNetworkEdit extends BaseNavigationActivity {

  /**
   * The SupportContact being edited.
   */
  private SupportContact editContact = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_support_network_edit);

    // Displaying information relating to the contact being edited
    Intent intent = getIntent();
    editContact = (SupportContact) intent.getSerializableExtra(Constants
        .SUPPORT_CONTACT_EDIT_MESSAGE);
    if (editContact == null) {
      // TODO: stephen, john handle null case
    }

    Switch viewSwitch = (Switch) findViewById(R.id.contactCrisisSwitch);

    TextView nameText = (TextView) findViewById(R.id.contactNameText);
    nameText.setText(editContact.getName());
    viewSwitch.setChecked(editContact.isCrisisContact());

    TextView phoneText = (TextView) findViewById(R.id.txtPhoneNumber);
    phoneText.setText(editContact.getPhoneNumber());
  }


  /**
   * When a user clicks the save button. Update the SupportContact properties and save the contact.
   *
   * @param clickedButton The button that was clicked.
   */
  public void saveSupportContact(View clickedButton) {
    Switch viewSwitch = (Switch) findViewById(R.id.contactCrisisSwitch);
    editContact.setIsCrisisContact(viewSwitch.isChecked());
    SupportNetworkService.getInstance(getBaseContext()).saveSupportContact(editContact);
  }
}
