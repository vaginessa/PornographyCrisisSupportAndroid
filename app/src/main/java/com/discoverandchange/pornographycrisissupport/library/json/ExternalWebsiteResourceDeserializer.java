package com.discoverandchange.pornographycrisissupport.library.json;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Converts a JSON object to an ExternalWebsiteResource object.
 * Used with the ResourceDeserializerService.
 * @author Stephen Nielson
 */
public class ExternalWebsiteResourceDeserializer extends MediaResourceDeserializer {

  /**
   * Converts a json object into an ExternalWebsiteResource object.
   * @param jsonResource The resource that will be converted into an ExternalWebsiteResource
   * @return The deserialized LibraryResource with it's properties filled in.
   */
  @Override
  public LibraryResource deserialize(JSONObject jsonResource) throws JSONException {
    ExternalWebsiteResource externalWebsite = new ExternalWebsiteResource();
    hydrateMedia(externalWebsite, jsonResource);

    String url = jsonResource.optString("url", null);
    externalWebsite.setUrl(url);
    Log.d(Constants.LOG_TAG, "External Website[" + externalWebsite.getTitle()
        + "] deserialized with url " + externalWebsite.getUrl());
    return externalWebsite;
  }
}
