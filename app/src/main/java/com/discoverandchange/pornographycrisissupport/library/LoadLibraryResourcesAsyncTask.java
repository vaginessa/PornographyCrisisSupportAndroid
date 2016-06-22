package com.discoverandchange.pornographycrisissupport.library;

import android.os.AsyncTask;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by snielson on 6/22/16.
 */
public class LoadLibraryResourcesAsyncTask extends AsyncTask<String, Void,
    Map<Range, List<LibraryResource>>>{

  private ResourceLibraryService service;
  private HTTPJSONLoader jsonLoader;
  private ResourceDeserializerService deserializerService;

  public LoadLibraryResourcesAsyncTask(ResourceLibraryService service, HTTPJSONLoader jsonLoader,
                                       ResourceDeserializerService deserializerService) {
    this.service = service;
    this.jsonLoader = jsonLoader;
    this.deserializerService = deserializerService;
  }

  @Override
  protected Map<Range, List<LibraryResource>> doInBackground(String... params) {

    String url = params[0];
    Map<Range, List<LibraryResource>> resources = null;
    try {
      String data = this.jsonLoader.get(url);
      resources = this.deserializerService.deserialize(data);
    } catch (IOException ex) {
      Log.e(Constants.LOG_TAG, "Could not retrieve json data from url", ex);
    }

    return resources;
  }

  @Override
  protected void onPostExecute(Map<Range, List<LibraryResource>> rangeListMap) {
    // TODO: stephen this will cause several notifies to happen...
    // is that what we want, figure out a way to fix this.
    for (Range range : rangeListMap.keySet()) {
      service.addResources(range, rangeListMap.get(range));
    }
  }


}
