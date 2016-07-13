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
import com.discoverandchange.pornographycrisissupport.utils.DialogHelper;

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
   * Boolean that tells an audio player to start playing as soon as it's prepare async method
   * is done.
   */
  private boolean startPlaying = false;

  /**
   * TODO: stephen test audio playback.
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
  }

  /**
   * Starts playing the audio.
   * @param startBtn The start button that was clicked
   */
  public void startPlayingAudio(View startBtn) {


    if (mediaPlayer == null) {
      createMediaPlayer();
    }
    startPlaying = true;
    mediaPlayer.prepareAsync(); // make this an async operation.

    setButtonEnabled(R.id.start, false);
    setButtonEnabled(R.id.stop, true);
  }

  /**
   * Stops the media from playing.
   * @param stopBtn The stop button that was clicked.
   */
  public void stopPlayingAudio(View stopBtn) {
    if (mediaPlayer != null && startPlaying) {
      mediaPlayer.stop();
      startPlaying = false;
    }
    setButtonEnabled(R.id.stop, false);
    setButtonEnabled(R.id.start, false);

    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
      mediaPlayer.stop();
    }
  }

  /**
   * Enables/Disables the passed in button id.
   * @param buttonId The resource id for the button we want to toggle.
   * @param isEnabled Whether to enable or disable the button.
   */
  private void setButtonEnabled(int buttonId, boolean isEnabled) {
    Button btn = (Button)findViewById(buttonId);
    if (btn != null) {
      btn.setEnabled(isEnabled);
    }
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

  /**
   * Creates The MediaPlayer for the audio and has it start playing as soon as it's asyncronously
   * finished loading it's data.
   * @return
   */
  private MediaPlayer createMediaPlayer() {
    String url = audioResource.getUrl();
    final MediaPlayer player = new MediaPlayer();

    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    try {
      player.setDataSource(url);
      player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
          if (startPlaying) {
            player.start();
          }
        }
      });

      // set the error listener in case we have problems playing the audio.
      player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
          Log.e(Constants.LOG_TAG, "AudioActivityController error playing audio. what code: "
              + what + "extra code: " + extra);
          DialogHelper.displayErrorDialog(getBaseContext(),
              R.string.audio_controller_play_error_title, R.string.audio_controller_play_error);
          return true;
        }
      });

    } catch (IOException ex) {
      Log.e(Constants.LOG_TAG, "Error Connecting", ex);
      DialogHelper.displayErrorDialog(getBaseContext(),
          R.string.audio_controller_play_error_title,
          R.string.audio_controller_play_error);
    }
    return player;
  }
}
