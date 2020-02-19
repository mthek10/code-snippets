import org.junit.Test;

import cs3500.animator.model.IShape;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeState;

import static junit.framework.TestCase.assertEquals;

/**
 * Used to test the linear interpolation of ShapeStates in an IShape.
 */
public class TestShapeLinearInterpolation {

  @Test
  public void testLinearInterpolationNoTheta() {
    IShape rect = new RectangleShape("r1",0);
    ShapeState state0 = new ShapeState(200,100,50,60,255,0,200);
    ShapeState state10 = new ShapeState(0,0,50,30,0,0,0);

    rect.addKeyframe(0,state0);
    rect.addKeyframe(10,state10);

    ShapeState state1 = new ShapeState(180,90,50,57,230,0,180);
    ShapeState state2 = new ShapeState(160,80,50,54,205,0,160);

    // Show that state requested at tick 1 interpolated correctly to match state1
    assertEquals(state1.toStringEnhanced(),rect.findShapeState(1).toStringEnhanced());

    // Show that state requested at tick 2 interpolated correctly to match state2
    assertEquals(state2.toStringEnhanced(),rect.findShapeState(2).toStringEnhanced());

  }

  @Test
  public void testLinearInterpolationTheta() {
    IShape rect = new RectangleShape("r1",0);
    ShapeState state0 = new ShapeState(200,100,50,60,255,0,200,0);
    ShapeState state10 = new ShapeState(0,0,50,30,0,0,0,360);

    rect.addKeyframe(0,state0);
    rect.addKeyframe(10,state10);

    ShapeState state1 = new ShapeState(180,90,50,57,230,0,180,36);
    ShapeState state2 = new ShapeState(160,80,50,54,205,0,160,72);

    // Show that state requested at tick 1 interpolated correctly to match state1
    assertEquals(state1.toStringEnhanced(),rect.findShapeState(1).toStringEnhanced());

    // Show that state requested at tick 2 interpolated correctly to match state2
    assertEquals(state2.toStringEnhanced(),rect.findShapeState(2).toStringEnhanced());
  }

}
