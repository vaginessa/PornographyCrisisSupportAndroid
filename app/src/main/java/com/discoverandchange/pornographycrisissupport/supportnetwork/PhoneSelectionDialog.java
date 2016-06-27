package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * Opens a dialog from which the user can select a phone number to attach to a support network
 * contact (among those provided by the Google Contacts application).
 * @author snielson
 */
public class PhoneSelectionDialog extends DialogFragment {

    /**
     * Creates a dialog box from which the user can select one phone number from a list.
     * @param savedInstanceState Information about what we will put in the dialog
     * @return The dialog box we are to create
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    // TODO: stephen, John this is not built, just something I was playing around
    // with in terms of a dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(R.string.library_controller_title)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
          }
        });
    // Create the AlertDialog object and return it
    return builder.create();
  }

}
