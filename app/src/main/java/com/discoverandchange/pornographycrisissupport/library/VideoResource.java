package com.discoverandchange.pornographycrisissupport.library;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * Represents a video resource that can be retrieved from a given media url.
 * This resource can be present on an external website
 * Expansion: Allow downloadable video files
 *
 * @author Stephen Nielson
 */
public class VideoResource extends MediaResource {

  private String url;


  // constructors
  public VideoResource() {
    super("Video");
  }


  // methods

  public String getUrl() {
    return url;
  }

  public void setUrl(String newUrl) {
    this.url = newUrl;
  }

  public int getDefaultThumbnailResource() {
    return R.drawable.ic_videocam_black_48dp;
  }
}
