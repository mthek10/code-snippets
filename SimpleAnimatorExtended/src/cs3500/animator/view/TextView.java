package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import cs3500.animator.controller.IActionListener;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShape;
import cs3500.animator.model.ShapeState;

/**
 * Displays the Animation from a Readable in a Textual Format out to an Appendable.
 */
public final class TextView implements IView {

  private Appendable out;
  private ArrayList<IShape> shapes;

  /**
   * Constructs a TextView from a Readable.
   *
   * @param out location to save ata
   */
  public TextView(Appendable out) {
    this.out = out;
  }


  @Override
  public void setListener(IActionListener listener) {
    throw new IllegalArgumentException("Method Not Applicable to TextView");
  }

  @Override
  public void display() {
    String stringBuilder = "";

    for (IShape currShape : this.shapes) {

      //output a string with the shape's from and to state
      Collections.sort(currShape.getTimes());

      //the shape creation line
      stringBuilder += (currShape.toString() + "\n");

      //for each time in the list, get the shapeState and output a line of its values
      for (CommandTimes time : currShape.getTimes()) {
        ShapeState startState = currShape.getShapeStates().get(time.getStartTime());
        ShapeState endState = currShape.getShapeStates().get(time.getEndTime());
        stringBuilder += ("motion " + currShape.getUserShapeName() + " " + time.getStartTime() +
                " " + startState.toString() + "    " + time.getEndTime() + " " +
                endState.toString() + "\n");
      }

      stringBuilder += ("\n");


    }

    if (stringBuilder.length() == 0) {
      try {
        this.out.append(stringBuilder);
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot append to Given appendable");
      }
    } else {

      try {
        this.out.append(stringBuilder.substring(0, stringBuilder.length() - 2));
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot append to Given appendable");
      }
    }

  }

  @Override
  public void repaintPanel() {
    throw new IllegalArgumentException("Method Not Applicable to TextView");
  }

  @Override
  public void setShapes(ArrayList<IShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setViewBounds(int windowX, int windowY, int windowWidth, int windowHeight) {
    throw new IllegalArgumentException("Method Not Applicable to TextView");
  }

  @Override
  public void setDelay(int delay) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("That method is not allowed in this class");
  }

  @Override
  public void setViewButtonListeners(ActionListener e) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setTimerPause() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setTimerResume() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setTick(int i) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void changeSpeed(float v) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setShapeJList() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setJListListeners() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void clearFields() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void toggleLooping() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setSelectedShape(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
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
  public void updateShapes(Collection<IShape> values) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public int getFinalTick() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setFinalTick(int finalTick) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("That method is not allowed in this class");
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
  public boolean timerIsRunning() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void incrementTick() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public void setOutput(Appendable out) {
    this.out = out;
  }

  @Override
  public String getFileWantedText() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }
}
