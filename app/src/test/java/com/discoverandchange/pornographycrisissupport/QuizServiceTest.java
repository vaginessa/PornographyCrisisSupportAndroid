package com.discoverandchange.pornographycrisissupport;

import com.discoverandchange.pornographycrisissupport.quiz.Quiz;
import com.discoverandchange.pornographycrisissupport.quiz.QuizService;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */

public class QuizServiceTest {


@Test
    public void testSaveQuizCrisisMode() {
        QuizService service = new QuizService();

        boolean isCrisis = service.saveQuiz(new Quiz(7));
        boolean isNotCrisis = service.saveQuiz(new Quiz(6));

        assertFalse("Crisis quiz should be true when score less than 7", isNotCrisis);
        assertTrue("Crisis quiz is 7 or greater!", isCrisis);
    }

@Test
    public void testGetLatestQuizScore() {
        QuizService service = new QuizService();

        int testQuizScore = service.getLatestQuizScore();

        assertTrue("Quiz score is greater than 0", testQuizScore > 0);
        assertTrue("Quiz score is less than 11", testQuizScore < 11);
        service.saveQuiz(new Quiz(7));
        assertTrue("Quiz score should be 7", service.getLatestQuizScore() == 7);
    }
}
