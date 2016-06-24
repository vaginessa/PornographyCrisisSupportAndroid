package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.db.PCSDBOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */

public class QuizService extends ContextWrapper {

  //ScoresProvider provider = new ScoresProvider();
  private SQLiteDatabase db;
  private PCSDBOpenHelper helper;

  public QuizService(Context base) {
    super(base);
  }

  public boolean saveQuiz(Quiz quiz) {
    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getReadableDatabase();

    // create ContentValues
    ContentValues values = new ContentValues();
    values.put(ScoresTable.SCORE, quiz.getScore());

    // insert to db
    db.insert(ScoresTable.TBL_SCORES, // table
        null, //nullColumnHack
        values); // key/value -> key = column, value = columnValues

    // close the db
    db.close();

    // logging
    Log.d("saveQuiz", "Score: " + quiz.getScore());

    // determine ifCrisisQuizScore
    if (quiz.getScore() > 7) {
      return true;
    }
    else {
      return false;
    }
  }

  public int getLatestQuizScore() {
    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getReadableDatabase();

    // build query
    Cursor cursor =
        db.query(ScoresTable.TBL_SCORES,    // table
        //null, //ScoresTable.ALL_COLUMNS,  // columns
        ScoresTable.ALL_COLUMNS,            // columns
        //"column=?",//null,                // selections
        null,                               // selections
        //new String[] {"score"},//null,    // selection args
        null,                               // selection args
        null,                               // group by
        null,                               // having
        ScoresTable.DATE_CREATED + " DESC", // order by
        "0,1");                             // limit

    // move cursor to first result
    if (cursor != null)
      cursor.moveToFirst();

    // build quiz
    Quiz quiz = new Quiz();
    quiz.setId(Integer.parseInt(cursor.getString(0)));    // 0 = column _id
    quiz.setScore(Integer.parseInt(cursor.getString(1))); // 1 = column score
    quiz.setDate(cursor.getString(2));                    // 2 = column dateCreated

    Log.d("getLatestQuiz",
        "ID:" + quiz.getId() +
        " Score:" + quiz.getScore() +
        " Date:" + quiz.getDate());

    // close the cursor
    cursor.close();

    // close the db
    db.close();

    return quiz.getScore();
  }

/*  public List<Quiz> getAllQuizzes() {
    List<Quiz> quizzes = new LinkedList<Quiz>();

    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getReadableDatabase();

    // build query and cursor
    String query = "SELECT * FROM " + ScoresTable.TBL_SCORES;
    Cursor cursor = db.rawQuery(query, null);

    // iterate through each row, add to the list
    Quiz quiz = null;
    if (cursor.moveToFirst()) {
      do {
        quiz = new Quiz();
        quiz.setId(Integer.parseInt(cursor.getString(0)));    // 0 = column _id
        quiz.setScore(Integer.parseInt(cursor.getString(1))); // 1 = column score
        quiz.setDate(cursor.getString(2));                    // 2 = column dateCreated

        // add quiz to quizzes
        quizzes.add(quiz);
      } while (cursor.moveToNext());
    }
    Log.d("getAllQuizzes", quizzes.toString());

    // close the db
    db.close();

    // return quizzes
    return quizzes;
  }*/

  public Cursor getAllQuizzes() {
    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getReadableDatabase();

    // build query and cursor
    String query = "SELECT * FROM " + ScoresTable.TBL_SCORES +
        " ORDER BY " + ScoresTable.DATE_CREATED + " DESC";
    Cursor cursor = db.rawQuery(query, null);

    // iterate through each row, add to the list
    Quiz quiz = null;
    if (cursor.moveToFirst()) {
      do {
        quiz = new Quiz();
        quiz.setId(Integer.parseInt(cursor.getString(0)));    // 0 = column _id
        quiz.setScore(Integer.parseInt(cursor.getString(1))); // 1 = column score
        quiz.setDate(cursor.getString(2));                    // 2 = column dateCreated
      } while (cursor.moveToNext());
    }
    Log.d("getAllQuizzes", cursor.toString());

    // close the db
    db.close();

    // return quizzes
    return cursor;
  }

  public int updateQuiz(Quiz quiz) {
    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getWritableDatabase();

    // create ContentValues
    ContentValues values = new ContentValues();
    values.put(ScoresTable.SCORE, quiz.getScore());

    // update the db
    int i = db.update(ScoresTable.TBL_SCORES,           // table
        values,                                         // column:value
        ScoresTable.SCORE_ID+" = ?",                    // seletion
        new String[] { String.valueOf(quiz.getId()) }); // selectionArgs

    // close the db
    db.close();

    // return quizzes
    return i;
  }

  public void deleteQuiz(Quiz quiz) {
    // get db
    helper = new PCSDBOpenHelper(this);
    db = helper.getWritableDatabase();

    // delete the quiz from db
    db.delete(ScoresTable.TBL_SCORES,                   // table
        ScoresTable.SCORE_ID+" = ?",                    // selections
        new String[] { String.valueOf(quiz.getId()) }); // selectionArgs

    // close the db
    db.close();

    // log
    Log.d("deleteQuiz", quiz.toString());
  }
}
