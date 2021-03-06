package com.discoverandchange.pornographycrisissupport;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.discoverandchange.pornographycrisissupport.library.controllers.LibraryController;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizController;
import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizHistoryController;
import com.discoverandchange.pornographycrisissupport.settings.controllers.SettingsController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.controllers.SupportNetworkListController;

/**
 * Shares common code for the menu navigation items.
 *
 * @author Stephen Nielson
 */
public class BaseNavigationActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  /*
  *  Uses code sample from here: http://stackoverflow.com/questions/4922641/sliding-drawer-appear-in-all-activities/25865925#25865925
  *  and here: http://stackoverflow.com/questions/30822326/sharing-navigationview-with-all-the-activities
   */

  /**
   * Holds a reference to the entire activity drawer layout.
   */
  protected DrawerLayout fullLayout;

  /**
   * Holds a reference to the inner frame that all inherited activities views will be stored into.
   */
  protected FrameLayout frameLayout;

  /**
   * Adjusts the application so that it fills the enter screen of the Android device.
   *
   * @param layoutResId Information about the resolution of the user's Android device
   */
  public void setContentView(int layoutResId) {

    fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
    frameLayout = (FrameLayout) fullLayout.findViewById(R.id.drawer_frame);

    getLayoutInflater().inflate(layoutResId, frameLayout, true);

    super.setContentView(fullLayout);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

  }

  /**
   * Allows the user to close the application drawer.
   */
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  /**
   * Adds items to the action bar if the action bar is present.
   *
   * @param menu The visible menu in our application
   * @return True if the menu / action bar is visible and active
   */
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.nav_drawer_main, menu);
    return true;
  }

  /**
   * Handles the action bar item clicks including clicks on Home button so long as parent activity
   * is specified.
   *
   * @param item The action bar item that the user has interacted with
   * @return True if an activity has been launched by the user
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (id) {
      case R.id.action_support_network: {
        this.launchActivity(SupportNetworkListController.class);
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
      case R.id.action_quiz_history: {
        this.launchActivity(QuizHistoryController.class);
        return true;
      }
      case R.id.action_settings: {
        this.launchActivity(SettingsController.class);
        return true;
      }
      default: {
        return super.onOptionsItemSelected(item);
      }
    }
  }


  /**
   * Handles navigation view item clicks.
   *
   * @param item The menu item that the user has interacted with
   * @return True if the user has interacted with a menu item
   */
  public boolean onNavigationItemSelected(MenuItem item) {

    int id = item.getItemId();

    switch (id) {
      case R.id.nav_support_network: {
        this.launchActivity(SupportNetworkListController.class);
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
      case R.id.nav_quiz_history: {
        this.launchActivity(QuizHistoryController.class);
      }
      break;
      case R.id.nav_settings: {
        this.launchActivity(SettingsController.class);
      }
      break;
      default: {
        Log.d(Constants.LOG_TAG, "BaseNavigationActivity.onNavigationItemSelected "
            + "activity launched but no option found for it. id: " + id);
      }
      break;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  /**
   * Given an activity class to launch launch the intent with it.
   *
   * @param clazz The class of the activity we want to launch from the navigation.
   */
  private void launchActivity(Class clazz) {
    Intent intent = new Intent(getBaseContext(), clazz);
    this.startActivity(intent);
  }
}