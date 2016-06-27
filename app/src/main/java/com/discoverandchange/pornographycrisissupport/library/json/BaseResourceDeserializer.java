package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.BaseResource;

import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public abstract class BaseResourceDeserializer implements ResourceDeserializer {

  protected void hydrateBaseResource(BaseResource resource, JSONObject jsonResource) {

    String description = jsonResource.optString("description", "");
    resource.setDescription(description);

    String thumbnail = jsonResource.optString("thumbnail", "");
    resource.setThumbnail(thumbnail);

    String title = jsonResource.optString("title", "");
    resource.setTitle(title);

    if (resource.getType() == null) {
      String type = jsonResource.optString("type", null);
      resource.setType(type);
    }
  }
}
