package com.discoverandchange.pornographycrisissupport.library;

import java.io.Serializable;

/**
 * Represents a resource in the resource library that can be displayed and clicked on to access
 * the content for that particular resource.  All resources in the resource library must implement
 * this interface.  If you implement this interface make sure to implement an associated
 * ResourceDeserializer class to hydrate this object from JSON.  Objects implementing this resource
 * should make sure to register with the LibraryResourceActivityRegistry.
 */
public interface LibraryResource extends Serializable {

  /**
   * The type of resource this is.  This is used to hydrate the resource with a deserializer.
   * @return the type of resource this object is.
   */
  String getType();

  /**
   * The thumbnail representation of this resource.
   * @return an image url.
   */
  String getThumbnail();

  /**
   * The human readable display title of this resource.
   * @return
   */
  String getTitle();

  /**
   * The human readable description of this resource.
   * @return
   */
  String getDescription();

}
