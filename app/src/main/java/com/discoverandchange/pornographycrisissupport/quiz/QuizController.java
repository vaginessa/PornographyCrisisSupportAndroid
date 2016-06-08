package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.LibraryController;

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
    Uri call = Uri.parse("tel:" + "801-555-5555");
    Intent intent = new Intent(Intent.ACTION_DIAL, call) ;
    startActivity(intent);
  }
}