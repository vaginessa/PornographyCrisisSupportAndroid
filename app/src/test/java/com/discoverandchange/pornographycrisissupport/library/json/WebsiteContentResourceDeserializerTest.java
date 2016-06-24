package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by snielson on 6/23/16.
 */
public class WebsiteContentResourceDeserializerTest {

  @Test
  public void testDeserialize() throws JSONException {
    String fullTest = "{\"type\":\"WebsiteContent\",\"content\":\"<b>I am html content</b>\"}";

    WebsiteContentResourceDeserializer deserializer = new WebsiteContentResourceDeserializer();
    LibraryResource websiteContent = deserializer.deserialize(new JSONObject(fullTest));

    assertThat("resource should be a video resource", websiteContent,  instanceOf(WebsiteContentResource.class));
    // TODO: stephen implement the rest of the methods when we have them.
  }
}
