package com.discoverandchange.pornographycrisissupport;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;

import com.discoverandchange.pornographycrisissupport.quiz.Quiz;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the QuizService class.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuizServiceTest {

  // TODO: Keith fix unit tests
  @Mock
  Context context;

  @Test
  public void testSaveQuiz() {
    //    QuizService service = new QuizService(context);
    //
    //    boolean isCrisis = service.saveQuiz(new Quiz(7));
    //    boolean isNotCrisis = service.saveQuiz(new Quiz(6));
    //
    //    assertFalse("Crisis quiz should be true when score less than 7", isNotCrisis);
    //    assertTrue("Crisis quiz is 7 or greater!", isCrisis);
  }

  @Test
  public void testGetLatestQuizScore() {

    //    QuizService service = new QuizService(context);
    //
    //    int testQuizScore = service.getLatestQuizScore();
    //
    //    assertTrue("Quiz score is greater than 0", testQuizScore > 0);
    //    assertTrue("Quiz score is less than 11", testQuizScore < 11);
    //    service.saveQuiz(new Quiz(7));
    //    assertTrue("Quiz score should be 7", service.getLatestQuizScore() == 7);
  }

  @Test
  public void testGetAllQuizzes() {
    //    QuizService service = new QuizService(context);

  }

  @Test
  public void testUpdateQuiz() {
    //    QuizService service = new QuizService(context);
    //
    //    // get data from db
    //    Cursor cursor = service.getAllQuizzes();
    //
    //    // verify rows returned is greater than 0
    //    assertTrue("Rows returned is greater than 0", cursor.getCount() > 0);
  }

  @Test
  public void testDeleteQuiz() {
    // QuizService.deleteQuiz not yet implemented
  }
}
