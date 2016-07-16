package com.discoverandchange.pornographycrisissupport.settings.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.firstuse.FirstUseChecklistService;
import com.discoverandchange.pornographycrisissupport.settings.SettingsService;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Handles the launching of the various setting activities in the app.
 */
public class SettingsController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings_controller);

    Button btn = (Button)findViewById(R.id.btnSettingsResetAppData);
    // hide this button if we are not in debug mode.
    if (!Constants.DEBUG_MODE) {
      btn.setVisibility(View.GONE);
    }
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

  /**
   * Developer function that clears all of the setup and settings information so we can test again.
   * @param btn The button that was clicked on.
   */
  public void resetAppData(View btn) {
    FirstUseChecklistService setupService = FirstUseChecklistService.getInstance(getBaseContext());
    setupService.resetSetup();

    SettingsService settingsService = SettingsService.getInstance(getBaseContext());
    settingsService.clearInspirationalQuote();
    settingsService.clearMeaningfulPicture();

    SupportNetworkService supportNetworkService = SupportNetworkService
        .getInstance(getBaseContext());
  }
}
