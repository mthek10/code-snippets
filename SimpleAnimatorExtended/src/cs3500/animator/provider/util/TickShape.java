package cs3500.animator.provider.util;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents all the data of a shape at a single tick.
 */
public class TickShape {
  private IntTuple position;
  private IntTuple size;
  private Color color;
  private int tick;

  /**
   * Creates a new tick shape.
   *
   * @param x the x position
   * @param y the y position
   * @param w the width of the shape
   * @param h the height of the shape
   * @param r the red value of the shape
   * @param g the green value of the shape
   * @param b the blue value of the shape
   * @param t the tick of this TickShape
   */
  public TickShape(int x, int y, int w, int h, int r, int g, int b, int t) {
    this.position = new IntTuple(x, y);
    this.size = new IntTuple(w, h);
    this.color = new Color(r, g, b);
    this.tick = t;
  }

  /**
   * The getter for the position.
   *
   * @return the position of this TickShape
   */
  public IntTuple getPosition() {
    return position;
  }

  /**
   * The getter for the size.
   *
   * @return the size of this TickShape
   */
  public IntTuple getSize() {
    return size;
  }

  /**
   * The getter for the color.
   *
   * @return the color of this TickShape
   */
  public Color getColor() {
    return color;
  }

  /**
   * The getter for the tick associated with a tick shape.
   *
   * @return the tick of this tick shape
   */
  public int getTick() {
    return this.tick;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof TickShape) {
      TickShape that = (TickShape) other;
      return this.position.equals(that.position) && this.size.equals(that.size)
              && this.color.equals(that.color);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, size, color);
  }

  @Override
  public String toString() {
    return Integer.toString(this.tick);
  }
}
