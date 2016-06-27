package com.discoverandchange.pornographycrisissupport;

/**
 * Holds global application constants.
 * Created by snielson on 6/7/16.
 */
public class Constants {
  public static final String LOG_TAG = "pcs";
  public static final String DATABASE_NAME = "pcs.db";
  public static final int DATABASE_VERSION = 1;
  public static final String SUPPORT_CONTACT_EDIT_MESSAGE = "supportNetwork.edit";

  public static final String LOG_TAG_DEBUG  = "pcs.debug";

  // TODO: stephen, when we publish this to the live server, switch the url
  //public static final String LIBRARY_RESOURCES_ENDPOINT =
  //    "https://api.discoverandchange.com/api/v1/psa/libraryresources/";

  public static final String LIBRARY_RESOURCES_ENDPOINT =
      "https://api-stage.discoverandchange.com/api/v1/psa/libraryresources/";

  public static final String LIBRARY_RESOURCES_AUTH_TOKEN = "$1$a./V6hrZ$VGZGdng5GCIS/kGEagM/l/";
  public static final String LIBRARY_RESOURCE_VIEW_MESSAGE = "libraryResource.view";
}
