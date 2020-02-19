package cs3500.animator.provider.view;

import java.io.OutputStream;

import cs3500.animator.provider.model.animations.AnimationModel;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.functions.DescriptionCloserFunc;
import cs3500.animator.provider.util.functions.DescriptionHeaderFunc;
import cs3500.animator.provider.util.functions.DescriptionMotionFunc;

/**
 * A view for {@link AnimationModel}, which displays to an {@link Appendable} using a simple text
 * description of all the shapes and motions in the animation.
 */
public class DescriptionView extends cs3500.animator.provider.view.TextualViewAbs {

  /**
   * Creates a DescriptionView given a map of shape names to Shapes and an Appendable to output the
   * view.
   *
   * @param model      an immutable version of the animation model
   * @param textOutput where the text is sent to
   */
  public DescriptionView(ImmutableAnimationModelImpl model, OutputStream textOutput) {
    super(model, textOutput);
  }

  @Override
  protected String getOverallHeader() {
    return "";
  }

  @Override
  protected String getOverallCloser() {
    return "";
  }

  @Override
  protected String getShapeDescription(Shape shape) {
    return super.shapeDescription(new DescriptionHeaderFunc(), new DescriptionMotionFunc(),
            new DescriptionCloserFunc(), shape);
  }

}
