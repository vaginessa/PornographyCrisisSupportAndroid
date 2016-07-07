package com.discoverandchange.pornographycrisissupport.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for saving and retrieving global user provided settings.
 * @author Stephen Nielson
 */
public class SettingsService {
  private static final String SETTINGS_PREFERENCES = "Settings";
  private static final String INSPIRATIONAL_QUOTE_SETTING = "InspirationalQuote";
  private static final String MEANINGFUL_PICTURE_SETTING = "MeaningfulPicture";
  private static SettingsService service;
  private static Context context;

  public synchronized static SettingsService getInstance(Context context) {
    if (service == null) {
      service = new SettingsService(context.getApplicationContext());
    }
    return service;
  }

  public SettingsService(Context context) {
    this.context = context;
  }

  /**
   * Saves the inspirational quote in the app and notifies any observers that the settings
   * have changed.
   * @param quote  The quote
   */
  public void saveInspirationalQuote(String quote) {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(INSPIRATIONAL_QUOTE_SETTING, quote);
    editor.apply();
  }

  /**
   * Clears the inspirational quote in the app and notifies any observers that the quote
   * has been removed.
   */
  public void clearInspirationalQuote() {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    if (preferences.contains(INSPIRATIONAL_QUOTE_SETTING)) {
      SharedPreferences.Editor editor = preferences.edit();
      editor.remove(INSPIRATIONAL_QUOTE_SETTING);
      editor.apply();
    }
  }

  /**
   * Returns the inspirational quote that is currently saved in the system.
   * @return The saved inspirational quote or null if there isn't one.
   */
  public String getInspirationalQuote() {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    return preferences.getString(INSPIRATIONAL_QUOTE_SETTING, null);
  }

  /**
   * Returns the resources from the settings service.
   * TODO: stephen this feels really out of place, I wonder if we should put it in the library.
   * Perhaps a library global resource with a particular key-value setting.
   * @return
   */
  public List<LibraryResource> getSettingsResources() {
    List<LibraryResource> resources = new ArrayList<>();
    String quote = this.getInspirationalQuote();
    if (!StringUtils.isEmpty(quote)) {
      InspirationalQuoteResource quoteResource = new InspirationalQuoteResource();
      quoteResource.setTitle("Inspirational Quote");
      quoteResource.setDescription("Your personal inspirational quote to help you deal with your cravings.");
      quoteResource.setText(this.getInspirationalQuote());
      resources.add(quoteResource);
    }
    return resources;
  }
}
