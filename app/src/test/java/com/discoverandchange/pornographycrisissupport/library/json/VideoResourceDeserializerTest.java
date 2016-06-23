package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by snielson on 6/23/16.
 */
public class VideoResourceDeserializerTest {

  @Test
  public void testDeserialize() throws JSONException {
    String fullTest = "{\"type\":\"Video\",\"url\":\"https://www.discoverandchange.com/wp-content/uploads/2016/02/DiscoverAndChangeIntroVideo.mp4\"}";

    VideoResourceDeserializer deserializer = new VideoResourceDeserializer();
    LibraryResource video = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", video,  instanceOf(VideoResource.class));
    // TODO: stephen implement the rest of the methods when we have them.
  }
}
