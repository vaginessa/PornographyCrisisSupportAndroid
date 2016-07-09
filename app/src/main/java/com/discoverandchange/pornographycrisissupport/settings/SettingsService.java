package com.discoverandchange.pornographycrisissupport.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.MeaningfulPictureResource;

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
  private Uri meaningfulPictureUri;

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
    this.clearSetting(INSPIRATIONAL_QUOTE_SETTING);
  }

  /**
   * Returns the inspirational quote that is currently saved in the system.
   * @return The saved inspirational quote or null if there isn't one.
   */
  public String getInspirationalQuote() {
    return getStringSetting(INSPIRATIONAL_QUOTE_SETTING, null);
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

    Uri pictureUri = this.getMeaningfulPictureUri();
    if (pictureUri != null) {
      MeaningfulPictureResource pictureResource = new MeaningfulPictureResource();
      // TODO: stephen look at making this a URI.
      pictureResource.setUrl(pictureUri.toString());
      pictureResource.setThumbnail(pictureUri.toString());
      pictureResource.setTitle("Meaningfull Picture");
      pictureResource.setDescription("Your personal meaningful picture to help you deal with your cravings.");
      resources.add(pictureResource);
    }
    return resources;
  }

  /**
   * Takes the passed in URI and saves it to the app settings. Since this is a URI it can be
   * a reference to a gallery picture, google drive picture, sd card, etc.
   * @param uri The unique resource identifier for the meaninigful picture.
   */
  public void saveMeaningfulPicture(Uri uri) {
    String uriString = uri.toString();
    this.saveStringSetting(MEANINGFUL_PICTURE_SETTING, uriString);
  }

  /**
   * Removes the meaningfull picture setting from the app.
   */
  public void clearMeaningfulPicture() {
    this.clearSetting(MEANINGFUL_PICTURE_SETTING);
  }

  /**
   * Returns the unique resource identifier for the meaningful picture.
   * @return A URI that points to a picture image.
   */
  public Uri getMeaningfulPictureUri() {
    String pictureUri = getStringSetting(MEANINGFUL_PICTURE_SETTING, null);
    if (StringUtils.isEmpty(pictureUri)) {
      return null;
    }
    return Uri.parse(pictureUri);
  }

  /**
   * Returns a setting for the passed in key in our shared preferences.  If the setting does not
   * exist it returns the default value.
   * @param key The unique settings key to return.
   * @param defaultValue the default value to return if the key does not exist in.
   * @return The settings value if the key exists, or the value passed in defaultValue
   */
  private String getStringSetting(String key, String defaultValue) {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    return preferences.getString(key, defaultValue);
  }

  // TODO: stephen finish documentating this class.
  private void saveStringSetting(String key, String value) {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

  private void clearSetting(String key) {
    SharedPreferences preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, 0);
    if (preferences.contains(key)) {
      SharedPreferences.Editor editor = preferences.edit();
      editor.remove(key);
      editor.apply();
    }
  }
}
