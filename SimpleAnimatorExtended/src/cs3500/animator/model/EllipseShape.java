package cs3500.animator.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import cs3500.animator.provider.model.shapes.Outline;

/**
 * Represents an Ellipse in the Animation.
 */
public final class EllipseShape extends Shape implements IShapeExtended {

  /**
   * Constructs an ellipse shape by setting parameters.
   *
   * @param userShapeName the shape identifier
   */
  public EllipseShape(String userShapeName, int drawOrder) {
    super(userShapeName, "ellipse", drawOrder);
  }

  /**
   * Constructs an ellipse shape by setting parameters.
   *
   * @param userShapeName the shape identifier
   */
  public EllipseShape(String userShapeName, int drawOrder, int shapeLayer) {
    super(userShapeName, "ellipse", drawOrder, shapeLayer);
  }

  @Override
  public String getSVGAttribute(String attribute) {
    switch (attribute) {
      case "x":
        return "cx";
      case "y":
        return "cy";
      case "width":
        return "rx";
      case "height":
        return "ry";
      default:
        throw new IllegalArgumentException("That attribute does not exist");
    }
  }

  @Override
  public String getSVGIdentifier() {
    return "ellipse";
  }

  @Override
  public Outline getOutline() {
    // From Provider Code
    return Outline.ELLIPSE;
  }

  @Override
  public void draw(Graphics2D g2d, int tick) {
    ShapeState currState = this.findShapeState(tick);
    java.awt.Shape shape = new Ellipse2D.Float(currState.getxPosn(),
            currState.getyPosn(), currState.getWidth(), currState.getHeight());
    super.draw(g2d, currState, shape);
  }

}
