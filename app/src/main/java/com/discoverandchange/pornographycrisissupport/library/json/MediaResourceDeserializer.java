package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.MediaResource;

import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public abstract class MediaResourceDeserializer extends BaseResourceDeserializer {

  protected void hydrateMedia(MediaResource video, JSONObject jsonResource) {
    // TODO: stephen methods to hydrate media resource
    hydrateBaseResource(video, jsonResource);
  }
}
