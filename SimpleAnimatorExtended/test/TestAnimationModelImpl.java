import org.junit.Test;



import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.ShapeState;

import static junit.framework.TestCase.assertEquals;


/**
 * Represents Test Class for AnimationModelImpl.
 */
public class TestAnimationModelImpl {

  @Test
  public void testBasicAnimationModel() {
    AnimationModelImpl m1 = new AnimationModelImpl();
    m1.addShape("r1", "rectangle");
    ShapeState s0 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    ShapeState s10 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    CommandTimes c1to10 = new CommandTimes(1, 10);


    m1.addCommand("r1", 1, s0, 10, s10);

    m1.addCommand("r1", 20, new ShapeState(200, 200,
                    50, 100, 255, 0, 0), 40,
            new ShapeState(200, 200, 50, 100, 255, 0, 0));

    m1.addShape("r2", "rectangle");
    m1.addCommand("r2", 1, s0, 10, s10);


    assertEquals("shape r2 rectangle\n" +
                    "motion r2 1 200.0 200.0 50.0 100.0 255 0 0    " +
                    "10 200.0 200.0 50.0 100.0 255 0 0\n" +
                    "\n" +
                    "shape r1 rectangle\n" +
                    "motion r1 1 200.0 200.0 50.0 100.0 255 0 0    " +
                    "10 200.0 200.0 50.0 100.0 255 0 0\n" +
                    "motion r1 20 200.0 200.0 50.0 100.0 255 0 0    " +
                    "40 200.0 200.0 50.0 100.0 255 0 0",
            m1.getAnimationState());

  }

  @Test
  public void testSetLayer() {
    // Testing the setLayer Feature

    AnimationModelImpl m1 = new AnimationModelImpl();
    m1.addShape("r1", "rectangle");
    ShapeState s0 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    ShapeState s10 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    CommandTimes c1to10 = new CommandTimes(1, 10);


    m1.addCommand("r1", 1, s0, 10, s10);

    m1.addCommand("r1", 20, new ShapeState(200, 200,
                    50, 100, 255, 0, 0), 40,
            new ShapeState(200, 200, 50, 100, 255, 0, 0));

    m1.addShape("r2", "rectangle");
    m1.addCommand("r2", 1, s0, 10, s10);


    // Test Layer BEFORE
    assertEquals(0,((IShapeExtended) m1.getShapes().get("r2")).getShapeLayer());
    // Use Model Set Layer
    m1.setLayer("r2", 1);
    // Test Layer AFTER
    assertEquals(1, ((IShapeExtended) m1.getShapes().get("r2")).getShapeLayer());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetLayerBad() {
    // Testing the setLayer Feature w/ Bad Input

    AnimationModelImpl m1 = new AnimationModelImpl();
    m1.addShape("r1", "rectangle");
    ShapeState s0 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    ShapeState s10 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    CommandTimes c1to10 = new CommandTimes(1, 10);


    m1.addCommand("r1", 1, s0, 10, s10);

    m1.addCommand("r1", 20, new ShapeState(200, 200,
                    50, 100, 255, 0, 0), 40,
            new ShapeState(200, 200, 50, 100, 255, 0, 0));

    m1.addShape("r2", "rectangle");
    m1.addCommand("r2", 1, s0, 10, s10);


    // Test Layer BEFORE
    assertEquals(0,((IShapeExtended) m1.getShapes().get("r2")).getShapeLayer());
    // Use Model Set Layer with BAD INPUT
    m1.setLayer("r2", -1);

  }


  @Test
  public void testLayeredAnimationModel() {
    // Testing addShape with layer parameter

    AnimationModelImpl m1 = new AnimationModelImpl();
    m1.addShape("r1", "rectangle",2);
    ShapeState s0 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    ShapeState s10 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    CommandTimes c1to10 = new CommandTimes(1, 10);


    m1.addCommand("r1", 1, s0, 10, s10);

    m1.addCommand("r1", 20, new ShapeState(200, 200,
                    50, 100, 255, 0, 0), 40,
            new ShapeState(200, 200, 50, 100, 255, 0, 0));

    m1.addShape("r2", "rectangle", 1);
    m1.addCommand("r2", 1, s0, 10, s10);


    // Check same functionality as addShape without parameter for animationState
    assertEquals("shape r2 rectangle\n" +
                    "motion r2 1 200.0 200.0 50.0 100.0 255 0 0    " +
                    "10 200.0 200.0 50.0 100.0 255 0 0\n" +
                    "\n" +
                    "shape r1 rectangle\n" +
                    "motion r1 1 200.0 200.0 50.0 100.0 255 0 0    " +
                    "10 200.0 200.0 50.0 100.0 255 0 0\n" +
                    "motion r1 20 200.0 200.0 50.0 100.0 255 0 0    " +
                    "40 200.0 200.0 50.0 100.0 255 0 0",
            m1.getAnimationState());

    // Check Layers
    assertEquals(2 ,((IShapeExtended) m1.getShapes().get("r1")).getShapeLayer());
    assertEquals(1 ,((IShapeExtended) m1.getShapes().get("r2")).getShapeLayer());

  }

}
