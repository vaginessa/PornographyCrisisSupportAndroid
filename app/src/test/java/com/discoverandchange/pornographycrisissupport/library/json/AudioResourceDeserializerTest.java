package com.discoverandchange.pornographycrisissupport.library.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;



/**
 * Tests the AudioResourceDeserializer class to make sure it
 * properly converts from JSON to an AudioResource.
 *
 * @author Stephen Nielson snielson@discoverandchange.com
 * @see AudioResourceDeserializer
 * @see AudioResource
 */
public class AudioResourceDeserializerTest {

  @Test
  /**
   * Verifies the deserialize method hydrates an AudioResource.
   */
  public void testDeserialize() throws JSONException {
    String type = "Audio";
    String audioUrl = "https://www.discoverandchange.com/wp-content/uploads/2016/04/"
        + "Relapse-Prevention-Strategies.mp3";
    String description = "Sample description";
    String title = "Audio Resource";
    String thumbnail = "thumb.png";
    String fullTest = "{\"type\":\"" + type + "\",\"url\":\"" + audioUrl + "\","
        + "\"description\":\"" + description + "\","
        + "\"title\":\"" + title + "\","
        + "\"thumbnail\":\"" + thumbnail + "\"}";

    AudioResourceDeserializer deserializer = new AudioResourceDeserializer();
    LibraryResource audio = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a audio resource", audio, instanceOf(AudioResource.class));
    MediaResourceAssert.assertMediaResourceDeserialized(audio, audioUrl, description, thumbnail,
        title, type);
  }
}
