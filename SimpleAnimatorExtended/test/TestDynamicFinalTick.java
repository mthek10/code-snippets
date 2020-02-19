import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;


/**
 * Test class for updated getFinalTick of Model to accommodate Slider functions.
 */
public class TestDynamicFinalTick {

  /**
   * Intializes the model from file path given.
   * @param filepath file path
   * @return AnimationModel
   */
  private AnimationModel initModel(String filepath) {
    // BUILD MODEL
    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();
    Readable readable = null;

    File f = new File(filepath);

    // If you don't have the file return null model
    if (!f.canRead()) {
      return null;
    }

    try {
      readable = new FileReader(f);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return (AnimationModel) AnimationReader.parseFile(readable, builder);
  }

  @Test
  public void testAddingRemovingKeyFrame() {
    AnimationModel model1 = initModel("buildings.txt");

    // Do not fail all tests if file is not found (Extra Tests)
    if (model1 == null) {
      System.out.println("File not found for extra tests");
      assertNull(model1);
    } else {

      // Check Final Tick Initially
      assertEquals(200, model1.getFinalTick());

      // Extend the background by mapping the shapestate at 200 to a new keyframe at 300
      model1.addKeyframe("background",
              300, model1.getShapes().get("background").findShapeState(200));

      // new Final tick should be 300
      assertEquals(300, model1.getFinalTick());

      // Now Remove That KeyFrame and Final Tick should reset to 200
      model1.removeKeyframe("background", 300);
      assertEquals(200, model1.getFinalTick());
    }
  }

  @Test
  public void testRemovingShape() {
    // If the shape with the final tick is removed then tick should calculate appropriately
    AnimationModel model2 = initModel("buildings.txt");

    // Do not fail all tests if file is not found (Extra Tests)
    if (model2 == null) {
      System.out.println("File not found for extra tests");
      assertNull(model2);
    } else {

      // Check Final Tick Initially
      assertEquals(200, model2.getFinalTick());

      // Extend the background by mapping the shapestate at 200 to a new keyframe at 300
      model2.addKeyframe("background",
              300, model2.getShapes().get("background").findShapeState(200));

      // new Final tick should be 300
      assertEquals(300, model2.getFinalTick());

      // Now Remove That KeyFrame and Final Tick should reset to 200
      model2.removeShape("background");
      assertEquals(200, model2.getFinalTick());

    }
  }

  @Test
  public void testingZeroTick() {
    // An empty model should have a final tick of 0
    AnimationModel model3 = new AnimationModelImpl();
    assertEquals(0, model3.getFinalTick());

  }

}
