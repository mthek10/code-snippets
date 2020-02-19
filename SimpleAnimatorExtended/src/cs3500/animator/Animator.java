package cs3500.animator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.IActionListener;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.provider.controller.AnimationController;
import cs3500.animator.provider.controller.ControllerProviderImpl;
import cs3500.animator.provider.model.animations.AnimationModelImplAdapter;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.view.AnimationView;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;

/**
 * Creates Animation From TextFiles through Command Line Arguments.
 */
public final class Animator {

  // Delay = 1000/speed
  private static final int DEFAULT_DELAY = 1000;
  private static final Appendable DEFAULT_OUT = System.out;
  private static final int DEFAULT_CANVAS_HEIGHT = 1000;
  private static final int DEFAULT_CANVAS_WIDTH = 1000;

  /**
   * Start of program.
   *
   * @param args input arguments for manipulating animation view
   */
  public static void main(String[] args) {

    // fileName and view must be initialized throw command line args
    String fileName = "";
    IView view = null;
    AnimationView pView = null;
    Appendable out = DEFAULT_OUT;
    int speed = DEFAULT_DELAY;
    boolean providerSelected = false;

    for (int a = 0; a < args.length; a++) {

      switch (args[a]) {
        case "-in":
          fileName = handleFileIn(args[a + 1]);
          break;
        case "-view":
          if (args[a + 1].equalsIgnoreCase("provider")) {
            providerSelected = true;
          } else {
            providerSelected = false;
            view = handleViewSelect(args[a + 1]);
          }
          break;
        case "-out":
          out = handleOutputSelect(args[a + 1]);
          break;
        case "-speed":
          speed = handleSpeedSelect(args[a + 1]);
          break;
        default:
          // Nothing happens (For Java Style)
          break;
      }

    }

    // If speed was specified in Command line reset it
    try {
      if (!providerSelected) {
        view.setDelay(speed);
      }
    } catch (Exception e) {
      // Don't Set Delay if view does not require
    }

    // If output was specified in Command Line reset it
    try {
      view.setOutput(out);
    } catch (Exception e) {
      // Don't Set Output if view does not require
    }

    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();
    Readable readable = null;

    File f = new File(fileName);

    try {
      readable = new FileReader(f);
    } catch (IOException e) {
      e.printStackTrace();
    }

    AnimationModel model = (AnimationModel) AnimationReader.parseFile(readable, builder);

    // Check if provider view is selected
    if (providerSelected) {
      // set provider view with model
      pView = new cs3500.animator.provider.view.EditorView(
              new ImmutableAnimationModelImpl(model),
              model.getWindowWidth(), model.getWindowHeight());
      AnimationController pController =
              new ControllerProviderImpl(new AnimationModelImplAdapter(model), pView);
      pController.run();
    } else {
      IActionListener controller = new Controller(model, view);
    }

    try {
      ((FileReader) readable).close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      if (out instanceof FileWriter) {
        ((FileWriter) out).close();
      }
    } catch (Exception e) {
      // Do nothing if closing because could be System.out
    }

  }

  /**
   * Handles the Output selection.
   * @param arg output
   * @return Appendable
   */
  private static Appendable handleOutputSelect(String arg) {

    Appendable out;

    if (arg.contains(".txt")) {
      try {
        out = new FileWriter(arg);
        return out;
      } catch (IOException e) {
        throw new IllegalArgumentException("Unable to write File to Inputted file");
      }
    } else if (arg.contains(".svg")) {
      try {
        out = new FileWriter(arg);
        return out;
      } catch (IOException e) {
        throw new IllegalArgumentException("Unable to write File to Inputted file");
      }
    } else {
      throw new IllegalArgumentException("File extension not recognized");
    }



  }

  /**
   * Convert inputted "speed" in ticks per second to delay for the a TIMER.
   */
  private static int handleSpeedSelect(String arg) {
    int speed = Integer.parseInt(arg);

    if (speed > 0) {
      return 1000 / speed;
    } else {
      throw new IllegalArgumentException("Invalid input for speed");
    }

  }

  /**
   * Handles File Input.
   * @param arg input file
   * @return checked input
   */
  private static String handleFileIn(String arg) {
    if (arg != null) {
      if (!arg.trim().equalsIgnoreCase("")) {
        // Use trim to delete whitespace around what actually user calls shape
        return arg;
      } else {
        throw new IllegalArgumentException("No characters passed in for string name");
      }
    } else {
      throw new IllegalArgumentException("Null String passed in");
    }
  }

  /**
   * Returns the IView from input string.
   * @param arg input string
   * @return IView
   */
  private static IView handleViewSelect(String arg) {
    IView view;

    switch (arg) {
      case "visual":
        view = new VisualView(DEFAULT_DELAY, 1000, 1000, 200, 200);
        break;
      case "text":
        view = new TextView(DEFAULT_OUT);
        break;
      case "svg":
        view = new SVGView(DEFAULT_OUT);
        break;
      case "edit":
        VisualView visualView = new VisualView(DEFAULT_DELAY, 1000, 1000, 200, 200);
        view = new EditorView(visualView);
        break;
      default:
        throw new IllegalArgumentException("Unrecognized View");
    }
    return view;
  }

}