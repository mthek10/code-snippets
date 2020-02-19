package cs3500.animator.provider.util.functions;

import java.util.function.Function;

/**
 * Represents the closing tag of a shape in an SVG view.
 */
public class SVGCloserFunc implements Function<String, String> {

  @Override
  public String apply(String s) {
    return "</" + s + ">\n";
  }
}
