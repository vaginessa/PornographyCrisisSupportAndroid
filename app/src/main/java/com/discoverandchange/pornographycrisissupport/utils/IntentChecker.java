package com.discoverandchange.pornographycrisissupport.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;


/**
 * Verifies that an intent can open without error before actually doing so.  All external
 * application intents should be checked with this class before we actually launch them.
 *
 * @author Stephen Nielson
 */
public class IntentChecker extends ContextWrapper {

  /**
   * Constructs the IntentChecker.
   *
   * @param base the android context to use.
   */
  public IntentChecker(Context base) {
    super(base);
  }

  /**
   * Returns true if the intent we are intending to launch is actually available in the system.
   *
   * @param intent The intent we want to check to see if it exists.
   * @return true if the intent is safe to launch, false otherwise.
   */
  public boolean isIntentSafeToLaunch(Intent intent) {
    PackageManager packageManager = getPackageManager();
    List activities = packageManager.queryIntentActivities(intent,
        PackageManager.MATCH_DEFAULT_ONLY);
    boolean isIntentSafe = activities.size() > 0;
    return isIntentSafe;
  }
}
