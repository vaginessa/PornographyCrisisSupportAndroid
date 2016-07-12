package com.discoverandchange.pornographycrisissupport.library;

/**
 * Represents an inspirational quote the user can view in the library.
 */
public class InspirationalQuoteResource extends BaseResource {

  /**
   * The text of the inspirational quote.
   */
  private String text;

  /**
   * Creates the Inspirational Quote.
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