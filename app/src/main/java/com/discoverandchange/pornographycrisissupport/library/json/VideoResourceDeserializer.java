package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;

import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public class VideoResourceDeserializer extends MediaResourceDeserializer {

  public LibraryResource deserialize(JSONObject jsonResource) {
    VideoResource video = new VideoResource();

    hydrateMedia(video,jsonResource);
    return video;
  }
}
