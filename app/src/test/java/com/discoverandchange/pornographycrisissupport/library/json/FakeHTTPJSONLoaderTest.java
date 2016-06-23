package com.discoverandchange.pornographycrisissupport.library.json;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by snielson on 6/23/16.
 */
public class FakeHTTPJSONLoaderTest {

  @Test
  public void testGet() throws IOException {
    FakeHTTPJSONLoader loader = new FakeHTTPJSONLoader();
    String result = loader.get("blah");
    System.out.println(result);
    assertNotNull("result should not be null");
  }
}
