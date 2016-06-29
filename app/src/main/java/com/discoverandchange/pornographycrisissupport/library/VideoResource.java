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

  // constructors
  public VideoResource() {
    super("Video");
  }


  // methods
  public int getDefaultThumbnailResource() {
    return R.drawable.ic_videocam_black_48dp;
  }
}
