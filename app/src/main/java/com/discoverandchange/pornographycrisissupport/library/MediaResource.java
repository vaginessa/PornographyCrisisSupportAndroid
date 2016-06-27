package com.discoverandchange.pornographycrisissupport.library;

/**
 * Represents a resource that can be played, listened to, or interacted with such as a video, audio,
 * or external website.
 */
public class MediaResource extends BaseResource {

  /**
   * The external URL to load this resource from.
   */
  private String url;

  public MediaResource(String type) {
    super(type);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
