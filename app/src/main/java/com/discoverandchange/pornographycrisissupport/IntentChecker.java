package com.discoverandchange.pornographycrisissupport;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;


/**
 * Verifies that an intent can open without error before actually doing so.
 *  @author Stephen Nielson
 */
public class IntentChecker extends ContextWrapper {

  public IntentChecker(Context base) {
    super(base);
  }

  public boolean isIntentSafeToLaunch(Intent intent) {
    PackageManager packageManager = getPackageManager();
    List activities = packageManager.queryIntentActivities(intent,
        PackageManager.MATCH_DEFAULT_ONLY);
    boolean isIntentSafe = activities.size() > 0;
    return isIntentSafe;
  }
}
