package cs3500.animator.provider.view;

import java.io.OutputStream;

import cs3500.animator.provider.model.animations.AnimationModel;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.functions.SVGCloserFunc;
import cs3500.animator.provider.util.functions.SVGHeaderFunc;
import cs3500.animator.provider.util.functions.SVGMotionFunc;

/**
 * A view for {@link AnimationModel}, which displays as text to an {@link Appendable} using SVG
 * format, describing all the shapes and motions in the animation.
 */
public class SVGView extends cs3500.animator.provider.view.TextualViewAbs {

  private int tickSpeed;

  private int canvasWidth;
  private int canvasHeight;

  /**
   * Creates a SVGView given a map of shape names to Shapes, an Appendable to output the view, and a
   * tick speed.
   *
   * @param model      an immutable version of the animation model
   * @param textOutput where the text is sent to
   * @param tickSpeed  tick speed in milliseconds/tick
   */
  public SVGView(ImmutableAnimationModelImpl model, OutputStream textOutput, int tickSpeed,
                 int canvasWidth, int canvasHeight) {
    super(model, textOutput);
    this.tickSpeed = tickSpeed;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
  }

  @Override
  protected String getOverallHeader() {
    return String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">\n", this.canvasWidth, this.canvasHeight);
  }

  @Override
  protected String getOverallCloser() {
    return "</svg>";
  }

  @Override
  protected String getShapeDescription(Shape shape) {
    return this.shapeDescription(new SVGHeaderFunc(), new SVGMotionFunc(tickSpeed),
            new SVGCloserFunc(), shape);
  }
}
