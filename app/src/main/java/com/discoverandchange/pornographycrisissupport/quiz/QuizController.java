package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;
import com.discoverandchange.pornographycrisissupport.library.LibraryController;
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

    // gets back the data for the display
    String[] from = {ScoresTable.SCORE};
    int[] to = {android.R.id.text1};
    cursorAdapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_1, null, from, to, 0);
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
      sendSupportNetworkTexts();

      // After saving quiz, launch the dialer
      launchDialer();
    } else {
      launchLibrary();
    }
  }

  /**
   * Sends out the crisis text message to people that you are really struggling.
   */
  private void sendSupportNetworkTexts() {
    SupportNetworkService.getInstance(getBaseContext()).contactNetwork();
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
}