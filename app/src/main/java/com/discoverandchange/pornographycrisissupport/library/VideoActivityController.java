package com.discoverandchange.pornographycrisissupport.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
 *
 */


public class VideoActivityController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_activity_controller);
  }
}
