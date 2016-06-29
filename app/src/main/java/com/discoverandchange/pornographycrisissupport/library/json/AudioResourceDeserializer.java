package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONObject;

/**
 * Converts a JSON object to an Audio Resource object.
 * Used with the ResourceDeserializerService.
 *
 * @author Stephen Nielson
 */
public class AudioResourceDeserializer extends MediaResourceDeserializer {

  @Override
  /**
   * Converts a json object into an Audio Resource object.
   * @param jsonResource The resource that will be converted into an AudioResource
   * @returns The deserialized LibraryResource
   */
  public LibraryResource deserialize(JSONObject jsonResource) {
    AudioResource audio = new AudioResource();
    hydrateMedia(audio, jsonResource);
    return audio;
  }
}
