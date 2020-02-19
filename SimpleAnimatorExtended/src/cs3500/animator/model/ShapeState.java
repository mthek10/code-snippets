package cs3500.animator.model;


import java.awt.Color;

/**
 * Represents the properties of an IShape at a certain tick. Using Floats for more accuracy in the
 * animation than ints. Updated to save theta for angles.
 */

public final class ShapeState {

  // Added functionality
  private final double DEFAULT_THETA = 0.0;

  //A shape has a position, width, height and color
  private final float xPosn;
  private final float yPosn;
  private final float width;
  private final float height;
  private final int red;
  private final int green;
  private final int blue;

  // Added functionality of angles
  private final double theta;

  /**
   * Construct a state for a shape.
   *
   * @param xPosn  the x position of the shape
   * @param yPosn  the y position of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param red    an integer representing the color's redness
   * @param green  an integer representing the color's greeness
   * @param blue   an integer representing the color's blueness
   * @throws IllegalArgumentException if a given position is negative, dimension is <= 0 or if a
   *                                  color value X is not 0 <= X <= 255
   */
  public ShapeState(float xPosn, float yPosn, float width, float height, int red,
                    int green, int blue) throws IllegalArgumentException {

    // Set Theta to 0.0 if not explicitly given for backwards compatibility
    this(xPosn,yPosn,width,height,red,green,blue,0.0);
  }


  /**
   * Construct a state for a shape.
   *
   * @param xPosn  the x position of the shape
   * @param yPosn  the y position of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param red    an integer representing the color's redness
   * @param green  an integer representing the color's greeness
   * @param blue   an integer representing the color's blueness
   * @throws IllegalArgumentException if a given position is negative, dimension is <= 0 or if a
   *                                  color value X is not 0 <= X <= 255
   */
  public ShapeState(float xPosn, float yPosn, float width, float height, int red,
                    int green, int blue, double theta) throws IllegalArgumentException {

    // Checks for Height,Width,and RGB values because xPosn and yPosn can be negative
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("A shape must have positive non-zero dimensions");
    }
    if (red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0) {
      throw new IllegalArgumentException("A Shape's color values must be within 0 and 255");
    }

    // Checks for Theta
    if (!(theta >= 0 && theta <= 360)) {
      throw new IllegalArgumentException("A Shape's Angle must be between 0 and 360 inclusive");
    }

    this.xPosn = xPosn;
    this.yPosn = yPosn;
    this.width = width;
    this.height = height;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.theta = theta;
  }


  /**
   * Return the state's x position.
   *
   * @return the x position
   */
  public float getxPosn() {
    return this.xPosn;
  }

  /**
   * Return the state's y position.
   *
   * @return the y position
   */
  public float getyPosn() {
    return this.yPosn;
  }

  /**
   * Return the shape's width.
   *
   * @return the width
   */
  public float getWidth() {
    return this.width;
  }

  /**
   * Return the shape's height.
   *
   * @return the height
   */
  public float getHeight() {
    return this.height;
  }

  /**
   * Return the shape's red rgb value.
   *
   * @return the red rgb value
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Return the shape's green rgb value.
   *
   * @return the green rgb value
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Return the shape's blue rgb value.
   *
   * @return the blue rgb value
   */
  public int getBlue() {
    return this.blue;
  }


  /**
   * Returns the Color made by the RGB values of this.
   *
   * @return the Color represented by this RGB values
   */
  public Color getColor() {
    return new Color(this.red, this.green, this.blue);
  }

  /**
   * Returns the angle of this.
   * @return angle of shape
   */
  public double getTheta() {
    return this.theta;
  }

  /**
   * Return a string representing all information about a shape given this state except theta.
   *
   * @return a string about the shape's state
   */
  @Override
  public String toString() {
    return this.getxPosn() + " " + this.getyPosn() + " " + this.getWidth() + " " +
            this.getHeight() + " " + this.getRed() + " " + this.getGreen() + " " + this.getBlue();
  }

  /**
   * Return a string representing all information about a shape given this state including theta.
   *
   * @return a string about the shape's state including theta
   */
  public String toStringEnhanced() {
    return this.getxPosn() + " " + this.getyPosn() + " " + this.getWidth() + " " +
            this.getHeight() + " " + this.getRed() + " " + this.getGreen() + " " + this.getBlue() +
            " " + this.getTheta();
  }

  /**
   * Determines whether this shapeState is equal to another shapeState.
   *
   * @param other the other shapeState comparing to
   * @return true if the two shapeStates are equal
   */
  public boolean equalsShapeState(ShapeState other) {
    return this.getxPosn() == other.getxPosn() && this.getyPosn() == other.getyPosn() &&
            this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight() &&
            this.getRed() == other.getRed() && this.getGreen() == other.getGreen() &&
            this.getBlue() == other.getBlue() && this.theta == other.getTheta();
  }
}
