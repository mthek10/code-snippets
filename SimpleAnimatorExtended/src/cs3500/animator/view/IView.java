package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import cs3500.animator.controller.IActionListener;
import cs3500.animator.model.IShape;

/**
 * Represents a the functionality of a View for any AnimationModel.
 */
public interface IView {

  /**
   * Set the listener for any actions.
   */
  void setListener(IActionListener listener);

  /**
   * Display this view.
   */
  void display();


  /**
   * Repaints the view.
   */
  void repaintPanel();


  /**
   * Reads in the Shapes to a IView as any type of Collection. Input Collection must be inputted in
   * ORDER of what to Draw. IF using the IShape interface which extends the Comparable interface
   * IView can sort for you.
   *
   * @param shapes a List of IShapes to display
   */
  void setShapes(ArrayList<IShape> shapes);

  /**
   * Sets the bounds of a window.
   *
   * @param windowX      x position of window
   * @param windowY      y position of window
   * @param windowWidth  width of window
   * @param windowHeight height of window
   */
  void setViewBounds(int windowX, int windowY, int windowWidth, int windowHeight);

  /**
   * Can set the delay of the Timer tick.
   *
   * @param delay delay for Timer
   */
  void setDelay(int delay) throws IllegalArgumentException;

  /**
   * Sets the Listeners for the Buttons to the ActionListener given.
   *
   * @param e given action listener
   */
  void setViewButtonListeners(ActionListener e);

  /**
   * Pauses the timer.
   */
  void setTimerPause();

  /**
   * Resumes the timer.
   */
  void setTimerResume();

  /**
   * Sets the current tick of a view.
   *
   * @param i tick
   */
  void setTick(int i);

  /**
   * Change the speed of an animation.
   *
   * @param v speed
   */
  void changeSpeed(float v);

  /**
   * Gets file text from view text field.
   *
   * @return fileWanted
   */
  String getFileWantedText();

  /**
   * Updates the views Lists.
   */
  void setShapeJList();

  /**
   * Sets the ListSelectionListeners to itself.
   */
  void setJListListeners();

  /**
   * Clears all the fields of the view.
   */
  void clearFields();

  /**
   * Toggles the looping of the animation in the view.
   */
  void toggleLooping();

  /**
   * Sets the IShape that user wants selected.
   *
   * @param o selected IShape
   */
  void setSelectedShape(Object o);

  /**
   * Returns the text from the Shape text field.
   *
   * @return shape wanted input
   */
  String getShapeWantedText();

  /**
   * Returns the time input.
   *
   * @return time wanted input
   */
  int getTimeWantedText();

  /**
   * Returns the x posn inputted.
   *
   * @return x posn wanted input
   */
  float getXWantedText();

  /**
   * Returns the y posn inputted.
   *
   * @return y posn wanted input
   */
  float getYWantedText();

  /**
   * Returns the width inputted.
   *
   * @return width wanted input
   */
  float getWidthWantedText();

  /**
   * Returns the height inputted.
   *
   * @return height wanted input
   */
  float getHeightWantedText();

  /**
   * Returns the red value inputted.
   *
   * @return red wanted input
   */
  int getRedWantedText();

  /**
   * Return the green value inputted.
   *
   * @return green wanted input
   */
  int getGreenWantedText();

  /**
   * Return the blue value inputted.
   *
   * @return blue wanted input
   */
  int getBlueWantedText();

  /**
   * Shows visual error message with s message.
   *
   * @param s error message to display
   */
  void showErrorMessage(String s);

  /**
   * Updates the list of Ordered Shapes to the view.
   *
   * @param values ordered list of shapes in order of drawing
   */
  void updateShapes(Collection<IShape> values);

  /**
   * Returns the final tick of animation.
   *
   * @return finalTick
   */
  int getFinalTick();

  /**
   * Sets the last tick that the timer should tick.
   *
   * @param finalTick last tick
   * @throws IllegalArgumentException if finalTick is negative
   */
  void setFinalTick(int finalTick) throws IllegalArgumentException;

  /**
   * Returns the type of shape wanted.
   *
   * @return type of shape as String
   */
  String getShapeWantedType();

  /**
   * Updates the CT for an IShape according to view input.
   *
   * @param currShape current Shape
   */
  void updateCommandTimes(IShape currShape);

  /**
   * Returns whether the timer is running (startted and not stopped).
   *
   * @return timer is running
   */
  boolean timerIsRunning();

  /**
   * Increments the tick of the animation.
   */
  void incrementTick();

  /**
   * Sets the output if applicable for the view.
   *
   * @param out output to set
   */
  void setOutput(Appendable out);
}