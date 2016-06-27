package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface that all resource deserializers must implement in order to convert a JSONObject into
 * a LibraryResource to be used in the resource library.  Note as a deserializer is re-used
 * across multiple deserializations their deserializer methods should be idempotent.
 * @author Stephen Nielson
 *
 */
public interface ResourceDeserializer {

  /**
   * Convert a JSONObject to a LibraryResource object taking all of the properties from that json
   * and setting them onto the LibraryResource object.
   * @param jsonResource The javascript object we are to convert into a LibraryResource
   * @return The deserialized LibraryResource with all of it's properties set from the JSON
   * @throws JSONException if the JSON is formatted improperly.
   */
  LibraryResource deserialize(JSONObject jsonResource) throws JSONException;
}
