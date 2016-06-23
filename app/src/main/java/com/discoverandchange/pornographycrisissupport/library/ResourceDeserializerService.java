package com.discoverandchange.pornographycrisissupport.library;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.library.json.ResourceDeserializer;

import org.apache.commons.lang3.Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by snielson on 6/22/16.
 */
public class ResourceDeserializerService {

  private static ResourceDeserializerService service;

  private Map<String, ResourceDeserializer> deserializers;

  /**
   * We open this up just so we can get easier unit testing.
   */
  public ResourceDeserializerService() {
    deserializers = new HashMap<>();
  }

  public static synchronized ResourceDeserializerService getInstance() {
    if (service == null) {
      service = new ResourceDeserializerService();
    }
    return service;
  }

  public void registerDeserializer(String resourceType, ResourceDeserializer deserializer) {
    deserializers.put(resourceType, deserializer);
  }

  public ResourceDeserializer getDeserializerForType(String resourceType) {
    if (deserializers.containsKey(resourceType)) {
      return deserializers.get(resourceType);
    }
    return null;
  }

  public Map<Range,List<LibraryResource>> deserialize(String data) {

    // format

    Map<Range, List<LibraryResource>> resourcesByRange = new HashMap<>();

    try {
      JSONObject object = new JSONObject(data);

      // normally I'd use Gson or something... but I think this will make it easier
      // for the rest of the team to see how this can work.

      JSONArray ranges = object.getJSONArray("ranges");
      for (int i = 0; i < ranges.length(); i++) {
        JSONObject jsonRange = (JSONObject) ranges.getJSONObject(i);
        Range range = hydrateRange(jsonRange);
        JSONArray resources = jsonRange.getJSONArray("resources");
        List<LibraryResource> libraryResources = new ArrayList<>();

        for (int r = 0; r < resources.length(); r++) {
          JSONObject jsonResource = (JSONObject) resources.getJSONObject(r);
          LibraryResource resource = hydrateResource(jsonResource);
          libraryResources.add(resource);
        }
        resourcesByRange.put(range, libraryResources);
      }
    }
    catch (JSONException ex) {
      Log.e(Constants.LOG_TAG, "JSON parsing error: ", ex);
    }
    catch (IllegalArgumentException ex) {
      Log.e(Constants.LOG_TAG, "Invalid resource type found", ex);
    }

    return resourcesByRange;
  }

  private Range hydrateRange(JSONObject jsonRange) {

    int start = jsonRange.optInt("start", 0);
    int end = jsonRange.optInt("end", 0);
    return Range.between(start, end);
  }

  private LibraryResource hydrateResource(JSONObject jsonResource) throws JSONException {
    String type = jsonResource.getString("type");
    ResourceDeserializer deserializer = getDeserializerForType(type);
    if (deserializer == null) {
      throw new IllegalArgumentException("Unsupported type found: " + type);
    }

    return deserializer.deserialize(jsonResource);
  }
}
