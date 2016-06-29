package com.discoverandchange.pornographycrisissupport.library;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;

/**
 * Displays a video library resource
 * Controls interactions with video-
 * Returns to Library Screen.
 *
 * @author john.okleberry@gmail.com
 */
public class VideoActivityController extends BaseNavigationActivity {

  //  Requires Permissions: "android.permission.INTERNET" & "android.permission.WAKE_LOCK"
  //          *
  //          * Helpful Resources:
  //          *      https://developer.android.com/guide/topics/media/mediaplayer.html (Initial Setup)
  //          *      https://developer.android.com/guide/appendix/media-formats.html   (Compatible Media)
  //          *      https://youtu.be/IVGE5J7-3AQ                                      (Best Instructions)
  //          *      http://stackoverflow.com/questions/21849602/android-how-to-play-mp4-video-from-url/21849982#21849982

  // TODO: John Replace with mediaController in the stop controls?
  MediaPlayer mediaPlayer;


  /**
   * Creates a view for playback of video resource content and allows for playback controls to be
   * implemented.
   *
   * @param savedInstanceState The information necessary to create this view
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {

      setContentView(R.layout.activity_video_activity_controller);
      String link = "https://www.discoverandchange.com/wp-content/uploads/2016/02/DiscoverAndChangeIntroVideo.mp4";
      VideoView videoView = (VideoView) findViewById(R.id.videoView);
      MediaController mediaController = new MediaController(VideoActivityController.this);
      mediaController.setAnchorView(videoView);
      Uri video = Uri.parse(link);
      videoView.setMediaController(mediaController);
      videoView.setVideoURI(video);
    } catch (Exception ex) {
      // TODO: John handle exception
      Log.e(Constants.LOG_TAG, "Error Connecting", ex);
    }


    // Handling the play button
    Button play = (Button) findViewById(R.id.play);
    play.setOnClickListener(new View.OnClickListener() {

      VideoView videoView = (VideoView) findViewById(R.id.videoView);

      @Override
      public void onClick(View view) {
        videoView.start();
      }

    });


    //
    // Needs to be fixed, kills the video controller as it stands
    //

    // Handling the stop button
    Button stop = (Button) findViewById(R.id.stop);
    stop.setOnClickListener(new View.OnClickListener() {

      VideoView videoView = (VideoView) findViewById(R.id.videoView);

      @Override
      public void onClick(View view) {
        if (view != null && videoView.isPlaying()) {
          videoView.stopPlayback();
          // videoView.release();
        }
      }
    });
  }

  /**
   * Handles the destruction of this activity to release media.
   */
  protected void onDestroy() {

    VideoView videoView = (VideoView) findViewById(R.id.videoView);

    if (videoView != null && videoView.isPlaying()) {
      videoView.stopPlayback();
      videoView = null;
    }
    super.onDestroy();

  }

}

