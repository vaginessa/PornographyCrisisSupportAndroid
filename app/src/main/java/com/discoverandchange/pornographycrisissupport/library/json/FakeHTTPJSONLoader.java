package com.discoverandchange.pornographycrisissupport.library.json;

import java.io.IOException;

/**
 * This is just temporary while we hook up the web services.
 * Created by snielson on 6/23/16.
 */
public class FakeHTTPJSONLoader extends HTTPJSONLoader {

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
      appendKeyValue(sb, "url", "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
          + "DiscoverAndChangeIntroVideo.mp4");
      sb.append("}");
  }

  private void appendTestAudio(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "Audio");
    sb.append(",");
    appendKeyValue(sb, "url", "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
        + "DiscoverAndChangeIntroVideo.mp4");
    sb.append("}");
  }

  private void appendTestExternalWebsite(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "ExternalWebsite");
    sb.append(",");
    appendKeyValue(sb, "url", "https://www.discoverandchange.com/wp-content/uploads/2016/02/"
        + "DiscoverAndChangeIntroVideo.mp4");
    sb.append("}");
  }

  private void appendTestWebsiteContent(StringBuilder sb) {
    sb.append("{");
    appendKeyValue(sb, "type", "WebsiteContent");
    sb.append(",");
    appendKeyValue(sb, "cpntent", "<b>I am website content</b>");
    sb.append("}");
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
