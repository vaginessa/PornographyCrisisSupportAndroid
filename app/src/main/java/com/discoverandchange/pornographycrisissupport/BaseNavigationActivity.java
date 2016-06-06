package com.discoverandchange.pornographycrisissupport;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.discoverandchange.pornographycrisissupport.library.LibraryController;
import com.discoverandchange.pornographycrisissupport.quiz.QuizController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Shares common code for the menu navigation items.
 * Uses code sample from here: http://stackoverflow.com/questions/4922641/sliding-drawer-appear-in-all-activities/25865925#25865925
 * and here: http://stackoverflow.com/questions/30822326/sharing-navigationview-with-all-the-activities
 * Created by snielson on 6/4/16.
 */
public class BaseNavigationActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

  protected DrawerLayout fullLayout;
  protected FrameLayout frameLayout;

  @Override
  public void setContentView(int layoutResID) {

    fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
    frameLayout = (FrameLayout) fullLayout.findViewById(R.id.drawer_frame);

    getLayoutInflater().inflate(layoutResID, frameLayout, true);

    super.setContentView(fullLayout);
//    TODO: stephen if you figure out how to get this floating bar to work, uncomment this as it came
//          with the sample code, but I could never get it to work.

//    //Your drawer content...
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
//
////    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////    fab.setOnClickListener(new View.OnClickListener() {
////      @Override
////      public void onClick(View view) {
////        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////            .setAction("Action", null).show();
////      }
////    });
//
//    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//    drawer.addDrawerListener(toggle);
//    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.nav_drawer_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (id) {
      case R.id.action_support_network: {
        this.launchActivity(SupportNetworkList.class);
        return true;
      }
      case R.id.action_quiz: {
        this.launchActivity(QuizController.class);
        return true;
      }
      case R.id.action_library: {
        this.launchActivity(LibraryController.class);
        return true;
      }
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    switch (id) {
      case R.id.nav_support_network: {
        this.launchActivity(SupportNetworkList.class);
      }
      break;
      case R.id.nav_quiz: {
        this.launchActivity(QuizController.class);
      }
      break;
      case R.id.nav_library: {
        this.launchActivity(LibraryController.class);
      }
      break;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  /**
   * Given an activity class to launch launch the intent with it.
   * @param clazz The class of the activity we want to launch from the navigation.
   */
  private void launchActivity(Class clazz) {
    Intent intent = new Intent(getBaseContext(), clazz);
    this.startActivity(intent);
  }
}