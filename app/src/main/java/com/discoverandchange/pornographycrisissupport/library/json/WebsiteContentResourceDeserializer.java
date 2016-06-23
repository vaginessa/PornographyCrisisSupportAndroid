package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;

import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public class WebsiteContentResourceDeserializer extends BaseResourceDeserializer {

  @Override
  public LibraryResource deserialize(JSONObject jsonResource) {
    WebsiteContentResource websiteContent = new WebsiteContentResource();
    hydrateBaseResource(websiteContent, jsonResource);
    return websiteContent;
  }
}
