package cs3500.animator.provider.util.functions;

import java.awt.Color;

/**
 * Represents methods that are used to write attributes in the format of SVG.
 */
class SVGFormatting {

  /**
   * This creates a string that is in the format of an SVG attribute and its value.
   *
   * @param type  the attribute type
   * @param value the value being assigned to that attribute
   * @return the string attribute="value"
   */
  protected String headerValue(String type, String value) {
    return type + "=" + this.quotationMarks(value);
  }

  /**
   * This creates a string with quotation marks around the given string.
   *
   * @param s the string to get quotation marks around it
   * @return the string s with quotation marks around it
   */
  protected String quotationMarks(String s) {
    return "\"" + s + "\"";
  }

  /**
   * This formats a color to be in SVG format.
   *
   * @param c the color to be formatted
   * @return the color red, green, and blue values separated by commas with parenthesis around them
   */
  protected String colorString(Color c) {
    return "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
  }

  /**
   * This converts ticks into milliseconds and formats them for SVG.
   *
   * @param msPerTick the ratio ot millisecond to tick
   * @param ticks     the number of ticks being converted
   * @return the converted value with the string ms appended on the end
   */
  protected String msTime(int msPerTick, int ticks) {
    return (msPerTick * ticks) + "ms";
  }
}
