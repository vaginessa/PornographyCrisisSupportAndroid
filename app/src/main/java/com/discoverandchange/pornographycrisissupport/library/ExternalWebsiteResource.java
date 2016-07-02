package com.discoverandchange.pornographycrisissupport.library;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * A resource representing an external website that can be displayed in the resource library.
 * @author Stephen Nielson
 */
public class ExternalWebsiteResource extends MediaResource {

  /**
   * The content for the website.
   */
  private String url;

  public ExternalWebsiteResource() {
    super("ExternalWebsite");
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public int getDefaultThumbnailResource() {
    return R.drawable.ic_web_black_48dp;
  }
}
