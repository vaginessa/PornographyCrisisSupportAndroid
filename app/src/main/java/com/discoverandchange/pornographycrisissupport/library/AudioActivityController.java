package com.discoverandchange.pornographycrisissupport.library;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;

import java.io.IOException;

/**
 * Handles the display and control of an AudioResource.
 */
public class AudioActivityController extends BaseNavigationActivity {


  // TODO: john Add in pause button for better playback options if there's time

  /**
   * The android media player resource for our audio file.
   */
  MediaPlayer mediaPlayer;

  /**
   * Loads up the audio view buttons and prepares the audio for playback.
   * @param savedInstanceState the saved data we want to restore from.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_audio_activity_controller);

    // Handling the play button
    Button play = (Button) findViewById(R.id.play);
    play.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View view) {
        String url = "https://www.discoverandchange.com/wp-content/uploads/2016/04/"
            + "Relapse-Prevention-Strategies.mp3";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
          mediaPlayer.setDataSource(url);
          mediaPlayer.prepare(); // might take long for buffering, etc.
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        mediaPlayer.start();

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
        }
      }
    });
  }

  /**
   * Handling destruction of this activity to release media.
   */
  protected void onDestroy() {
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      mediaPlayer.stop();
      mediaPlayer.release();
      mediaPlayer = null;
    }
    super.onDestroy();

  }
}
