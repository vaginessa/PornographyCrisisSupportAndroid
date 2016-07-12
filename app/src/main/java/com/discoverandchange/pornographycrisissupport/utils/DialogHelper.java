package com.discoverandchange.pornographycrisissupport.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Responsible for display dialog messages that are used throughout the app.
 */
public class DialogHelper {


  /**
   * Displays an Alert box representing an error. The error will contain the given title
   * and message passed in from the resources string file. IE R.string.message.
   *
   * @param titleStringId   The resource string id for the alert title
   * @param messageStringId The resource string id for the alert message displayed.
   */
  public static void displayErrorDialog(Context context, int titleStringId, int messageStringId) {
    new AlertDialog.Builder(context)
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
}
