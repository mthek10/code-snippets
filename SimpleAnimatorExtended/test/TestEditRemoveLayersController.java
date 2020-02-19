import org.junit.Test;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IViewExtended;
import cs3500.animator.view.VisualView;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Testing the ability to edit layers through controller and remove entire layers.
 */
public class TestEditRemoveLayersController {

  @Test
  public void testEditLayers() {

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

    // test given default layer of 0 to "r"
    assertEquals(0, ((IShapeExtended) model.getShapes().get("r")).getShapeLayer());

    // test conditions by setting up view for layer change
    ((IViewExtended) view).setShapeWantedText("r");
    ((IViewExtended) view).setLayerWantedText(2);

    // test conditions by simulating a action fired from the edit layer button
    ActionEvent e = new ActionEvent(new JButton("Edit Layer"), 0, "Edit Layer");
    controller.actionPerformed(e);

    // check model for changes
    assertEquals(2, ((IShapeExtended) model.getShapes().get("r")).getShapeLayer());


  }


  @Test
  public void testRemoveLayers() {

    AnimationModel model = new AnimationModelImpl();
    model.setBounds(200, 200, 1000, 1000);

    ShapeState state1 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 0);
    ShapeState state2 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 360);


    model.addShape("r", "rectangle");
    model.addCommand("r", 0, state1, 10, state2);

    ShapeState state3 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 0);
    ShapeState state4 = new ShapeState(300, 200,
            300, 400, 0, 0, 0, 360);
    model.addShape("e1", "ellipse");
    model.addCommand("e1",0,state3,10, state4);
    model.addShape("e2", "ellipse");
    model.addCommand("e2",0,state3,10, state4);


    // Set up View
    Appendable out = new StringBuilder("");
    VisualView visualView = new VisualView(100,1000,1000,
            100,100);
    EditorView view = new EditorView(visualView);

    // Set up Controller
    Controller controller = new Controller(model, view);
    view.setTimerPause();

    // test given default layer of 0 to all shapes
    assertEquals(0, ((IShapeExtended) model.getShapes().get("r")).getShapeLayer());
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e1")).getShapeLayer());
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e2")).getShapeLayer());


    // test conditions by setting up view for layer change
    ((IViewExtended) view).setShapeWantedText("r");
    ((IViewExtended) view).setLayerWantedText(2);

    // test conditions by simulating a action fired from the edit layer button
    ActionEvent e = new ActionEvent(new JButton("Edit Layer"), 0, "Edit Layer");
    controller.actionPerformed(e);

    // check model for changes (only r should change)
    assertEquals(2, ((IShapeExtended) model.getShapes().get("r")).getShapeLayer());
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e1")).getShapeLayer());
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e2")).getShapeLayer());

    // Remove Layer 2
    // set view for layer remove
    ((IViewExtended) view).setLayerWantedText(2);

    // set controller for simulated action fired from remove layer button
    ActionEvent event2 = new ActionEvent(new JButton("Remove Layer"), 0,
            "Remove Layer");
    controller.actionPerformed(event2);

    // check model for changes
    // model should only have two shapes now e1 e2 and NOT r
    assertEquals(2, model.getShapes().size());
    assertTrue(model.getShapes().containsKey("e1"));
    assertTrue(model.getShapes().containsKey("e2"));
    assertFalse(model.getShapes().containsKey("r"));
    // e1 and e2 layers should remain untouched
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e1")).getShapeLayer());
    assertEquals(0, ((IShapeExtended) model.getShapes().get("e2")).getShapeLayer());

  }

}
