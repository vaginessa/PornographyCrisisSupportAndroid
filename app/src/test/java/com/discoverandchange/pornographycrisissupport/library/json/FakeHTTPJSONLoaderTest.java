package com.discoverandchange.pornographycrisissupport.library.json;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

/**
 * Tests the FakeHTTPJSONLoader class to make sure it returns a fake
 * JSON string that our deserialization classes can use for testing w/o having
 * a fully implemented external JSON api.
 * @see FakeHTTPJSONLoader
 * @author Stephen Nielson <snielson@discoverandchange.com>
 */
public class FakeHTTPJSONLoaderTest {

  @Test
  /**
   * Verifies the get method retrieves a fake JSON string.
   */
  public void testGet() throws IOException {
    String url = "http://fakedomain.com/blah.json";
    FakeHTTPJSONLoader loader = new FakeHTTPJSONLoader();
    String result = loader.get(url);
    assertNotNull("result should not be null");
  }
}
