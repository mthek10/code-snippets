package cs3500.animator.provider.util;

/**
 * Represents a motion for a shape to undertake including the start and end tick and states.
 */
public class Motion implements Comparable<Motion> {
  private TickShape startState;
  private TickShape endState;

  /**
   * Creates a new motion with the given start and end ticks and TickShapes.
   *
   * @param startState the TickState that the motion starts with
   * @param endState   the TickState that the motion ends with
   */
  public Motion(TickShape startState, TickShape endState) {
    this.startState = startState;
    this.endState = endState;
  }

  /**
   * Checks to see if this motion's duration overlaps with the given motion.
   *
   * @param other the motion being check against this motion for overlap
   * @return true if this and other do not overlap, false otherwise
   */
  public boolean noOverlap(Motion other) {
    return (other.startState.getTick() <= this.startState.getTick()
            && other.endState.getTick() <= this.startState.getTick())
            || (other.startState.getTick() >= this.endState.getTick()
            && other.endState.getTick() >= this.endState.getTick());
  }

  /**
   * Checks if this motion transitions into the given motion. A transition in this case is having
   * the same start state as this motion's end state.
   *
   * @param other the motion being checked against this motion to see if the two transition
   * @return true if this motion transitions into the other motion
   */
  public boolean goodTransition(Motion other) {
    return this.endState.equals(other.startState);
  }

  /**
   * The getter for the start TickShape.
   *
   * @return the start TickShape of this motion
   */
  public TickShape getStartState() {
    return this.startState;
  }

  /**
   * The getter for the end TickShape.
   *
   * @return the end TickShape of this motion
   */
  public TickShape getEndState() {
    return this.endState;
  }

  /**
   * The getter for the start tick.
   *
   * @return the start tick
   */
  public int getStart() {
    return this.startState.getTick();
  }

  /**
   * The getter for the end tick.
   *
   * @return the end tick
   */
  public int getEnd() {
    return this.endState.getTick();
  }

  @Override
  public int compareTo(Motion other) {
    return this.startState.getTick() - other.startState.getTick();
  }
}
