package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 6/21/16.
 */
public interface LibraryServiceObserver {
  void resourcesLoaded(ResourceLibraryService service);

  // TODO: stephen we probably need to observe when a resource load error occurred
  // since this is asynchronous we should fix that problem.
}
