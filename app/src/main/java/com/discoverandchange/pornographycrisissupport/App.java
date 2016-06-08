package com.discoverandchange.pornographycrisissupport;

import android.app.Application;
import android.content.Context;

/**
 * Created by snielson on 6/7/16.
 */
public class App extends Application {

  protected static Context context = null;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  public static Context getContext() {
    return context;
  }

}