package com.discoverandchange.pornographycrisissupport.firstuse.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.firstuse.FirstUseChecklistService;
import com.discoverandchange.pornographycrisissupport.library.controllers.MeaningfulPictureController;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.controllers.SupportNetworkListController;

/**
 * Displays a checklist for the first time user to interact with.
 */
public class FirstUseController extends AppCompatActivity {

  /**
   * The very start of the setup process.
   */
  private static final int INITIAL_STEP = 1;

  /**
   * Setup the support network setup step.
   */
  private static final int SUPPORT_STEP = 2;

  /**
   * Setup the meaningful image setup step.
   */
  private static final int IMAGE_STEP = 3;

  /**
   * Verify our support network is being notified that we are contacting them.
   */
  private static final int TEST_STEP = 4;

  /**
   * The last step of our setup.
   */
  private static final int FINAL_STEP = TEST_STEP;

  /**
   * State to indicate we finished setting up the controller.
   */
  private static final int COMPLETED_STEP = 5;

  /**
   * Name of the shared preferences file we use for tracking the initial setup.
   */
  private static final String FIRST_USE_PREFERENCES = "FirstUsePreferences";

  /**
   * Allows the user to interact with the checklist buttons.
   *
   * @param savedInstanceState Allows app state to be saved before checklist opened
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_use_controller);
  }

  @Override
  protected void onResume() {
    super.onResume();

    // either update our buttons because we've come back from a previous setup activity
    // or someone has created us.
    FirstUseChecklistService service = FirstUseChecklistService.getInstance(getBaseContext());
    if (service.isSetupComplete()) {
      handleSetupCompleted();
    } else {
      updateStepButtonsForState(service.getCurrentSetupStep());
    }
  }

  private void updateStepButtonsForState(int currentSetupStep) {

    Button support = (Button) findViewById(R.id.checklistButtonSupport);
    Button image = (Button) findViewById(R.id.checklistButtonImage);
    Button test = (Button) findViewById(R.id.checklistButtonTest);
    // And other buttons...

    support.setEnabled(false);
    image.setEnabled(false);
    test.setEnabled(false);
    // And other buttons...

    switch (currentSetupStep) {
      case FirstUseChecklistService.SUPPORT_STEP: {
        support.setEnabled(true);
      }
      break;
      case FirstUseChecklistService.IMAGE_STEP: {
        image.setEnabled(true);
      }
      break;
      case FirstUseChecklistService.TEST_STEP: {
        test.setEnabled(true);
      }
      break;
      default: {
        Log.d(Constants.LOG_TAG, "updateStepButtonsForState called with invalid state of "
            + currentSetupStep);
      }
      break;
    }
  }

  public void sendToNextStep(View btn) {
    FirstUseChecklistService service = FirstUseChecklistService.getInstance(getBaseContext());
    service.launchCurrentSetupActivity(this);
  }

  /**
   * Allows the first button to open the SupportNetworkListController to add first contacts.
   *
   * @param btn The button the user clicked on.
   */
  public void sendToSupportNetwork(View btn) {
    //Do Something
    Intent intent = new Intent(getBaseContext(), SupportNetworkListController.class);
    startActivity(intent);
    updateState(IMAGE_STEP);
  }

  /**
   * Allows the second button to open the MeaningfulPictureController to add a user-selected pic.
   *
   * @param btn Required for the screen to be updated to the new Meaningful Picture activity.
   */
  public void sendToMeaningfulImage(View btn) {
    Intent intent = new Intent(getBaseContext(), MeaningfulPictureController.class);
    startActivity(intent);
    updateState(TEST_STEP);
  }

  /**
   * Allows the second button to open the TestMessageController to verify support network reached.
   *
   * @param btn Required for the screen to be updated to the new TestMessageController activity
   */
  public void sendToTestSms(View btn) {
    // Placeholder for testMessage
    Intent intent = new Intent(getBaseContext(), TestMessageController.class);
    startActivity(intent);
    updateState(COMPLETED_STEP);
  }

  /**
   * Walks the user through the first-time setup checklist one step at a time before letting the
   * user take their first cravings quiz.
   *
   * @param newState Used to change the checklist status over time, higher values are closer
   *                 to finish.
   */
  public void updateState(int newState) {
    SharedPreferences pref = getSharedPreferences(FIRST_USE_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor ed = pref.edit();
    ed.putInt("step", newState);
    if (newState != FINAL_STEP) {
      ed.commit();
    }

    Button support = (Button) findViewById(R.id.checklistButtonSupport);
    Button image = (Button) findViewById(R.id.checklistButtonImage);
    Button test = (Button) findViewById(R.id.checklistButtonTest);
    // And other buttons...

    support.setEnabled(false);
    image.setEnabled(false);
    test.setEnabled(false);
    // And other buttons...

    switch (newState) {
      case INITIAL_STEP: {
        support.setEnabled(true);
      }
      break;
      case SUPPORT_STEP: {
        image.setEnabled(true);
      }
      break;
      case IMAGE_STEP: {
        test.setEnabled(true);
      }
      break;
      case COMPLETED_STEP: {
        ed.putBoolean("activity_executed", true);
        ed.commit();
      }
      break;
      // Add more button values here if new items added to checklist...
      default: {
        Log.d(Constants.LOG_TAG, "UpdateState called with invalid state of " + newState);
      }
      break;
    }
  }

  private void handleSetupCompleted() {
    Intent intent = new Intent(getBaseContext(), QuizController.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }
}
