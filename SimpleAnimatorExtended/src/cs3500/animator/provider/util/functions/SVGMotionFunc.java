package cs3500.animator.provider.util.functions;

import java.util.StringJoiner;

import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.Motion;
import cs3500.animator.provider.util.TickShape;

/**
 * Represents a 2 argument function that writes a shape's motion in the SVG format.
 */
public class SVGMotionFunc extends SVGFormatting implements Function2Arg<Shape, Motion, String> {

  private int msPerTick;

  /**
   * Creates a new SVGMotionFunc with a given ratio of milliseconds per tick.
   *
   * @param msPerTick the number of milliseconds per tick
   */
  public SVGMotionFunc(int msPerTick) {
    this.msPerTick = msPerTick;
  }

  @Override
  public String apply(Shape shape, Motion motion) {
    StringJoiner description = new StringJoiner("");
    switch (shape.getOutline()) {
      case ELLIPSE:
        writeMotionByShape(motion, "cx", "cy", "rx", "ry", description);
        break;
      case RECTANGLE:
        writeMotionByShape(motion, "x", "y", "width", "height", description);
        break;
      default:
        throw new IllegalArgumentException("Invalid shape outline");
    }
    return description.toString();
  }

  /**
   * Writes the animations of a shape based on what the attributes are named.
   *
   * @param m  the motion being written
   * @param x  the name of the field that represents x
   * @param y  the name of the field that represents y
   * @param w  the name of the field that represents width
   * @param h  the name of the field that represents height
   * @param js the string joiner being written to
   */
  private void writeMotionByShape(Motion m, String x, String y, String w, String h,
                                  StringJoiner js) {
    TickShape start = m.getStartState();
    TickShape end = m.getEndState();
    if (start.getPosition().getX() != end.getPosition().getX()) {
      motionLine(js, m, x, Integer.toString(start.getPosition().getX()),
              Integer.toString(end.getPosition().getX()));
    }
    if (start.getPosition().getY() != end.getPosition().getY()) {
      motionLine(js, m, y, Integer.toString(start.getPosition().getY()),
              Integer.toString(end.getPosition().getY()));
    }
    if (start.getSize().getX() != end.getSize().getX()) {
      motionLine(js, m, w, Integer.toString(start.getSize().getX()),
              Integer.toString(end.getSize().getX()));
    }
    if (start.getSize().getX() != end.getSize().getY()) {
      motionLine(js, m, h, Integer.toString(start.getSize().getY()),
              Integer.toString(end.getSize().getY()));
    }
    if (!start.getColor().equals(end.getColor())) {
      motionLine(js, m, "fill", this.colorString(start.getColor()),
              this.colorString(end.getColor()));
    }
  }

  /**
   * writes one line of the motion in SVG format.
   *
   * @param sj        the StringJoiner being written to
   * @param m         the motion being written
   * @param attribute the attribute that is changing in this line
   * @param from      the value the attribute starts with
   * @param to        the value the attribute ends with
   */
  private void motionLine(StringJoiner sj, Motion m, String attribute, String from, String to) {
    sj.add(" <animate attributeType=\"xml\"");
    sj.add(this.headerValue(" begin", this.msTime(msPerTick, m.getStart())));
    sj.add(this.headerValue(" dur", this.msTime(msPerTick, m.getEnd() - m.getStart())));
    sj.add(this.headerValue(" attributeName", attribute));
    sj.add(this.headerValue(" from", from));
    sj.add(this.headerValue(" to", to));
    sj.add(this.headerValue(" fill", "freeze"));
    sj.add("/>\n");
  }
}
