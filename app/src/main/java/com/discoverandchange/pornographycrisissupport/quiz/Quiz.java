package com.discoverandchange.pornographycrisissupport.quiz;

/**
 * Holds a quiz object and its properties.
 *
 * @author Keith Higbee
 */
public class Quiz {

  /**
   * The id of the quiz from the database.
   */
  private int id;
  /**
   * The score of the quiz from the database.
   */
  private int score;
  /**
   * The date the quiz was taken.
   */
  private String date;

  // constructors
  public Quiz(int score) {
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
