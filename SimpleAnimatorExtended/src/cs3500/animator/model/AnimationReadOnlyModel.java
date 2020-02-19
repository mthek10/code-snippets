package cs3500.animator.model;

import java.util.HashMap;

/**
 * Interface for Non-manipulation Observer-only use cases for our Model.
 */
public interface AnimationReadOnlyModel {

  /**
   * Returns the last tick of any commands of any shapes in the model.
   *
   * @return last tick possible
   */
  int getFinalTick();

  /**
   * Returns a copy of the HashMap of Shapes in the Model.
   *
   * @return a copy of HashMap of Shapes
   */
  HashMap<String, IShape> getShapes();

  /**
   * Returns the Animation state.
   *
   * @return String of Shape's Animation States
   */
  String getAnimationState();

  /**
   * Returns the defined height of the window.
   *
   * @return defined height of window.
   */
  int getWindowHeight();

  /**
   * Returns the defined width of the window.
   *
   * @return defined width of window.
   */
  int getWindowWidth();

  /**
   * Returns the defined x coordinate of the window location.
   *
   * @return defined x of window
   */
  int getWindowX();

  /**
   * Returns the defined y coordinate of the window location.
   *
   * @return defined y of window
   */
  int getWindowY();

}