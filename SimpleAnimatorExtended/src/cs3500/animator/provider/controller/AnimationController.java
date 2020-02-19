package cs3500.animator.provider.controller;

//import model.shapes.Shape;

public interface AnimationController {

  /**
   * Runs this Animation.
   */
  void run();

  /**
   * Switches between playing the animation and having the animation paused.
   */
  void togglePlay();

  /**
   * Switches between simply stopping the animation and starting it again from the beginning at the
   * end of the animation.
   */
  void toggleLoop();

  /**
   * Figures out if the animation is playing.
   *
   * @return true if the animation is currently playing
   */
  boolean isRunning();

  /**
   * Returns the rate of ticks per millisecond.
   *
   * @return the speed of the animation
   */
  int getSpeed();

  /**
   * Sets the rate of ticks per second to a given value.
   *
   * @param rate the new ratio of ticks per second
   */
  void setSpeed(int rate);

  /**
   * Checks if the animation is looping.
   *
   * @return true if the animation is looping
   */
  boolean isLooping();
}
