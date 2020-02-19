package cs3500.animator.provider.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.model.animations.AnimationModel;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Outline;
import cs3500.animator.provider.util.TickShape;

/**
 * A view for {@link AnimationModel} that displays the animation as a series of images in a window
 * using the Java Swing library.
 */
public class VisualView extends JFrame implements AnimationView {

  AnimationPanel panel;

  /**
   * Creates a VisualView with the given list of shapes, and canvas size.
   *
   * @param model        an immutable version of the animation model
   * @param canvasWidth  the width of the canvas
   * @param canvasHeight the height of the canvas
   */
  public VisualView(ImmutableAnimationModelImpl model, int canvasWidth, int canvasHeight) {
    super();

    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    this.panel = new AnimationPanel(model, canvasWidth, canvasHeight);

    JScrollPane scrollPane = new JScrollPane(panel);

    this.add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Sets this {@link JFrame} to be visible.
   */
  @Override
  public void display() {
    this.pack();
    this.setVisible(true);
  }

  /**
   * Opens an extra window showing the given error message.
   *
   * @param error the error message
   */
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Refreshes this frame's main panel to show the next tick.
   */
  @Override
  public void refresh() {
    this.panel.refresh();
  }

  @Override
  public void reset() {
    this.panel.resetTick();
  }

  @Override
  public int getTick() {
    return this.panel.getTick();
  }

  @Override
  public void addKeyAction(KeyStroke key, Action action) {
    // There is no keyboard input to take for this basic graphical view.
  }

  @Override
  public void setInfoText(String info) {
    // There is no place to show user info in this basic graphical view.
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
