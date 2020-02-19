package cs3500.animator.model;

/**
 * Represents the start and end times for a given command.
 */
public final class CommandTimes implements Comparable<CommandTimes> {

  //Represents the last tick of animation created
  private static int lastEndTime = 0;
  private final int startTime;
  private final int endTime;

  /**
   * Creates a CommandTimes object.
   *
   * @param startTime the start time of a command
   * @param endTime   the end time of a command
   * @throws IllegalArgumentException if time is negative or the end time comes before start
   */
  public CommandTimes(int startTime, int endTime) throws IllegalArgumentException {
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    // Times can be equal if the State is a Still State meaning no Change just single frame
    if (endTime < startTime) {
      throw new IllegalArgumentException("The endTime cannot come before the startTime");
    }
    this.startTime = startTime;
    this.endTime = endTime;

    // if end time of new CommandTime is after the LatestEndTime then save it to static variable
    if (endTime > lastEndTime) {
      lastEndTime = endTime;
    }
  }

  /**
   * Returns the lastEndTime of all the made CommandTimes.
   *
   * @return the lastEndTime of all the made CommandTimes because static.
   */
  public static int getLastEndTime() {
    return lastEndTime;
  }

  @Override
  public int compareTo(CommandTimes other) {
    if (this.startTime == other.startTime) {
      return this.endTime - other.endTime;
    }
    return this.startTime - other.startTime;
  }

  /**
   * Get the start time.
   *
   * @return the start time
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Get the end time.
   *
   * @return the end time
   */
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Returns whether or not this matches the given start and end times.
   *
   * @param startTime startTime to match
   * @param endTime   endTime to match
   * @return if this has the same startTime and endTime as given
   */
  public boolean matches(int startTime, int endTime) {
    return (startTime == this.startTime && endTime == this.endTime);
  }


}
