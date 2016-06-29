package com.discoverandchange.pornographycrisissupport.library;

import android.os.Bundle;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;

/**
 * Handles the display of an ExternalWebsiteResource to the user.
 *
 * @author Stephen Nielson
 */
public class ExternalWebsiteActivityController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_external_website_activity_controller);
  }
}
