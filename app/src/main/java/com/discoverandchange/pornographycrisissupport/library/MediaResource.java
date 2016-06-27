package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 */
public class MediaResource extends BaseResource {

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
