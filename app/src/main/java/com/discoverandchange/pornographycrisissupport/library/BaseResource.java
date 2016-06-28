package com.discoverandchange.pornographycrisissupport.library;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * Represents a primitive LibraryResource type that implements all of the basic functionality
 * that all LibraryResource types should have such as title, description, and it's core type.
 */
public class BaseResource implements LibraryResource {

  /**
   * The type of resource this is.
   */
  private String type;

  /**
   * A thumbnail of the resource.
   */
  private String thumbnail;

  /**
   * The display title of this resource.
   */
  private String title;

  /**
   * The human readable description of this resource.
   */
  private String description;

  /**
   * Constructs the BaseResource with the given passed in type.
   * @param type The type of resource such as Audio, Video, etc.
   */
  public BaseResource(String type) {
    this.type = type;
    thumbnail = null;
    title = null;
    description = null;
  }

  public String getType() {
    return type;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setThumbnail(String thumbnail){
    this.thumbnail = thumbnail;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int getDefaultThumbnailResource() {
    return R.drawable.ic_play_48dp;
  }
}
