package com.discoverandchange.pornographycrisissupport.library;

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
}
