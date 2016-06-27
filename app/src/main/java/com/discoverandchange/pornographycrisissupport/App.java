package com.discoverandchange.pornographycrisissupport;

import android.app.Application;
import android.content.Context;

import com.discoverandchange.pornographycrisissupport.library.AudioActivityController;
import com.discoverandchange.pornographycrisissupport.library.AudioResource;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteActivityController;
import com.discoverandchange.pornographycrisissupport.library.ExternalWebsiteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResourceActivityRegistry;
import com.discoverandchange.pornographycrisissupport.library.ResourceDeserializerService;
import com.discoverandchange.pornographycrisissupport.library.VideoActivityController;
import com.discoverandchange.pornographycrisissupport.library.VideoResource;
import com.discoverandchange.pornographycrisissupport.library.WebsiteActivityController;
import com.discoverandchange.pornographycrisissupport.library.WebsiteContentResource;
import com.discoverandchange.pornographycrisissupport.library.json.AudioResourceDeserializer;
import com.discoverandchange.pornographycrisissupport.library.json.ExternalWebsiteResourceDeserializer;
import com.discoverandchange.pornographycrisissupport.library.json.VideoResourceDeserializer;
import com.discoverandchange.pornographycrisissupport.library.json.WebsiteContentResourceDeserializer;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Creates an instance of the support network when the application is started.
 * @author snielson
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

    // setup the deserializer
    ResourceDeserializerService deserializerService = ResourceDeserializerService.getInstance();
    deserializerService.registerDeserializer("Video", new VideoResourceDeserializer());
    deserializerService.registerDeserializer("Audio", new AudioResourceDeserializer());
    deserializerService.registerDeserializer("WebsiteContent", new WebsiteContentResourceDeserializer());
    deserializerService.registerDeserializer("ExternalWebsite", new ExternalWebsiteResourceDeserializer());

  }

  public static Context getContext() {
    return context;
  }

}