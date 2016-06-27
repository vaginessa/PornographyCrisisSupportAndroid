package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;

import org.json.JSONObject;

/**
 * Converts a JSON object to an VideoResource object.
 * Used with the ResourceDeserializerService.
 * @author Stephen Nielson
 */
public class VideoResourceDeserializer extends MediaResourceDeserializer {

  @Override
  /**
   * Converts a json object into an Audio Resource object.
   * @param jsonResource The resource that will be converted into an VideoResource
   * @returns The deserialized LibraryResource
   */
  public LibraryResource deserialize(JSONObject jsonResource) {
    VideoResource video = new VideoResource();

    hydrateMedia(video,jsonResource);
    return video;
  }
}
