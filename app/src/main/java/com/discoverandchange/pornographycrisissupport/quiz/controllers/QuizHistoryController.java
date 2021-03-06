package com.discoverandchange.pornographycrisissupport.quiz.controllers;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.discoverandchange.pornographycrisissupport.BaseNavigationActivity;
import com.discoverandchange.pornographycrisissupport.R;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;

/**
 * QuizHistoryController displays all quiz scores in a list.
 * @author Keith Higbee
 */
public class QuizHistoryController extends BaseNavigationActivity {

  /**
   * Creates the view displaying all quiz scores.
   * @param savedInstanceState Any saved data needed for this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz_history_controller);

    // get data from db
    Cursor cursor = (new QuizService(this)).getAllQuizzes();

    // where the data is coming from
    String[] from = { ScoresTable.SCORE, ScoresTable.DATE_CREATED };

    // what is displaying the data
    int[] to = { R.id.quizHistoryScore, R.id.quizHistoryDate };

    // cursor adapter wrapped around the cursor
    CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
        R.layout.item_quiz_history, cursor, from, to, 0);

    // get reference to the ListView
    ListView list = (ListView) findViewById(android.R.id.list);

    // pass adapter to the ListView
    list.setAdapter(cursorAdapter);
  }
}
