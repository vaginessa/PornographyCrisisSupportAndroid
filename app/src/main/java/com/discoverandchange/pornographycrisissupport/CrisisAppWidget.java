package com.discoverandchange.pornographycrisissupport;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.discoverandchange.pornographycrisissupport.quiz.controllers.QuizController;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

/**
 * Implementation of App Widget functionality.
 */
public class CrisisAppWidget extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
    super.onEnabled(context);
    //SupportContact supportContact;
    //SupportNetworkService supportNetworkService = SupportNetworkService.getInstance(context);
    //supportContact = supportNetworkService.getCrisisSupportContact();
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }

  public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {

    // Create an intent to launch the Dialer
    QuizController quizController = new QuizController();
    Intent intent = new Intent(context, QuizController.class);
    intent.putExtra("isCrisisLaunchFromWidget", true);

    // apparently the old intent's stick around and new data added to the intent is not seen
    // really wierd.  To fix this problem I use the update_current flag
    // http://stackoverflow.com/a/26720501
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.crisis_widget);
    //views.setTextViewText(R.id.appwidget_text, widgetText);

    // Get the layout and attach an on-click listener
    views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

}

