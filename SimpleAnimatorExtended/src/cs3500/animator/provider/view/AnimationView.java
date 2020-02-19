package cs3500.animator.provider.view;


import java.util.List;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.model.shapes.Outline;
import cs3500.animator.provider.util.TickShape;


/**
 * Represents the output of an animation in some format, using the data from an AnimationModel.
 */
public interface AnimationView {

  /**
   * Displays this animation. The way this view outputs the animation depends heavily on the kind of
   * view it is.
   */
  void display();

  /**
   * Shows the next tick of this animation.
   */
  void refresh();

  /**
   * Resets this animation back to its first tick.
   */
  void reset();

  /**
   * Gets the current tick of this animation.
   *
   * @return the current tick
   */
  int getTick();

  /**
   * Adds a key action to this animation.
   *
   * @param key    the key that triggers the action
   * @param action the action that is triggered
   */
  void addKeyAction(KeyStroke key, Action action);

  /**
   * Displays an error message to the user in a pop-up window.
   *
   * @param error the error to be displayed to the user
   */
  void showErrorMessage(String error);

  /**
   * Sets the info text of this view.
   *
   * @param info the text to display to the user
   */
  void setInfoText(String info);

  /**
   * Updates the list of the names of all shapes to select from.
   *
   * @param shapeNames the new shape names for the view
   */
  void setShapeInput(List<String> shapeNames);

  /**
   * Sets the selection listener for shape input.
   *
   * @param listener the selection listener
   */
  void setShapeInputListener(ListSelectionListener listener);

  /**
   * Updates the list of key frame ticks to select from.
   *
   * @param keyFrames the new key frames
   */
  void setKeyFrames(List<TickShape> keyFrames);

  /**
   * Allows the user to select a new Color.
   */
  void setColor();

  /**
   * Gets the input speed.
   *
   * @return the integer representing the speed of the ticks
   */
  int getSpeedInput();

  /**
   * Gets the new shape name input.
   *
   * @return the new shape name
   */
  String getNewShapeName();

  /**
   * Gets the input outline.
   *
   * @return the outline input
   */
  Outline getOutlineInput();

  /**
   * Updates the choices of outlines for shapes.
   *
   * @param outlines the new outline selection
   */
  void setOutlineInput(List<Outline> outlines);

  /**
   * Gets the current x coordinate input.
   *
   * @return the x input
   */
  int getXInput();

  /**
   * Gets the current y coordinate input.
   *
   * @return the y input
   */
  int getYInput();

  /**
   * Gets the current width input.
   *
   * @return the width input
   */
  int getWidthInput();

  /**
   * Gets the current height input.
   *
   * @return the height input
   */
  int getHeightInput();

  /**
   * Gets the current color input.
   *
   * @return the color input
   */
  Color getColorInput();

  /**
   * Gets the current selected shape name.
   *
   * @return the name of the selected shape
   */
  String getSelectedShapeName();

  /**
   * Gets the current selected key frame tick.
   *
   * @return the tick of the selected key frame
   */
  int getSelectedKFTick();
}
