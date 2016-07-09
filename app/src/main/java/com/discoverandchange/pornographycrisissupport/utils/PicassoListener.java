package com.discoverandchange.pornographycrisissupport.utils;

import android.net.Uri;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by snielson on 7/8/16.
 */
public class PicassoListener implements Picasso.Listener {
  @Override
  public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
    Log.e(Constants.LOG_TAG, "Failed to load image for uri: " + uri.toString(), exception);
  }
}
