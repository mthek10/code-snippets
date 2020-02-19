package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.animator.controller.IActionListener;
import cs3500.animator.controller.IListener;
import cs3500.animator.model.IShape;

/**
 * Class to display an animation visually using Java Swing. Future add parameter in Constructor to
 * set different Delays.
 */

public final class VisualView extends JFrame implements IViewExtended {
  private AnimationPanel panel;
  private Timer timer;
  private int delay; // 1000 = 1 seconds


  /**
   * Constructs a VisualView by setting window bounds and adding an AnimationPanel. The setBounds
   * method required in the IView interface is in the JFrame class which VisualView extends.
   */
  public VisualView(int delay, int windowWidth, int windowHeight, int windowX, int windowY) {

    this.delay = delay;

    this.timer = new Timer(0, null);
    timer.stop();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(windowWidth, windowHeight);
    this.setLocation(windowX, windowY);

    // Create JPanel and Add to this (JFrame)
    panel = new AnimationPanel();

    // Add scroll bar features
    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
    panel.setPreferredSize(new Dimension(windowWidth, windowHeight));
    this.add(scrollPane);

    pack();
  }

  @Override
  public void display() {
    setVisible(true);
  }

  @Override
  public void setDelay(int delay) throws IllegalArgumentException {
    if (delay >= 1) {
      this.delay = delay;
    } else {
      throw new IllegalArgumentException("Delay cannot be negative");
    }
  }

  @Override
  public void setViewButtonListeners(ActionListener e) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this class");
  }

  //does nothing
  @Override
  public void setViewBounds(int windowX, int windowY, int windowWidth, int windowHeight) {
    return;
  }

  @Override
  public void setListener(IActionListener listener) {
    // Allows us to use lambda to trigger the actionPerformed method in our controller passed in
    ActionListener listen = e -> {

      listener.action();

    };


    // set Timer with the ActionListener created above
    timer = new Timer(delay, listen);

    timer.start();
  }

  @Override
  public void setListener(IListener listener) {
    setListener((IActionListener)listener);
  }


  @Override
  public void repaintPanel() {
    this.panel.repaint();
  }

  public AnimationPanel getPanel() {
    return this.panel;
  }

  public void setTick(int tick) {
    this.panel.setTick(tick);
  }

  public void setTimerPause() {
    this.timer.stop();
  }

  public void setTimerResume() {
    this.timer.start();
  }

  public void changeSpeed(float speedFactor) {
    this.timer.setDelay((int) Math.min(1000, Math.max(1, this.timer.getDelay() * speedFactor)));
  }

  @Override
  public void setShapeJList() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this class");
  }

  @Override
  public void setJListListeners() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void clearFields() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this class");
  }

  @Override
  public void toggleLooping() {
    this.panel.toggleLooping();
  }

  @Override
  public void setSelectedShape(Object o) {
    this.panel.setSelectedShape(o);
  }

  @Override
  public String getShapeWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getTimeWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public float getXWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public float getYWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public float getWidthWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public float getHeightWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getRedWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getGreenWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getBlueWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void showErrorMessage(String s) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void updateShapes(Collection<IShape> values) {
    this.panel.updateShapes(values);
  }

  @Override
  public int getFinalTick() {
    return this.panel.getFinalTick();
  }

  @Override
  public void setFinalTick(int finalTick) throws IllegalArgumentException {
    this.panel.setFinalTick(finalTick);
  }

  @Override
  public String getShapeWantedType() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void updateCommandTimes(IShape currShape) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public boolean timerIsRunning() {
    return this.timer.isRunning();
  }

  @Override
  public void incrementTick() {
    this.panel.incrementTick();
  }

  @Override
  public void setOutput(Appendable out) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public String getFileWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  public Collection<IShape> getShapes() {
    return this.panel.getShapes();
  }

  @Override
  public void setShapes(ArrayList<IShape> shapes) {

    Collections.sort(shapes);

    // Reading shapes in to AnimationPanel
    panel.setShapes(shapes);
  }

  @Override
  public int getTick() {
    return this.panel.getTick();
  }

  @Override
  public void setScrubber(int tick) {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }


  @Override
  public double getThetaWantedText() {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getLayerWantedText() {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setLayerWantedText(int layerWantedText) {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setShapeWantedText(String shapeWantedText) {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }
}
