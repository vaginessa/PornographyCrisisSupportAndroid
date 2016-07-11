package com.discoverandchange.pornographycrisissupport.quiz.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;
import com.discoverandchange.pornographycrisissupport.firstuse.controllers.first_use_controller;
import com.discoverandchange.pornographycrisissupport.library.controllers.LibraryController;
import com.discoverandchange.pornographycrisissupport.quiz.EndCallListener;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Controls the quiz and actions taken after saving a quiz score.
 * Actions are either launching the dialer or launching the library.
 *
 * @author Keith Higbee
 */
public class QuizController extends BaseNavigationActivity {

  private CursorAdapter cursorAdapter;

  /**
   * Creates the initial arrays and cursorAdapter for communicating with the database.
   *
   * @param savedInstanceState Any saved data needed for this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    if (isCrisisWidgetLaunch()) {
      QuizService service = new QuizService(getBaseContext());
      service.saveQuiz(new Quiz(10));
      handleCrisisQuizScore();
    } else if (isFirstUseSetupLaunch()) {
      launchFirstUseSetup();
    } else {
      // gets back the data for the display
      String[] from = {ScoresTable.SCORE};
      int[] to = {android.R.id.text1};
      cursorAdapter = new SimpleCursorAdapter(this,
          android.R.layout.simple_list_item_1, null, from, to, 0);
    }
  }

  /**
   * Handles the flow of saving a quiz score.  If the score is a crisis score, it
   * launches the dialer.  Otherwise, it launches the library
   *
   * @param btn Used for determining debugging
   */
  public void handleSubmitQuizClick(View btn) {
    SeekBar slider = (SeekBar) findViewById(R.id.cravingsLevelSlider);
    if (slider == null) {
      throw new RuntimeException("Could not find slider.  Check id is named properly");
    }

    Log.d("PornAddictionSupport", "handleSubmitQuizClick() called with: "
        + "btn = [" + btn + "]" + " slider equal to " + slider.getProgress());

    // save slider value to new Quiz
    Quiz quiz = new Quiz();
    // slider is 0 based so we have to add one.  Converts 0 -> 9 into 1 -> 10
    quiz.setScore(slider.getProgress() + 1);

    // Save the score (passes the current context to the QuizService)
    boolean isCrisisQuizScore = (new QuizService(this)).saveQuiz(quiz);

    // get the latest score
    int latestScore = (new QuizService(this)).getLatestQuizScore();

    if (isCrisisQuizScore) {
      handleCrisisQuizScore();
    } else {
      launchLibrary();
    }
  }

  /**
   * Launch the resource library for people not in severe crisis mode.
   */
  public void launchLibrary() {
    Intent intent = new Intent(getBaseContext(), LibraryController.class);
    startActivity(intent);
  }

  /**
   * Launches the dialer if the support network has been created.
   */
  public void launchDialer() {
    SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
    SupportContact contact = service.getCrisisSupportContact();

    if (contact != null) {
      EndCallListener listener = new EndCallListener();
      TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context
          .TELEPHONY_SERVICE);
      telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

      Uri call = Uri.parse("tel:" + contact.getPhoneNumber());
      Intent intent = new Intent(Intent.ACTION_DIAL, call);
      startActivity(intent);
    } else {
      // if we don't have a crisis contact then we should just launch the library
      Log.d(Constants.LOG_TAG, "SupportContact crisis not found, launching library");
      launchLibrary();
    }
  }


  /**
   * Launches the first use setup.
   */
  private void launchFirstUseSetup() {
    Intent intent = new Intent(this, first_use_controller.class);
    startActivity(intent);
    finish();
  }

  /**
   * Checks to see if the first time launch setup has not been completed
   * @return True if the setup of the app has not been finished.
   */
  private boolean isFirstUseSetupLaunch() {
    // Execute another the first use checklist if hasn't been opened before
    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

    // Store the value temporarily to ensure that we get a boolean true / false instead of always true
    boolean isSetupFinished = pref.getBoolean("activity_executed", false);
    return !isSetupFinished;
  }

  /**
   * Checks if this activity was launched from the widget crisis button.
   * @return Returns true if this is the crisis widget launch.
   */
  private boolean isCrisisWidgetLaunch() {
    boolean isCrisisLaunchFromWidget = false;
    Intent intent = getIntent();
    if (intent != null) {
      isCrisisLaunchFromWidget = intent.getBooleanExtra("isCrisisLaunchFromWidget", false);

    }
    return isCrisisLaunchFromWidget;
  }


  /**
   * Sends off text messages and launches the dialer when we have a crisis quiz score.
   */
  private void handleCrisisQuizScore() {
    sendSupportNetworkTexts();

    // After saving quiz, launch the dialer
    launchDialer();
  }

  /**
   * Sends out the crisis text message to people that you are really struggling.
   */
  private void sendSupportNetworkTexts() {
    SupportNetworkService.getInstance(getBaseContext()).contactNetwork();
  }
}