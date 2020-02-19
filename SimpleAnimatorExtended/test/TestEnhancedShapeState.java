import org.junit.Test;

import cs3500.animator.model.ShapeState;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test new and backwards compatibility of the ShapeState w/ angle functionality.
 */
public class TestEnhancedShapeState {

  @Test
  public void testBackwardsCompatibility() {
    // Testing without defining theta

    ShapeState state1 = new ShapeState(10,15,20,
            25,0,100,200);
    ShapeState state2 = new ShapeState(10,15,20,
            25,0,100,200);


    // Test that if not defined theta will be 0
    assertEquals(0.0,state1.getTheta());

    // Test that matching shape states will work if equal and no theta defined
    assertTrue(state1.equalsShapeState(state2));

    // Testing that if not explicit ShapeState will print out without theta
    assertEquals("10.0 15.0 20.0 25.0 0 100 200", state1.toString());

    // Testing that if explicit but not defined ShapeState will print 0.0
    assertEquals("10.0 15.0 20.0 25.0 0 100 200 0.0", state1.toStringEnhanced());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadInputThetaNeg() {
    ShapeState state1 = new ShapeState(10,15,20,
            25,0,100,200, -5.5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadInputTheta360Plus() {
    ShapeState state1 = new ShapeState(10,15,20,
            25,0,100,200, 360.1);
  }

  @Test
  public void testToStringEnhanced() {
    ShapeState state1 = new ShapeState(10,15,20,
            25,0,100,200, 360);

    // test output with theta defined
    assertEquals("10.0 15.0 20.0 25.0 0 100 200 360.0",state1.toStringEnhanced());
  }

  @Test
  public void testEqualsOtherShapeState() {
    // Test equal with theta defined
    ShapeState state1 = new ShapeState(10,15,20,
            25,0,100,200, 360);
    ShapeState state2 = new ShapeState(10,15,20,
            25,0,100,200, 360);

    assertTrue(state1.equalsShapeState(state2));
    assertTrue(state2.equalsShapeState(state1));

  }


}
