package com.discoverandchange.pornographycrisissupport.firstuse.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.controllers.LibraryController;
import com.discoverandchange.pornographycrisissupport.library.controllers.MeaningfulPictureController;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizController;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizHistoryController;
import com.discoverandchange.pornographycrisissupport.settings.controllers.MeaningfulPictureSettingsController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.controllers.SupportNetworkListController;

import java.io.IOException;

public class FirstUseController extends AppCompatActivity {

  // TODO: John & Stephen Robust verification that steps are being completed

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_use_controller);

    final BaseNavigationActivity baseActivity;

    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
    int stepValue = pref.getInt("step", 1);

    updateState(stepValue);

    // Handling the support network button
    Button support = (Button) findViewById(R.id.checklistButtonSupport);
    support.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Do Something
        Intent intent = new Intent(getBaseContext(), SupportNetworkListController.class);
        startActivity(intent);
        updateState(2);
      }

    });

    // Handling the select inspiring image button
    Button image = (Button) findViewById(R.id.checklistButtonImage);
    image.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(getBaseContext(), MeaningfulPictureController.class);
        startActivity(intent);
        updateState(3);
      }
//    baseActivity.launchActivity(SupportNetworkListController.class);
    });

    // Handling the send test message button
    Button test = (Button) findViewById(R.id.checklistButtonTest);
    test.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        updateState(4);

      }
//    baseActivity.launchActivity(SupportNetworkListController.class);
    });

    // Handling the take quiz message button
    Button quiz = (Button) findViewById(R.id.checklistButtonQuiz);
    quiz.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        updateState(5);

      }
//    baseActivity.launchActivity(SupportNetworkListController.class);
    });
  }

  public void updateState(int newState) {
    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
    SharedPreferences.Editor ed = pref.edit();
    ed.putInt("step", newState);
    if (newState >= 4) {
      ed.commit();
    }

    // To use our First Use Checklist in order
    final int initialStep = 1;
    final int supportStep = 2;
    final int imageStep = 3;
    final int testStep = 4;
    final int defaultStep = 5;


    Button support = (Button) findViewById(R.id.checklistButtonSupport);
    Button image = (Button) findViewById(R.id.checklistButtonImage);
    Button test = (Button) findViewById(R.id.checklistButtonTest);
    Button quiz = (Button) findViewById(R.id.checklistButtonQuiz);
    // And other buttons...

    support.setEnabled(false);
    image.setEnabled(false);
    test.setEnabled(false);
    quiz.setEnabled(false);
    // And other buttons...

    switch (newState) {
      case initialStep:
        support.setEnabled(true);
        break;
      case supportStep:
        image.setEnabled(true);
        break;
      case imageStep:
        Intent intentPic = new Intent(getBaseContext(), MeaningfulPictureSettingsController.class);
        startActivity(intentPic);
        test.setEnabled(true);
        break;
      case testStep:
        ed.putBoolean("activity_executed", true);
        ed.commit();
        Intent intent = new Intent(getBaseContext(), TestMessageController.class); // Placeholder for testMessage
        startActivity(intent);
        quiz.setEnabled(true);
        break;
      // Add more button values here if new items added to checklist...
      case defaultStep:
        Intent intentQuiz = new Intent(getBaseContext(), QuizController.class);
        startActivity(intentQuiz);
        break;

    }
  }
}
