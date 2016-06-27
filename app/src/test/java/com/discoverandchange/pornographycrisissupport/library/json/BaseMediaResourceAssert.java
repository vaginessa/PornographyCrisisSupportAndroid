package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Helper class to assert BaseMediaResource properties are properly filled in on a library
 * resource.
 * @author Stephen Nielson <snielson@discoverandchange.com>
 */
public class BaseMediaResourceAssert {

  /**
   * Asserts that all of the BaseMediaResource properties on the LibraryResource object actually
   * exist and are the expected values in the assert.
   * @param resource The resource we are checking properties against
   * @param description The value that the resource description should have.
   * @param thumbnail The value that the resource thumbnail should have.
   * @param title The value that the resource title should have.
   * @param type The actual resource type that the resource should be.
   */
  public static void assertResourceDeserialized(LibraryResource resource, String description,
                                                String thumbnail, String title, String type) {
    assertThat("resource description should have been deserialized", resource.getDescription(),
        is(description));
    assertThat("resource thumbnail should have been deserialized", resource.getThumbnail(),
        is(thumbnail));
    assertThat("resource title should have been deserialized", resource.getTitle(),
        is(title));
    assertThat("resource type should have been deserialized", resource.getType(), is(type));
  }
}
