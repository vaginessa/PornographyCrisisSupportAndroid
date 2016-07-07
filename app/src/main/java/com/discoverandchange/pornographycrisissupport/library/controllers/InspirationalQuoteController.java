package com.discoverandchange.pornographycrisissupport.library.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.Constants;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;

public class InspirationalQuoteController extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspirational_quote_controller);
    TextView txtInspirationalQuote = (TextView)findViewById(R.id.txtInspirationalQuote);

    Intent intent = this.getIntent();
    Object data = intent.getSerializableExtra(Constants.LIBRARY_RESOURCE_VIEW_MESSAGE);
    if (data == null || !(data instanceof InspirationalQuoteResource)) {
      // TODO: stephen handle this error case.
    }
    InspirationalQuoteResource resource = (InspirationalQuoteResource)data;
    txtInspirationalQuote.setText(resource.getText());
  }
}
