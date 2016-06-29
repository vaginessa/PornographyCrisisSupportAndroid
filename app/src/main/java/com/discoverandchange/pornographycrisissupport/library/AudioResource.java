package com.discoverandchange.pornographycrisissupport.library;

/**
 * Represents an Audio resource that can be retrieved from a given media url.
 * This resource can be present on an external website
 * Expansion: Allow downloadable audio files
 *
 * @author Stephen Nielson
 */
public class AudioResource extends MediaResource {

  private String url;


  // Constructors
  public AudioResource() {
    super("Audio");
  }

  // methods
  public String getUrl() {
    return url;
  }

  public void setUrl(String newUrl) {
    this.url = newUrl;
  }
}
