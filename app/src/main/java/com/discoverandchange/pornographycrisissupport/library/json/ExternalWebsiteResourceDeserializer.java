package com.discoverandchange.pornographycrisissupport.library.json;

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

  @Override
  /**
   * Converts a json object into an ExternalWebsiteResource object.
   * @param jsonResource The resource that will be converted into an ExternalWebsiteResource
   * @returns The deserialized LibraryResource with it's properties filled in.
   */
  public LibraryResource deserialize(JSONObject jsonResource) throws JSONException {
    ExternalWebsiteResource externalWebsite = new ExternalWebsiteResource();
    hydrateMedia(externalWebsite, jsonResource);
    return externalWebsite;
  }
}
