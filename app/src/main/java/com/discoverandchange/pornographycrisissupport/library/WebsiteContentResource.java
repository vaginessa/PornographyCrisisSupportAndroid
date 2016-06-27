package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 */
public class WebsiteContentResource extends BaseResource {

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
