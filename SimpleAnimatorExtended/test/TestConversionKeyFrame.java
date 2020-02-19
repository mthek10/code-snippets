import org.junit.Test;

import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeState;

import static org.junit.Assert.assertEquals;

/**
 * Test class for additional features of Key Frames in our model.
 */
public class TestConversionKeyFrame {

  @Test
  public void testRemoveXStateFromXX() {

    ShapeState s1 = new ShapeState(200, 200, 100, 100,
            255, 0, 0);

    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.removeState(1);

    assertEquals(0, r.getTimes().size());

  }

  @Test
  public void testRemoveXStateXXXY() {

    ShapeState s1 = new ShapeState(200, 200, 100, 100,
            255, 0, 0);
    ShapeState s2 = new ShapeState(200, 200, 100, 100,
            0, 255, 0);


    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.addState(1, s1);
    r.addState(10, s2);
    r.addCommandTimes(new CommandTimes(1, 10));

    r.removeState(1);

    assertEquals(1, r.getTimes().size());
    assertEquals(true, r.getTimes().get(0).matches(10, 10));


  }

  @Test
  public void testRemoveYStateXXXY() {

    ShapeState s1 = new ShapeState(200, 200, 100, 100,
            255, 0, 0);
    ShapeState s2 = new ShapeState(200, 200, 100, 100,
            0, 255, 0);


    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.addState(1, s1);
    r.addState(10, s2);
    r.addCommandTimes(new CommandTimes(1, 10));

    r.removeState(10);

    assertEquals(1, r.getTimes().size());
    assertEquals(true, r.getTimes().get(0).matches(1, 1));


  }


  @Test
  public void testRemoveXStateXXXYYZ() {

    ShapeState s1 = new ShapeState(200, 200, 100,
            100, 255, 0, 0);
    ShapeState s2 = new ShapeState(200, 200, 100,
            100, 0, 255, 0);
    ShapeState s3 = new ShapeState(200, 200, 100,
            100, 0, 0, 255);


    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.addState(1, s1);
    r.addState(10, s2);
    r.addCommandTimes(new CommandTimes(1, 10));

    r.addState(10, s2);
    r.addState(20, s3);
    r.addCommandTimes(new CommandTimes(10, 20));


    r.removeState(1);

    assertEquals(2, r.getTimes().size());
    assertEquals(true, r.getTimes().get(0).matches(10, 10));
    assertEquals(true, r.getTimes().get(1).matches(10, 20));


  }


  @Test
  public void testRemoveYStateXXXYYZ() {

    ShapeState s1 = new ShapeState(200, 200, 100,
            100, 255, 0, 0);
    ShapeState s2 = new ShapeState(200, 200, 100,
            100, 0, 255, 0);
    ShapeState s3 = new ShapeState(200, 200, 100,
            100, 0, 0, 255);


    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.addState(1, s1);
    r.addState(10, s2);
    r.addCommandTimes(new CommandTimes(1, 10));

    r.addState(10, s2);
    r.addState(20, s3);
    r.addCommandTimes(new CommandTimes(10, 20));


    r.removeState(10);

    assertEquals(2, r.getTimes().size());
    assertEquals(true, r.getTimes().get(0).matches(1, 1));
    assertEquals(true, r.getTimes().get(1).matches(1, 20));


  }


  @Test
  public void testRemoveZStateXXXYYZ() {

    ShapeState s1 = new ShapeState(200, 200, 100,
            100, 255, 0, 0);
    ShapeState s2 = new ShapeState(200, 200, 100,
            100, 0, 255, 0);
    ShapeState s3 = new ShapeState(200, 200, 100,
            100, 0, 0, 255);


    RectangleShape r = new RectangleShape("r1", 0);
    r.addState(1, s1);
    r.addState(1, s1);

    r.addCommandTimes(new CommandTimes(1, 1));

    r.addState(1, s1);
    r.addState(10, s2);
    r.addCommandTimes(new CommandTimes(1, 10));

    r.addState(10, s2);
    r.addState(20, s3);
    r.addCommandTimes(new CommandTimes(10, 20));


    r.removeState(20);

    assertEquals(2, r.getTimes().size());
    assertEquals(true, r.getTimes().get(0).matches(1, 1));
    assertEquals(true, r.getTimes().get(1).matches(1, 10));

  }


}
