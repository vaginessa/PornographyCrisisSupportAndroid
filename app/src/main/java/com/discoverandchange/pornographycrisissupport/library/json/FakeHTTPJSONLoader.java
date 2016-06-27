package com.discoverandchange.pornographycrisissupport.library.json;

import java.io.IOException;

/**
 * This is just temporary while we hook up the web services.
 * Created by snielson on 6/23/16.
 */
public class FakeHTTPJSONLoader extends HTTPJSONLoader {

  public FakeHTTPJSONLoader() {
    super("");
  }

  @Override
  public String get(String apiURL) throws IOException {

    StringBuilder sb = new StringBuilder();
    sb.append("{");
    appendKey(sb, "ranges");
    sb.append(": [{");
    appendKeyValue(sb, "start", 1);
    sb.append(",");
    appendKeyValue(sb, "end", 3);
    sb.append(",");
    appendKey(sb, "resources");
    sb.append(": [");
    appendTestVideo(sb);
    sb.append(",");
    appendTestAudio(sb);
    sb.append(",");
    appendTestExternalWebsite(sb);
    sb.append(",");
    appendTestVideo(sb);
    sb.append(",");
    appendTestAudio(sb);
    sb.append(",");
    appendTestWebsiteContent(sb);
    sb.append("]"); // end resource list
    sb.append("}]"); // end range list
    sb.append("}"); // end json

    return sb.toString();
  }

  private void appendTestVideo(StringBuilder sb) {
      sb.append("{");
      appendKeyValue(sb, "type", "Video");
      sb.append(",");
      appendTestMediaResource(sb, "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
          + "DiscoverAndChangeIntroVideo.mp4", "Test Video", "Sample video description", "thumb.png");
      sb.append("}");
  }

  private void appendTestAudio(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "Audio");
    sb.append(",");
    appendTestMediaResource(sb, "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
        + "DiscoverAndChangeIntroVideo.mp4", "Test Audio", "Sample audio description", "thumb.png");
    sb.append("}");
  }

  private void appendTestExternalWebsite(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "ExternalWebsite");
    sb.append(",");
    appendTestMediaResource(sb, "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
        + "DiscoverAndChangeIntroVideo.mp4", "Test Website", "Sample website description", "thumb.png");

    sb.append("}");
  }

  private void appendTestWebsiteContent(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "WebsiteContent");
    sb.append(",");
    appendKeyValue(sb, "content", "<b>I am website content</b>");
    sb.append(",");
    appendTestBaseResource(sb, "Test Website", "Sample website description", "thumb.png");
    sb.append("}");
  }


  private void appendTestMediaResource(StringBuilder sb, String url, String title,
                                       String description, String thumbnail) {
    appendKeyValue(sb, "url", url);
    sb.append(",");
    appendTestBaseResource(sb, title, description, thumbnail);
  }

  private void appendTestBaseResource(StringBuilder sb, String title, String description, String thumbnail) {
    appendKeyValue(sb, "title", title);
    sb.append(",");
    appendKeyValue(sb, "description", description);
    sb.append(",");
    appendKeyValue(sb, "thumbnail", thumbnail);
  }


  private void appendKey(StringBuilder sb, String key) {
    sb.append("\"");
    sb.append(key);
    sb.append("\"");
  }

  private void appendKeyValue(StringBuilder sb, String key, int value) {
    appendKey(sb, key);
    sb.append(":")
        .append(value);
  }

  private void appendKeyValue(StringBuilder sb, String key, String value) {
    appendKey(sb, key);
    sb.append(":")
        .append("\"")
        .append(value)
        .append("\"");
  }
}
