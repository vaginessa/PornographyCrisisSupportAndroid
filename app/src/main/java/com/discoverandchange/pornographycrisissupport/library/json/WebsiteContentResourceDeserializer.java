package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;

import org.json.JSONObject;

/**
 * Converts a JSON object to an WebsiteContentResource object.
 * Used with the ResourceDeserializerService.
 * @author Stephen Nielson
 */
public class WebsiteContentResourceDeserializer extends BaseResourceDeserializer {

  @Override
  /**
   * Converts a json object into an WebsiteContentResource object.
   * @param jsonResource The resource that will be converted into an WebsiteContentResource
   * @returns The deserialized LibraryResource
   */
  public LibraryResource deserialize(JSONObject jsonResource) {
    WebsiteContentResource websiteContent = new WebsiteContentResource();
    hydrateBaseResource(websiteContent, jsonResource);


    String content = jsonResource.optString("content", null);
    websiteContent.setContent(content);
    return websiteContent;
  }
}
