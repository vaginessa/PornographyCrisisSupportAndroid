package com.discoverandchange.pornographycrisissupport.quiz;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.db.ScoresDbOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.ScoresProvider;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

public class QuizController extends BaseNavigationActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

  private CursorAdapter cursorAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    // gets back the data for the display
    String[] from = {ScoresDbOpenHelper.SCORE};
    int[] to = {android.R.id.text1};
    cursorAdapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_1, null, from, to, 0);

    // initiates the Loader--gets the data asynchronously
    getLoaderManager().initLoader(0, null, this);
  }

  public void handleSubmitQuizClick(View btn) {
    SeekBar slider = (SeekBar)findViewById(R.id.cravingsLevelSlider);
    if (slider == null) {
      throw new RuntimeException("Could not find slider.  Check id is named properly");
    }

    Log.d("PornAddictionSupport", "handleSubmitQuizClick() called with: " +
        "btn = [" + btn + "]" + " slider equal to " + slider.getProgress());

    //Intent intent = new Intent(getBaseContext(), LibraryController.class);
    //startActivity(intent);

    // Save the score (passes the current context to the QuizService)
    boolean saveQuiz = (new QuizService(this)).saveQuiz(slider.getProgress());

    // After saving quiz, launch the dialer
    launchDialer();
  }

  public void launchDialer() {
    //EndCallListenerTest listener = new EndCallListenerTest();
    SupportNetworkService service = SupportNetworkService.getInstance(getBaseContext());
    SupportContact contact = service.getCrisisSupportContact();

    EndCallListener listener = new EndCallListener();
    TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
    mTM.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

    Uri call = Uri.parse("tel:" + contact.getPhoneNumber());
    Intent intent = new Intent(Intent.ACTION_DIAL, call) ;
    startActivity(intent);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this, ScoresProvider.CONTENT_URI,
        null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    cursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    cursorAdapter.swapCursor(null);
  }
}