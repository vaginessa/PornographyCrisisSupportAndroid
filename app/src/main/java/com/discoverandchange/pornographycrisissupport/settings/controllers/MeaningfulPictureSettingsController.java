package com.discoverandchange.pornographycrisissupport.settings.controllers;

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
import com.discoverandchange.pornographycrisissupport.settings.SettingsService;
import com.discoverandchange.pornographycrisissupport.utils.PicassoListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MeaningfulPictureSettingsController extends BaseNavigationActivity {

  // some of the code of this class has been adapted from this example:
  // http://codetheory.in/android-pick-select-image-from-gallery-with-intents/

  private int PICK_IMAGE_REQUEST = 1;

  // TODO: stephen finish documenting this class.
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meaningfull_picture_settings_controller);
    this.loadInitialPicture();
  }

  public void handleSelectPicture(View btn) {
    Intent intent = new Intent();
    // Show only images, no videos or anything else
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    // Always show the chooser (if there are multiple options available)
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }

  public void handleClearImage(View btn) {
    ImageView imageCurrentMeaningfullPicture =
        (ImageView) findViewById(R.id.imageCurrentMeaningfullPicture);
    // clear it out.
    imageCurrentMeaningfullPicture.setImageResource(0);
    SettingsService service = SettingsService.getInstance(getBaseContext());
    service.clearMeaningfulPicture();

    final TextView txtNoImage = (TextView) findViewById(R.id.txtViewMeaningfullPictureDefaultMessage);
    txtNoImage.setVisibility(View.VISIBLE);
  }

  private void loadInitialPicture() {
    ImageView imageCurrentMeaningfullPicture =
        (ImageView) findViewById(R.id.imageCurrentMeaningfullPicture);

    final TextView txtNoImage = (TextView) findViewById(R.id.txtViewMeaningfullPictureDefaultMessage);

    SettingsService service = SettingsService.getInstance(getBaseContext());
    Uri uri = service.getMeaningfulPictureUri();

    // no uri, nothing to load.
    if (uri == null) {
      return;
    }

    new Picasso.Builder(getBaseContext())
        .listener(new PicassoListener())
        .build()
        .load(uri)
        .fit()
        .centerInside()
        .into(imageCurrentMeaningfullPicture, new Callback() {
      @Override
      public void onSuccess() {
        Log.d(Constants.LOG_TAG, "meaningfull picture loaded fine.");
        txtNoImage.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onError() {
        Log.d(Constants.LOG_TAG, "meaningfull picture failed to load");
        txtNoImage.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    ImageView imageCurrentMeaningfullPicture =
        (ImageView)findViewById(R.id.imageCurrentMeaningfullPicture);

    final TextView txtNoImage = (TextView)findViewById(R.id.txtViewMeaningfullPictureDefaultMessage);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {

      final Uri uri = data.getData();

      grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

      final int readFlagGranted = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
      if (readFlagGranted == Intent.FLAG_GRANT_READ_URI_PERMISSION) {
        // compiler doesn't like sending in readFlagGranted, so to surpress the warning we send it in
        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
      }

      // make sure to cancel any current load requests, in case they select an image before
      // the default one has loaded.
      Picasso.with(getBaseContext()).cancelRequest(imageCurrentMeaningfullPicture);
      Picasso.with(getBaseContext()).load(uri)
          .fit().into(imageCurrentMeaningfullPicture, new Callback() {
        @Override
        public void onSuccess() {
          txtNoImage.setVisibility(View.INVISIBLE);
          SettingsService service = SettingsService.getInstance(getBaseContext());
          service.saveMeaningfulPicture(uri);
        }

        @Override
        public void onError() {
          txtNoImage.setVisibility(View.VISIBLE);
        }
      });
    }
  }


}
