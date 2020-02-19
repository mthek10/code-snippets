package cs3500.animator.model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cs3500.animator.provider.util.IntTuple;
import cs3500.animator.provider.util.TickShape;

/**
 * Represents the base abstracted shape properties and methods. Class Invariants: times will never
 * have duplicate CommandTimes.
 */
public abstract class Shape implements IShapeExtended {

  //A hashmap contains the shape's state at each tick where a command occurs
  private final HashMap<Integer, ShapeState> states;

  //a list to hold all the times for original commands given
  private final ArrayList<CommandTimes> times;

  //a string to represent what shape this is
  private final String userShapeName;

  // A String to represent the type of shape
  private final String shapeType;

  // Order to Draw
  private final int drawOrder;

  // Functionality for layering
  private int shapeLayer;


  /**
   * Constructs the Basic elements of the Shape with default layering 0.
   */
  public Shape(String userShapeName, String shapeType, int drawOrder) {
    // use default layering 0 if not defined
    this(userShapeName,shapeType,drawOrder,0);
  }

  /**
   * Constructs the Basic elements of the Shape.
   */
  public Shape(String userShapeName, String shapeType, int drawOrder, int shapeLayer) {
    this.states = new HashMap<Integer, ShapeState>();
    this.times = new ArrayList<CommandTimes>();
    this.userShapeName = userShapeName;
    this.shapeType = shapeType;
    this.drawOrder = drawOrder;
    if (shapeLayer >= 0) {
      this.shapeLayer = shapeLayer;
    } else {
      throw new IllegalArgumentException("Shape Layer has to be positive");
    }
  }

  /**
   * Returns the last time for this shape.
   *
   * @return the last time for this shape
   */
  public static int getLastEndTime() {
    return CommandTimes.getLastEndTime();
  }


  /**
   * Add a state to this shape at a given time.
   *
   * @param time       time at which state describes shape
   * @param shapeState state of shape to be added
   */
  @Override
  public void addState(int time, ShapeState shapeState) {
    this.states.put(time, shapeState);
  }

  /**
   * Find all the transitional states between two times for this shape and store them. Acts as the
   * "addStates" in terms of naming methods in the class.
   *
   * @param startTime start of time for State command
   * @param endTime   end of time for State command
   */
  public ShapeState linearInterpolation(int startTime, int endTime, int targetTime) {
    int timeDifference = endTime - startTime;

    ShapeState state1 = states.get(startTime);
    ShapeState state2 = states.get(endTime);

    float xDifferenceOverTime = (state2.getxPosn() - state1.getxPosn()) / timeDifference;
    float yDifferenceOverTime = (state2.getyPosn() - state1.getyPosn()) / timeDifference;
    float heightDifferenceOverTime = (state2.getHeight() - state1.getHeight()) / timeDifference;
    float widthDifferenceOverTime = (state2.getWidth() - state1.getWidth()) / timeDifference;
    int redDifferenceOverTime = (state2.getRed() - state1.getRed()) / timeDifference;
    int greenDifferenceOverTime = (state2.getGreen() - state1.getGreen()) / timeDifference;
    int blueDifferenceOverTime = (state2.getBlue() - state1.getBlue()) / timeDifference;
    double thetaDifferenceOverTime = (state2.getTheta() - state1.getTheta()) / timeDifference;


    int timePassed = targetTime - startTime;
    return new ShapeState(state1.getxPosn() + (timePassed * xDifferenceOverTime),
            state1.getyPosn() + (timePassed * yDifferenceOverTime),
            state1.getWidth() + (timePassed * widthDifferenceOverTime),
            state1.getHeight() + (timePassed * heightDifferenceOverTime),
            state1.getRed() + (timePassed * redDifferenceOverTime),
            state1.getGreen() + (timePassed * greenDifferenceOverTime),
            state1.getBlue() + (timePassed * blueDifferenceOverTime),
            (state1.getTheta() + (timePassed * thetaDifferenceOverTime)));
  }

  /**
   * Return a copy HashMap mapping times to the shape's states. Prevents manipulation of the Model.
   *
   * @return the hashmap
   */
  public HashMap<Integer, ShapeState> getShapeStates() {
    return (HashMap<Integer, ShapeState>) this.states.clone();
  }

  /**
   * Return a copy of ArrayList that holds the CommandTimes for all the commands for this shape.
   *
   * @return the copy of the arraylist
   */
  public ArrayList<CommandTimes> getTimes() {
    return (ArrayList<CommandTimes>) this.times.clone();
  }

  /**
   * Return the unique string identifier for this shape.
   *
   * @return the shape's identifier
   */
  public String getUserShapeName() {
    return this.userShapeName;
  }

  @Override
  public void addCommandTimes(CommandTimes newTime) {
    this.times.add(newTime);
    Collections.sort(times);

  }

  @Override
  public String toString() {
    return "shape " + this.userShapeName + " " + this.shapeType;
  }

  @Override
  public void removeCommandTimes(int startTime, int endTime) throws IllegalArgumentException {
    boolean found = false;

    for (CommandTimes c : this.times) {
      if (c.matches(startTime, endTime)) {
        this.times.remove(c);

        // remove the corresponding shape states
        this.removeStates(startTime, endTime);

        found = true;
      }
    }

    // If not found throw Exception
    if (!found) {
      throw new IllegalArgumentException("No Command found with given times");
    }
  }

  /**
   * Removes the ShapeStates between the start and end times. Start and End times already checked
   * because matched a CommandTime from removeCommandTimes which has argument checking in
   * Constructor of CommandTimes.
   *
   * @param startTime startTime for removal
   * @param endTime   endTime for removal
   */
  private void removeStates(int startTime, int endTime) {
    for (int a = startTime; a <= endTime; a++) {
      this.states.remove(a);
    }
  }


  @Override
  public int getDrawOrder() {
    return this.drawOrder;
  }

  @Override
  public String getShapeType() {
    return this.shapeType;
  }

  @Override
  public ShapeState findShapeState(int time) throws IllegalArgumentException {
    if (this.getShapeStates().get(time) != null) {
      return this.getShapeStates().get(time);
    }

    if (time < this.getTimes().get(0).getStartTime()
            || time > this.getTimes().get(this.getTimes().size() - 1).getEndTime()) {
      throw new IllegalArgumentException("There is no state at the desired time");
    }

    ShapeState outputState = null;
    for (CommandTimes ct : this.getTimes()) {
      if (time > ct.getStartTime() && time < ct.getEndTime()) {
        outputState = this.linearInterpolation(ct.getStartTime(), ct.getEndTime(), time);
        break;
      }
    }
    return outputState;
  }


  @Override
  public void editShapeState(int time, ShapeState state) {
    this.states.replace(time, state);
  }

  @Override
  public void removeState(int timeEntered) {
    // Remove state if applicable
    if (this.states.containsKey(timeEntered)) {
      this.states.remove(timeEntered);

      // Update CommandTimes
      if (this.times.size() == 1) {
        // if one command time is (x,x) = only one keyframe
        // delete time AND have to delete shape
        this.times.clear();
      } else if (this.times.size() == 2) {
        // if == 2 commandtimes (At least 2 key frames) --> (x,x) (x,y)

        // if first item == timeEntered (x,x) then delete the command time && set x,y --> (y,y)
        if (this.times.get(0).getStartTime() == timeEntered) {
          int y = this.times.get(1).getEndTime();
          this.times.clear();
          this.times.add(new CommandTimes(y, y));
        } else if (this.times.get(1).getEndTime() == timeEntered) {
          // if remove y then delete last CT and end up with just (x,x)
          this.times.remove(1);
        }

      } else if (this.times.size() > 2) {
        // if == 3+ command times (x,x) (x,y) (y,z) (z,a)

        int a = 0;
        while (a < this.times.size()) {
          if (this.times.get(a).getEndTime() == timeEntered) {
            this.times.remove(a);

            // case if first CT is deleted
            if (a == 0) {
              // don't increment a because ArrayList resets index after removing
              int y = this.times.get(a).getEndTime();
              this.times.add(new CommandTimes(y, y));
              this.times.remove(a);
              Collections.sort(this.times);
              break;
            } else if (a == this.times.size()) {
              // IF removed last item in list size because removed element already
              // Don't remove anything else
            } else {
              int x1 = this.times.get(a).getEndTime();
              this.times.remove(a);
              this.times.add(new CommandTimes(this.times.get(a - 1).getStartTime(), x1));
            }

          }
          a++;

        }

        Collections.sort(this.times);

      }

    } else {
      throw new IllegalArgumentException("Shape does not have state at time entered");
    }
  }

  @Override
  public void addKeyframe(int timeWanted, ShapeState inputShapeState) {

    // the new KeyFrame is the first keyFrame
    if (this.times.size() == 0) {
      CommandTimes timeAdd = new CommandTimes(timeWanted, timeWanted);
      this.times.add(timeAdd);
    } else
      //the new keyframe is before the initial keyframe
      if (timeWanted < this.times.get(0).getStartTime()) {
        if (this.times.get(0).getStartTime() == this.times.get(0).getEndTime()) {
          CommandTimes newCommandTimes = new CommandTimes(timeWanted,
                  this.times.get(0).getEndTime());
          CommandTimes newCommandTimesDup = new CommandTimes(timeWanted, timeWanted);
          this.times.remove(0);
          this.times.add(newCommandTimes);
          this.times.add(newCommandTimesDup);
        } else {
          this.times.add(new CommandTimes(timeWanted, this.times.get(0).getStartTime()));
          //     this.states.put(timeWanted, inputShapeState);
        }
      }
      //the new keyframe is after the last keyframe
      else if (timeWanted > this.times.get(this.times.size() - 1).getEndTime()) {
        if (this.times.get(this.times.size() - 1).getStartTime()
                == this.times.get(this.times.size() - 1).getEndTime()) {
          CommandTimes newCommandTimes =
                  new CommandTimes(this.times.get(this.times.size() - 1).getEndTime(), timeWanted);
          this.times.remove(this.times.size() - 1);
          this.times.add(newCommandTimes);
        } else {
          this.times.add(new CommandTimes(this.times.get(this.times.size() - 1).getEndTime(),
                  timeWanted));
          //   this.states.put(timeWanted, inputShapeState);
        }
      }
      //otherwise it is between existing keyframes
      else {
        for (int i = 0; i < this.times.size(); i++) {
          if (timeWanted > this.times.get(i).getStartTime()
                  && timeWanted < this.times.get(i).getEndTime()) {
            CommandTimes newTimeBefore =
                    new CommandTimes(this.times.get(i).getStartTime(), timeWanted);
            CommandTimes newTimeAfter =
                    new CommandTimes(timeWanted, this.times.get(i).getEndTime());
            this.times.remove(i);
            this.times.add(newTimeBefore);
            this.times.add(newTimeAfter);
          }
        }
      }
    this.states.put(timeWanted, inputShapeState);
    Collections.sort(this.times);

  }

  /*
    From Here on Implements the Provider Shape interface methods
   */
  @Override
  public int getID() {
    return this.getDrawOrder();
  }

  @Override
  public int getStartTick() {
    // Sort times to ensure order
    Collections.sort(this.times);
    // First Command Time Start Time is the First Frame
    return this.times.get(this.times.size() - 1).getStartTime();
  }

  @Override
  public int getEndTick() {
    // Sort times to ensure order
    Collections.sort(this.times);
    // Last Command Time End Time is the last Frame
    return this.times.get(this.times.size() - 1).getEndTime();
  }

  @Override
  public String getName() {
    return this.getUserShapeName();
  }

  @Override
  public boolean hasKeyFrame(int tick) {
    // Check each Command Time to see if there is a KeyFrame at the tick
    for (CommandTimes ct : this.times) {
      if (ct.getStartTime() == tick || ct.getEndTime() == tick) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Color getColor(int tick) throws IllegalArgumentException {
    ShapeState selectedState = this.findShapeState(tick);
    return new Color(selectedState.getRed(), selectedState.getGreen(), selectedState.getBlue());
  }

  @Override
  public void removeKeyFrame(int tick) {
    // Wrapping the Remove State to Update Command Times also
    this.removeState(tick);
  }

  @Override
  public void addTickShape(int tick, TickShape next) {
    int x = next.getPosition().getX();
    int y = next.getPosition().getY();
    int width = next.getSize().getX(); // Make Sure this is Width
    int height = next.getSize().getY();
    int r = next.getColor().getRed();
    int g = next.getColor().getGreen();
    int b = next.getColor().getBlue();
    ShapeState stateConvert = new ShapeState(x, y, width, height, r, g, b);

    // use key frame method to update Command Times
    this.addKeyframe(tick, stateConvert);
  }


  @Override
  public IntTuple getSize(int tick) throws IllegalArgumentException {
    int width = (int) this.findShapeState(tick).getWidth();
    int height = (int) this.findShapeState(tick).getHeight();
    return new IntTuple(width, height);
  }

  @Override
  public IntTuple getPosn(int tick) throws IllegalArgumentException {
    int x = (int) this.findShapeState(tick).getxPosn();
    int y = (int) this.findShapeState(tick).getyPosn();
    return new IntTuple(x, y);
  }

  @Override
  public boolean isStillRunning(int tick) {
    // ensure order
    Collections.sort(this.times);
    // If tick is between start and end times of shape
    return tick >= this.times.get(0).getStartTime()
            && tick <= this.times.get(this.times.size() - 1).getEndTime();
  }

  @Override
  public boolean hasStarted(int tick) {
    return this.times.size() > 0 && tick >= this.times.get(0).getStartTime();
  }

  @Override
  public boolean isVisible(int tick) {
    return hasStarted(tick) && isStillRunning(tick);
  }

  @Override
  public ArrayList<TickShape> getKeyFrames() {

    ArrayList<TickShape> keyFramesConvert = new ArrayList<TickShape>();

    if (this.times.size() == 0) {
      return keyFramesConvert;
    }

    // Add first command time start time and map
    int timeConvert;
    ShapeState currShapeStateConvert;

    timeConvert = this.times.get(0).getStartTime();
    currShapeStateConvert = this.states.get(timeConvert);

    keyFramesConvert.add(convertShapeStateToTickShape(timeConvert, currShapeStateConvert));

    // Run Through each ShapeState Key Frame and Add as TickShape
    for (CommandTimes ct : this.times) {
      timeConvert = ct.getEndTime();
      currShapeStateConvert = this.states.get(timeConvert);

      keyFramesConvert.add(convertShapeStateToTickShape(timeConvert, currShapeStateConvert));

    }

    return keyFramesConvert;
  }

  /**
   * Converts ShapeState and int time into a TickShape.
   *
   * @param timeConvert  time of Shape State
   * @param stateConvert Shape State to convert
   * @return TickShape equivalent
   */
  private TickShape convertShapeStateToTickShape(int timeConvert, ShapeState stateConvert) {
    TickShape tickShapeConvert;

    tickShapeConvert = new TickShape((int) stateConvert.getxPosn(),
            (int) stateConvert.getyPosn(),
            (int) stateConvert.getWidth(),
            (int) stateConvert.getHeight(),
            stateConvert.getRed(),
            stateConvert.getGreen(),
            stateConvert.getBlue(),
            timeConvert);

    return tickShapeConvert;
  }


  @Override
  public void move(int tick, IntTuple dest) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Use IShape Functions");
  }

  @Override
  public void changeColor(int tick, Color newColor) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Use IShape Functions");
  }

  @Override
  public void changeSize(int tick, IntTuple newSize) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Use IShape Functions");
  }

  /**
   * Abstracts the drawing method to take in java.awt.Shape from the subclasses and draws onto g2d
   *
   * @param g2d       Graphics2D to draw onto
   * @param currState ShapeState to draw Shape as
   * @param shape     Shape to draw
   */
  public void draw(Graphics2D g2d, ShapeState currState, java.awt.Shape shape) {
    g2d.setPaint(currState.getColor());

    // Set rotation of shape correctly then reset the transform
    AffineTransform saveAt = g2d.getTransform();
    g2d.rotate(Math.toRadians(currState.getTheta()),currState.getxPosn()
                    + (currState.getWidth() / 2),
            currState.getyPosn() + (currState.getHeight() / 2));
    g2d.fill(shape);
    g2d.setTransform(saveAt);
  }

  @Override
  public void setShapeLayer(int shapeLayer) {
    if (shapeLayer >= 0) {
      this.shapeLayer = shapeLayer;
    } else {
      throw new IllegalArgumentException("Layer has to be positive number");
    }
  }

  @Override
  public int getShapeLayer() {
    return this.shapeLayer;
  }

  @Override
  public int compareTo(cs3500.animator.provider.model.shapes.Shape other) {
    // If other is an IShapeExtended then check shapeLayering first then draw order
    // If other is an IShape only then use draw order
    // If other is an pShape only then useID
    if (other instanceof IShape) {
      if (other instanceof IShapeExtended) {
        if (this.shapeLayer == ((IShapeExtended) other).getShapeLayer()) {
          return this.drawOrder - ((IShape)other).getDrawOrder();
        }
        return this.shapeLayer - ((IShapeExtended) other).getShapeLayer();
      } else {
        // else just use draw order
        return this.getDrawOrder() - ((IShape)other).getDrawOrder();
      }
    }
    
    return this.getID() - other.getID();
  }

}
