package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;

public class SupportNetworkEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_network_edit);




        // Displaying information relating to the contact being edited
        Intent intent = getIntent();
        SupportContact editContact = (SupportContact) intent.getSerializableExtra(Constants.SUPPORT_CONTACT_EDIT_MESSAGE);
        TextView editView = (TextView) findViewById(R.id.contactNameText);

        // Questions to address:    Which XML file name are we using here? (activity_support_network_edit.xml)
        //                          Do I not need a schema to save to a database? (SQL database resource link)
        //


        // Save / Persist the contact information to the database
        // 1. Identify what info is to be saved
        // 2.
//        R.id.<name_from_xml_file>
//                contactNameTextView
//        R.id.contactNameText.getContactID();
//        SupportNetworkService.getInstance().addSupportContact(supportContact);


    }


    public void saveSupportContact(View clickedButton){

    }



}
