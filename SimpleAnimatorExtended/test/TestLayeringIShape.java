import org.junit.Test;

import cs3500.animator.model.EllipseShape;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.RectangleShape;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Used to test the setting, getting, and comparing of shape layering.
 */
public class TestLayeringIShape {

  @Test (expected = IllegalArgumentException.class)
  public void testSettingBadLayer() {
    // Correct Construction
    IShapeExtended rect = new RectangleShape("r1", 0, -1);
    // incorrect resetting
    rect.setShapeLayer(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructingBadLayer() {
    IShapeExtended rect = new RectangleShape("r1", 0, -1);
  }

  @Test
  public void testSettingGettingValid() {
    IShapeExtended rect = new RectangleShape("r1", 0, 0);
    IShapeExtended ellipse = new EllipseShape("e1", 1, 1);

    // test Rectangle
    assertEquals(0, rect.getShapeLayer());
    rect.setShapeLayer(1);
    assertEquals(1, rect.getShapeLayer());

    // test Ellipse
    assertEquals(1, ellipse.getShapeLayer());
    ellipse.setShapeLayer(0);
    assertEquals(0, ellipse.getShapeLayer());
  }

  @Test
  public void testComparingShapeLayer() {
    IShapeExtended rect = new RectangleShape("r1", 1, 0);
    IShapeExtended ellipse = new EllipseShape("e1", 0, 1);

    assertTrue(rect.compareTo(ellipse) < 0);

    // Put shapes on same layer and test
    ellipse.setShapeLayer(0);

    // Should be -1 now because 0 gets drawn first for draw order
    assertTrue(rect.compareTo(ellipse) > 0);
  }

}
