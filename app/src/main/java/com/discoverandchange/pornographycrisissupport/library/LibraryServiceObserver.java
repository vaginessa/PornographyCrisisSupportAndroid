package com.discoverandchange.pornographycrisissupport.library;

/**
 * Observer interface for those wanting to be notified of events from the ResourceLibraryService class.
 */
public interface LibraryServiceObserver {

  /**
   * Fired when the ResourceLibraryService has finished loading all of it's network resources. The
   * service is passed along so you can retrieve the loaded resources.
   * @param service The service that finished loading it's resources.
   */
  void resourcesLoaded(ResourceLibraryService service);

  // TODO: stephen we probably need to observe when a resource load error occurred
  // since this is asynchronous we should fix that problem.
}
