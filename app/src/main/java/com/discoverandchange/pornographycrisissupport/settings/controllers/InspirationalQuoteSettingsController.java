package com.discoverandchange.pornographycrisissupport.settings.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.settings.SettingsService;

import org.apache.commons.lang3.StringUtils;

/**
 * Handles the saving, updating, and clearing of the inspirational quote settings
 * for the entire app.
 */
public class InspirationalQuoteSettingsController extends BaseNavigationActivity {

  /**
   * Creates the layout and displays the saved inspirational quote settings if there is one.
   * @param savedInstanceState {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspirational_quote_settings_controller);

    SettingsService service = SettingsService.getInstance(getBaseContext());
    String text = service.getInspirationalQuote();
    if (!StringUtils.isEmpty(text)) {
      EditText editText = (EditText)findViewById(R.id.editTextInspirationalQuote);
      editText.setText(text);
    }
  }

  /**
   * Validates and saves the user's entered quote.
   * @param btn The save button that was clicked.
   */
  public void handleSaveQuote(View btn) {
    EditText editText = (EditText)findViewById(R.id.editTextInspirationalQuote);
    String text = editText.getText().toString().trim();

    if (StringUtils.isEmpty(text)) {
      displayMissingTextMessage();
      return;
    }

    SettingsService service = SettingsService.getInstance(getBaseContext());
    service.saveInspirationalQuote(text);
    finish();
  }

  /**
   * Clears both the text display of the saved quote and the quote in the app settings.
   * @param btn The clear button that was clicked.
   */
  public void handleClearQuote(View btn) {
    SettingsService service = SettingsService.getInstance(getBaseContext());
    service.clearInspirationalQuote();
    EditText editText = (EditText)findViewById(R.id.editTextInspirationalQuote);
    editText.setText("");
  }

  /**
   * Displays a dialog telling the user to provide an inspirational quote.
   */
  private void displayMissingTextMessage() {
    new AlertDialog.Builder(this)
        .setTitle(R.string.inspirational_quote_missing_title)
        .setMessage(R.string.inspirational_quote_missing)
        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // right now we do nothing if they click the ok button.
          }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
  }
}