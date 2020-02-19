import org.junit.Test;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.VisualView;

import static junit.framework.TestCase.assertEquals;

/**
 * Testing Functionality of Scrubbing Callback.
 */
public class TestScrubber {

  @Test
  public void testScrubber() {
    // Testing Controller Response to View Scrub Firing.

    // Setup Model
    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 360);

    model.addShape("r", "rectangle");
    model.addCommand("r", 0, state1, 10, state2);

    // Set up View
    Appendable out = new StringBuilder("");
    VisualView visualView = new VisualView(100,1000,1000,
            100,100);
    EditorView view = new EditorView(visualView);

    // Set up Controller
    Controller controller = new Controller(model, view);
    view.setTimerPause();

    // Scrub to 15
    view.setScrubber(8);

    // Test to Make Sure View does not snap to tick when set by program
    // (no interference so view can progress scrubber like youtube video
    // and only snap when user "isAdjusting()" scrubber
    assertEquals(0, view.getTick());

    // Scrub again
    view.setScrubber(3);

    // Test reScrub
    assertEquals(0, view.getTick());


    // test Controller response
    JSlider sliderMock = new JSlider();
    // Acts like user is adjusting
    sliderMock.setValueIsAdjusting(true);
    sliderMock.setValue(8);
    ChangeEvent e = new ChangeEvent(sliderMock);
    // Controller should snap view to scrubbed tick 8
    controller.stateChanged(e);

    assertEquals(8, view.getTick());

  }

}
