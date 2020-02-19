package cs3500.animator.provider.model.shapes;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.provider.util.IntTuple;
import cs3500.animator.provider.util.TickShape;

/**
 * Represents a shape with timelines of values for its position, size, and color, across its
 * lifespan.
 */
public interface Shape extends Comparable<Shape> {


  /**
   * Gets the unique ID of this shape.
   *
   * @return the unique ID
   */
  int getID();

  /**
   * Gets the outline of the shape.
   *
   * @return the shape's outline
   */
  Outline getOutline();

  /**
   * Gets the start tick of this shape.
   *
   * @return the first tick of this shape
   */
  int getStartTick();

  /**
   * Gets the end tick of this shape.
   *
   * @return the last tick of this shape
   */
  int getEndTick();

  /**
   * Adds data from a whole tick shape for each attribute to each corresponding timeline.
   *
   * @param tick the tick where where the shape should look like the tick shape
   * @param next the tick shape that holds all the new values
   */
  void addTickShape(int tick, TickShape next);

  /**
   * Adds a new position to shape's animation.
   *
   * @param tick the tick where the shape reaches the destination
   * @param dest the position the shape will be in when the animation finishes with the x as x and
   *             the y as y
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  void move(int tick, IntTuple dest) throws IllegalArgumentException;

  /**
   * Adds a new color to this shape's animation.
   *
   * @param tick     the tick where the shape becomes the given color
   * @param newColor the color the shape will have when the animation finishes
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  void changeColor(int tick, Color newColor) throws IllegalArgumentException;

  /**
   * Adds a new size to this shape's animation.
   *
   * @param tick    the tick where the shape becomes the new size
   * @param newSize the final size of the shape with the x as width and the y as height
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  void changeSize(int tick, IntTuple newSize) throws IllegalArgumentException;

  /**
   * Gets the name of this shape.
   *
   * @return this shape's name
   */
  String getName();

  /**
   * Checks if the shape is actually being displayed.
   *
   * @param tick the time being checked to see if the shape is visible
   * @return true if the shape can be seen at the given tick
   */
  boolean isVisible(int tick);

  /**
   * Checks if the shape has appeared yet.
   *
   * @param tick the tick being checked
   * @return true if the shape has appeared
   */
  boolean hasStarted(int tick);

  /**
   * Checks if the shape has ended its animations.
   *
   * @param tick the tick being checked
   * @return true if the shape has not finished its animation
   */
  boolean isStillRunning(int tick);

  /**
   * Gets the position at the given tick in this shape's animation.
   *
   * @param tick the given tick
   * @return the position as an {@link IntTuple}
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  IntTuple getPosn(int tick) throws IllegalArgumentException;

  /**
   * Gets the size at the given tick in this shape's animation.
   *
   * @param tick the given tick
   * @return the size as an {@link IntTuple}
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  IntTuple getSize(int tick) throws IllegalArgumentException;

  /**
   * Gets the color at the given tick in this shape's animation.
   *
   * @param tick the given tick
   * @return the color as a {@link Color}
   * @throws IllegalArgumentException if {@code tick} is outside of this shape's lifespan
   */
  Color getColor(int tick) throws IllegalArgumentException;

  /**
   * Creates a list of each of the key frames.
   *
   * @return a list of all of the key frames
   */
  ArrayList<TickShape> getKeyFrames();

  /**
   * Removes a key event from the given tick.
   *
   * @param tick the tick where the key event is being removed from
   */
  void removeKeyFrame(int tick);

  /**
   * Checks if the shape has a key event at the given tick.
   *
   * @param tick the tick being checked
   * @return true if there is a key frame there
   */
  boolean hasKeyFrame(int tick);

}
