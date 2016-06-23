package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by snielson on 6/23/16.
 */
public class ExternalWebsiteResourceDeserializerTest {

  @Test
  public void testDeserialize() throws JSONException {
    String fullTest = "{\"type\":\"ExternalWebsite\",\"url\":\"https://www.discoverandchange.com/relapse-strategies/\"}";

    ExternalWebsiteResourceDeserializer deserializer = new ExternalWebsiteResourceDeserializer();
    LibraryResource website = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", website,  instanceOf(ExternalWebsiteResource.class));
    // TODO: stephen implement the rest of the methods when we have them.
  }
}
