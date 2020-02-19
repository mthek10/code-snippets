import org.junit.Test;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShape;
import cs3500.animator.model.RectangleShape;
import cs3500.animator.model.ShapeState;
import cs3500.animator.provider.model.animations.AnimationModelImplAdapter;
import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.TickShape;

import static org.junit.Assert.assertEquals;

/**
 * Tests features of adapted capabilities of model.
 */
public class TestModelAdapting {

  @Test
  public void testIShapeExtendedCapabilities() {

    // Our IShape Class took on the functionality of the Provider Shape Class
    IShape rect = new RectangleShape("R1", 0);
    Shape pRect = new RectangleShape("R1", 0);

    // testing equality functionality between the IShape type and the Provider Shape type
    assertEquals(rect.getUserShapeName(), pRect.getName());
    assertEquals(rect.getDrawOrder(), pRect.getID());

    // Add a Key Frame to Each type
    rect.addKeyframe(0, new ShapeState(5, 10,
            20, 25, 255, 0, 200));
    pRect.addTickShape(0, new TickShape(5, 10, 20, 25,
            255, 0, 200, 0));

    // Check that both can pull out equal data from the same RectangleShape
    // Checks x,y,width,height,r,g,b
    assertEquals((int) rect.getShapeStates().get(0).getxPosn(), pRect.getPosn(0).getX());
    assertEquals((int) rect.getShapeStates().get(0).getyPosn(), pRect.getPosn(0).getY());
    assertEquals((int) rect.getShapeStates().get(0).getWidth(), pRect.getSize(0).getX());
    assertEquals((int) rect.getShapeStates().get(0).getHeight(), pRect.getSize(0).getY());
    assertEquals(rect.getShapeStates().get(0).getRed(), pRect.getColor(0).getRed());
    assertEquals(rect.getShapeStates().get(0).getGreen(), pRect.getColor(0).getGreen());
    assertEquals(rect.getShapeStates().get(0).getBlue(), pRect.getColor(0).getBlue());

  }

  @Test
  public void testBasicAnimationModel() {
    AnimationModelImpl m1 = new AnimationModelImpl();
    AnimationModelImplAdapter m2 = new AnimationModelImplAdapter(new AnimationModelImpl());


    m1.addShape("r1", "rectangle");
    m2.addShape("r1", "rectangle");

    ShapeState s0 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    ShapeState s10 = new ShapeState(200, 200, 50,
            100, 255, 0, 0);
    CommandTimes c1to10 = new CommandTimes(1, 10);


    m1.addCommand("r1", 1, s0, 10, s10);
    m2.addKeyFrame("r1", 1, (int) s0.getxPosn(),
            (int) s0.getyPosn(), (int) s0.getWidth(), (int) s0.getHeight(),
            s0.getRed(), s0.getGreen(),
            s0.getBlue());
    m2.addKeyFrame("r1", 10, (int) s10.getxPosn(),
            (int) s10.getyPosn(), (int) s10.getWidth(), (int) s10.getHeight(),
            s10.getRed(), s10.getGreen(),
            s10.getBlue());


    m1.addShape("r2", "rectangle");
    m2.addShape("r2", "rectangle");


    // Checks the number of key frames added to ensure same for
    assertEquals(m1.getShapes().get("r1").getShapeStates().values().size(),
            m2.getImmutable().getShapeByName("r1").getKeyFrames().size());

    // Checks the shapes in model
    assert (m1.getShapes().containsKey("r1"));
    assert (m2.getImmutable().getShapeByName("r1") != null);
    assert (m1.getShapes().containsKey("r2"));
    assert (m2.getImmutable().getShapeByName("r2") != null);

  }

}
