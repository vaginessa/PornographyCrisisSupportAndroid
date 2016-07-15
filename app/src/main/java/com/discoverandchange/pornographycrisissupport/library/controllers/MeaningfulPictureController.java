package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.MeaningfulPictureResource;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

import java.io.File;

/**
 * Displays the Meaningful Picture resource.
 */
public class MeaningfulPictureController extends BaseNavigationActivity {

  /**
   * Creates the picture view or displays an error message if it failed to load.
   * @param savedInstanceState {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meaningful_picture_controller);

    Intent intent = getIntent();
    Object obj = intent.getSerializableExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (obj == null || !(obj instanceof MeaningfulPictureResource)) {
      Log.wtf(Constants.LOG_TAG, "Sent null meaningful picture resource when we shouldn't have");
      finish();
      return;
    }

    final TextView txtNoImage = (TextView)findViewById(R.id.txtMeaningfulPictureNoImage);
    // default to invisible so we show it only on the error.
    txtNoImage.setVisibility(View.INVISIBLE);

    MeaningfulPictureResource resource = (MeaningfulPictureResource)obj;
    ImageView imageView = (ImageView)findViewById(R.id.imageMeaningfullPicture);
    if (StringUtils.isEmpty(resource.getUrl())) {
      Log.wtf(Constants.LOG_TAG, "sent invalid meaningful picture resource");
    }


    Uri uri = Uri.parse(resource.getUrl());
    Picasso.with(getBaseContext()).load(uri).into(imageView,
        new Callback() {
          @Override
          public void onSuccess() {
            txtNoImage.setVisibility(View.INVISIBLE);
          }

          @Override
          public void onError() {
            txtNoImage.setVisibility(View.VISIBLE);
          }
        });
  }
}