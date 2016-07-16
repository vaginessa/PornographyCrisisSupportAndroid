package com.discoverandchange.pornographycrisissupport.firstuse.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.firstuse.FirstUseChecklistService;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;


/**
 * Controls sending a test message during first time setup.
 *
 * @author Keith Higbee
 */
public class TestMessageController extends BaseNavigationActivity{

  boolean isUsername = false;
  String username;
  String message;
  /**
   * Creates the initial arrays and cursorAdapter for communicating with the database.
   *
   * @param savedInstanceState Any saved data needed for this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_message_controller);
  }

  @Override
  protected void onStart() {
    super.onStart();
    onPause();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (username == null) {
      setUserName();
    }
}

  @Override
  protected void onRestart() {
    super.onRestart();
  }

  /**
   * Prompt the user for their name.  This name is used as part of the
   * text message sent out during first time setup.
   */
  private void setUserName() {
    //setup the dialog box
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setMessage("Enter your name. It will be added to the message sent to your crisis contact")
        .setTitle("Please enter your name");

    final EditText input = new EditText(this);
    alert.setView(input);
    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        // save the input value for the username
        username = String.valueOf(input.getText());

        // set the message
        setDefaultMessage(username, message);
        dialog.dismiss();
      }
    });

    alert.show();
  }

  /**
   * Setup EditText box with default message.
   * Message can be changed.
   *
   * @param username Name of the user needing help
   * @param text Default message to be sent to the contacts
   */
  public void setDefaultMessage(String username, String text) {
    this.message = text;
    message = "You have been added to the support network for " +
        username + ". You will be sent a message at this phone number when " +
        username + " reports that they are in need of support.";

    EditText editText = (EditText)findViewById(R.id.test_message);
    editText.setText(message, TextView.BufferType.EDITABLE);
  }

  /**
   * Handles the flow of saving a quiz score.  If the score is a crisis score, it
   * launches the dialer.  Otherwise, it launches the library
   *
   * @param btn Used for determining debugging
   */
  public void handleSendTestMessageClick(View btn) throws Exception {
    sendSupportNetworkTexts();
  }


  /**
   * Sends out the crisis text message to people that you are really struggling.
   */
  private void sendSupportNetworkTexts() throws Exception {
    boolean isSuccess = false;
    try {
      SupportNetworkService.getInstance(getBaseContext()).testSendMessage(message);
      isSuccess = true;
    }
    catch (Exception ex) {
      throw new Exception(ex.toString());
    }

    if (isSuccess) {
      // go back to previous activity
      FirstUseChecklistService firstUseChecklistService = FirstUseChecklistService
          .getInstance(getBaseContext());
      firstUseChecklistService.markStepComplete(FirstUseChecklistService.TEST_STEP);
      finish(); // go back to our caller
    }
  }
}
