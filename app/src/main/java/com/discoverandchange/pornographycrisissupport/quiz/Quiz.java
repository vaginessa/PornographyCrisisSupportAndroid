package com.discoverandchange.pornographycrisissupport.quiz;

/**
 * Created by khigbee on 6/6/16.
 */

public class Quiz {

  private int id;
  private int score;
  private String date;

  // constructors
  public Quiz(int score)
  {
    this.score = score;
  }

  public Quiz() {
    this(0);
  }

  // methods
  public int getId() {
    return id;
  }

  public int getScore() {
    return score;
  }

  public String getDate() {
    return date;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
