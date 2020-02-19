package cs3500.animator.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import cs3500.animator.model.IShape;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.ShapeState;

/**
 * Holds and Draws the actual animation of IShapes. Holds IShapes as an Ordered Collection of
 * IShapes in order of what to draw. Shapes Passed in as an ArrayList which is ordered. ArrayList is
 * a Collection.
 */
public final class AnimationPanel extends javax.swing.JPanel {

  private int tick;
  private int finalTick;
  private Collection<IShape> shapes;
  private boolean isLooping;

  private IShape selectedShape;

  /**
   * Constructs the AnimationPanel. Package Private Because Used by Visual View only.
   */
  AnimationPanel() {
    super(new BorderLayout());
    tick = 0;
    finalTick = 0;
    isLooping = true;
    selectedShape = null;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    AffineTransform affineTransform = g2d.getTransform();

    ShapeState workingShapeState;
    // Run through shapes
    // Have each shape paint itself shapestate at the current tick
    for (IShape shape : shapes) {

      // If Shape Has No tick then don't draw it
      try {
        // set paint color for Graphics in Shape draw
        ((IShapeExtended) shape).draw((Graphics2D)g,this.tick);
      } catch (Exception e) {
        //If other IShapes have animations and the current shape is done -> Don't draw for this tick
      }
    }
  }

  /**
   * Draws a shape based on the given IShape and shapeState.
   *
   * @param g          the graphics object that will draw the shape
   * @param shape      the given shape
   * @param shapeState the given shapeState
   */
  private void draw(Graphics2D g, IShape shape, ShapeState shapeState) {
    Shape currShape;
    if (shape.getShapeType().equals("rectangle")) {
      currShape = new Rectangle2D.Float(shapeState.getxPosn(), shapeState.getyPosn(),
              shapeState.getWidth(), shapeState.getHeight());
    }
    else if (shape.getShapeType().equalsIgnoreCase("ellipse")) {
      currShape = new Ellipse2D.Float(shapeState.getxPosn(), shapeState.getyPosn(),
              shapeState.getWidth(), shapeState.getHeight());
    } else {
      throw new IllegalArgumentException("Shape not recognized");
    }
    if (this.selectedShape == null) {
      g.setPaint(shapeState.getColor());
    } else {
      if (shape.equals(this.selectedShape)) {
        g.setPaint(shapeState.getColor());
      } else {
        g.setPaint(new Color(100, 100, 100));
      }
    }
    g.fill(currShape);
  }

  /**
   * Gets the collection of IShapes from this panel.
   *
   * @return the collection of IShapes
   */
  public Collection<IShape> getShapes() {
    return this.shapes;
  }

  /**
   * Reads in the ORDERED Collection of IShapes in Order to be drawn.
   *
   * @param shapes ordered collection of IShapes to be drawn
   */
  protected void setShapes(Collection<IShape> shapes) {
    this.shapes = shapes;
  }

  /**
   * Returns the tick this panel is currently on.
   *
   * @return the panel's tick
   */
  public int getTick() {
    return this.tick;
  }

  /**
   * Sets the tick for this panel.
   *
   * @param tick the desired tick
   */
  public void setTick(int tick) {
    this.tick = tick;
  }

  /**
   * Returns the last tick of this panel (the last tick of the animation.
   *
   * @return the last tick
   */
  public int getFinalTick() {
    return this.finalTick;
  }

  /**
   * Sets the final tick which is the last tick of the animation that the IShapes have commands
   * for.
   *
   * @param finalTick last tick
   */
  void setFinalTick(int finalTick) {
    if (finalTick >= 0) {
      this.finalTick = finalTick;
    } else {
      throw new IllegalArgumentException("Final Tick of Animation cannot be negative");
    }
  }

  /**
   * Get a sorted list of shape identifiers from this panel.
   *
   * @return the sorted list of shape identifiers
   */
  public Collection<String> getShapeIdentifers() {
    ArrayList<String> output = new ArrayList<>();
    for (IShape shape : this.shapes) {
      output.add(shape.getUserShapeName());
    }
    Collections.sort(output);
    return new ArrayList<>(output);
  }

  /**
   * Toggles the loop status of this panel between true and false.
   */
  public void toggleLooping() {
    if (this.isLooping) {
      this.isLooping = false;
    } else {
      this.isLooping = true;
    }
  }

  /**
   * Sets a new selected shape based on which shape the user selects in the EditorView.
   *
   * @param o the shape object that was selected
   */
  public void setSelectedShape(Object o) {
    this.selectedShape = (IShape) o;
  }

  /**
   * Updates this panel's collection of IShapes.
   *
   * @param values the updated collection of IShapes
   */
  public void updateShapes(Collection<IShape> values) {
    this.shapes = values;
  }

  /**
   * Increments the panel's tick when called if it hasn't reached the end of the animation.
   * Otherwise, it resets to 0 if it is looping or returns if not.
   */
  public void incrementTick() {
    if (tick < finalTick) {
      tick++;
    } else {
      if (isLooping) {
        tick = 0;
      } else {
        return;
      }
    }
  }
}