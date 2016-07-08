package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;

import org.apache.commons.lang3.StringUtils;

/**
 * Handles the display of an ExternalWebsiteResource to the user.
 *
 * @author Stephen Nielson
 */
public class ExternalWebsiteActivityController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_external_website_activity_controller);

    WebView webView = (WebView) findViewById(R.id.webViewExternal);

    Intent intent = this.getIntent();
    Object obj = (Object) intent.getSerializableExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (obj == null || !(obj instanceof ExternalWebsiteResource)) {
      displayError(webView);
      Log.wtf(Constants.LOG_TAG, "ExternalWebsiteActivityController called without an "
          + "ExternalWebsiteResource");
    } else {
      ExternalWebsiteResource resource = (ExternalWebsiteResource) obj;
      String url = resource.getUrl();
      if (StringUtils.isNotEmpty(url)) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
      } else {
        displayError(webView);
        Log.e(Constants.LOG_TAG, "ExternalWebsiteResource content was empty");
      }
    }
  }


  private void displayError(WebView webView) {
    String errorHtml = "<h1>Error</h1><p>An error occurred and we could not load this content</p>";
    webView.loadDataWithBaseURL("", errorHtml, "text/html", "utf-8", "");
  }
}
