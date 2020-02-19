package cs3500.animator.provider.model.animations;

import java.util.List;

import cs3500.animator.provider.model.shapes.Shape;


/**
 * Represents the data of an animation.
 */
public interface AnimationModel {

  /**
   * Adds a new shape to the animation.
   *
   * @param s the new shape being added
   */
  void addShape(Shape s);

  /**
   * Removes the shape with the given name from the animation.
   *
   * @param name the name of the shape to be removed
   */
  void removeShape(String name);

  /**
   * Gets all the names of current shapes.
   *
   * @return the List of names
   */
  List<String> currentNames();


  /**
   * Gets the shape with the corresponding name.
   *
   * @param name the name of the shape to be returned
   * @return the shape with the current name
   * @throws IllegalArgumentException if the shape does not exist
   */
  Shape getShapeByName(String name) throws IllegalArgumentException;

  /**
   * Gets this animation's canvas width.
   *
   * @return the canvas width
   */
  int getCanvasWidth();

  /**
   * Gets this animation's canvas width.
   *
   * @return the canvas width
   */
  int getCanvasHeight();

  /**
   * Figures out if the animation is over.
   *
   * @return true if the animation is over
   */
  boolean isOver(int tick);

  /**
   * Converts the model into an immutable version.
   *
   * @return the model with the same data but immutable
   */
  ImmutableAnimationModelImpl getImmutable();
}
