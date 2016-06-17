package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;

public class SupportNetworkEdit extends BaseNavigationActivity {

    private SupportContact editContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_network_edit);

        Switch viewSwitch = (Switch)findViewById(R.id.contactCrisisSwitch);

        // Displaying information relating to the contact being edited
        Intent intent = getIntent();
        editContact = (SupportContact) intent.getSerializableExtra(Constants.SUPPORT_CONTACT_EDIT_MESSAGE);
        if (editContact == null) {
            // TODO: stephen, john handle null case.
        }

        TextView editView = (TextView)findViewById(R.id.contactNameText);
        editView.setText(editContact.getName());
        viewSwitch.setChecked(editContact.isCrisisContact());

    }


  /**
   * When a user clicks the save button. Update the SupportContact properties and save the contact.
   * @param clickedButton The button that was clicked.
   */
  public void saveSupportContact(View clickedButton){
        Switch viewSwitch = (Switch)findViewById(R.id.contactCrisisSwitch);
        editContact.setIsCrisisContact(viewSwitch.isChecked());
        SupportNetworkService.getInstance(getBaseContext()).saveSupportContact(editContact);
    }
}
