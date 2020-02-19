package cs3500.animator.provider.model.timelines;

/**
 * Represents data points of type T over a span of ticks.
 */
public interface Timeline<T> {

  /**
   * Gets the value of {@code this} timeline at the specified tick.
   *
   * @param tick the tick to get the value from
   * @return the value at the given tick
   */
  T getValue(int tick);

  /**
   * Returns whether {@code this} timeline has a value at the given tick.
   *
   * @param tick the given tick
   * @return whether tick has a defined value
   */
  boolean hasKeyFrame(int tick);

  /**
   * Adds a new key frame to {@code this} timeline.
   *
   * @param tick  the tick of the new key frame
   * @param value the value at the new key frame
   */
  void addKeyFrame(int tick, T value);

  /**
   * Removes the event at the given tick.
   *
   * @param tick the tick where the event is being removed from
   */
  void removeKeyFrame(int tick);

  /**
   * Finds the tick of the first item in the timeline.
   *
   * @return the tick of the first item
   */
  int getFirstTick();

  /**
   * Finds the tick of the last item in the timeline.
   *
   * @return the tick of the last item in the timeline
   */
  int getLastTick();

}
