package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snielson on 6/22/16.
 *
 *
 */
public class LibraryResourceActivityRegistry {
  Map<Class<? extends LibraryResource>, Class<? extends Activity>> activitiesMap;

  private static LibraryResourceActivityRegistry registry;

  // Singleton used here,
  public static synchronized LibraryResourceActivityRegistry getInstance() {
    if (registry == null) {
      registry = new LibraryResourceActivityRegistry();
    }
    return registry;
  }

  private LibraryResourceActivityRegistry() {
    this.activitiesMap = new HashMap<>();
  }

  public void registerResource(Class<? extends LibraryResource> resourceClass,
                               Class<? extends Activity> activityClass)
  {
    activitiesMap.put(resourceClass, activityClass);
  }

  public Class<? extends Activity> getActivityForResource(Class<? extends LibraryResource> resource) {
    if (activitiesMap.containsKey(resource)) {
      return activitiesMap.get(resource);
    }
    return null;
  }
}
