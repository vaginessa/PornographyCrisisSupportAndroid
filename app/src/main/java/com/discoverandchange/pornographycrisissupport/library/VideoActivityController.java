package com.discoverandchange.pornographycrisissupport.library;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;

/**
 * Created by oogle on 6/23/2016.
 * Displays a video library resource
 * Controls interactions with video.
 * Returns to Library Screen
 *
 * Requires Permissions: "android.permission.INTERNET" & "android.permission.WAKE_LOCK"
 *
 * Helpful Resources:
 *      https://developer.android.com/guide/topics/media/mediaplayer.html (Initial Setup)
 *      https://developer.android.com/guide/appendix/media-formats.html   (Compatible Media)
 *      https://youtu.be/IVGE5J7-3AQ                                      (Best Instructions)
 */


public class VideoActivityController extends BaseNavigationActivity {

  MediaPlayer mediaPlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_activity_controller);

    // Handling the play button
    Button play = (Button) findViewById(R.id.play);
    play.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dummy);  //  <THIS WAS JUST A SAMPLE FROM THE YOUTUBE VIDEO>
//        Uri path = Uri.parse("https://www.discoverandchange.com/wp-content/uploads/2016/04/Relapse-Prevention-Strategies.mp3");

        mediaPlayer = MediaPlayer.create(VideoActivityController.this, path);
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

