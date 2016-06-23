package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public class AudioResourceDeserializer extends MediaResourceDeserializer {

  @Override
  public LibraryResource deserialize(JSONObject jsonResource) {
    AudioResource audio = new AudioResource();
    hydrateMedia(audio, jsonResource);
    return audio;
  }
}
