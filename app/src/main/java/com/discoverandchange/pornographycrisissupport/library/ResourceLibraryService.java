package com.discoverandchange.pornographycrisissupport.library;

import android.util.Log;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snielson on 6/6/16.
 */
public class ResourceLibraryService {
  public ResourceLibraryService() {
    Log.i(Constants.LOG_TAG_DEBUG, "ResourceLibraryService constructor called.");
  }
  public List<LibraryResource> getResourcesForQuiz(Quiz quiz) {
    return new ArrayList<LibraryResource>();
  }

  public void addResources(Range range, List<LibraryResource> mild) {
    if (mild == null) {
      String error = "attempt to add null resources in library service.";
      Log.wtf(Constants.LOG_TAG, error);
      return;
    }
  }
}
