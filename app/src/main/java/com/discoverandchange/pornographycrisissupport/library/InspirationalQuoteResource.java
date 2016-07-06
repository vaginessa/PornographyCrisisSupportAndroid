package com.discoverandchange.pornographycrisissupport.library;

/**
 * Created by snielson on 7/6/16.
 */
public class InspirationalQuoteResource extends BaseResource {

  /**
   * The text of the inspirational quote.
   */
  private String text;

  /**
   * Creates the Inspirational Quote
   *
   */
  public InspirationalQuoteResource() {
    super("InspirationalQuote");
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
