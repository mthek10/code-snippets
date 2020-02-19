package cs3500.animator.view;

import cs3500.animator.controller.IListener;

/**
 * A revised interface to Add functionality to views and accept revised interfaces in project.
 */
public interface IViewExtended extends IView {

  /**
   * Returns the current tick of the animation.
   * @return current tick
   */
  int getTick();


  /**
   * Sets the position of the slider on the track to the tick given.
   * @param tick tick of animation
   */
  void setScrubber(int tick);


  /**
   * Sets the given IListener more compactly integrated in newer code.
   * @param listener IListener
   */
  void setListener(IListener listener);

  /**
   * Returns the theta angle inputted by the user as a double.
   * @return angle inputted
   */
  double getThetaWantedText();

  /**
   * Returns the int from the layer wanted text field.
   * @return layer
   */
  int getLayerWantedText();

  /**
   * Sets the value of the layer text field.
   * @param layerWantedText layer to set to
   */
  void setLayerWantedText(int layerWantedText);

  /**
   * Sets the value of the shape text field.
   * @param shapeWantedText shape user name
   */
  void setShapeWantedText(String shapeWantedText);

}
