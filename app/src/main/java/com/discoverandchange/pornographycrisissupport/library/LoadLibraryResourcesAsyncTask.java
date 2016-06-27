package com.discoverandchange.pornographycrisissupport.library;

import android.os.AsyncTask;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.library.json.HTTPJSONLoader;

import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Handles the loading of library resources in the background and adding them back to the library
 * resource service once it's done.
 * TODO: stephen look at moving this back into the library service code... as it needs to modify
 * some internal state saying the resources have loaded
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
  /**
   * Retrieves the JSON api and loads the resources on a background thread.
   * @param params The urls that we should load resources from.  Right now we only load the first
   *               one.
   */
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
  /**
   * Given the loaded resources from the background add them to the resource library service
   * and mark the resources as the loading being complete.
   */
  protected void onPostExecute(Map<Range, List<LibraryResource>> rangeListMap) {
    // resources never loaded and we have an exception here
    if (rangeListMap != null) {
      for (Range range : rangeListMap.keySet()) {
        service.addResources(range, rangeListMap.get(range));
      }
    }
    else {
      // so the app can continue with null resources we should mark the resources loaded
      // TODO: stephen perhaps have an resourcesFailed handler
    }
    service.markResourcesLoaded();
  }


}
