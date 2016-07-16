package com.discoverandchange.pornographycrisissupport.library;

/**
 * Observer interface for those wanting to be notified of events from the ResourceLibraryService
 * class.
 */
public interface LibraryServiceObserver {

  /**
   * Fired when the ResourceLibraryService has finished loading all of it's network resources. The
   * service is passed along so you can retrieve the loaded resources.
   * @param service The service that finished loading it's resources.
   */
  void resourcesLoaded(ResourceLibraryService service);

  /**
   * Fired when the ResourceLibraryService had an error in loading the network resrouces.
   * The service is passed along so you can retrieve more information about it.
   * @param service The service that failed to load it's resources.
   */
  void resourcesLoadError(ResourceLibraryService service);
}
