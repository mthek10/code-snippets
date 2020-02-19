package cs3500.animator.model;

/**
 * A Command Object represents a command for a shape. A command is two shape states with times
 * associated with each one.
 */

public class Command implements Comparable<Command> {
  private int startTime;
  private ShapeState startState;
  private int endTime;
  private ShapeState endState;

  /**
   * Creates a Command object that stores two shapeStates and the times they occur at for a shape.
   *
   * @param startTime  the time of the first state
   * @param startState the first state
   * @param endTime    the time of the second state
   * @param endState   the second state
   */
  public Command(int startTime, ShapeState startState, int endTime, ShapeState endState) {
    this.startTime = startTime;
    this.startState = startState;
    this.endTime = endTime;
    this.endState = endState;
  }

  /**
   * Gets the start time.
   *
   * @return the start time
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Returns the start state.
   *
   * @return the start state
   */
  public ShapeState getStartState() {
    return this.startState;
  }

  /**
   * Returns the end time.
   *
   * @return the end time
   */
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Returns the end state.
   *
   * @return the end state
   */
  public ShapeState getEndState() {
    return this.endState;
  }

  @Override
  public int compareTo(Command o) {
    return this.getStartTime() - o.getStartTime();
  }
}
