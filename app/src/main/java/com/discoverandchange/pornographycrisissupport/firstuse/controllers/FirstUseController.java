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

  /**
   * Setup button handler that will send the user onto the next activity setup as determined
   * by our FirstUseChecklistService
   * @param btn The button the user clicked on.
   */
  public void sendToNextStep(View btn) {
    FirstUseChecklistService service = FirstUseChecklistService.getInstance(getBaseContext());
    service.launchCurrentSetupActivity(this);
  }

  private void handleSetupCompleted() {
    Intent intent = new Intent(getBaseContext(), QuizController.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
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
}
