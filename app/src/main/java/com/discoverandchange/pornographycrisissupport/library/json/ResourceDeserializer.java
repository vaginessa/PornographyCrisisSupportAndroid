package com.discoverandchange.pornographycrisissupport.library.json;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by snielson on 6/23/16.
 */
public interface ResourceDeserializer {

  LibraryResource deserialize(JSONObject jsonResource) throws JSONException;
}
