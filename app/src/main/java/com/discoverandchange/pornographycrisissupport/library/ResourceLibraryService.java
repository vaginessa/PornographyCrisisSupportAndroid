package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Range;
/**
 * Created by snielson on 6/6/16.
 */
public class ResourceLibraryService {

  private Set<LibraryServiceObserver> observers;

  private Map<Range, List<LibraryResource>> resourcesByRange;

  public ResourceLibraryService() {
    resourcesByRange = new HashMap<Range, List<LibraryResource>>();
    observers = new HashSet<>();
  }
  public List<LibraryResource> getResourcesForQuiz(Quiz quiz) {
    if (quiz == null) {
      throw new IllegalArgumentException("Quiz argument cannot be null");
    }
    for (Range range : resourcesByRange.keySet()) {
      if (range.contains(quiz.getScore())) {
        return resourcesByRange.get(range);
      }
    }

    return null;
  }

  public void addResources(Range range, List<LibraryResource> resources) {
    if (resources == null) {
      String error = "attempt to add null resources in library service.";
      Log.wtf(Constants.LOG_TAG, error);
      return;
    }

    List<LibraryResource> mapResources = new ArrayList<>();
    if (!resourcesByRange.containsKey(range)) {
      resourcesByRange.put(range, mapResources);
    }
    else {
      mapResources = resourcesByRange.get(range);
    }
    mapResources.addAll(resources);
    this.notifyObservers();
  }

  public void addResource(Range range, LibraryResource resource) {
    List<LibraryResource> mapResources = new ArrayList<>();
    if (!resourcesByRange.containsKey(range)) {
      resourcesByRange.put(range, mapResources);
    }
    else {
      mapResources = resourcesByRange.get(range);
    }
    mapResources.add(resource);
    this.notifyObservers();
  }

  public void registerResource(Class<? extends LibraryResource> resourceClass,
                               Class<Activity> activityClass)
  {

  }

  /**
   * Adds a library service observer to be notified when resources change
   * in the library.  If the observer already is in the list, it is a noop.
   * @param observer
   */
  public void registerObserver(LibraryServiceObserver observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  /**
   * Removes the library service observer.  If the observer is not observing
   * this object it is a noop.
   * @param observer
   */
  public void removeObserver(LibraryServiceObserver observer) {
    if (observers.contains(observer)) {
      observers.remove(observer);
    }
  }

  public boolean isResourcesLoaded() {
    return false;
  }

  public void loadResources() {

  }

  /**
   * Runs through all of the observers and tells them that our resources have been loaded.
   */
  private void notifyObservers() {
    for (LibraryServiceObserver observer : observers) {
      // TODO: stephen perhaps a better name for this would be resourcesChanged.
      observer.resourcesLoaded(this);
    }
  }
}
