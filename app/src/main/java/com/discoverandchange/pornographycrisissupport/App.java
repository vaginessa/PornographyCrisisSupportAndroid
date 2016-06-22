package com.discoverandchange.pornographycrisissupport;

import android.app.Application;
import android.content.Context;

import com.discoverandchange.pornographycrisissupport.library.LibraryResourceActivityRegistry;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Created by snielson on 6/7/16.
 */
public class App extends Application {

  protected static Context context = null;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    SupportNetworkService service = SupportNetworkService.getInstance(context);

    // do we lazy load the quiz?  At least we need to grab the latest quiz score if we have one
    // right?
    // probably not the quiz history until later....

    LibraryResourceActivityRegistry registry = LibraryResourceActivityRegistry.getInstance();
    // TODO: stephen, john add the activities mappings here when the classes are implemented.

  }

  public static Context getContext() {
    return context;
  }

}