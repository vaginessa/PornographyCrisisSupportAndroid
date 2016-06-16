package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * Created by snielson on 6/15/16.
 */
public class PhoneSelectionDialog extends DialogFragment {

  @Override
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
