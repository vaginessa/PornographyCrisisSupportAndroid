package com.discoverandchange.pornographycrisissupport.utils;

import android.net.Uri;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.squareup.picasso.Picasso;

/**
 * Handles the logging and displaying of errors when a picasso image fails to load.
 */
public class PicassoListener implements Picasso.Listener {

  /**
   * Logs that the image failed to load and the specific uri for debugging purposes.
   * @param picasso {@inheritDoc}
   * @param uri {@inheritDoc}
   * @param exception {@inheritDoc}
   */
  @Override
  public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
    Log.e(Constants.LOG_TAG, "Failed to load image for uri: " + uri.toString(), exception);
  }
}
