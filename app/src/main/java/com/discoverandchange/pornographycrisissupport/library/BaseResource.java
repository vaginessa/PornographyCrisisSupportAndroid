package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/22/16.
 */
public class BaseResource implements LibraryResource {

  private final String type;

  public BaseResource(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
