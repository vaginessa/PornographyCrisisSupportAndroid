package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.MeaningfulPictureResource;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MeaningfulPictureController extends BaseNavigationActivity {

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

    MeaningfulPictureResource resource = (MeaningfulPictureResource)obj;
    File file = new File(resource.getUrl());
    ImageView imageView = (ImageView)findViewById(R.id.imageMeaningfullPicture);
    Picasso.with(getBaseContext()).load(file).into(imageView);
  }
}
