package com.discoverandchange.pornographycrisissupport.settings.controllers;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.library.InspirationalQuoteResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.ResourceLibraryService;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

public class InspirationalQuoteController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspirational_quote_controller);
  }

  public void handleSaveQuote(View btn) {
    
  }
}
