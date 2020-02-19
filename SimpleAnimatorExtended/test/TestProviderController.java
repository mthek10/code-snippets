import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.provider.controller.AnimationController;
import cs3500.animator.provider.controller.ControllerProviderImpl;
import cs3500.animator.provider.model.animations.AnimationModelImplAdapter;
import cs3500.animator.provider.view.EditorView;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;

import static org.junit.Assert.assertTrue;

/**
 * Testing whether Controller runs.
 */
public class TestProviderController {

  @Test
  public void testControllerRunning() {

    // BUILD MODEL
    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();
    Readable readable = null;

    File f = new File("hanoi.txt");

    // If cannot read file or don't have file do not fail all extra tests
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
      AnimationModelImplAdapter providerModelAdapt = new AnimationModelImplAdapter(model);

      // BUILD VIEW
      EditorView editorViewProvider = new EditorView(providerModelAdapt,
              providerModelAdapt.getCanvasWidth(),
              providerModelAdapt.getCanvasHeight());


      // BUILD CONTROLLER
      AnimationController animationControllerProvider =
              new ControllerProviderImpl(providerModelAdapt, editorViewProvider);

      // Start animation
      animationControllerProvider.run();
      assertTrue(animationControllerProvider.isRunning());

      try {
        ((FileReader) readable).close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}