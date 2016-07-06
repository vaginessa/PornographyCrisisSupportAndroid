package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.AudioResource;

import java.io.IOException;

/**
 * Handles the display and control of an AudioResource.
 */
public class AudioActivityController extends BaseNavigationActivity {

  // The android media player resource for our audio file.

  MediaPlayer mediaPlayer;

  // A variable to hold our video to play
  private AudioResource audioResource = null;

  /**
   * Loads up the audio view buttons and prepares the audio for playback.
   * @param savedInstanceState the saved data we want to restore from.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_audio_activity_controller);

    // To utilize link data from the selected audioResource
    Intent intent = getIntent();
    audioResource = (AudioResource) intent.getSerializableExtra(Constants
            .LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (audioResource == null) {
      Log.wtf(Constants.LOG_TAG, "Sent null audio resource when we shouldn't have");
      finish();
      return;
    }

    TextView title = (TextView)findViewById(R.id.audioTitle);
    title.setText(audioResource.getTitle());

    // Handling the play button
    Button play = (Button) findViewById(R.id.play);
    play.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View view) {
        String url = audioResource.getUrl();

        if (mediaPlayer == null) {
          mediaPlayer = new MediaPlayer();

          mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long for buffering, etc.
          } catch (IOException ex) {
            Log.e(Constants.LOG_TAG, "Error Connecting", ex);
          }
          mediaPlayer.start();
        }
      }
    });


    // Handling the stop button
    Button stop = (Button) findViewById(R.id.stop);
    stop.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
          mediaPlayer.stop();
          mediaPlayer.release();
          mediaPlayer = null;
        }
      }
    });
  }

  /**
   * Handling destruction of this activity to release media.
   */
  protected void onDestroy() {
    if (mediaPlayer != null) {
      mediaPlayer.release();
      mediaPlayer = null;
    }
    super.onDestroy();

  }
}
