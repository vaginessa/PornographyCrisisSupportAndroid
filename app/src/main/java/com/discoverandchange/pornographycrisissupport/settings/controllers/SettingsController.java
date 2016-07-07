package com.discoverandchange.pornographycrisissupport.settings.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;

/**
 * Handles the launching of the various setting activities in the app.
 */
public class SettingsController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings_controller);
  }

  /**
   * Opens the meaningful picture setting for setting a picture to show up in the library.
   * @param btn the button that was clicked on.
   */
  public void openMeaningfulPicture(View btn) {
    Intent intent = new Intent(getBaseContext(), MeaningfulPictureSettingsController.class);
    startActivity(intent);
  }

  /**
   * Opens the inspirational quote settings page for adding an inspirational quote to the library.
   * @param btn The button that was clicked on.
   */
  public void openInspirationalQuote(View btn) {
    Intent intent = new Intent(getBaseContext(), InspirationalQuoteSettingsController.class);
    startActivity(intent);
  }
}
