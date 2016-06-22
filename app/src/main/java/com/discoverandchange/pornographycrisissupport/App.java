package com.discoverandchange.pornographycrisissupport;

import android.app.Application;
import android.content.Context;

import com.discoverandchange.pornographycrisissupport.library.AudioActivityController;
import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteActivityController;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResourceActivityRegistry;
import com.discoverandchange.pornographycrisissupport.library.VideoActivityController;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteActivityController;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;
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

    // register the library resources, to the activities that display them.
    LibraryResourceActivityRegistry registry = LibraryResourceActivityRegistry.getInstance();
    registry.registerResource(AudioResource.class, AudioActivityController.class);
    registry.registerResource(VideoResource.class, VideoActivityController.class);
    registry.registerResource(ExternalWebsiteResource.class, ExternalWebsiteActivityController.class);
    registry.registerResource(WebsiteContentResource.class, WebsiteActivityController.class);

  }

  public static Context getContext() {
    return context;
  }

}