package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkEdit;

import java.util.ArrayList;
import java.util.List;

public class LibraryController extends BaseNavigationActivity
  implements LibraryServiceObserver {

  private LibraryResourceListAdapter libraryResourceListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_library_controller);

    ResourceLibraryService service = ResourceLibraryService.getInstance();
    setupListAdapter(service);
  }

  @Override
  protected void onStart() {
    super.onStart();

    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.registerObserver(this);
    if (!service.isResourcesLoaded()) {
      service.loadResources();
    }
  }

  private void setupListAdapter(ResourceLibraryService service) {
    QuizService quizService = new QuizService(getBaseContext());
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
  protected void onStop() {
    super.onStop();
    ResourceLibraryService service = ResourceLibraryService.getInstance();
    service.removeObserver(this); // remove ourselves so we don't have memory leaks.
  }

  @Override
  public void resourcesLoaded(ResourceLibraryService service) {
    // grab our resources.
    setupListAdapter(service);
  }
}
