package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResourceActivityRegistry;
import com.discoverandchange.pornographycrisissupport.library.LibraryResourceListAdapter;
import com.discoverandchange.pornographycrisissupport.library.LibraryServiceObserver;
import com.discoverandchange.pornographycrisissupport.library.ResourceLibraryService;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;
import com.discoverandchange.pornographycrisissupport.settings.SettingsService;

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

  /**
   * Creates the library list and displays the current quiz score on the activity.
   * @param savedInstanceState {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_library_controller);
  }

  /**
   * Starts the activity and begins the loading of the library resources if they are not loaded
   * already.
   */
  @Override
  protected void onStart() {
    super.onStart();

    Log.d(Constants.LOG_TAG, "starting library");

    QuizService quizService = new QuizService(getBaseContext());
    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.registerObserver(this);
    if (!service.isResourcesLoaded()) {
      service.loadResources();
    } else {
      // setup our text view and setup our list adapter.
      setupListAdapter(service, quizService);
    }
    TextView latestScore = (TextView) findViewById(R.id.latestScore);
    int score = quizService.getLatestQuizScore();

    Log.d(Constants.LOG_TAG, "setting latest score view: " + score);
    latestScore.setText(Integer.toString(score));

    toggleContactNetworkMessageForQuizScore(quizService, score);
  }

  /**
   * Handles cleanup code for the library.
   */
  @Override
  protected void onStop() {
    super.onStop();
    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.removeObserver(this); // remove ourselves so we don't have memory leaks.
    Log.d(Constants.LOG_TAG, "stopping library");
  }

  /**
   * Reloads the library with the new library resources.
   * @param service The service that finished loading it's resources.
   */
  @Override
  public void resourcesLoaded(ResourceLibraryService service) {
    QuizService quizService = new QuizService(getBaseContext());
    // grab our resources.
    setupListAdapter(service, quizService);

  }

  /**
   * Display an error message saying the resources failed loading and ask the user to retry
   * loading the resources.  It will keep asking the prompt until the user cancels or network
   * connectivity is re-established.
   * @param service The service that failed to load.
   */
  @Override
  public void resourcesLoadError(ResourceLibraryService service) {
    Log.e(Constants.LOG_TAG, "Failed to load resource library");
    final ResourceLibraryService libraryService = service;
    new AlertDialog.Builder(this)
        .setTitle(R.string.library_controller_no_network_title)
        .setMessage(R.string.library_controller_no_network)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // right now we do nothing if they click the ok button.
            new AsyncTask<Void, Void, Void>() {

              @Override
              protected Void doInBackground(Void... params) {
                libraryService.loadResources();
                return null;
              }
            }.execute();
          }
        })
        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // right now we do nothing if they click the cancel button because the dialog will be
            // auto-dismissed.
          }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
  }

  private void toggleContactNetworkMessageForQuizScore(QuizService quizService, int score) {
    TextView contactsNotifiedMessage =
        (TextView)findViewById(R.id.libraryContactNetworkNotifiedMessage);
    if (!quizService.isCrisisScore(score)) {
      contactsNotifiedMessage.setVisibility(View.GONE);
    } else {
      contactsNotifiedMessage.setVisibility(View.VISIBLE);
    }
  }

  private void setupListAdapter(ResourceLibraryService service, QuizService quizService) {
    List<LibraryResource> resources = new ArrayList<>();

    // load in the resources from the settings if we have any.
    SettingsService settingsService = SettingsService.getInstance(getBaseContext());
    resources.addAll(settingsService.getSettingsResources());

    if (service.isResourcesLoaded()) {
      int latestScore = quizService.getLatestQuizScore();
      Log.d(Constants.LOG_TAG, "retrieving resources for score: " + latestScore);
      resources.addAll(service.getResourcesForQuizScore(latestScore));
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
        Class<? extends Activity> activityToLoad = registry.getActivityForResource(
            resourceToLoad.getClass());
        Intent intent = new Intent(getBaseContext(), activityToLoad);
        intent.putExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE, resourceToLoad);
        startActivity(intent);
      }
    });
  }
}