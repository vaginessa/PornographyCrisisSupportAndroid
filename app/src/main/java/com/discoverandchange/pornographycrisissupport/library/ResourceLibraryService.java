package com.discoverandchange.pornographycrisissupport.library;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.library.json.FakeHTTPJSONLoader;
import com.discoverandchange.pornographycrisissupport.library.json.HTTPJSONLoader;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Range;

/**
 * Handles the storage and retrieval of LibraryResource objects and their associated mapping
 * to quiz scores.  Notifies any observers when the resource library has been loaded.
 */
public class ResourceLibraryService {

  /**
   * Singleton reference
   */
  private static ResourceLibraryService service;

  /**
   * The list of observers to be notified when the resource library is modified.
   */
  private Set<LibraryServiceObserver> observers;

  /**
   * Maps a range of quiz scores to the list of resources that corresponds to that range.
   */
  private Map<Range, List<LibraryResource>> resourcesByRange;

  /**
   * Tracks if the resources have been loaded from the network yet.
   */
  private boolean resourcesLoaded = false;

  /**
   * Returns the singleton instance of the ResourceLibraryService.  As this is synchronized
   * be careful calling this too often in threaded methods.
   * @return The fully instantiated instance of the ResourceLibraryService.
   */
  public synchronized static ResourceLibraryService getInstance() {
    if (service == null) {
      service = new ResourceLibraryService();
    }
    return service;
  }

  /**
   * Constructs a vanilla ResourceLibraryService.
   */
  public ResourceLibraryService() {
    resourcesByRange = new HashMap<Range, List<LibraryResource>>();
    observers = new HashSet<>();
  }

  /**
   * Retrieves a list of resources that correspond to the given quiz score.  If there are no resources
   * for that score then an empty list is returned.
   * @param latestQuizScore The quiz score that we want to retrieve resources for.
   * @return Empty list if no resources match the quiz score, otherwise list of LibraryResources.
   */
  public List<LibraryResource> getResourcesForQuizScore(int latestQuizScore) {
    for (Range range : resourcesByRange.keySet()) {
      if (range.contains(latestQuizScore)) {
        return resourcesByRange.get(range);
      }
    }

    // should never happen but in the event no resources load, or for some reason
    // the quiz score is out of range for what we have saved we want the app
    // to behave normally so we return an empty list.
    return new ArrayList<>();
  }

  /**
   * Given a quiz object, return the list of resources that correspond to the score of that quiz.
   * If there are no resources for that score than an empty list is returned.
   * @param quiz Quiz object containing the score we want resources for.  Can't be null.
   * @return The list of library resources if the score matches a range in the service,
   *      empty list otherwise.
   * @throws IllegalArgumentException if the quiz score is null.
   */
  public List<LibraryResource> getResourcesForQuiz(Quiz quiz) {
    if (quiz == null) {
      throw new IllegalArgumentException("Quiz argument cannot be null");
    }

    return getResourcesForQuizScore(quiz.getScore());
  }

  /**
   * For a given range add the list of LibraryResources for that range.
   * @param range The range interval (min to max) that the resources should correspond to.
   * @param resources The list of resources to add to the service for the passed in range.
   * @throws IllegalArgumentException if the range is null or the resources is null.
   */
  public void addResources(Range range, List<LibraryResource> resources) {
    if (resources == null) {
      String error = "attempt to add null resources in library service.";
      Log.wtf(Constants.LOG_TAG, error);
      throw new IllegalArgumentException(error);
    }

    if (range == null) {
      String error = "attempt to add null range in library service.";
      Log.wtf(Constants.LOG_TAG, error);
      throw new IllegalArgumentException(error);
    }

    List<LibraryResource> mapResources = new ArrayList<>();
    if (!resourcesByRange.containsKey(range)) {
      resourcesByRange.put(range, mapResources);
    }
    else {
      mapResources = resourcesByRange.get(range);
    }
    mapResources.addAll(resources);
  }

  /**
   * For a given range add the individual LibraryResource object to that range.
   * @param range The range interval (min to max) that the resource should correspond to.
   * @param resource The resource to add to the service for the passed in range.
   * @throws IllegalArgumentException if the range is null or the resource is null.
   */
  public void addResource(Range range, LibraryResource resource) {
    List<LibraryResource> mapResources = new ArrayList<>();
    if (!resourcesByRange.containsKey(range)) {
      resourcesByRange.put(range, mapResources);
    }
    else {
      mapResources = resourcesByRange.get(range);
    }
    mapResources.add(resource);
  }

  /**
   * Adds a library service observer to be notified when resources change
   * in the library.  If the observer already is in the list, it is a noop.
   * @param observer The object implementing the observer resource you want to be notified.
   */
  public void registerObserver(LibraryServiceObserver observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  /**
   * Removes the library service observer.  If the observer is not observing
   * this object it is a noop.
   * @param observer The object implementing the observer resource you want to be notified.
   */
  public void removeObserver(LibraryServiceObserver observer) {
    if (observers.contains(observer)) {
      observers.remove(observer);
    }
  }

  /**
   * Returns if the resources have been loaded from the external network.
   * @return true if the network resources have been loaded, false otherwise.
   */
  public boolean isResourcesLoaded() {

    return resourcesLoaded;
  }

  /**
   * Marks the external network resources as being loaded and complete.  Notifies all the observers
   * that the resources have been loaded.
   */
  public void markResourcesLoaded() {
    this.resourcesLoaded = true;
    this.notifyObservers();
  }

  /**
   * Attempts to load all of the resources from the network.  This is an asynchronous method. If the
   * caller wants to be notified when the resources have finished loader it must implement the
   * LibraryServiceObserver class and add itself as an observer to this class.
   */
  public void loadResources() {
    resourcesLoaded = false;
    //HTTPJSONLoader loader = new FakeHTTPJSONLoader();
    HTTPJSONLoader loader = new HTTPJSONLoader(Constants.LIBRARY_RESOURCES_AUTH_TOKEN);
    LoadLibraryResourcesAsyncTask task = new LoadLibraryResourcesAsyncTask(this, loader,
      ResourceDeserializerService.getInstance());
    task.execute(Constants.LIBRARY_RESOURCES_ENDPOINT);
  }

  /**
   * Runs through all of the observers and tells them that our resources have been loaded.
   */
  private void notifyObservers() {
    for (LibraryServiceObserver observer : observers) {
      observer.resourcesLoaded(this);
    }
  }
}
