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

/**
 * Deserializes a JSON string into the java objects that the string represents.  Users of this
 * service can register deserializers with this class and connect them with a LibraryResource
 * corresponding type.
 *
 * @author Stephen Nielson
 */
public class ResourceDeserializerService {

  /**
   * Singleton instance of the deserializer.
   */
  private static ResourceDeserializerService service;

  /**
   * Mapping of resource types to their corresponding deserializer objects.
   */
  private Map<String, ResourceDeserializer> deserializers;

  /**
   * We open this up just so we can get easier unit testing.
   */
  ResourceDeserializerService() {
    deserializers = new HashMap<>();
  }

  /**
   * Returns the singleton instance of this service. Note that care should be used in calling this
   * method in multiple threads as the method is synchronized.
   *
   * @return The instantiated instance of this singleton.
   */
  public static synchronized ResourceDeserializerService getInstance() {
    if (service == null) {
      service = new ResourceDeserializerService();
    }
    return service;
  }

  /**
   * Register the object to use to deserializer the resource for the given resourceType.
   *
   * @param resourceType The type value that a given deserializer should be used for.
   * @param deserializer The deserializer that will turn a JSONObject into a LibraryResource.
   */
  public void registerDeserializer(String resourceType, ResourceDeserializer deserializer) {
    deserializers.put(resourceType, deserializer);
  }

  /**
   * Retrieves the deserializer that will be given for the specific LibraryResource type.
   *
   * @param resourceType The string value representing a LibraryResource type
   * @return The deserializer that will turn a JSONObject into a LibraryResource.
   */
  public ResourceDeserializer getDeserializerForType(String resourceType) {
    if (deserializers.containsKey(resourceType)) {
      return deserializers.get(resourceType);
    }
    return null;
  }

  /**
   * Given a JSON string representing a mapping of range objects to library resources, convert
   * that json string into a Map with ranges mapped onto a list of library resources.
   *
   * @param data The json string representing a mapping of range objects to library resources.
   * @return The hydrated mapping, or null if we could not deserialize the object.
   */
  public Map<Range, List<LibraryResource>> deserialize(String data) {

    try {
      JSONObject object = new JSONObject(data);
      return deserialize(object);
    } catch (JSONException ex) {
      Log.e(Constants.LOG_TAG, "JSON parsing error: ", ex);
    }
    return null;
  }

  /**
   * Given a JSONObject of data it converts the JSONObject into it's corresponding java types
   * for the map of ranges to library resources.
   *
   * @param data The JSON representing the map of ranges to library resources.
   * @return a map where each range is mapped to a list of library resources.
   */
  public Map<Range, List<LibraryResource>> deserialize(JSONObject data) {
    Map<Range, List<LibraryResource>> resourcesByRange = new HashMap<>();

    try {
      // normally I'd use Gson or something... but I think this will make it easier
      // for the rest of the team to see how this can work.

      JSONArray ranges = data.getJSONArray("ranges");
      for (int i = 0; i < ranges.length(); i++) {
        JSONObject jsonRange = (JSONObject) ranges.getJSONObject(i);
        Range range = hydrateRange(jsonRange);
        if (Constants.DEBUG_MODE) {
          Log.d(Constants.LOG_TAG_DEBUG, "deserialized range of " + range.getMinimum()
              + " - " + range.getMaximum());
        }
        JSONArray resources = jsonRange.getJSONArray("resources");
        List<LibraryResource> libraryResources = new ArrayList<>();

        for (int r = 0; r < resources.length(); r++) {
          if (Constants.DEBUG_MODE) {
            Log.d(Constants.LOG_TAG_DEBUG, "Deserializing resource in range[" + i + "] count: " + r);
          }
          JSONObject jsonResource = (JSONObject) resources.getJSONObject(r);
          LibraryResource resource = hydrateResource(jsonResource);
          libraryResources.add(resource);
        }
        resourcesByRange.put(range, libraryResources);
      }
    } catch (JSONException ex) {
      Log.e(Constants.LOG_TAG, "JSON parsing error: ", ex);
    } catch (IllegalArgumentException ex) {
      Log.e(Constants.LOG_TAG, "Invalid resource type found", ex);
    }

    return resourcesByRange;
  }

  /**
   * Given a JSONObject representing a range, convert it into the appropriate Range object.
   * @param jsonRange the JSONObject representing a Range.
   * @return The populated range object
   */
  private Range hydrateRange(JSONObject jsonRange) {

    int start = jsonRange.optInt("start", 0);
    int end = jsonRange.optInt("end", 0);
    return Range.between(start, end);
  }

  /**
   * Converts a JSONObject representing a LibraryResource into the correct resource object
   * using the deserializer corresponding to the JSONObject's type value
   * @param jsonResource The JSONObject that represents a LibraryResource
   * @return The correctly constructoed LibraryResource.
   * @throws JSONException If we cannot deserialize the object
   */
  private LibraryResource hydrateResource(JSONObject jsonResource) throws JSONException {
    String type = jsonResource.getString("type");
    if (Constants.DEBUG_MODE) {
      Log.d(Constants.LOG_TAG, "Attempting to deserialize resource of type: " + type);
    }
    ResourceDeserializer deserializer = getDeserializerForType(type);
    if (deserializer == null) {
      throw new IllegalArgumentException("Unsupported type found: " + type);
    }

    return deserializer.deserialize(jsonResource);
  }
}