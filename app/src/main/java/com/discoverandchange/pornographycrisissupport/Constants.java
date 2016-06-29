package com.discoverandchange.pornographycrisissupport;

/**
 * Holds global application constants.
 * @author Stephen Nielson
 * @author Keith Higbee
 * @author John Okleberry
 */
public class Constants {
  /**
   * Represents the android logging tag for regular messages.
   */
  public static final String LOG_TAG = "pcs";

  /**
   * Represents the database name that the app stores data under.
   */
  public static final String DATABASE_NAME = "pcs.db";

  /**
   * Represents our current database version.
   */
  public static final int DATABASE_VERSION = 1;

  /**
   * Represents the message used to pass serialized support network contacts to be edited in the
   * app.
   */
  public static final String SUPPORT_CONTACT_EDIT_MESSAGE = "supportNetwork.edit";

  /**
   * Represents the android logging tag for debug type messages.
   */
  public static final String LOG_TAG_DEBUG  = "pcs.debug";

  // TODO: stephen, when we publish this to the live server, switch the url
  /**
   * The URL that the app retrieves it's network resources from.
   */
  //public static final String LIBRARY_RESOURCES_ENDPOINT =
  //    "https://api.discoverandchange.com/api/v1/psa/libraryresources/";

  /**
   * The URL that the app retrieves it's network resources from.
   */
  public static final String LIBRARY_RESOURCES_ENDPOINT =
      "https://api-stage.discoverandchange.com/api/v1/psa/libraryresources/";

  /**
   * The API authentication token that is passed along with API requests.
   */
  public static final String LIBRARY_RESOURCES_AUTH_TOKEN = "$1$a./V6hrZ$VGZGdng5GCIS/kGEagM/l/";

  /**
   * Represents the message used to passed serialized LibraryResource objects that can be viewed
   * in the individual activity controllers.
   */
  public static final String LIBRARY_RESOURCE_VIEW_MESSAGE = "libraryResource.view";
}
