package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.MediaResource;

import org.json.JSONObject;

/**
 * Parent class that handles the deserialization of any media resources (some kind of playable or
 * clickable resource).
 * @author Stephen Nielson
 */
public abstract class MediaResourceDeserializer extends BaseResourceDeserializer {

  /**
   * This will copy all of the Media and BaseResource properties that are in the primitive form in the
   * JSONObject onto the MediaResource object provided.  Essentially hydrating the object.
   * @param media The resource that we want to be populated with media values.
   * @param jsonResource The javascript object notation values for this object.
   */
  protected void hydrateMedia(MediaResource media, JSONObject jsonResource) {
    hydrateBaseResource(media, jsonResource);

    String url = jsonResource.optString("url", null);
    media.setUrl(url);
  }
}
