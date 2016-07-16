package com.discoverandchange.pornographycrisissupport.library.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Tests the WebsiteContentResourceDeserializer class to make sure it
 * properly converts from JSON to an WebsiteContentResource
 * @see WebsiteContentResourceDeserializer
 * @see WebsiteContentResource
 * @author Stephen Nielson snielson@discoverandchange.com
 */
public class WebsiteContentResourceDeserializerTest {

  @Test
  /**
   * Verifies the deserialize method hydrates an WebsiteContentResource.
   */
  public void testDeserialize() throws JSONException {
    String type = "WebsiteContent";
    String content = "<b>I am html content</b>";
    String description = "Sample description";
    String title = "Audio Resource";
    String thumbnail = "thumb.png";
    String fullTest = "{\"type\":\"" + type + "\",\"content\":\"" + content + "\","
        + "\"description\":\"" + description + "\","
        + "\"title\":\"" + title + "\","
        + "\"thumbnail\":\"" + thumbnail + "\"}";

    WebsiteContentResourceDeserializer deserializer = new WebsiteContentResourceDeserializer();
    LibraryResource resource = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a WebsiteContent resource", resource,
        instanceOf(WebsiteContentResource.class));
    WebsiteContentResource contentResource = (WebsiteContentResource)resource;

    assertThat("resource content should be deserialized", contentResource.getContent(),
        is(content));
    BaseMediaResourceAssert.assertResourceDeserialized(resource, description, thumbnail,
        title, type);
  }
}
