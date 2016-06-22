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
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkEdit;

import java.util.ArrayList;
import java.util.List;

public class LibraryController extends BaseNavigationActivity {

  private LibraryResourceListAdapter libraryResourceListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_library_controller);

    List<LibraryResource> resources = new ArrayList<>();
    resources.add(new VideoResource());
    resources.add(new AudioResource());
    resources.add(new WebsiteContentResource());
    resources.add(new ExternalWebsiteResource());

    libraryResourceListAdapter = new LibraryResourceListAdapter(getBaseContext(), resources);

    ListView libraryListView = (ListView) findViewById(R.id.libraryListView);
    libraryListView.setAdapter(libraryResourceListAdapter);

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
}
