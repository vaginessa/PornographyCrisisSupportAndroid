package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by oogle on 6/23/2016.
 * Represents an Audio resource that can be retrieved from a given media url.
 * This resource can be present on an external website
 * Expansion: Allow downloadable audio files
 */
public class AudioResource {

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
