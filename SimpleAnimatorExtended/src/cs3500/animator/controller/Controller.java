package cs3500.animator.controller;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShape;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.ShapeState;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ModelBuilder;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewExtended;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;


/**
 * Implementation of the Controller interface which passes information from Read-Only model to an
 * IView. Also converts the Unordered Collection of Shapes from the Model into an Ordered Sorted
 * ArrayList in order of what to draw first.
 */
public class Controller implements IListener {

  private IView view;
  private AnimationModel model;

  /**
   * Constructs a Controller and Sets up the IView with the IShapes collection from the Model.
   *
   * @param m ReadOnly Model
   * @param v IView to display data
   */
  public Controller(AnimationModel m, IView v) {
    model = m;
    view = v;

    // Converts HashMap to an Ordered List
    ArrayList<IShape> shapeList = new ArrayList<IShape>(model.getShapes().values());
    Collections.sort(shapeList);
    view.setShapes(shapeList);

    if (!(view instanceof TextView)) {
      view.setViewBounds(m.getWindowX(), m.getWindowY(), m.getWindowWidth(), m.getWindowHeight());
    }

    if (view instanceof VisualView) {
      view.setListener(this);
      view.setFinalTick(model.getFinalTick());
      view.setViewBounds(m.getWindowX(), m.getWindowY(), m.getWindowWidth(), m.getWindowHeight());
    }

    if (view instanceof EditorView) {
      view.setShapeJList();
      view.setViewBounds(m.getWindowX(), m.getWindowY(), m.getWindowWidth(), m.getWindowHeight());
      view.setFinalTick(model.getFinalTick());
      view.setListener(this);
      view.setJListListeners();
    }


    view.display();
  }


  @Override
  public void action() {
    // Gets called at every tick
    // View's JPanel Impl keeps track off tick numbers
    // if action is getting called view is an IViewExtended
    // Use getTick to set the Scrubber

    view.repaintPanel();

    if (this.view.timerIsRunning()) {
      this.view.incrementTick();
    }

    ((IViewExtended)this.view).setScrubber(((IViewExtended)this.view).getTick());

  }


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Pause":
        this.view.setTimerPause();
        return;
      case "Start/Resume":
        this.view.setTimerResume();
        return;
      case "Rewind":
        this.view.setTick(0);
        ((IViewExtended)this.view).setScrubber(((IViewExtended)this.view).getTick());
        this.view.repaintPanel();
        this.view.setTimerPause();
        return;
      case "Speed up":
        this.view.changeSpeed((float) .5);
        return;
      case "Slow down":
        this.view.changeSpeed((float) 2);
        return;
      case "Clear":
        this.clear();
        return;
      case "Toggle Looping":
        this.view.toggleLooping();
        return;
      case "Add/Edit Keyframe":
        this.addEditKeyframe();
        return;
      case "Remove Keyframe":
        this.removeKeyframe();
        return;
      case "Add Shape":
        this.addShape();
        return;
      case "Remove Shape":
        this.removeShape();
        return;
      case "Load":
        this.loadFile();
        return;
      case "Save Text":
        this.saveToFile("Text");
        return;
      case "Save SVG":
        this.saveToFile("SVG");
        return;
      case "Edit Layer":
        this.editLayer();
        return;
      case "Remove Layer":
        this.removeLayer();
        return;
      default:
        return;
    }
  }

  /**
   * Removes a layer from the model by iterating through shapes and removing if on layer.
   */
  private void removeLayer() {

    // Collect names of all the shapes to be removed in the list
    ArrayList<String> shapesToRemove = new ArrayList<>();
    int layerWanted = ((IViewExtended)this.view).getLayerWantedText();

    // iterate through collection of shapes searching for layer
    for (IShape s : this.model.getShapes().values()) {
      if (((IShapeExtended)s).getShapeLayer() == layerWanted) {
        shapesToRemove.add(s.getUserShapeName());
      }
    }

    // remove all shapes in list
    for (String name : shapesToRemove) {
      this.model.removeShape(name);
    }

    // Reset View
    ArrayList<IShape> shapesDraw = new ArrayList<>(this.model.getShapes().values());
    Collections.sort(shapesDraw);
    this.view.updateShapes(shapesDraw);
    this.clear();
  }

  /**
   * Edits the layer of the user chosen shape.
   */
  private void editLayer() {

    // Edit Shape
    String shapeWanted = this.view.getShapeWantedText();
    int layerWanted = ((IViewExtended)view).getLayerWantedText();

    this.model.setLayer(shapeWanted, layerWanted);

    ArrayList<IShape> shapesDraw = new ArrayList<>(this.model.getShapes().values());
    Collections.sort(shapesDraw);

    this.view.updateShapes(shapesDraw);
    this.clear();
  }

  /**
   * Saves Current model to the type of file specified by typeOutput.
   *
   * @param typeOutput type of output
   */
  private void saveToFile(String typeOutput) {

    String fileName = this.view.getFileWantedText();
    Appendable out = null;

    File file = new File(fileName);
    try {
      out = new FileWriter(file);
    } catch (IOException e) {
      this.view.showErrorMessage("Cannot work with specified file");
    }


    IView viewWork;

    switch (typeOutput) {
      case "Text":
        viewWork = new TextView(out);
        break;
      case "SVG":
        viewWork = new SVGView(out);
        break;
      default:
        throw new IllegalArgumentException("Unrecognized Output Type");
    }

    ArrayList<IShape> shapesSort = new ArrayList<IShape>(this.model.getShapes().values());
    Collections.sort(shapesSort);
    viewWork.setShapes(shapesSort);
    viewWork.display();

  }


  /**
   * Loads a new model into the view from the file name given by the IView.
   */
  private void loadFile() {

    AnimationReader reader = new AnimationReader();
    AnimationBuilder builder = new ModelBuilder();
    Readable readable = null;

    File file = new File(this.view.getFileWantedText());

    try {
      readable = new FileReader(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    AnimationModel modelNew = (AnimationModel) AnimationReader.parseFile(readable, builder);

    ArrayList<IShape> shapesSort = new ArrayList<IShape>(modelNew.getShapes().values());
    Collections.sort(shapesSort);

    this.setNewModel(modelNew);
    this.view.setFinalTick(this.model.getFinalTick());
    this.view.updateShapes(shapesSort);
    this.view.setTick(0);
    this.view.repaintPanel();
  }

  /**
   * Sets the model to a new model when loading a new file.
   *
   * @param model the model that will be updated to this controller
   */
  private void setNewModel(AnimationModel model) {
    this.model = model;
  }

  /**
   * Removes the desired shape from this view.
   */
  private void removeShape() {
    if (this.model.getShapes().get(this.view.getShapeWantedText()) == null) {
      this.view.showErrorMessage("The shape to remove does not exist");
      return;
    }

    this.model.removeShape(this.view.getShapeWantedText());


    ArrayList<IShape> shapesSort = new ArrayList<>(this.model.getShapes().values());
    Collections.sort(shapesSort);
    this.view.updateShapes(shapesSort);
    // Added to ensure shape was not holding final tick then deleted
    this.view.setFinalTick(this.model.getFinalTick());
    this.clear();

  }

  /**
   * Adds a shape to the view.
   */
  private void addShape() {
    String shapeType = this.view.getShapeWantedType();
    String shapeName = this.view.getShapeWantedText();
    int layer = ((IViewExtended)this.view).getLayerWantedText();

    if (shapeName.equals("")) {
      this.view.showErrorMessage("The name of the shape to be added cannot be an empty string");
      return;
    }
    if (this.model.getShapes().get(shapeName) != null) {
      this.view.showErrorMessage("The shape to be added already exists");
      return;
    }

    this.model.addShape(shapeName, shapeType);

    if (this.model.getShapes().get(shapeName) instanceof IShapeExtended) {
      ((IShapeExtended) this.model.getShapes().get(shapeName)).setShapeLayer(layer);
    }
    // Sorted by drawing order
    ArrayList<IShape> shapesSort = new ArrayList<>(this.model.getShapes().values());
    Collections.sort(shapesSort);
    this.view.updateShapes(shapesSort);
    this.clear();
  }

  /**
   * Clears all the text fields, resets the selected shape and repaints the panel.
   */
  private void clear() {
    this.view.clearFields();
    this.view.setSelectedShape(null);
    this.view.repaintPanel();
  }

  /**
   * Remove a certain keyframe from the view.
   */
  private void removeKeyframe() {
    String shapeWanted = this.view.getShapeWantedText();
    int timeWanted = 0;
    if (shapeWanted.equals("")) {
      this.view.showErrorMessage("The shape input cannot be empty");
      return;
    }
    //if the shape does not exist then show an error message and return
    if (this.model.getShapes().get(shapeWanted) == null) {
      this.view.showErrorMessage("There desired shape to add/edit " +
              "this keyframe to doesn't exist");
    }
    try {
      timeWanted = this.view.getTimeWantedText();
      if (timeWanted < 0) {
        this.view.showErrorMessage("The time input cannot be negative");
        return;
      }

    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The time input must be an integer");
    }
    try {
      this.model.removeKeyframe(shapeWanted, timeWanted);
      //check if removing the final tick and reset
      if (timeWanted == this.view.getFinalTick()) {
        // reset final tick
        this.view.setFinalTick(this.model.getFinalTick());
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage(e.getMessage());
    }
    this.clear();
  }

  /**
   * Add or edit a desired keyframe at a certain time.
   */
  private void addEditKeyframe() {
    String shapeWanted = this.view.getShapeWantedText();
    int timeWanted = 0;
    float xWanted = 0;
    float yWanted = 0;
    float widthWanted = 0;
    float heightWanted = 0;
    int redWanted = 0;
    int greenWanted = 0;
    int blueWanted = 0;
    double thetaWanted = 0.0;

    if (shapeWanted.equals("")) {
      this.view.showErrorMessage("The shape input cannot be empty");
      return;
    }
    //if the shape does not exist then show an error message and return
    if (this.model.getShapes().get(shapeWanted) == null) {
      this.view.showErrorMessage("There desired shape to " +
              "add/edit this keyframe to doesn't exist");
    }
    try {
      timeWanted = this.view.getTimeWantedText();
      //show a popup error and return if the time is negative
      if (timeWanted < 0) {
        this.view.showErrorMessage("The time input cannot be negative");
        return;
      }

      //check if adding to the end of animation to extend slider
      if (timeWanted > this.view.getFinalTick()) {
        // reset final tick
        this.view.setFinalTick(timeWanted);
      }

    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The time input must be an integer");
      return;
    }
    try {
      xWanted = this.view.getXWantedText();
      //show a popup error and return if the x position is negative
      if (xWanted < 0) {
        this.view.showErrorMessage("The x position must be at least 0");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The x position input must be a float");
      return;
    }
    try {
      yWanted = this.view.getYWantedText();
      //show a popup error and return if the y position is negative
      if (yWanted < 0) {
        this.view.showErrorMessage("The y position must be at least 0");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The y position input must be a float");
      return;
    }
    try {
      widthWanted = this.view.getWidthWantedText();
      //show an error message and return if the width is not positive
      if (widthWanted <= 0) {
        this.view.showErrorMessage("The width input must be positive");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The width input must be a float");
      return;
    }
    try {
      heightWanted = this.view.getHeightWantedText();
      //show an error message and return if the height is not positive
      if (heightWanted <= 0) {
        this.view.showErrorMessage("The height must be positive");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The height input must be a float");
      return;
    }
    try {
      redWanted = this.view.getRedWantedText();
      //show an error message an return if not 0 <= red <= 255
      if (redWanted < 0 || redWanted > 255) {
        this.view.showErrorMessage("The red input must be between 0 and 255 inclusive");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The red input must be an integer");
      return;
    }
    try {
      greenWanted = this.view.getGreenWantedText();
      //show an error message and return if not 0 <= green <= 255
      if (greenWanted < 0 || greenWanted > 255) {
        this.view.showErrorMessage("The green input must be between 0 and 255 inclusive");
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The green input must be an integer");
      return;
    }
    try {
      blueWanted = this.view.getBlueWantedText();
      //show an error message and return if not 0 <= blue <= 255
      if (blueWanted < 0 || blueWanted > 255) {
        this.view.showErrorMessage("The blue input must be between 0 and 255 inclusive");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The blue input must be an integer");
      return;
    }
    try {
      thetaWanted = ((IViewExtended)this.view).getThetaWantedText();
      //show an error message and return if not 0 <= blue <= 255
      if (thetaWanted < 0 || thetaWanted > 360) {
        this.view.showErrorMessage("The angle input must be between 0 and 360 inclusive");
        return;
      }
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage("The angle input must be a double");
      return;
    }

    ShapeState inputShapeState = new ShapeState(xWanted, yWanted, widthWanted, heightWanted,
            redWanted, greenWanted, blueWanted, thetaWanted);
    //if the shape has no keyframes then make one with a command time to itself
    if (this.model.getShapes().get(shapeWanted).getTimes().size() == 0) {
      this.model.addCommandTime(shapeWanted, new CommandTimes(timeWanted, timeWanted));
      this.model.addShapeState(timeWanted, shapeWanted, inputShapeState);
      return;
    }

    //if the shape already has a keyframe at this time, just edit it
    for (CommandTimes ct : this.model.getShapes().get(shapeWanted).getTimes()) {
      if (ct.getStartTime() == timeWanted || ct.getEndTime() == timeWanted) {
        this.model.editShapeState(shapeWanted, timeWanted, inputShapeState);
        this.view.updateShapes(this.model.getShapes().values());
        this.view.repaintPanel();
        return;
      }
    }

    //otherwise it's an add
    //we have to change the panel's final tick if the added keyframe is after the current last tick
    if (timeWanted > this.view.getFinalTick()) {
      this.view.setFinalTick(timeWanted);
    }
    this.model.addKeyframe(shapeWanted, timeWanted, inputShapeState);
    this.view.updateShapes(this.model.getShapes().values());
    this.view.repaintPanel();

    this.view.updateCommandTimes(this.model.getShapes().get(shapeWanted));

  }

  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider) e.getSource();
    int tickWanted = source.getValue();
    // this ensures that when the controller sets the scrubber pointer to value this doesn't trigger
    if (source.getValueIsAdjusting()) {
      // Checks to ensure view correctly provides a tick within range of animation
      if (tickWanted >= 0 && tickWanted <= model.getFinalTick()) {
        this.view.setTick(tickWanted);
        this.view.repaintPanel();
        // Pause animation after scrubbing
        this.view.setTimerPause();
      }
    }
  }
}