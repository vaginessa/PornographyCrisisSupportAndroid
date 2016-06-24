package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 * Represents an Audio resource that can be retrieved from a given media url.
 * This resource can be present on an external website
 * Expansion: Allow downloadable audio files
 */
public class AudioResource extends MediaResource {

    public AudioResource() {
        super("Audio");
    }
    private String url;

    // constructors
    public AudioResource(String newUrl)
    {
        this.url = newUrl;
    }

    // methods
    public String getUrl() {
        return url;
    }

    public void setUrl(String newUrl) {
        this.url = newUrl;
    }



}
