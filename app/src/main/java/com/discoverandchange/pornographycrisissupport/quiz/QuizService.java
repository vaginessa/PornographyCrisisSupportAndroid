package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.db.ScoresTable;
import com.discoverandchange.pornographycrisissupport.db.ScoresProvider;

import java.util.List;

/**
 *
 */

public class QuizService extends ContextWrapper {
  List<Quiz> listScores;

  public QuizService(Context base) {
    super(base);
  }

  public boolean saveQuiz(Quiz quiz) {
    throw new RuntimeException("Method not implemented");
  }

  public boolean saveQuiz(int score) {
    ContentValues values = new ContentValues();
    values.put(ScoresTable.SCORE, score);
    Uri scoreUri = getContentResolver().insert(ScoresProvider.CONTENT_URI, values);
    Log.d("QuizService", "Inserted score " + scoreUri.getLastPathSegment());
    return true;
  }

  public int getLatestQuizScore() {
    return 0;
  }

}
