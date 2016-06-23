package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public class ExternalWebsiteResourceDeserializer extends MediaResourceDeserializer {

  @Override
  public LibraryResource deserialize(JSONObject jsonResource) throws JSONException {
    ExternalWebsiteResource externalWebsite = new ExternalWebsiteResource();
    hydrateMedia(externalWebsite, jsonResource);
    return externalWebsite;
  }
}
