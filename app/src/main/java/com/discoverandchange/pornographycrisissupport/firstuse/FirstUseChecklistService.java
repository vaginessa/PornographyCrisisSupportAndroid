package com.discoverandchange.pornographycrisissupport.firstuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.discoverandchange.pornographycrisissupport.firstuse.controllers.FirstUseController;
import com.discoverandchange.pornographycrisissupport.firstuse.controllers.TestMessageController;
import com.discoverandchange.pornographycrisissupport.settings.controllers.MeaningfulPictureSettingsController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.controllers.SupportNetworkListController;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the functionality to display a checklist of setup items for the user and let them select
 * list items. Most functionality will be performed by other services.
 */
public class FirstUseChecklistService {

  /**
   * The very start of the setup process.
   */
  public static final int INITIAL_STEP = 1;

  /**
   * Setup the support network setup step.
   */
  public static final int SUPPORT_STEP = 2;

  /**
   * Setup the meaningful image setup step.
   */
  public static final int IMAGE_STEP = 3;

  /**
   * Verify our support network is being notified that we are contacting them.
   */
  public static final int TEST_STEP = 4;

  /**
   * State to indicate we finished setting up the controller.
   */
  public static final int COMPLETED_STEP = 5;

  /**
   * The last step of our setup.
   */
  public static final int FINAL_STEP = COMPLETED_STEP;

  /**
   * Name of the shared preferences file we use for tracking the initial setup.
   */
  private static final String FIRST_USE_PREFERENCES = "FirstUsePreferences";

  /**
   * The first use instance service.
   */
  private static FirstUseChecklistService instance;

  /**
   * The application context for this first use.
   */
  private final Context context;

  private Map<Integer, Class<? extends Activity>> setupActivities;

  /**
   * Returns the current service instance or creates one for the passed in context
   *
   * @param context The current application context.
   * @return The instantiated service.
   */
  public static synchronized FirstUseChecklistService getInstance(Context context) {

    if (instance == null) {
      instance = new FirstUseChecklistService(context.getApplicationContext());
    }

    return instance;
  }

  /**
   * Constructs the service for the given application context.
   *
   * @param context The global application context.
   */
  public FirstUseChecklistService(Context context) {
    this.context = context;
    setupActivities = new HashMap<>();
    setupActivities.put(SUPPORT_STEP, SupportNetworkListController.class);
    setupActivities.put(IMAGE_STEP, MeaningfulPictureSettingsController.class);
    setupActivities.put(TEST_STEP, TestMessageController.class);
  }

  /**
   * Returns true if the first use setup has been completed.
   *
   * @return True if the first use has finished, false otherwise.
   */
  public boolean isSetupComplete() {
    SharedPreferences pref = context.getSharedPreferences(FIRST_USE_PREFERENCES,
        Context.MODE_PRIVATE);
    return pref.getBoolean("activity_executed", false);
  }

  /**
   * Returns the current setup step that the application user is on. If the user has not finished
   * any setup they will be on @see FirstUseChecklistService.INITIAL_STEP
   *
   * @return The current step the application user is on.
   */
  public int getCurrentSetupStep() {
    SharedPreferences pref = context.getSharedPreferences(FIRST_USE_PREFERENCES,
        Context.MODE_PRIVATE);
    int stepValue = pref.getInt("step", INITIAL_STEP);
    return stepValue;
  }

  /**
   * Launches the setup activity using the passed in activity context as a base.
   *
   * @param currentActivityContext The current activity context.
   */
  public void launchSetup(Context currentActivityContext) {
    Intent intent = new Intent(currentActivityContext, FirstUseController.class);
    currentActivityContext.startActivity(intent);
  }

  /**
   * Launches the activity to setup the current step the user is on.
   *
   * @param currentActivityContext The current activity context the user is on.
   */
  public void launchCurrentSetupActivity(Context currentActivityContext) {
    int step = getCurrentSetupStep();

    if (!this.setupActivities.containsKey(step)) {
      throw new IllegalStateException("Current setup in invalid state: " + step);
    }

    Class<? extends Activity> clazz = this.setupActivities.get(step);
    Intent intent = new Intent(currentActivityContext, clazz);
    currentActivityContext.startActivity(intent);
  }

  /**
   * Used to mark an activity setup to be complete.  The various activities in the application can
   * mark their setup process as being complete by passing in the setup step they correspond to.
   *
   * @param completedStep The step to mark as completed.
   */
  public void markStepComplete(int completedStep) {

    int newState = completedStep + 1;
    SharedPreferences pref = context.getSharedPreferences(FIRST_USE_PREFERENCES,
        Context.MODE_PRIVATE);
    SharedPreferences.Editor ed = pref.edit();
    ed.putInt("step", newState);

    if (newState == COMPLETED_STEP) {
      ed.putBoolean("activity_executed", true);
    } else {
      ed.putBoolean("activity_executed", false);
    }
    ed.commit();

  }

  /**
   * Clears the setup being complete and puts it back on the original first step.
   */
  public void resetSetup() {
    markStepComplete(INITIAL_STEP);
  }
}
