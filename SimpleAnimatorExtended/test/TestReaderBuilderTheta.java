import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test Class to test the integrated features of theta in the ModelBuilder AnimationReader utility.
 */
public class TestReaderBuilderTheta {

  @Test
  public void testReaderBuilderSimple() {

    // Testing Being Able to Read a File of Theta Format and Build Correct Model
    // MUST HAVE rotatedemo.txt in project

    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();
    Readable readable = null;

    File f = new File("rotatedemo.txt");

    if (!f.canRead()) {
      System.out.println("File not found for extra tests");
      assertTrue(!f.canRead());
    } else {

      try {
        readable = new FileReader(f);
      } catch (IOException e) {
        e.printStackTrace();
      }

      AnimationModel model = (AnimationModel) AnimationReader.parseFile(readable, builder);

      // Test Theta in Rectangle R (changing theta defined)
      assertEquals(0.0, model.getShapes().get("R").getShapeStates().get(1).getTheta());
      assertEquals(60.0, model.getShapes().get("R").getShapeStates().get(10).getTheta());
      assertEquals(120.0, model.getShapes().get("R").getShapeStates().get(50).getTheta());

    }

  }

}
