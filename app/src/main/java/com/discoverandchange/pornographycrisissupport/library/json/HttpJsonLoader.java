package com.discoverandchange.pornographycrisissupport.library.json;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Retrieves the JSON data for the resource library from the network.
 * @author Stephen Nielson
 */
public class HttpJsonLoader {

  /**
   * The time when we should timeout from reading from the socket.
   */
  private static final int READ_TIMEOUT = 10000;

  /**
   * The time we should timeout when we have not yet made a connection.
   */
  private static final int CONNECTION_TIMEOUT = 15000;

  /**
   * The authentication token to use for communicating over the API.
   */
  private String authToken = null;

  /**
   * Constructs the loader with the given authentication token to allow us to talk to the JSON
   * API.
   * @param authToken the authentication token to use to allow api requests to be made for the
   *                  library.
   */
  public HttpJsonLoader(String authToken) {
    // FUTURE: stephen eventually we want this to do some kind of oauth or JWT token to verify the
    // user has access to the api.
    this.authToken = authToken;
  }

  /**
   * Given the API url to hit, it connects to the api passing the authentication token and retrieves
   * the json content as a string.
   * @param apiUrl The URL to connect to.
   * @return The retrieved json data, or null if nothing was retrieved.
   * @throws IOException An exception if we can't connect to the api or anything fails while
   *         reading from it.
   */
  public String get(String apiUrl) throws IOException {
    String destinationUrl = apiUrl + "?token=" + this.authToken;
    InputStream is = null;

    try {
      URL url = new URL(destinationUrl);
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
