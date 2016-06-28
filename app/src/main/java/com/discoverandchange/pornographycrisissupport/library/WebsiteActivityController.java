package com.discoverandchange.pornographycrisissupport.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;

import org.apache.commons.lang3.StringUtils;

/**
 * Displays the website for the resource
 * @author Stephen Nielson
 */
public class WebsiteActivityController extends AppCompatActivity {

  /**
   * Creates the view for the website
   * @param savedInstanceState Any saved data needed for this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_website_activity_controller);

    WebView webView = (WebView)findViewById(R.id.webView);
    Intent intent = this.getIntent();
    Object obj = (Object) intent.getSerializableExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (obj == null || !(obj instanceof WebsiteContentResource)) {
      displayError(webView);
      Log.wtf(Constants.LOG_TAG, "WebsiteActivityController called without a WebsiteContentResource");
    }
    else {
      WebsiteContentResource resource = (WebsiteContentResource) obj;
      String content = resource.getContent();
      if (StringUtils.isNotEmpty(content)) {
        webView.loadDataWithBaseURL("", resource.getContent(), "text/html", "utf-8", "");
      }
      else {
        displayError(webView);
        Log.e(Constants.LOG_TAG, "WebsiteContentResource content was empty");
      }
    }
  }

  private void displayError(WebView webView) {
    String errorHtml = "<h1>Error</h1><p>An error occurred and we could not load this content</p>";
    webView.loadDataWithBaseURL("", errorHtml, "text/html", "utf-8", "");
  }
}
