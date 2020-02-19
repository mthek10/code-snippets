import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Tests the Reading and Building of models with Layer Defined from File Layer.
 */
public class TestReaderBuilderLayer {


  @Test
  public void testBuilderReaderLayerBackground() {

    // MUST HAVE buildingsLayerBackground1 in DemoDay dir
    // Testing that Reads Layer correctly
    // & builds model correctly
    // & Collections.sort will correctly order layer to be drawn

    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();

    Readable readable = null;

    File f = new File("DemoDay/buildingsLayerBackground1.txt");

    // If file not found or cannot read do not fail all extra tests
    if (!f.canRead()) {
      System.out.println("File not found for extra tests");
      assertTrue(!f.canRead());
    } else {


      try {
        readable = new FileReader(f);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

      AnimationModel model = (AnimationModel) AnimationReader.parseFile(readable, builder);

      // Read in correctly
      assertEquals(0, ((IShapeExtended) model.getShapes().get("B0")).getShapeLayer());
      assertEquals(0, ((IShapeExtended) model.getShapes().get("B1")).getShapeLayer());
      assertEquals(0, ((IShapeExtended) model.getShapes().get("B2")).getShapeLayer());
      assertEquals(0, ((IShapeExtended) model.getShapes().get("B3")).getShapeLayer());
      assertEquals(1, ((IShapeExtended) model.getShapes().get("background")).getShapeLayer());

      ArrayList<IShape> drawOrder = new ArrayList<>(model.getShapes().values());
      Collections.sort(drawOrder);

      // assert last thing drawn is background because highest layer
      assertTrue(drawOrder.get(drawOrder.size() - 1).getUserShapeName()
              .equalsIgnoreCase("background"));

    }

  }
}
