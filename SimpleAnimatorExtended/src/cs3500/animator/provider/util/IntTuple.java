package cs3500.animator.provider.util;

/**
 * The class representing something that holds two integers.
 */
public class IntTuple {
  private final int x;
  private final int y;


  /**
   * Creates an IntTuple.
   *
   * @param x the first int
   * @param y the second int
   */
  public IntTuple(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x value.
   *
   * @return the x value
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y value.
   *
   * @return the y value
   */
  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof IntTuple) {
      IntTuple that = (IntTuple) other;
      return this.x == that.x && this.y == that.y;
    }
    return false;
  }

  @Override
  public int hashCode() {
    String code = Integer.toString(this.x) + Integer.toString(this.y);
    return Integer.parseInt(code);
  }
}
