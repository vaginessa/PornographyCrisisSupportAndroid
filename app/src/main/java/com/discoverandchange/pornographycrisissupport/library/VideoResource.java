package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 * Represents a video resource that can be retrieved from a given media url.
 * This resource can be present on an external website
 * Expansion: Allow downloadable video files
 */
public class VideoResource extends MediaResource {
    public VideoResource() {
        super("Video");


        private String url;

        // constructors
        public VideoResource(String newUrl)
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
