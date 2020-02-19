package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Shape;

//import model.animations.ImmutableAnimationModelImpl;
//import model.shapes.Shape;

/**
 * Represents the region where the shapes of an animation are drawn in {@link VisualView} and {@link
 * provider.view.EditorView}.
 */
@SuppressWarnings("serial")
public class AnimationPanel extends JPanel {

  private ImmutableAnimationModelImpl model;

  private int tick;

  private int canvasWidth;
  private int canvasHeight;

  /**
   * Creates an AnimationPanel, using a Map of shapes and a given tick speed.
   *
   * @param model an immutable version of the animation model
   */
  public AnimationPanel(ImmutableAnimationModelImpl model, int canvasWidth, int canvasHeight) {
    super();
    this.setBackground(Color.WHITE);

    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;

    this.model = model;

    this.tick = 1;
  }

  /**
   * Repaints this panel and increments the tick by 1.
   */
  public void refresh() {
    this.repaint();
    this.tick++;
  }

  /**
   * Resets this animation's tick back to its first tickS.
   */
  public void resetTick() {
    this.tick = 1;
  }

  /**
   * Gets the current tick of this animation.
   *
   * @return the current tick
   */
  public int getTick() {
    return this.tick;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.canvasWidth, this.canvasHeight);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    for (String shape : this.model.currentNames()) {
      Shape curr = model.getShapeByName(shape);
      if (curr.isVisible(this.tick)) {
        g2d.setColor(curr.getColor(this.tick));
        switch (curr.getOutline()) {
          case ELLIPSE:
            g2d.fillOval(curr.getPosn(this.tick).getX(), curr.getPosn(this.tick).getY(),
                    curr.getSize(this.tick).getX(), curr.getSize(this.tick).getY());
            break;
          case RECTANGLE:
            g2d.fillRect(curr.getPosn(this.tick).getX(), curr.getPosn(this.tick).getY(),
                    curr.getSize(this.tick).getX(), curr.getSize(this.tick).getY());
            break;
          default:
            throw new IllegalStateException("Invalid shape type found.");
        }
      }
    }

    g2d.drawString(Integer.toString(this.tick), this.getWidth() - 20, this.getHeight() - 20);
  }
}
