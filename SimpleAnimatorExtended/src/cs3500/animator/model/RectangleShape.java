package cs3500.animator.model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import cs3500.animator.provider.model.shapes.Outline;

/**
 * Represents a Rectangle in the Animation.
 */
public final class RectangleShape extends Shape implements IShapeExtended {

  /**
   * Constructs a rectangle shape by setting parameters.
   *
   * @param userShapeName the shape identifier
   */
  public RectangleShape(String userShapeName, int drawOrder) {
    super(userShapeName, "rectangle", drawOrder);
  }

  /**
   * Constructs a rectangle shape by setting parameters.
   *
   * @param userShapeName the shape identifier
   * @param shapeLayer the layer of a shape
   */
  public RectangleShape(String userShapeName, int drawOrder, int shapeLayer) {
    super(userShapeName, "rectangle", drawOrder, shapeLayer);
  }

  @Override
  public String getSVGAttribute(String attribute) {
    switch (attribute) {
      case "x":
        return "x";
      case "y":
        return "y";
      case "width":
        return "width";
      case "height":
        return "height";
      default:
        throw new IllegalArgumentException("That attribute does not exist");
    }
  }

  @Override
  public String getSVGIdentifier() {
    return "rect";
  }


  @Override
  public Outline getOutline() {
    // From Provider Code
    return Outline.RECTANGLE;
  }

  @Override
  public void draw(Graphics2D g2d, int tick) {
    ShapeState currState = this.findShapeState(tick);
    java.awt.Shape shape = new Rectangle2D.Float(currState.getxPosn(), currState.getyPosn(),
            currState.getWidth(), currState.getHeight());

    super.draw(g2d, currState, shape);

  }

}
