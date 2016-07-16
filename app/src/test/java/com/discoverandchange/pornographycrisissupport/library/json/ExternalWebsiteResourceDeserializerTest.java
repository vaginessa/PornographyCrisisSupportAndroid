package com.discoverandchange.pornographycrisissupport.library.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Tests the ExternalWebsiteResourceDeserializer class to make sure it
 * properly converts from JSON to an ExternalWebsiteResource.
 * @see ExternalWebsiteResourceDeserializer
 * @see ExternalWebsiteResource
 * @author Stephen Nielson snielson@discoverandchange.com
 */
public class ExternalWebsiteResourceDeserializerTest {

  @Test
  /**
   * Verifies the deserialize method hydrates an ExternalWebsiteResource.
   */
  public void testDeserialize() throws JSONException {
    String type = "ExternalWebsite";
    String url = "https://www.discoverandchange.com/relapse-strategies/";
    String description = "Sample description";
    String title = "External Website Resource";
    String thumbnail = "thumb.png";
    String fullTest = "{\"type\":\"" + type + "\",\"url\":\"" + url + "\","
        + "\"description\":\"" + description + "\","
        + "\"title\":\"" + title + "\","
        + "\"thumbnail\":\"" + thumbnail + "\"}";

    ExternalWebsiteResourceDeserializer deserializer = new ExternalWebsiteResourceDeserializer();
    LibraryResource website = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", website,
        instanceOf(ExternalWebsiteResource.class));
    MediaResourceAssert.assertMediaResourceDeserialized(website, url, description, thumbnail,
        title, type);
  }
}
