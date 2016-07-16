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
import com.discoverandchange.pornographycrisissupport.utils.IntentChecker;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.firstuse.FirstUseChecklistService;
import com.discoverandchange.pornographycrisissupport.settings.SettingsService;
import com.discoverandchange.pornographycrisissupport.utils.DialogHelper;
import com.discoverandchange.pornographycrisissupport.utils.PicassoListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Handles the setting of a meaningful picture that will display in the resource library.
 * Users can pick a picture from their gallery, or other applicable image providers, and save it.
 * This controller also handles the clearing of this setting.
 */
public class MeaningfulPictureSettingsController extends BaseNavigationActivity {

  // some of the code of this class has been adapted from this example:
  // http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
  /**
   * The callback identifier for when an image has been picked.
   */
  private static final int PICK_IMAGE_REQUEST = 1;

  /**
   * Handles the creation of the controller and the display of the saved picture if there is any.
   *
   * @param savedInstanceState {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meaningfull_picture_settings_controller);
    this.loadInitialPicture();
  }

  /**
   * Launches the pick picture intent or tells the user to install one if none is available.
   *
   * @param btn The button that was clicked.
   */
  public void handleSelectPicture(View btn) {

    IntentChecker checker = new IntentChecker(getBaseContext());

    Intent intent = new Intent();
    // Show only images, no videos or anything else
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    if (checker.isIntentSafeToLaunch(intent)) {
      // Always show the chooser (if there are multiple options available)
      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    } else {
      DialogHelper.displayErrorDialog(this, R.string.picture_app_missing_title,
          R.string.picture_app_missing);
    }
  }

  /**
   * Removes the picture from the controller as well as the saved settings value.
   *
   * @param btn The clear button that was clicked.
   */
  public void handleClearImage(View btn) {
    ImageView imageCurrentMeaningfullPicture =
        (ImageView) findViewById(R.id.imageCurrentMeaningfullPicture);
    // clear it out.
    imageCurrentMeaningfullPicture.setImageResource(0);
    SettingsService service = SettingsService.getInstance(getBaseContext());
    service.clearMeaningfulPicture();

    final TextView txtNoImage = (TextView) findViewById(
        R.id.txtViewMeaningfullPictureDefaultMessage);
    txtNoImage.setVisibility(View.VISIBLE);
  }

  /**
   * Handles the picture that was selected, saving it in the settings and displaying
   * it on the screen
   *
   * @param requestCode {@inheritDoc}
   * @param resultCode  {@inheritDoc}
   * @param data        The intent containing the picture data that was selected.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    ImageView imageCurrentMeaningfullPicture =
        (ImageView) findViewById(R.id.imageCurrentMeaningfullPicture);

    final TextView txtNoImage =
        (TextView) findViewById(R.id.txtViewMeaningfullPictureDefaultMessage);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {

      final Uri uri = data.getData();

      grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

      final int readFlagGranted = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
      if (readFlagGranted == Intent.FLAG_GRANT_READ_URI_PERMISSION) {
        // compiler doesn't like sending in readFlagGranted, so to surpress the warning
        // we send it in
        getContentResolver().takePersistableUriPermission(uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION);
      }

      // make sure to cancel any current load requests, in case they select an image before
      // the default one has loaded.
      Picasso.with(getBaseContext()).cancelRequest(imageCurrentMeaningfullPicture);
      Picasso.with(getBaseContext()).load(uri)
          .fit().centerInside().into(imageCurrentMeaningfullPicture,
          new Callback() {
              @Override
              public void onSuccess() {
                txtNoImage.setVisibility(View.INVISIBLE);
                SettingsService service = SettingsService.getInstance(getBaseContext());
                service.saveMeaningfulPicture(uri);

                handleFirstUseSetup();
              }

              @Override
              public void onError() {
                txtNoImage.setVisibility(View.VISIBLE);
              }
            });
    }
  }

  private void handleFirstUseSetup() {
    FirstUseChecklistService firstUseChecklistService = FirstUseChecklistService
        .getInstance(getBaseContext());
    if (!firstUseChecklistService.isSetupComplete()) {
      firstUseChecklistService.markStepComplete(FirstUseChecklistService.IMAGE_STEP);
      firstUseChecklistService.launchSetup(this);
    }
  }

  /**
   * Displays the saved picture onto the view if we have one. Updates the default text message
   * based upon the picture loading or not.
   */
  private void loadInitialPicture() {
    ImageView imageCurrentMeaningfullPicture =
        (ImageView) findViewById(R.id.imageCurrentMeaningfullPicture);

    final TextView txtNoImage =
        (TextView) findViewById(R.id.txtViewMeaningfullPictureDefaultMessage);

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
}
