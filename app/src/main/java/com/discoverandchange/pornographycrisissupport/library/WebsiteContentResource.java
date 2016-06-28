package com.discoverandchange.pornographycrisissupport.library;

import com.discoverandchange.pornographycrisissupport.R;

/**
 * Represents a website resource to be retrieved from a given url
 * @author Stephen Nielson
 */
public class WebsiteContentResource extends BaseResource {

  /**
   * The content for the website
   */
  private String content;

  public WebsiteContentResource() {
    super("WebsiteContent");
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public int getDefaultThumbnailResource() {
    return R.drawable.ic_assignment_black_48dp;
  }
}
