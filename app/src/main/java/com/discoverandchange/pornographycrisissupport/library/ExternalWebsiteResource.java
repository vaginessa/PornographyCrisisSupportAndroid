package com.discoverandchange.pornographycrisissupport.library;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * A resource representing an external website that can be displayed in the resource library.
 * @author Stephen Nielson
 */
public class ExternalWebsiteResource extends MediaResource {

  public ExternalWebsiteResource() {
    super("ExternalWebsite");
  }

  public int getDefaultThumbnailResource() {
    return R.drawable.ic_web_black_48dp;
  }
}
