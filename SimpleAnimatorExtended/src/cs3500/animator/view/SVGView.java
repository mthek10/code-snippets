package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.sun.javafx.binding.StringFormatter;

import cs3500.animator.controller.IActionListener;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShape;
import cs3500.animator.model.ShapeState;

/**
 * An IView that creates a SVG output to the appendable specified.
 */
public final class SVGView implements IView {

  private Appendable out;
  private int windowWidth;
  private int windowHeight;
  private ArrayList<IShape> shapes;


  /**
   * Constructs SVG View.
   * @param out Appendable
   */
  public SVGView(Appendable out) {
    this.out = out;
  }

  /**
   * Creates the svg statement shape by shape and appends to the Appendable.
   */
  private void createSVG() throws IOException {


    String headerTemplate = "<svg width=\"%d\" height=\"%d\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">\n";
    String svgHeader =
            StringFormatter.format(headerTemplate,
                    this.windowWidth, this.windowHeight).getValue();

    out.append(svgHeader);

    for (IShape currShape : this.shapes) {

      //the initial statement for the creation of a shape
      out.append(this.getInitialShape(currShape) + "\n");

      //add the visibility parameters to the output
      out.append(this.getSVGVisibility(currShape) + "\n");

      //add each animation statement for the shape
      for (CommandTimes ct : currShape.getTimes()) {
        out.append(this.getSVGCommand(ct.getStartTime(), ct.getEndTime(), currShape) + "\n");

        // Check if rotation command is needed
        if (currShape.getShapeStates().get(ct.getStartTime()).getTheta() !=
                currShape.getShapeStates().get(ct.getEndTime()).getTheta()) {
          out.append(this.getSVGRotationCommand(ct.getStartTime(), ct.getEndTime(), currShape)
                  + "\n");
        }
      }

      out.append(this.endShapeCommand(currShape) + "\n\n");

    }

    out.append("</svg>");
  }

  @Override
  public void setListener(IActionListener listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("That method is not allowed in this class");
  }

  @Override
  public void display() {

    try {
      createSVG();
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot Append to Given Appendable");
    }

  }

  @Override
  public void repaintPanel() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("That method is not allowed in this class");
  }

  @Override
  public void setShapes(ArrayList<IShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setViewBounds(int windowX, int windowY, int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
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

  /**
   * Creates a string representing the information of a shape in SVG format.
   *
   * @param shape the desired shape
   * @return a String in SVG syntax representing info about the shape
   */
  public String getInitialShape(IShape shape) {

    String shapeType = shape.getShapeType();

    String shapeID = shape.getUserShapeName();
    ShapeState initialState = shape.getShapeStates().get(shape.getTimes().get(0).getStartTime());

    // Used because width and height are in radius which is half for ellipse
    String widthConvert;
    String heightConvert;

    if (shape.getSVGAttribute("width").equalsIgnoreCase("rx")) {
      widthConvert = "" + initialState.getWidth() / 2;
    } else {
      widthConvert = "" + initialState.getWidth();
    }

    if (shape.getSVGAttribute("height").equalsIgnoreCase("ry")) {
      heightConvert = "" + initialState.getHeight() / 2;
    } else {
      heightConvert = "" + initialState.getHeight();
    }

    return "  <" + shape.getSVGIdentifier() + " id=\"" + shapeID + "\" " +
            shape.getSVGAttribute("x") + "=\"" +
            initialState.getxPosn() + "\" " + shape.getSVGAttribute("y") + "=\"" +
            initialState.getyPosn() + "\" " + shape.getSVGAttribute("width") + "=\"" +
            widthConvert +
            "\" " + shape.getSVGAttribute("height") + "=\"" +
            heightConvert + "\" visibility=\"hidden\" fill=\"rgb(" +
            initialState.getRed() + "," +
            initialState.getGreen() + "," + initialState.getBlue() + ")\"  >";
  }

  /**
   * Creates a closing tag for the shape in SVG format.
   *
   * @param shape the desired shape
   * @return a string representing the shapes ending tag
   */
  public String endShapeCommand(IShape shape) {
    return "  </" + shape.getSVGIdentifier() + ">";
  }


  /**
   * Constructs a String in SVG format about the visibility of the given shape.
   *
   * @param shape the desired shape
   * @return a string representing the visibility of the shape
   */
  public String getSVGVisibility(IShape shape) {
    return "    <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\"" +
            shape.getTimes().get(0).getStartTime() + "s\" dur=\"1\" fill=\"freeze\" />";
  }

  /**
   * Creates a String representing the SVG syntax for a motion command of a shape.
   *
   * @param startTime the start time of the motion
   * @param endTime   the end time of the motion
   * @param shape     the desired shape for creating the SVG style motion string
   * @return a String representing the motion of the shape in an SVG format
   */
  public String getSVGCommand(int startTime, int endTime, IShape shape) {
    ShapeState startState = shape.getShapeStates().get(startTime);
    ShapeState endState = shape.getShapeStates().get(endTime);

    StringBuilder output = new StringBuilder();

    if (startState.getxPosn() != endState.getxPosn()) {
      output.append(this.buildSVGCommand(shape.getSVGAttribute("x"), "XML",
              Integer.toString(startTime), Integer.toString(endTime - startTime),
              Float.toString(startState.getxPosn()), Float.toString(endState.getxPosn())));
    }
    if (startState.getyPosn() != endState.getyPosn()) {
      output.append(this.buildSVGCommand(shape.getSVGAttribute("y"), "XML",
              Integer.toString(startTime), Integer.toString(endTime - startTime),
              Float.toString(startState.getyPosn()), Float.toString(endState.getyPosn())));
    }
    if (startState.getWidth() != endState.getWidth()) {
      output.append(this.buildSVGCommand(shape.getSVGAttribute("width"), "XML",
              Integer.toString(startTime), Integer.toString(endTime - startTime),
              Float.toString(startState.getWidth()), Float.toString(endState.getWidth())));
    }
    if (startState.getHeight() != endState.getHeight()) {
      output.append(this.buildSVGCommand(shape.getSVGAttribute("height"), "XML",
              Integer.toString(startTime), Integer.toString(endTime - startTime),
              Float.toString(startState.getHeight()), Float.toString(endState.getHeight())));
    }
    if (startState.getRed() != endState.getRed() || startState.getGreen() != endState.getGreen() ||
            startState.getBlue() != endState.getBlue()) {
      output.append(this.buildSVGCommand("fill", "CSS",
              Integer.toString(startTime), Integer.toString(endTime - startTime),
              "rgb(" + startState.getRed() + "," + startState.getGreen() + "," +
                      startState.getBlue() + ")",
              "rgb(" + endState.getRed() + "," + endState.getGreen() + "," +
                      endState.getBlue() + ")"));
    }

    return output.toString();
  }

  /**
   * Builds an SVG command based on the given attributes.
   *
   * @param attributeName the attribute name
   * @param attributeType the attribute type
   * @param startTime     the start time of the command
   * @param duration      the duration of the command
   * @param fromValue     the starting value of the beginning of the command
   * @param toValue       the ending value of the ending of the command
   * @return a string representing a shape's command in SVG format
   */
  private String buildSVGCommand(String attributeName, String attributeType, String startTime,
                                 String duration, String fromValue, String toValue) {
    return "    <animate attributeName=\"" + attributeName + "\" attributeType=\"" +
            attributeType + "\" begin=\"" + startTime + "s\" dur=\"" +
            duration + "s\" fill=\"freeze\" from=\"" + fromValue + "\" to=\"" + toValue + "\" />";
  }

  /**
   * Handles rotation SVG commands.
   * @param startTime start time
   * @param endTime end time
   * @param currShape current shape
   * @return SVG command for applicable rotation
   */
  private String getSVGRotationCommand(int startTime, int endTime, IShape currShape) {
    String rotationCommand = "";

    // Check if there is rotation
    if (currShape.getShapeStates().get(startTime).getTheta() != currShape.getShapeStates().get(
            endTime).getTheta()) {

      double theta1 = currShape.getShapeStates().get(startTime).getTheta();
      double theta2 = currShape.getShapeStates().get(endTime).getTheta();
      float x1;
      float y1;
      float x2;
      float y2;
      // If Rectangle has to rotate around center not upper left corner
      if (currShape.getShapeType().equalsIgnoreCase("rectangle")) {
        x1 = currShape.getShapeStates().get(startTime).getxPosn()
                + (currShape.getShapeStates().get(startTime).getWidth() / 2);
        y1 = currShape.getShapeStates().get(startTime).getyPosn()
                + (currShape.getShapeStates().get(startTime).getHeight() / 2);
        x2 = currShape.getShapeStates().get(endTime).getxPosn()
                + (currShape.getShapeStates().get(endTime).getWidth() / 2);
        y2 = currShape.getShapeStates().get(endTime).getyPosn()
                + (currShape.getShapeStates().get(endTime).getHeight() / 2);
      } else {
        x1 = currShape.getShapeStates().get(startTime).getxPosn();
        y1 = currShape.getShapeStates().get(startTime).getyPosn();
        x2 = currShape.getShapeStates().get(endTime).getxPosn();
        y2 = currShape.getShapeStates().get(endTime).getyPosn();
      }
      int dur = endTime - startTime;

      rotationCommand = StringFormatter.format(" <animateTransform attributeName=\"transform\" " +
              "attributeType=\"XML\" type=\"rotate\" from=\"" + theta1
              + " " + x1 + " " + y1 + "\" to=\"" +
              theta2 + " " + x2 + " " + y2 + "\" " +
              "dur=\"" + dur + "s\" begin=\"" + startTime + "s\"/>").getValue();

    }

    // If no rotation return empty string
    return rotationCommand;
  }
}
