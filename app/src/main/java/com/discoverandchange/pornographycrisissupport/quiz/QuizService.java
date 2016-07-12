package com.discoverandchange.pornographycrisissupport.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.discoverandchange.pornographycrisissupport.db.PcsDbOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.ScoresTable;


/**
 * Connects to the SQLite database and performs CRUD operations with the data.
 *
 * @author Keith Higbee
 */
public class QuizService extends ContextWrapper {

  /**
   * The minimum quiz score that is considered a crisis.
   */
  private static final int CRISIS_SCORE_THRESHOLD = 7;

  /**
   * The database reference.
   */
  private SQLiteDatabase db;
  /**
   * The Helper reference.
   */
  private PcsDbOpenHelper helper;

  public QuizService(Context base) {
    super(base);
  }

  /**
   * Gets data from the quiz and saves it to the database.
   *
   * @param quiz The quiz object that contains the data to be saved
   * @return Returns true if the score is a crisis score
   */
  public boolean saveQuiz(Quiz quiz) {
    // get db
    helper = new PcsDbOpenHelper(this);
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
    return isCrisisScore(quiz.getScore());
  }

  /**
   * Gets the latest quiz from the database and saves it to a quiz object.
   *
   * @return The most recent quiz score
   */
  public int getLatestQuizScore() {
    try {
      // get db
      helper = new PcsDbOpenHelper(this);
      db = helper.getReadableDatabase();

    }
    catch (SQLiteException ex) {
      return 0;
    }

    Quiz quiz = new Quiz();
    if (db != null) {

      // build query
      Cursor cursor =
          db.query(ScoresTable.TBL_SCORES,        // table
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
      if (cursor != null) {
        cursor.moveToFirst();
      }


      if (cursor.getCount() != 0) {
        // build quiz
        quiz.setId(Integer.parseInt(cursor.getString(0)));    // 0 = column _id
        quiz.setScore(Integer.parseInt(cursor.getString(1))); // 1 = column score
        quiz.setDate(cursor.getString(2));                    // 2 = column dateCreated

        Log.d("getLatestQuiz",
            "ID:" + quiz.getId()
                + " Score:" + quiz.getScore()
                + " Date:" + quiz.getDate());
      }


      // close the cursor
      cursor.close();

      // close the db
      db.close();
    }
    return quiz.getScore();
  }

  /*
  public List<Quiz> getAllQuizzes() {
    List<Quiz> quizzes = new LinkedList<Quiz>();

    // get db
    helper = new PcsDbOpenHelper(this);
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

  /**
   * Gets all quizzes from the database.
   *
   * @return The cursor data from the query
   */
  public Cursor getAllQuizzes() {
    // get db
    helper = new PcsDbOpenHelper(this);
    db = helper.getReadableDatabase();

    // build query and cursor
    String query = "SELECT * FROM " + ScoresTable.TBL_SCORES
        + " ORDER BY " + ScoresTable.DATE_CREATED + " DESC";
    Cursor cursor = db.rawQuery(query, null);

    // iterate through each row, add to the list
    Quiz quiz;
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

  /**
   * Updates a quiz score based on id number.
   *
   * @param quiz The quiz to be updated
   * @return The value of the quiz id
   */
  public int updateQuiz(Quiz quiz) {
    // get db
    helper = new PcsDbOpenHelper(this);
    db = helper.getWritableDatabase();

    // create ContentValues
    ContentValues values = new ContentValues();
    values.put(ScoresTable.SCORE, quiz.getScore());

    // update the db
    int id = db.update(ScoresTable.TBL_SCORES,           // table
        values,                                         // column:value
        ScoresTable.SCORE_ID + " = ?",                    // selection
        new String[]{String.valueOf(quiz.getId())}); // selectionArgs

    // close the db
    db.close();

    // return quizzes
    return id;
  }

  /**
   * Deletes a quiz based on id number.
   *
   * @param quiz The quiz to be deleted
   */
  public void deleteQuiz(Quiz quiz) {
    // get db
    helper = new PcsDbOpenHelper(this);
    db = helper.getWritableDatabase();

    // delete the quiz from db
    db.delete(ScoresTable.TBL_SCORES,                   // table
        ScoresTable.SCORE_ID + " = ?",                    // selections
        new String[]{String.valueOf(quiz.getId())}); // selectionArgs

    // close the db
    db.close();

    // log
    Log.d("deleteQuiz", quiz.toString());
  }

  /**
   * Returns true if the passed in score should be treated as a crisis score range.
   * @param score The score to check if it's a crisis score or not
   * @return Returns true if the score is a crisis score as determined by the service.
   */
  public boolean isCrisisScore(int score) {
    if (score >= CRISIS_SCORE_THRESHOLD) {
      return true;
    }
    return score >= CRISIS_SCORE_THRESHOLD;
  }
}
