package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.discoverandchange.pornographycrisissupport.App;
import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.LibraryController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import java.net.URI;

public class QuizController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
  }

  public void handleSubmitQuizClick(View btn) {
    SeekBar slider = (SeekBar)findViewById(R.id.cravingsLevelSlider);
    if (slider == null) {
      throw new RuntimeException("Could not find slider.  Check id is named properly");
    }

    Log.d("PornAddictionSupport", "handleSubmitQuizClick() called with: " + "btn = [" + btn + "]" + " slider equal to " + slider.getProgress());

    //Intent intent = new Intent(getBaseContext(), LibraryController.class);
    //startActivity(intent);

    launchDialer();
  }

  public void launchDialer() {
    //EndCallListenerTest listener = new EndCallListenerTest();
    SupportNetworkService service = new SupportNetworkService(SmsManager.getDefault());
    SupportContact contact = service.getCrisisSupportContact();


    EndCallListener listener = new EndCallListener();
    TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
    mTM.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

    Uri call = Uri.parse("tel:" + contact.getPhoneNumber());
    Intent intent = new Intent(Intent.ACTION_DIAL, call) ;
    startActivity(intent);
  }
}