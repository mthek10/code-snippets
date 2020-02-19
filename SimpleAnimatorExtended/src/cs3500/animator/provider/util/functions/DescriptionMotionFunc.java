package cs3500.animator.provider.util.functions;

import java.util.StringJoiner;

import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.IntTuple;
import cs3500.animator.provider.util.Motion;
import cs3500.animator.provider.util.TickShape;

/**
 * Represents a 2 argument function that formats a shape's motion to the description style.
 */
public class DescriptionMotionFunc implements Function2Arg<Shape, Motion, String> {


  @Override
  public String apply(Shape shape, Motion motion) {
    StringJoiner description = new StringJoiner(" ");
    description.add("motion");
    description.add(shape.getName());
    description.add(writeTickShape(motion.getStartState(), motion.getStart()));
    description.add(writeTickShape(motion.getEndState(), motion.getEnd()) + "\n");
    return description.toString();
  }

  /**
   * Writes the data of one tick shape delimited by spaces.
   *
   * @param shape the tick shape that holds the data in question
   * @param tick  the tick that this TickShape is happening on
   * @return a string of the tick, position, size, and color of the given TickShape
   */
  private String writeTickShape(TickShape shape, int tick) {
    StringJoiner description = new StringJoiner(" ");
    description.add(Integer.toString(tick));
    description.add(writeIntTuple(shape.getPosition()));
    description.add(writeIntTuple(shape.getSize()));
    description.add(Integer.toString(shape.getColor().getRed()));
    description.add(Integer.toString(shape.getColor().getGreen()));
    description.add(Integer.toString(shape.getColor().getBlue()));
    return description.toString();
  }

  /**
   * Writes an IntTuple with a space between each value.
   *
   * @param it the IntTuple being written
   * @return a string of the x and y of the given int tuple with a space between
   */
  private String writeIntTuple(IntTuple it) {
    StringJoiner description = new StringJoiner(" ");
    description.add(Integer.toString(it.getX()));
    description.add(Integer.toString(it.getY()));
    return description.toString();
  }
}
