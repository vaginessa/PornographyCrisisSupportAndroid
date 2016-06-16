package com.discoverandchange.pornographycrisissupport.quiz;

/**
 * Created by khigbee on 6/6/16.
 */

public class Quiz {

  private int score;

  // constructors
  public Quiz(int score)
  {
    this.score = score;
  }

  public Quiz() {
    this(0);
  }

  // methods
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
