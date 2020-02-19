import org.junit.Test;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.TextView;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for TextView.
 */
public class TextViewTest {


  @Test
  public void testNoShapeTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    // Set up View
    Appendable out = new StringBuilder("");
    TextView view = new TextView(out);

    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("", out.toString());
  }

  @Test(expected = Exception.class)
  public void testExceptionTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    // Set up View
    Appendable out = null;
    TextView view = new TextView(out);

    // Set up Controller
    Controller controller = new Controller(model, view);

  }


  @Test
  public void testOneShapeTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300,
            200, 300, 400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300,
            200, 300, 10, 0, 0, 0);

    model.addShape("r", "rectangle");
    model.addCommand("r", 10, state1, 20, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    TextView view = new TextView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("shape r rectangle\n" +
            "motion r 10 300.0 200.0 300.0 400.0 0 0 0   " +
            " 20 300.0 200.0 300.0 10.0 0 0 0", out.toString());
  }

  @Test
  public void testMultipleShapeTextView() {
    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 10, 0, 0, 0);
    ShapeState state3 = new ShapeState(100, 200,
            300, 400, 0, 0, 0);
    ShapeState state4 = new ShapeState(300, 200,
            300, 400, 0, 0, 0);

    model.addShape("r", "rectangle");
    model.addCommand("r", 10, state1, 20, state2);

    model.addShape("r2", "rectangle");
    model.addCommand("r2", 5, state3, 15, state4);

    // Set up View
    Appendable out = new StringBuilder("");
    TextView view = new TextView(out);


    // Set up Controller
    Controller controller = new Controller(model, view);

    assertEquals("shape r rectangle\n" +
            "motion r 10 300.0 200.0 300.0 400.0 0 0 0    20 300.0 200.0 300.0 10.0 0 0 0\n" +
            "\n" +
            "shape r2 rectangle\n" +
            "motion r2 5 100.0 200.0 300.0 400.0 0 0 0    15 300.0 200.0 " +
            "300.0 400.0 0 0 0", out.toString());
  }

}
