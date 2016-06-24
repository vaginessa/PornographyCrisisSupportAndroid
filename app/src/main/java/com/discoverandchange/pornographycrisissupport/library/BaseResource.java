package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 */
public class BaseResource implements LibraryResource {

  private String type;
  private String thumbnail;
  private String title;
  private String description;

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
}
