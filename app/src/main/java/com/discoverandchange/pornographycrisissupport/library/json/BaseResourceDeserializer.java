package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.BaseResource;

import org.json.JSONObject;

/**
 * Abstract class that handles the conversion from a JSON object to all the filled in properties
 * of a BaseResource class.
 * @author Stephen Nielson
 */
public abstract class BaseResourceDeserializer implements ResourceDeserializer {

  /**
   * This will copy all of the BaseResource properties that are in the primitive form in the
   * JSONObject onto the BaseResource object provided.  Essentially hydrating the object.
   * @param resource The resource that we want to be populated.
   * @param jsonResource The javascript object notation values for this object.
   */
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
