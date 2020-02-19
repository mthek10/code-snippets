package cs3500.animator.model;

/**
 * Represents the Full Interface for our Model which has manipulation capabilities. Adds on
 * Manipulation functionality to the ReadOnly Model.
 */
public interface AnimationModel extends AnimationReadOnlyModel {


  /**
   * Add a command to the given shape. This is done by adding the times the command occurs to the
   * shape and the states at each given time.
   *
   * @param userShapeName the identifier for the shape
   * @param startTime     the time the command is initiated
   * @param shapeState1   the state of the shape when the command is initiated
   * @param endTime       the time the command ends
   * @param shapeState2   the state of the shape when the command ends
   */
  void addCommand(String userShapeName, int startTime, ShapeState shapeState1, int endTime,
                  ShapeState shapeState2) throws IllegalArgumentException;


  /**
   * Add a shape to the hashmap of known shapes that exist within this animation model. Also, this
   * method increments the currentDrawPosition variable to utilize IShape interface's extension of
   * the Comparable interface. This allows us to sort the Collection of IShapes passed into the vie
   * to be draw in order they were read in.
   *
   * @param userShapeName name of shape as referenced by User
   * @param shapeName     name of the type of shape
   */

  void addShape(String userShapeName, String shapeName) throws IllegalArgumentException;

  /**
   * Add a shape to the hashmap of known shapes that exist within this animation model. Also, this
   * method increments the currentDrawPosition variable to utilize IShape interface's extension of
   * the Comparable interface. This allows us to sort the Collection of IShapes passed into the vie
   * to be draw in order they were read in.
   *
   * @param userShapeName name of shape as referenced by User
   * @param shapeName     name of the type of shape
   * @param layer         the layer to be added to shape
   */

  void addShape(String userShapeName, String shapeName, int layer) throws IllegalArgumentException;


  /**
   * Removes and returns the Shape from the model.
   *
   * @param userShapeName name that user assigned when creating shape
   * @return IShape that was removed
   * @throws IllegalArgumentException if given userShapeName does not match any previously declared
   *                                  shapes
   */
  IShape removeShape(String userShapeName) throws IllegalArgumentException;


  /**
   * Removes the ShapeState(s) between the given startTime and endTime for the given IShape.
   *
   * @param userShapeName user given name of IShape to remove command from
   * @param startTime     startTime inputted in addCommand
   * @param endTime       endTime inputted in addCommand
   * @throws IllegalArgumentException if not CommandTime exists for given shape and times
   */
  void removeCommand(String userShapeName, int startTime, int endTime)
          throws IllegalArgumentException;

  /**
   * Sets the saved bounds variable in the model for the a view.
   *
   * @param x      x position of view
   * @param y      y position of view
   * @param height height of view
   * @param width  width of view
   */
  void setBounds(int x, int y, int height, int width);

  /**
   * Add a state for the shape to the hashmap containing all the shape's states.
   *
   * @param time       time at which shape state is for
   * @param shapeName  the shape identifier
   * @param shapeState the properties of the shape at a particular instance
   */
  void addShapeState(int time, String shapeName, ShapeState shapeState);


  /**
   * Adds a CommandTime to the given IShape mapped to the shapeWanted.
   *
   * @param shapeWanted  user given name of IShape
   * @param commandTimes start and end time to add
   */
  void addCommandTime(String shapeWanted, CommandTimes commandTimes);

  /**
   * Edits the given ShapeState of a the IShape mapped to the shapeWanted field.
   *
   * @param shapeWanted     user given name of IShape
   * @param timeWanted      time of state to edit
   * @param inputShapeState new state to save
   */
  void editShapeState(String shapeWanted, int timeWanted, ShapeState inputShapeState);

  /**
   * Adds a key frame to the shape specified and time specified.
   *
   * @param shapeWanted     user given name of IShape
   * @param timeWanted      time of state to add
   * @param inputShapeState state to add
   */
  void addKeyframe(String shapeWanted, int timeWanted, ShapeState inputShapeState);

  /**
   * Removes a keyframe from the IShape mapped to shapeWanted at the timeWanted.
   *
   * @param shapeWanted user given name of IShape
   * @param timeWanted  time of state to remove
   */
  void removeKeyframe(String shapeWanted, int timeWanted);


  /**
   * Sets the layer of the shape mapped to given shape name.
   * @param shapeWanted shape name
   * @param layer layer wanted
   */
  void setLayer(String shapeWanted, int layer);

}