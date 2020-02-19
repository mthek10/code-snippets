package cs3500.animator.model;


import java.awt.Graphics2D;

/**
 * An extension on the IShape interface to allow for the easy integration of rotation.
 */

public interface IShapeExtended extends IShape {

  /**
   * Draws itself as a shape with the properties at the given tick onto the Graphics.
   * @param g2d Graphics 2d to draw on
   * @param tick tick to draw at
   */
  void draw(Graphics2D g2d, int tick);


  /**
   * Sets the IShape's shapeLayer to given value.
   * @param shapeLayer shape layer
   */
  void setShapeLayer(int shapeLayer);

  /**
   * Returns the layering for the IShape.
   * @return shapeLayer
   */
  int getShapeLayer();

}
