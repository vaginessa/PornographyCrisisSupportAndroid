package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for displaying library resources, the user's current quiz score, and launching
 * library resource activities when a user clicks on a library resource.
 */
public class LibraryController extends BaseNavigationActivity
  implements LibraryServiceObserver {

  /**
   * The list adapter that holds LibraryResources and handles displaying them.
   */
  private LibraryResourceListAdapter libraryResourceListAdapter;

  @Override
  /**
   * Creates the library list and displays the current quiz score on the activity
   * @param savedInstancedState the information saved for this activity.
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_library_controller);

    QuizService quizService = new QuizService(getBaseContext());
    TextView latestScore = (TextView)findViewById(R.id.latestScore);
    latestScore.setText(Integer.toString(quizService.getLatestQuizScore()));


    ResourceLibraryService service = ResourceLibraryService.getInstance();
    setupListAdapter(service, quizService);
  }

  @Override
  /**
   * Starts the activity and begins the loading of the library resources if they are not loaded
   * already.
   */
  protected void onStart() {
    super.onStart();

    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.registerObserver(this);
    if (!service.isResourcesLoaded()) {
      service.loadResources();
    }
  }

  private void setupListAdapter(ResourceLibraryService service, QuizService quizService) {
    List<LibraryResource> resources = new ArrayList<>();
    if (service.isResourcesLoaded()) {
      resources = service.getResourcesForQuizScore(quizService.getLatestQuizScore());
    }

    libraryResourceListAdapter = new LibraryResourceListAdapter(getBaseContext(), resources);

    ListView libraryListView = (ListView) findViewById(R.id.libraryListView);
    libraryListView.setAdapter(libraryResourceListAdapter);


    // Displays the list of resources and responds to user touches on list items
    // resourceToLoad is item touched
    //
    libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        LibraryResource resourceToLoad = (LibraryResource) parent.getItemAtPosition(position);
        LibraryResourceActivityRegistry registry = LibraryResourceActivityRegistry.getInstance();
        Class<? extends Activity> activityToLoad = registry.getActivityForResource(resourceToLoad.getClass());
        Intent intent = new Intent(getBaseContext(), activityToLoad);
        intent.putExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE, resourceToLoad);
        startActivity(intent);
      }
    });
  }

  @Override
  /**
   * Handles cleanup code for the library.
   */
  protected void onStop() {
    super.onStop();
    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.removeObserver(this); // remove ourselves so we don't have memory leaks.
  }

  @Override
  public void resourcesLoaded(ResourceLibraryService service) {
    QuizService quizService = new QuizService(getBaseContext());
    // grab our resources.
    setupListAdapter(service, quizService);
  }
}
