package cs3500.animator.provider.util.functions;


import java.util.function.Function;

import cs3500.animator.provider.model.shapes.Shape;

/**
 * Represents a function that creates the header of a shape in the description format.
 */
public class DescriptionHeaderFunc implements Function<Shape, String> {

  @Override
  public String apply(Shape shape) {
    return "shape " + shape.getName() + " " + shape.getOutline().toString() + "\n";
  }
}
