package com.discoverandchange.pornographycrisissupport.library;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;

public class AudioActivityController extends BaseNavigationActivity {

  MediaPlayer mediaPlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_audio_activity_controller);

    // Handling the play button
    Button play = (Button) findViewById(R.id.play);
    play.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dummy);    // <THIS WAS JUST A SAMPLE FROM THE YOUTUBE VIDEO>
  //      Uri path = Uri.parse("https://www.discoverandchange.com/wp-content/uploads/2016/04/Relapse-Prevention-Strategies.mp3");

        mediaPlayer = MediaPlayer.create(AudioActivityController.this, path);
        mediaPlayer.start();
      }
    });

    // Handling the stop button
    Button stop = (Button) findViewById(R.id.stop);
    play.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
        if(mediaPlayer.isPlaying()){
          mediaPlayer.stop();
          mediaPlayer.release();
        }
      }
    });
  }

  // Handling destruction of this activity to release media
  protected void onDestroy() {
    if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
      mediaPlayer.stop();
      mediaPlayer.release();
      mediaPlayer = null;
    }
    super.onDestroy();

  }
}
