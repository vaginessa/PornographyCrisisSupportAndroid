package com.discoverandchange.pornographycrisissupport.library.json;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Retrieves the JSON data from the network.
 * Created by snielson on 6/22/16.
 */
public class HTTPJSONLoader {

  private static final int READ_TIMEOUT = 10000;
  private static final int CONNECTION_TIMEOUT = 15000;

  public HTTPJSONLoader() {}

  public String get(String apiURL) throws IOException {
    InputStream is = null;

    try {
      URL url = new URL(apiURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setReadTimeout(READ_TIMEOUT /* milliseconds */);
      conn.setConnectTimeout(CONNECTION_TIMEOUT /* milliseconds */);
      conn.setRequestMethod("GET");
      conn.setDoInput(true);
      // Starts the query
      conn.connect();
      int response = conn.getResponseCode();
      Log.d(Constants.LOG_TAG_DEBUG, "The response is: " + response);
      is = conn.getInputStream();

      // Convert the InputStream into a string
      String contentAsString = IOUtils.toString(is);
      Log.d(Constants.LOG_TAG_DEBUG, contentAsString);
      return contentAsString;

      // Makes sure that the InputStream is closed after the app is
      // finished using it.
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }
}
