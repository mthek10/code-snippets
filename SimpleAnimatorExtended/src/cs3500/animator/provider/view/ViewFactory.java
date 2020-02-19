package cs3500.animator.provider.view;

import java.io.OutputStream;

import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;

/**
 * A factory of views which creates instances of {@link AnimationView}s.
 */
public class ViewFactory {

  private ImmutableAnimationModelImpl model;
  private OutputStream textOutput;
  private int tickSpeed;
  private int canvasWidth;
  private int canvasHeight;

  /**
   * Creates a ViewFactory with the given map of shape names to shapes, an Appendable for output, a
   * tick speed in milliseconds/tick, and a canvas size.
   *
   * @param model        an immutable version of the animation model
   * @param out          the OutputStream for output
   * @param tickSpeed    the tick speed in ms/tick
   * @param canvasWidth  the canvas width
   * @param canvasHeight the canvas height
   */
  public ViewFactory(ImmutableAnimationModelImpl model, OutputStream out, int tickSpeed,
                     int canvasWidth, int canvasHeight) {
    this.model = model;
    this.textOutput = out;
    this.tickSpeed = tickSpeed;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
  }

  /**
   * Creates an instance of {@link AnimationView} based on a given String.
   *
   * @param type the type of view
   * @return the new {@link AnimationView}
   */
  public AnimationView createView(String type) {
    switch (type) {
      case "text":
        return new DescriptionView(model, textOutput);
      case "svg":
        return new cs3500.animator.provider.view.SVGView(model,
                textOutput, tickSpeed, canvasWidth, canvasHeight);
      case "visual":
        return new VisualView(model, canvasWidth, canvasHeight);
      case "edit":
        return new EditorView(model, canvasWidth, canvasHeight);
      default:
        throw new IllegalArgumentException("Given type is not a valid view type.");
    }
  }
}
