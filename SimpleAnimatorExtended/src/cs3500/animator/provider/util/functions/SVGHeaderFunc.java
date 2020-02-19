package cs3500.animator.provider.util.functions;

import java.util.StringJoiner;
import java.util.function.Function;

import cs3500.animator.provider.model.shapes.Shape;

/**
 * Represents the function that writes the header of a shape in an SVG view.
 */
public class SVGHeaderFunc extends SVGFormatting implements Function<Shape, String> {

  @Override
  public String apply(Shape shape) {
    StringJoiner header = new StringJoiner(" ");
    switch (shape.getOutline()) {
      case RECTANGLE:
        this.createShapeTag(shape, header, "rect", "x", "y", "width", "height");
        break;
      case ELLIPSE:
        this.createShapeTag(shape, header, "ellipse", "cx", "cy", "rx", "ry");
        break;
      default:
        throw new IllegalArgumentException("Unsupported shape outline");
    }
    return header.toString();
  }

  /**
   * Writes a header tag using a shape's specific attribute names.
   *
   * @param s       the whole shape
   * @param sj      the string joiner being written to
   * @param outline the shape's specific outline
   * @param posX    the name of the x position field
   * @param posY    the name of the y position field
   * @param sizeW   the name of the width field
   * @param sizeH   the name of the height field
   */
  private void createShapeTag(Shape s, StringJoiner sj, String outline, String posX, String posY,
                              String sizeW, String sizeH) {
    sj.add("<" + outline);
    sj.add("id=" + this.quotationMarks(s.getName()));
    sj.add(this.headerValue(posX, Integer.toString(s.getPosn(s.getStartTick()).getX())));
    sj.add(this.headerValue(posY, Integer.toString(s.getPosn(s.getStartTick()).getY())));
    sj.add(this.headerValue(sizeW, Integer.toString(s.getSize(s.getStartTick()).getX())));
    sj.add(this.headerValue(sizeH, Integer.toString(s.getSize(s.getStartTick()).getY())));
    sj.add(this.headerValue("fill", this.colorString(s.getColor(s.getStartTick()))));
    sj.add("visibility=\"visible\"");
    sj.add(">");
    sj.add("\n");
  }

}
