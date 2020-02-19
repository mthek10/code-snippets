import org.junit.Test;

import cs3500.animator.model.CommandTimes;

import static org.junit.Assert.assertEquals;

/**
 * Represents Test Class for Command Times.
 */
public class TestCommandTimes {

  @Test
  public void testConstructor() {
    CommandTimes time1 = new CommandTimes(1, 40);
    assertEquals(time1.getStartTime(), 1);
    assertEquals(time1.getEndTime(), 40);

    CommandTimes time2 = new CommandTimes(0, 1);
    assertEquals(time2.getStartTime(), 0);
    assertEquals(time2.getEndTime(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor1() {
    CommandTimes time2 = new CommandTimes(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor2() {
    CommandTimes time2 = new CommandTimes(1, -2);
  }

  @Test
  public void testCompareTo() {
    CommandTimes time1 = new CommandTimes(1, 10);
    CommandTimes time2 = new CommandTimes(30, 55);
    assertEquals(time1.compareTo(time2), -29);
    assertEquals(time2.compareTo(time1), 29);
  }
}
