package com.discoverandchange.pornographycrisissupport.quiz;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizHistoryController extends BaseNavigationActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz_history_controller);

    // get data from db
    Cursor cursor = (new QuizService(this)).getAllQuizzes();

    // where the data is coming from
    String[] from = { ScoresTable.SCORE, ScoresTable.DATE_CREATED };

    // what is displaying the data
    int[] to = { android.R.id.text1 };

    // cursor adapter wrapped around the cursor
    CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_1, cursor, from, to, 0);

    // get reference to the ListView
    ListView list = (ListView) findViewById(android.R.id.list);

    // pass adapter to the ListView
    list.setAdapter(cursorAdapter);
  }
}
