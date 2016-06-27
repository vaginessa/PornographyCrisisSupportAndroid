package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry that connects LibraryResource objects with their associated Activity classes that
 * are used to display and interact with that LibraryResource.  This allows activities and library
 * resources to be added and re-configured at runtime w/o having to change lots of code.
 * Note this is a singleton and users should use getInstance() to retrieve the registry.
 */
public class LibraryResourceActivityRegistry {
  /**
   * Maps objects that implement LibraryResource to the Activity class that they are to be used
   * with.
   */
  Map<Class<? extends LibraryResource>, Class<? extends Activity>> activitiesMap;

  /**
   * The singleton instance of the registry
   */
  private static LibraryResourceActivityRegistry registry;

  /**
   * Returns the singleton instance of the registry.  This method should be used sparingly
   * in threads as it is synchronized and a performance penalty will occur if you frequently
   * call this method.
   * @return  The registry to be used.
   */
  public static synchronized LibraryResourceActivityRegistry getInstance() {
    if (registry == null) {
      registry = new LibraryResourceActivityRegistry();
    }
    return registry;
  }

  /**
   * Maps a LibraryResource object with the activity that should be loaded when a user wants to
   * interact with it.
   * @param resourceClass  The class of the library resource we are registering.
   * @param activityClass  The class of the activity we want to launch for this resource.
   */
  public void registerResource(Class<? extends LibraryResource> resourceClass,
                               Class<? extends Activity> activityClass)
  {
    activitiesMap.put(resourceClass, activityClass);
  }

  /**
   * Retrieves the Activity class that should be loaded for the given LibraryResource object.
   * @param resource The class of the LibraryResource we want the activity for.
   * @return
   */
  public Class<? extends Activity> getActivityForResource(Class<? extends LibraryResource> resource) {
    if (activitiesMap.containsKey(resource)) {
      return activitiesMap.get(resource);
    }
    return null;
  }

  private LibraryResourceActivityRegistry() {
    this.activitiesMap = new HashMap<>();
  }

}
