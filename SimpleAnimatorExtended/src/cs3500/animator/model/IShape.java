package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.provider.model.shapes.Shape;

/**
 * An interface for all shapes in an animation. The interface extends the {@code Comparable<IShape>}
 * interface. All IShapes are Comparable so they can be sorted in an order (Example order to be
 * drawn).
 */
public interface IShape extends Shape {


  /**
   * Adds a state to a particular shape.
   *
   * @param time       time at which state describes shape
   * @param shapeState state of shape to be added
   */
  void addState(int time, ShapeState shapeState);


  /**
   * Adds the times for a specific command being added to the animation.
   *
   * @param times a start and stop time for the particular command
   */
  void addCommandTimes(CommandTimes times);

  /**
   * Returns a copy of the HashMap of Time,ShapeStates of a Shape.
   *
   * @return state
   */
  HashMap<Integer, ShapeState> getShapeStates();

  /**
   * Returns the list of CommandTimes.
   *
   * @return time
   */
  ArrayList<CommandTimes> getTimes();

  /**
   * Return the unique string of the user defined name for Shape.
   *
   * @return userShapeName
   */
  String getUserShapeName();

  /**
   * Used to remove a Command from this IShape.
   *
   * @param startTime startTime of previously inputted command
   * @param endTime   endTime of previously inputted command
   * @throws IllegalArgumentException if command with given times does not exist
   */
  void removeCommandTimes(int startTime, int endTime) throws IllegalArgumentException;

  /**
   * Returns the drawOrder variable.
   *
   * @return drawOrder
   */
  int getDrawOrder();

  /**
   * Returns the shapeType. Example "rectangle".
   *
   * @return shapeType
   */
  String getShapeType();

  /**
   * Checks is the passed in String is an SVG attribute and returns it.
   *
   * @param attribute attribute to check
   * @return SVG attribute
   */
  String getSVGAttribute(String attribute);

  /**
   * Returns the unique SVG Identifier for each type of shape (Example "rect").
   *
   * @return SVG Identifier
   */
  String getSVGIdentifier();

  /**
   * Finds the ShapeState at a given time.
   *
   * @param time the desired time
   * @return the desired ShapeState
   */
  ShapeState findShapeState(int time);

  /**
   * Replace the ShapeState at the time wanted with the inoutShapeState.
   *
   * @param timeWanted      the desired time
   * @param inputShapeState the desired state
   */
  void editShapeState(int timeWanted, ShapeState inputShapeState);

  /**
   * Add a ShapeState at the desired time.
   *
   * @param timeWanted      the desired time
   * @param inputShapeState the desired state
   */
  void addKeyframe(int timeWanted, ShapeState inputShapeState);

  /**
   * Removes the ShapeState at the desired time.
   *
   * @param timeEntered the desired time
   */
  void removeState(int timeEntered);

}
