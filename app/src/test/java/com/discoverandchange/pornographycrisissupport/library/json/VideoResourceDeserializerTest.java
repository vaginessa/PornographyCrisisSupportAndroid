package com.discoverandchange.pornographycrisissupport.library.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Tests the VideoResourceDeserializer class to make sure it
 * properly converts from JSON to an VideoResource
 *
 * @author Stephen Nielson snielson@discoverandchange.com
 * @see VideoResourceDeserializer
 * @see VideoResource
 */
public class VideoResourceDeserializerTest {

  @Test
  /**
   * Verifies the deserialize method hydrates an VideoResource.
   */
  public void testDeserialize() throws JSONException {
    String type = "Video";
    String url = "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
        + "DiscoverAndChangeIntroVideo.mp4";
    String description = "Sample description";
    String title = "Audio Resource";
    String thumbnail = "thumb.png";
    String fullTest = "{\"type\":\"" + type + "\",\"url\":\"" + url + "\","
        + "\"description\":\"" + description + "\","
        + "\"title\":\"" + title + "\","
        + "\"thumbnail\":\"" + thumbnail + "\"}";

    VideoResourceDeserializer deserializer = new VideoResourceDeserializer();
    LibraryResource video = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", video, instanceOf(VideoResource.class));
    MediaResourceAssert.assertMediaResourceDeserialized(video, url, description, thumbnail,
        title, type);
  }
}
