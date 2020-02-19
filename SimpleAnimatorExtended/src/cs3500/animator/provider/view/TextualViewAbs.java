package cs3500.animator.provider.view;

import java.awt.Color;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.model.animations.AnimationModel;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Outline;
import cs3500.animator.provider.model.shapes.Shape;
import cs3500.animator.provider.util.Motion;
import cs3500.animator.provider.util.TickShape;
import cs3500.animator.provider.util.functions.Function2Arg;

/**
 * An abstract view for {@link AnimationModel}, which represents views that generate text output in
 * some format.
 */
public abstract class TextualViewAbs implements cs3500.animator.provider.view.AnimationView {

  protected ImmutableAnimationModelImpl model;
  private PrintWriter output;

  /**
   * Creates a TextualView given a map of shape names to Shapes and an OutputStream to output the
   * view.
   *
   * @param model      an immutable version of the animation model
   * @param textOutput the OutputStream to display to
   */
  public TextualViewAbs(ImmutableAnimationModelImpl model, OutputStream textOutput) {
    this.model = model;
    this.output = new PrintWriter(textOutput);
  }

  private String descriptionLoop() {
    StringBuilder build = new StringBuilder();
    build.append(this.getOverallHeader());
    for (String name : model.currentNames()) {
      Shape shape = model.getShapeByName(name);
      build.append(this.getShapeDescription(shape) + "\n");
    }
    build.append(this.getOverallCloser());
    return build.toString();
  }

  protected String shapeDescription(Function<Shape, String> header,
                                    Function2Arg<Shape, Motion, String> motion,
                                    Function<String, String> closer, Shape shape) {
    StringBuilder description = new StringBuilder();
    description.append(header.apply(shape));
    List<Motion> sequence = this.shapeMotions(shape.getKeyFrames());
    for (Motion m : sequence) {
      description.append(motion.apply(shape, m));
    }
    String closerText = this.closerShapeText(shape.getOutline().toString());
    description.append(closer.apply(closerText));
    return description.toString();
  }

  private String closerShapeText(String outline) {
    switch (outline) {
      case "rectangle":
        return "rect";
      case "ellipse":
        return "ellipse";
      default:
        throw new IllegalArgumentException("invalid outline type");
    }
  }

  private List<Motion> shapeMotions(ArrayList<TickShape> keyFrames) {
    ArrayList<Motion> motions = new ArrayList<>();
    for (int i = 0; i < keyFrames.size() - 1; i++) {
      motions.add(new Motion(keyFrames.get(i), keyFrames.get(i + 1)));
    }
    return motions;
  }

  protected abstract String getOverallHeader();

  protected abstract String getOverallCloser();

  protected abstract String getShapeDescription(Shape shape);

  /**
   * Appends this animation's text description to {@code output}. NEEDS MORE DETAIL
   */
  @Override
  public void display() {
    this.output.print(this.descriptionLoop());
    this.output.close();
  }

  @Override
  public void refresh() {
    // There is nothing to refresh for a text-based view.
  }

  @Override
  public void reset() {
    // There is nothing to reset for a text-based view.
  }

  @Override
  public int getTick() {
    throw new UnsupportedOperationException("This view does not have a realtime tick.");
  }

  @Override
  public void showErrorMessage(String error) {
    // There are no pop-up error messages to show for a text-based view.
  }

  @Override
  public void addKeyAction(KeyStroke key, Action action) {
    // There is no keyboard input for a text-based view.
  }

  @Override
  public void setInfoText(String info) {
    // There is no place to show user info in a text-based view.
  }

  @Override
  public void setShapeInput(List<String> shapeNames) {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public void setShapeInputListener(ListSelectionListener listener) {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public void setKeyFrames(List<TickShape> keyFrames) {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public String getNewShapeName() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public Outline getOutlineInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public void setOutlineInput(List<Outline> outlines) {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public Color getColorInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public void setColor() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getSpeedInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getXInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getYInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getWidthInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getHeightInput() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public String getSelectedShapeName() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }

  @Override
  public int getSelectedKFTick() {
    throw new UnsupportedOperationException("This view does not support user input.");
  }
}
