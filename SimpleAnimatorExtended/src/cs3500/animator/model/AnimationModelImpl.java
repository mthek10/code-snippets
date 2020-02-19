package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * An implementation of the Animation Model which uses HashMap to stores IShape(s). Uses Builder to
 * Construct the given AnimationModelImpl. AnimationModelImpl Detail: If you want IShape to Appear
 * on the VisualView you need to have it defined at the time. For it to disappear, you simply do no
 * need to define the command for that IShape during the interval you want it to disappear. Future
 * Implementation: Can investigate use of LinkedHashSet to maintain ordering of the IShapes to be
 * drawn.
 */
public final class AnimationModelImpl implements AnimationModel {


  //a hashmap to store each shape to a unique identifier
  private final HashMap<String, IShape> shapes;

  private int windowWidth;
  private int windowHeight;
  private int windowX;
  private int windowY;

  // Keeps track of order to draw IShape(s)
  private int currentDrawPosition;


  /**
   * Constructs an AnimationModelImpl object.
   *
   * @param shapesToAdd           a HashMap mapping string identifiers to shape objects
   * @param commandsToAddPerShape a HashMap mapping string identifiers to commands for each shape
   * @param shapeBuildOrder       the order the shapes should be displayed
   * @param x                     the x position for this object's bounds
   * @param y                     the y position for this object's bounds
   * @param width                 the width of the model
   * @param height                the height of the model
   */
  public AnimationModelImpl(HashMap<String, String> shapesToAdd, HashMap<String,
          ArrayList<Command>> commandsToAddPerShape, ArrayList<String> shapeBuildOrder,
                            int x, int y, int width, int height) {
    this.shapes = new LinkedHashMap<String, IShape>();


    //add each shape in shapesToAdd to this.shapes
    for (String s : shapeBuildOrder) {
      this.addShape(s, shapesToAdd.get(s));
    }

    //go through all the commands for each shape
    for (String s : commandsToAddPerShape.keySet()) {
      ArrayList<Command> currCommandList = commandsToAddPerShape.get(s);

      //sort the list of commands based on their start time
      Collections.sort(currCommandList);

      //go through and make sure that there are no gaps and states are continuous
      for (int i = 0; i < currCommandList.size(); i++) {

        //only look at the things that aren't the beginning or end
        if (i != currCommandList.size() - 1) {
          if (currCommandList.get(i).getEndTime() != currCommandList.get(i + 1).getStartTime() ||
                  !currCommandList.get(i).getEndState().equalsShapeState(currCommandList.
                          get(i + 1).getStartState())) {
            throw new IllegalArgumentException("There is a gap or non-continuous states");
          }
        }
      }
      //we've checked that there are no gaps and that states transition continuously. Add each state
      //to the shape's hashmap of states and add each commandTime to its arraylist
      IShape currShape = this.shapes.get(s);
      for (Command command : currCommandList) {
        currShape.addCommandTimes(new CommandTimes(command.getStartTime(), command.getEndTime()));
        currShape.addState(command.getStartTime(), command.getStartState());
        currShape.addState(command.getEndTime(), command.getEndState());
      }
    }

    this.setBounds(x, y, height, width);

  }

  /**
   * Basic constructor to help testing.
   */
  public AnimationModelImpl() {
    this.shapes = new HashMap<>();
  }

  /**
   * Constructs model with layers.
   * @param shapesToAdd map of shape names to type
   * @param commandsToAddPerShape commands mapped to each shape name
   * @param shapeBuildOrder order built
   * @param x x
   * @param y y
   * @param width width
   * @param height height
   * @param shapesLayers map of name of shape to layer
   */
  public AnimationModelImpl(HashMap<String, String> shapesToAdd, HashMap<String,
          ArrayList<Command>> commandsToAddPerShape, ArrayList<String> shapeBuildOrder,
                            int x, int y, int width, int height,
                            HashMap<String, Integer> shapesLayers) {
    this(shapesToAdd,commandsToAddPerShape,shapeBuildOrder,x,y,width,height);

    // Fill Shapes with Layers
    for (IShape s: this.shapes.values()) {
      // Get specified layer
      int layer = shapesLayers.get(s.getUserShapeName());
      if (s instanceof IShapeExtended) {
        ((IShapeExtended)s).setShapeLayer(layer);
      }
    }
  }

  @Override
  public String getAnimationState() {
    StringBuilder output = new StringBuilder();

    for (String s : shapes.keySet()) {
      IShape currShape = shapes.get(s);
      //output a string with the shape's from and to state
      Collections.sort(currShape.getTimes());

      //the shape creation line
      output.append(currShape.toString() + "\n");

      //for each time in the list, get the shapeState and output a line of its values
      for (CommandTimes time : currShape.getTimes()) {
        ShapeState startState = currShape.getShapeStates().get(time.getStartTime());
        ShapeState endState = currShape.getShapeStates().get(time.getEndTime());
        output.append("motion " + s + " " + time.getStartTime() + " " + startState.toString()
                + "    " + time.getEndTime() + " " + endState.toString() +
                "\n");
      }

      output.append("\n");


    }
    return output.toString().substring(0, output.length() - 2);
  }

  @Override
  public int getWindowHeight() {
    return this.windowHeight;
  }

  @Override
  public int getWindowWidth() {
    return this.windowWidth;
  }

  @Override
  public int getWindowX() {
    return this.windowX;
  }

  @Override
  public int getWindowY() {
    return this.windowY;
  }


  @Override
  public void addCommand(String userShapeName, int startTime, ShapeState shapeState1, int endTime,
                         ShapeState shapeState2) throws IllegalArgumentException {

    // Time Checking done in CommandTimes Constructor
    // Check if userShapeName corresponds to an actual shape
    if (this.shapes.containsKey(userShapeName)) {

      // iterate through timesList checking each pair
      for (CommandTimes c : this.shapes.get(userShapeName).getTimes()) {
        // Make sure no overlap
        if (startTime > c.getStartTime() && startTime < c.getEndTime()
                || endTime > c.getStartTime() && endTime < c.getEndTime()) {
          throw new IllegalArgumentException("Command Overlaps with an existing command");
        }
      }

      addShapeState(startTime, userShapeName, shapeState1);
      addShapeState(endTime, userShapeName, shapeState2);
      this.shapes.get(userShapeName).addCommandTimes(new CommandTimes(startTime, endTime));


    } else {
      throw new IllegalArgumentException("Specified Shape name does not exist in the model");
    }

  }

  @Override
  public void addShapeState(int time, String shapeName, ShapeState shapeState) {

    // Check that shape is there and time for ShapeState is not conflicting with data
    if (!this.shapes.containsKey(shapeName)) {
      throw new IllegalArgumentException("No Shape found with that shape name");
    }

    // If the shapestate you are trying to add conflicts but is not the same as the present state
    if (this.shapes.get(shapeName).getShapeStates().containsKey(time)
            && !this.shapes.get(shapeName).getShapeStates().get(time)
            .toString().equalsIgnoreCase(shapeState.toString())) {
      throw new IllegalArgumentException("Inputted shape already has a Shape State at that time");
    }

    shapes.get(shapeName).addState(time, shapeState);
  }

  @Override
  public void addCommandTime(String shapeWanted, CommandTimes commandTimes) {
    this.shapes.get(shapeWanted).addCommandTimes(commandTimes);
  }

  @Override
  public void editShapeState(String shapeWanted, int timeWanted, ShapeState inputShapeState) {
    this.shapes.get(shapeWanted).editShapeState(timeWanted, inputShapeState);
  }

  @Override
  public void addKeyframe(String shapeWanted, int timeWanted, ShapeState inputShapeState) {
    this.shapes.get(shapeWanted).addKeyframe(timeWanted, inputShapeState);
  }

  @Override
  public void removeKeyframe(String shapeWanted, int timeWanted) {
    this.shapes.get(shapeWanted).removeState(timeWanted);
  }

  @Override
  public void setLayer(String shapeWanted, int layer) {
    if (this.shapes.keySet().contains(shapeWanted)) {
      if (this.shapes.get(shapeWanted) instanceof IShapeExtended) {
        ((IShapeExtended) this.shapes.get(shapeWanted)).setShapeLayer(layer);
      }
    } else {
      throw new IllegalArgumentException("No Such Shape Found");
    }
  }

  /**
   * Add a shape to the hashmap of known shapes that exist within this animation model. Also, this
   * method increments the currentDrawPosition variable to utilize IShape interface's extension of
   * the Comparable interface. This allows us to sort the Collection of IShapes passed into the vie
   * to be draw in order they were read in.
   *
   * @param userShapeName name of shape as referenced by User
   * @param shapeName     name of the type of shape
   */
  @Override
  public void addShape(String userShapeName, String shapeName) throws IllegalArgumentException {
    // Default layer 0
    this.addShape(userShapeName,shapeName,0);
  }




  @Override
  public void addShape(String userShapeName, String shapeName, int layer)
          throws IllegalArgumentException {
    if (this.shapes.keySet().contains(userShapeName)) {
      throw new IllegalArgumentException("This identifier already exists for another shape");
    }

    IShapeExtended shapeToAdd;

    switch (shapeName) {
      case "rectangle":
        shapeToAdd = new RectangleShape(userShapeName, currentDrawPosition, layer);
        break;
      case "ellipse":
        shapeToAdd = new EllipseShape(userShapeName, currentDrawPosition, layer);
        break;
      default:
        throw new IllegalArgumentException("Unrecognized Shape");
    }

    this.shapes.put(userShapeName, shapeToAdd);

    // Increment currentDrawPosition
    currentDrawPosition++;
  }

  @Override
  public IShape removeShape(String userShapeName) throws IllegalArgumentException {
    try {
      return this.shapes.remove(userShapeName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Given Shape Name was not found in Model");
    }
  }

  @Override
  public void removeCommand(String userShapeName, int startTime, int endTime)
          throws IllegalArgumentException {

    IShape workingShape;

    // If userShapeName is found in Model
    if (this.shapes.containsKey(userShapeName)) {

      workingShape = this.shapes.get(userShapeName);

      // removeCommandTimes if it does not throw an Exception removes the ShapeStates from the
      try {
        workingShape.removeCommandTimes(startTime, endTime);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Such a command doesn't exist");
      }

    } else {
      throw new IllegalArgumentException("Given Shape Name was not found in Model");
    }

  }

  @Override
  public void setBounds(int x, int y, int height, int width) throws IllegalArgumentException {

    // Error check input height width because x and y can technically be negative.
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Cannot have negative Bounds for the Window");
    }

    this.windowX = x;
    this.windowY = y;
    this.windowHeight = height;
    this.windowWidth = width;
  }

  @Override
  public HashMap<String, IShape> getShapes() {
    //return (HashMap<String, IShape>) this.shapes.clone();
    return new HashMap<String, IShape>(this.shapes);
  }


  @Override
  public int getFinalTick() {
    /*
      return Shape.getLastEndTime();
     */

    int finalTickCalc = 0;
    int workingTime = 0;
    for (IShape s: this.shapes.values()) {
      // Get the times for the IShape
      ArrayList<CommandTimes> timesArrayList = s.getTimes();
      // Sort so Last Time is the End Time for the Shape
      Collections.sort(timesArrayList);
      // check if the last time of the shape is the last time of the animation
      if (timesArrayList.size() > 0) {
        workingTime = timesArrayList.get(timesArrayList.size() - 1).getEndTime();
        if (workingTime > finalTickCalc) {
          finalTickCalc = workingTime;
        }
      }

    }

    return finalTickCalc;
  }
}
