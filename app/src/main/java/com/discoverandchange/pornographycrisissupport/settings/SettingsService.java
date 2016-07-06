package com.discoverandchange.pornographycrisissupport.settings;

import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snielson on 7/6/16.
 */
public class SettingsService {
  private static SettingsService service;

  public synchronized static SettingsService getInstance() {
    if (service == null) {
      service = new SettingsService();
    }
    return service;
  }

  public SettingsService() {
  }

  /**
   * Saves the inspirational quote in the app and notifies any observers that the settings
   * have changed.
   * @param quote  The quote
   */
  public void saveInspirationalQuote(String quote) {

  }

  /**
   * Clears the inspirational quote in the app and notifies any observers that the quote
   * has been removed.
   */
  public void clearInspirationalQuote() {

  }

  /**
   * Returns the inspirational quote that is currently saved in the system.
   * @return The saved inspirational quote or null if there isn't one.
   */
  public String getInspirationalQuote() {
    //
    return null;
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
