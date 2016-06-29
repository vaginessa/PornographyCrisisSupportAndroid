package com.discoverandchange.pornographycrisissupport.library.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

/**
 * Tests the FakeHttpJsonLoader class to make sure it returns a fake
 * JSON string that our deserialization classes can use for testing w/o having
 * a fully implemented external JSON api.
 * @see FakeHttpJsonLoader
 * @author Stephen Nielson <snielson@discoverandchange.com>
 */
public class FakeHttpJsonLoaderTest {

  @Test
  /**
   * Verifies the get method retrieves a fake JSON string.
   */
  public void testGet() throws IOException, JSONException {
    String url = "http://fakedomain.com/blah.json";
    FakeHttpJsonLoader loader = new FakeHttpJsonLoader();
    String result = loader.get(url);
    assertNotNull("result should not be null");
    JSONObject obj = new JSONObject(result); // should be able to load it back into json
    assertNotNull("json should have hydrated", obj);
    System.out.println(result);
  }
}
