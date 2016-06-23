package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by snielson on 6/23/16.
 */
public class AudioResourceDeserializerTest {

  @Test
  public void testDeserialize() throws JSONException {
    String fullTest = "{\"type\":\"Audio\",\"url\":\"https://www.discoverandchange.com/wp-content/uploads/2016/04/Relapse-Prevention-Strategies.mp3\"}";

    AudioResourceDeserializer deserializer = new AudioResourceDeserializer();
    LibraryResource audio = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", audio,  instanceOf(AudioResource.class));
    // TODO: stephen implement the rest of the methods when we have them.
  }
}
