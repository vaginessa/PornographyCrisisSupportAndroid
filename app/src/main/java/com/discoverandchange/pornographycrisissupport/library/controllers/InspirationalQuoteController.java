package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;

/**
 * Displays the inspirational quote resource sent via the LibraryController.
 */
public class InspirationalQuoteController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspirational_quote_controller);
    TextView txtInspirationalQuote = (TextView)findViewById(R.id.txtInspirationalQuote);

    Intent intent = this.getIntent();
    Object data = intent.getSerializableExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (data == null || !(data instanceof InspirationalQuoteResource)) {
      Log.wtf(Constants.LOG_TAG, "Sent null inspirational quote resource when we shouldn't have");
      finish();
      return;
    }
    InspirationalQuoteResource resource = (InspirationalQuoteResource)data;
    txtInspirationalQuote.setText(resource.getText());
  }
}
