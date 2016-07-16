package com.discoverandchange.pornographycrisissupport.library.json;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.MediaResource;

/**
 * Helper class to assert MediaResource properties are properly filled in on a library
 * resource.
 * @author Stephen Nielson snielson@discoverandchange.com
 */
public class MediaResourceAssert {

  /**
   * Asserts that all of the MediaResource properties on the LibraryResource object actually
   * exist and are the expected values in the assert.
   * @param resource The resource we are checking properties against
   * @param url The value that the resource url should have.
   * @param description The value that the resource description should have.
   * @param thumbnail The value that the resource thumbnail should have.
   * @param title The value that the resource title should have.
   * @param type The actual resource type that the resource should be.
   */
  public static void assertMediaResourceDeserialized(LibraryResource resource, String url,
                                                     String description, String thumbnail,
                                                     String title, String type) {
    assertThat("resource should be of type media resource", resource,
        instanceOf(MediaResource.class));
    MediaResource mediaResource = (MediaResource)resource;
    assertThat("media resource url should have been deserialized", mediaResource.getUrl(),
        is(url));
    BaseMediaResourceAssert.assertResourceDeserialized(resource, description, thumbnail, title,
        type);
  }
}
