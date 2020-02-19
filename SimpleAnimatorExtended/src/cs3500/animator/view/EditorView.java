package cs3500.animator.view;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.BorderFactory;

import cs3500.animator.controller.IActionListener;
import cs3500.animator.controller.IListener;
import cs3500.animator.model.CommandTimes;
import cs3500.animator.model.IShape;
import cs3500.animator.model.IShapeExtended;
import cs3500.animator.model.ShapeState;

/**
 * A view to edit animations in GUI which handles IShapes.
 */
public class EditorView extends JFrame implements IViewExtended, ListSelectionListener {
  private IShape selectedShape;

  private VisualView view;

  //buttons to change what the view does
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton rewindButton;
  private JButton speedUpButton;
  private JButton slowDownButton;

  //popup message box for user errors
  private JOptionPane errorMessage;

  //looping stuff
  private JLabel loopStatus;
  private JButton toggleLoop;

  private JLabel display;
  private JList<String> shapes;
  private JList<Integer> commandTimes;
  private JScrollPane scrollShapes;

  //the DefaultListModel's that update the contents of the JLists
  private DefaultListModel<String> jListShapes;
  private DefaultListModel<Integer> jListShapeCommandTimes;

  //editor JLabels and text fields
  private JTextField shapeWantedText;
  //Time text field
  private JTextField timeWantedText;
  //X Position text field
  private JTextField xWantedText;
  //Y Position text field
  private JTextField yWantedText;
  //Width text field
  private JTextField widthWantedText;
  //Height text field
  private JTextField heightWantedText;
  //Red text field
  private JTextField redWantedText;
  //Green text field
  private JTextField greenWantedText;
  //Blue text field
  private JTextField blueWantedText;
  // Theta Field
  private JTextField thetaWantedText;
  // Layer Field
  private JTextField layerWantedText;

  //shape type button
  private JRadioButton newShapeRectangle;
  private JRadioButton newShapeEllipse;
  //buttons for adding/removing Keyframes/Shapes + clearing the text fields to reset the animation
  private JButton addShape;
  private JButton removeShape;
  private JButton addEdit;
  private JButton remove;
  private JButton clear;
  private JTextField fileWantedText;
  private JButton loadButton;
  private JButton saveTextButton;
  private JButton saveSVGButton;
  //slider used for scrubbing through animation
  private JSlider scrubber;
  private JButton editLayer;
  private JButton removeLayer;

  /**
   * Creates an EditorView object which allows the user to see the VisualView it contains and make
   * edits to it.
   *
   * @param view the VisualView that this object contains
   */
  public EditorView(VisualView view) {
    super();
    this.view = view;
    this.initLayout();
    this.view.setBounds(this.getBounds());
    this.view.setTimerPause();
  }

  /**
   * Initializes Layout of Editor View.
   */
  private void initLayout() {
    this.setTitle("Animation Editor");
    //this.setSize(1000, 1000);
    this.setMinimumSize(new Dimension(2300, 1200));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //set the selected shape and time to default values
    this.selectedShape = null;

    this.jListShapes = new DefaultListModel<>();
    this.jListShapeCommandTimes = new DefaultListModel<>();

    this.errorMessage = new JOptionPane();

    this.setLayout(new FlowLayout());

    // For AnimationPanel and Slide
    JPanel animationPanel = new JPanel();
    animationPanel.setLayout(new BoxLayout(animationPanel,BoxLayout.Y_AXIS));
    animationPanel.setMinimumSize(new Dimension(400, 400));
    animationPanel.setMaximumSize(new Dimension(400, 400));
    JPanel animation = this.view.getPanel();
    animation.setBorder(BorderFactory.createLineBorder(Color.BLACK,5,false));
    animationPanel.add(animation);

    this.scrubber = new JSlider();
    animationPanel.add(scrubber);
    // Minimum can be set because always 0;
    this.scrubber.setMinimum(0);
    this.scrubber.setPaintTicks(true);
    this.scrubber.setPaintLabels(true);

    this.add(animationPanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setMinimumSize(new Dimension(400, 400));
    buttonPanel.setMaximumSize(new Dimension(400, 400));
    buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED,3,true));
    this.add(buttonPanel);

    JPanel shapeListPanel = new JPanel();
    shapeListPanel.setLayout(new BoxLayout(shapeListPanel, BoxLayout.Y_AXIS));
    JLabel shapeListLabel = new JLabel("Shapes");
    shapeListPanel.add(shapeListLabel);
    this.shapes = new JList<>(this.jListShapes);
    this.scrollShapes = new JScrollPane(this.shapes);
    shapeListPanel.add(this.scrollShapes);

    this.add(shapeListPanel);

    JPanel shapeTimesPanel = new JPanel();
    shapeTimesPanel.setLayout(new BoxLayout(shapeTimesPanel, BoxLayout.Y_AXIS));
    JLabel shapeTimesLabel = new JLabel("Keyframes");
    shapeTimesPanel.add(shapeTimesLabel);
    this.commandTimes = new JList<>(this.jListShapeCommandTimes);
    JScrollPane scrollCommandTimes = new JScrollPane(this.commandTimes);
    shapeTimesPanel.add(scrollCommandTimes);
    this.add(shapeTimesPanel);

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new GridLayout(0, 2));
    editPanel.setPreferredSize(new Dimension(400, 500));
    this.add(editPanel);

    // Create Panel Grouping of Shape and Layer
    JPanel shapeAndLayerPanel = new JPanel();
    shapeAndLayerPanel.setLayout(new FlowLayout());

    //add the shape panel to the edit panel
    JPanel shapePanel = new JPanel();
    shapePanel.setLayout(new FlowLayout());
    JLabel shapeWanted = new JLabel("Shape:");
    shapePanel.add(shapeWanted);
    this.shapeWantedText = new JTextField(10);
    shapePanel.add(this.shapeWantedText);
    shapeAndLayerPanel.add(shapePanel);

    //add the layer panel to the edit panel
    JPanel layerPanel = new JPanel();
    layerPanel.setLayout(new FlowLayout());
    JLabel layerWanted = new JLabel("Layer:");
    layerPanel.add(layerWanted);
    this.layerWantedText = new JTextField(10);
    layerPanel.add(this.layerWantedText);
    shapeAndLayerPanel.add(layerPanel);

    // Visually Group Panel together
    shapeAndLayerPanel.setBorder(BorderFactory.
            createLineBorder(new Color(0,0,0),3));

    //add the time panel to the edit panel
    JPanel timePanel = new JPanel();
    timePanel.setLayout(new FlowLayout());
    JLabel timeWanted = new JLabel("Time:");
    timePanel.add(timeWanted);
    this.timeWantedText = new JTextField(10);
    timePanel.add(this.timeWantedText);
    editPanel.add(timePanel);
    //add the x panel to the edit panel
    JPanel xPanel = new JPanel();
    xPanel.setLayout(new FlowLayout());
    JLabel xWanted = new JLabel("X Position:");
    xPanel.add(xWanted);
    this.xWantedText = new JTextField(10);
    xPanel.add(this.xWantedText);
    editPanel.add(xPanel);
    //add the y panel to the edit panel
    JPanel yPanel = new JPanel();
    yPanel.setLayout(new FlowLayout());
    JLabel yWanted = new JLabel("Y Position:");
    yPanel.add(yWanted);
    this.yWantedText = new JTextField(10);
    yPanel.add(this.yWantedText);
    editPanel.add(yPanel);
    //add the width panel to the edit panel
    JPanel widthPanel = new JPanel();
    widthPanel.setLayout(new FlowLayout());
    JLabel widthWanted = new JLabel("Width:");
    widthPanel.add(widthWanted);
    this.widthWantedText = new JTextField(10);
    widthPanel.add(this.widthWantedText);
    editPanel.add(widthPanel);
    //add the height panel to the edit panel
    JPanel heightPanel = new JPanel();
    heightPanel.setLayout(new FlowLayout());
    JLabel heightWanted = new JLabel("Height:");
    heightPanel.add(heightWanted);
    this.heightWantedText = new JTextField(10);
    heightPanel.add(this.heightWantedText);
    editPanel.add(heightPanel);
    //add the red panel to the edit panel
    JPanel redPanel = new JPanel();
    redPanel.setLayout(new FlowLayout());
    JLabel redWanted = new JLabel("Red:");
    redPanel.add(redWanted);
    this.redWantedText = new JTextField(10);
    redPanel.add(this.redWantedText);
    editPanel.add(redPanel);
    //add the green panel to the edit panel
    JPanel greenPanel = new JPanel();
    greenPanel.setLayout(new FlowLayout());
    JLabel greenWanted = new JLabel("Green:");
    greenPanel.add(greenWanted);
    this.greenWantedText = new JTextField(10);
    greenPanel.add(this.greenWantedText);
    editPanel.add(greenPanel);
    //add the blue panel to the edit panel
    JPanel bluePanel = new JPanel();
    bluePanel.setLayout(new FlowLayout());
    JLabel blueWanted = new JLabel("Blue:");
    bluePanel.add(blueWanted);
    this.blueWantedText = new JTextField(10);
    bluePanel.add(this.blueWantedText);
    editPanel.add(bluePanel);
    //add the theta panel to the edit panel
    JPanel thetaPanel = new JPanel();
    thetaPanel.setLayout(new FlowLayout());
    JLabel thetaWanted = new JLabel("Angle:");
    thetaPanel.add(thetaWanted);
    this.thetaWantedText = new JTextField(10);
    thetaPanel.add(this.thetaWantedText);
    editPanel.add(thetaPanel);


    //add the radiopanel and buttons
    JPanel newShapeSelection = new JPanel();
    newShapeSelection.setLayout(new BoxLayout(newShapeSelection, BoxLayout.Y_AXIS));
    newShapeSelection.add(shapeAndLayerPanel);
    JLabel newShapeType = new JLabel("Desired Shape Type");
    newShapeSelection.add(newShapeType);
    this.newShapeRectangle = new JRadioButton("rectangle");
    this.newShapeRectangle.setActionCommand(this.newShapeRectangle.getText());
    this.newShapeRectangle.setSelected(true);
    newShapeSelection.add(this.newShapeRectangle);
    this.newShapeEllipse = new JRadioButton("ellipse");
    this.newShapeEllipse.setActionCommand(this.newShapeEllipse.getText());
    newShapeSelection.add(this.newShapeEllipse);
    ButtonGroup newShapeButtonGroup = new ButtonGroup();
    newShapeButtonGroup.add(this.newShapeEllipse);
    newShapeButtonGroup.add(this.newShapeRectangle);
    this.addShape = new JButton("Add Shape");
    this.removeShape = new JButton("Remove Shape");
    this.editLayer = new JButton("Edit Layer");
    this.removeLayer = new JButton("Remove Layer");
    newShapeSelection.add(this.addShape);
    newShapeSelection.add(this.removeShape);
    newShapeSelection.add(this.editLayer);
    newShapeSelection.add(this.removeLayer);
    this.add(newShapeSelection);

    //add the edit button panel to the edit panel
    JPanel editButtonPanel = new JPanel();
    editButtonPanel.setLayout(new FlowLayout());
    this.addEdit = new JButton("Add/Edit Keyframe");
    this.remove = new JButton("Remove Keyframe");
    this.clear = new JButton("Clear");
    editButtonPanel.add(this.addEdit);
    editButtonPanel.add(this.remove);
    editButtonPanel.add(this.clear);
    editPanel.add(editButtonPanel);


    this.resumeButton = new JButton("Start/Resume");
    buttonPanel.add(this.resumeButton);


    this.pauseButton = new JButton("Pause");
    buttonPanel.add(this.pauseButton);

    //the rewind button
    this.rewindButton = new JButton("Rewind");
    buttonPanel.add(this.rewindButton);

    //speed up button
    this.speedUpButton = new JButton("Speed up");
    buttonPanel.add(this.speedUpButton);

    //slow down button
    this.slowDownButton = new JButton("Slow down");
    buttonPanel.add(this.slowDownButton);

    this.loopStatus = new JLabel("Currently Looping");
    this.toggleLoop = new JButton("Toggle Looping");
    buttonPanel.add(this.loopStatus);
    buttonPanel.add(this.toggleLoop);

    JPanel loadPanel = new JPanel();
    JLabel fileEnterLabel = new JLabel("File:");
    this.loadButton = new JButton("Load");
    this.fileWantedText = new JTextField(10);
    this.saveTextButton = new JButton("Save Text");
    this.saveSVGButton = new JButton("Save SVG");
    loadPanel.add(fileEnterLabel);
    loadPanel.add(this.fileWantedText);
    loadPanel.add(loadButton);
    loadPanel.add(this.saveTextButton);
    loadPanel.add(this.saveSVGButton);
    this.add(loadPanel);
  }

  /**
   * Sets the view's listener to the given IActionListener.
   *
   * @param listener the given IActionListener
   */
  @Override
  public void setListener(IActionListener listener) {
    // Perform Checks if older code trying to pass in IListener
    if (listener instanceof IListener) {
      setListener((IListener) listener);
    } else {
      this.view.setListener(listener);
    }
  }

  @Override
  public void setListener(IListener listener) {
    this.view.setListener(listener);
    this.scrubber.addChangeListener((IListener)listener);
    this.scrubber.setMaximum(this.view.getFinalTick());

    // Can Also Set Button Listeners because IListener is an ActionListener
    setViewButtonListeners(listener);
  }

  @Override
  public double getThetaWantedText() {
    return Double.parseDouble(this.thetaWantedText.getText());
  }

  @Override
  public int getLayerWantedText() {
    return Integer.parseInt(this.layerWantedText.getText());
  }

  @Override
  public void setLayerWantedText(int layerWantedText) {
    this.layerWantedText.setText(Integer.toString(layerWantedText));
  }

  @Override
  public void setShapeWantedText(String shapeWantedText) {
    this.shapeWantedText.setText(shapeWantedText);
  }

  /**
   * Displays the view by making it visible.
   */
  @Override
  public void display() {
    this.setVisible(true);
  }

  /**
   * Repaints the panel by calling the same function on the view holding the panel.
   */
  @Override
  public void repaintPanel() {
    this.view.repaintPanel();
  }

  /**
   * Sets the visual view's list of shapes to the given list of shapes.
   *
   * @param shapes a List of IShapes to display
   */
  @Override
  public void setShapes(ArrayList<IShape> shapes) {
    this.view.setShapes(shapes);
  }

  /**
   * Sets the max bounds of the this view.
   *
   * @param windowX      x position of window
   * @param windowY      y position of window
   * @param windowWidth  width of window
   * @param windowHeight height of window
   */
  @Override
  public void setViewBounds(int windowX, int windowY, int windowWidth, int windowHeight) {
    this.view.setViewBounds(windowX, windowY, windowWidth, windowHeight);
  }

  /**
   * Sets the delay of the timer by calling the same method on the contained view.
   *
   * @param delay delay for Timer
   * @throws IllegalArgumentException if the delay is not positive
   */
  @Override
  public void setDelay(int delay) throws IllegalArgumentException {
    this.view.setDelay(delay);
  }

  /**
   * Sets the action listener for all the buttons to the given action listener.
   *
   * @param e the given action listener
   */
  public void setViewButtonListeners(ActionListener e) {
    this.resumeButton.addActionListener(e);
    this.rewindButton.addActionListener(e);
    this.pauseButton.addActionListener(e);
    this.speedUpButton.addActionListener(e);
    this.slowDownButton.addActionListener(e);
    this.addEdit.addActionListener(e);
    this.remove.addActionListener(e);
    this.clear.addActionListener(e);
    this.toggleLoop.addActionListener(e);
    this.addShape.addActionListener(e);
    this.removeShape.addActionListener(e);
    this.editLayer.addActionListener(e);
    this.removeLayer.addActionListener(e);
    this.loadButton.addActionListener(e);
    this.saveTextButton.addActionListener(e);
    this.saveSVGButton.addActionListener(e);
  }

  /**
   * Stops the timer and pauses the animation.
   */
  @Override
  public void setTimerPause() {
    this.view.setTimerPause();
  }

  /**
   * Starts the timer and resumes the animation.
   */
  @Override
  public void setTimerResume() {
    this.view.setTimerResume();
  }

  /**
   * Sets the current tick for the view.
   *
   * @param i the desired tick
   */
  @Override
  public void setTick(int i) {
    this.view.setTick(i);
  }

  /**
   * Changes the speed of the animation.
   *
   * @param v the desired speed
   */
  @Override
  public void changeSpeed(float v) {
    this.view.changeSpeed(v);
  }

  /**
   * Sets the scroll pane to the list of shape identifiers in this view.
   */
  public void setShapeJList() {

    // Zeros out list before updating
    this.jListShapes.clear();

    for (String s : view.getPanel().getShapeIdentifers()) {
      this.jListShapes.addElement(s);
    }
    this.scrollShapes.updateUI();
  }

  /**
   * Sets the JList listeners to this view.
   */
  @Override
  public void setJListListeners() {
    this.shapes.addListSelectionListener(this);
    this.commandTimes.addListSelectionListener(this);
  }

  /**
   * Clear the JList of command times so no times are showing in the ScrollPane.
   */
  private void clearCommandTimes() {
    this.jListShapeCommandTimes = new DefaultListModel<>();
    this.commandTimes.setModel(this.jListShapeCommandTimes);
    this.commandTimes.updateUI();
  }

  /**
   * Reset all of the text field boxes, reset the selected shape and reset the selected time.
   */
  @Override
  public void clearFields() {
    this.shapeWantedText.setText("");
    this.timeWantedText.setText("");
    this.xWantedText.setText("");
    this.yWantedText.setText("");
    this.widthWantedText.setText("");
    this.heightWantedText.setText("");
    this.redWantedText.setText("");
    this.greenWantedText.setText("");
    this.blueWantedText.setText("");
    this.thetaWantedText.setText("");
    this.layerWantedText.setText("");

    this.shapes.clearSelection();
    this.commandTimes.clearSelection();


    this.clearCommandTimes();

  }

  /**
   * Toggles the looping of this view.
   */
  @Override
  public void toggleLooping() {
    this.view.toggleLooping();
    if (this.loopStatus.getText().equals("Currently Looping")) {
      this.loopStatus.setText("Not Looping");
    } else {
      this.loopStatus.setText("Currently Looping");
    }
    this.loopStatus.updateUI();
  }

  /**
   * Set the selected shape based on what shape identifier was selected in the ScrollPane.
   *
   * @param o the shape that was selected
   */
  @Override
  public void setSelectedShape(Object o) {
    this.selectedShape = (IShape) o;
    this.view.setSelectedShape(o);
  }

  /**
   * Return the text input for the desired shape.
   *
   * @return the desired shape string
   */
  @Override
  public String getShapeWantedText() {
    return this.shapeWantedText.getText();
  }

  /**
   * Return the text input for the desired time.
   *
   * @return the desired time int
   */
  @Override
  public int getTimeWantedText() {
    return Integer.parseInt(this.timeWantedText.getText());
  }

  /**
   * Return the text input for the desired x position.
   *
   * @return the desired x position float
   */
  @Override
  public float getXWantedText() {
    return Float.parseFloat(this.xWantedText.getText());
  }

  /**
   * Return the text input for the desired y position.
   *
   * @return the desired y position float
   */
  @Override
  public float getYWantedText() {
    return Float.parseFloat(this.yWantedText.getText());
  }

  /**
   * Return the text input for the desired width.
   *
   * @return the desired width float
   */
  @Override
  public float getWidthWantedText() {
    return Float.parseFloat(this.widthWantedText.getText());
  }

  /**
   * Return the text input for the desired height.
   *
   * @return the desired height float
   */
  @Override
  public float getHeightWantedText() {
    return Float.parseFloat(this.heightWantedText.getText());
  }

  /**
   * Return the text input for the desired red value.
   *
   * @return the desired red value int
   */
  @Override
  public int getRedWantedText() {
    return Integer.parseInt(this.redWantedText.getText());
  }

  /**
   * Return the text input for the desired green value.
   *
   * @return the desired green value int
   */
  @Override
  public int getGreenWantedText() {
    return Integer.parseInt(this.greenWantedText.getText());
  }

  /**
   * Return the text input for the desired blue value.
   *
   * @return the desired blue value int
   */
  @Override
  public int getBlueWantedText() {
    return Integer.parseInt(this.blueWantedText.getText());
  }

  /**
   * Display an error message.
   *
   * @param s the error message to be displayed
   */
  @Override
  public void showErrorMessage(String s) {
    this.errorMessage.showMessageDialog(this, s);

  }

  /**
   * Updates this view's collection of shapes that exist in the animation.
   *
   * @param values the new collection of shapes
   */
  @Override
  public void updateShapes(Collection<IShape> values) {
    ArrayList<IShape> shapes = new ArrayList<>(values);
    Collections.sort(shapes);
    this.view.updateShapes(shapes);
    this.setShapeJList();
  }

  /**
   * Gets the final tick of the animation.
   *
   * @return the final tick of the animation
   */
  @Override
  public int getFinalTick() {
    return this.view.getFinalTick();
  }

  /**
   * Sets the final tick of the animation and adjusts the slider position.
   *
   * @param finalTick last tick
   * @throws IllegalArgumentException if the tick is not >0
   */
  @Override
  public void setFinalTick(int finalTick) throws IllegalArgumentException {
    this.view.setFinalTick(finalTick);
    this.scrubber.setMaximum(finalTick);
  }

  /**
   * When creating a new shape, gets the type of the desired shape to be created.
   *
   * @return a string representing the type of shape to be created
   */
  @Override
  public String getShapeWantedType() {
    if (this.newShapeRectangle.isSelected()) {
      return "rectangle";
    } else if (this.newShapeEllipse.isSelected()) {
      return "ellipse";
    }

    return "";
  }


  /**
   * Whenever a value is changed in either JList of shapes and times, call certain methods based on
   * which JList was just selected.
   *
   * @param e the list event that is triggered when something from either JList is selected
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting() && e.getSource().equals(this.shapes)) {
      //update the DefaultList for commandtimes
      IShape currShape = null;
      for (IShape s : this.view.getShapes()) {
        if (s.getUserShapeName() == this.shapes.getSelectedValue()) {
          currShape = s;
        }
      }
      //update the command times now that a shape is selected
      this.updateCommandTimes(currShape);

      //set the shape textField to this shape's identifier
      this.shapeWantedText.setText(currShape.getUserShapeName());
      this.selectedShape = currShape;

      if (this.selectedShape instanceof IShapeExtended) {
        this.layerWantedText.setText(
                Integer.toString(((IShapeExtended)this.selectedShape).getShapeLayer()));
      }

      this.view.setSelectedShape(this.selectedShape);
      this.repaintPanel();
    }

    if (e.getValueIsAdjusting() && e.getSource().equals(this.commandTimes)) {
      this.view.setTick(this.commandTimes.getSelectedValue());
      this.view.repaintPanel();
      this.view.setTimerPause();

      this.timeWantedText.setText(this.commandTimes.getSelectedValue().toString());
      this.setTextFields(this.commandTimes.getSelectedValue());
      //this.view.setSelectedShape(this.selectedShape);
    }
  }

  /**
   * Update the JList of times based on what shape is selected from the other JList.
   */
  @Override
  public void updateCommandTimes(IShape currShape) {
    DefaultListModel<Integer> newCommandTimes = new DefaultListModel<>();

    ArrayList<CommandTimes> times = currShape.getTimes();

    for (int i = 0; i < times.size(); i++) {
      //remove duplicate times since the initial time is from 1 to 1
      if (!newCommandTimes.contains(times.get(i).getStartTime())) {
        newCommandTimes.addElement(times.get(i).getStartTime());
      }
      if (!newCommandTimes.contains(times.get(i).getEndTime())) {
        newCommandTimes.addElement(times.get(i).getEndTime());
      }


    }
    this.jListShapeCommandTimes = newCommandTimes;
    this.commandTimes.setModel(this.jListShapeCommandTimes);
    this.commandTimes.updateUI();
  }

  /**
   * Checks if the view's timer is not paused.
   *
   * @return true if the timer is not paused
   */
  @Override
  public boolean timerIsRunning() {
    return this.view.timerIsRunning();
  }

  /**
   * Increments the tick of the view by 1.
   */
  @Override
  public void incrementTick() {
    this.view.incrementTick();


  }

  @Override
  public void setOutput(Appendable out) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This method is not allowed in this view");
  }

  @Override
  public String getFileWantedText() {
    return this.fileWantedText.getText();
  }

  /**
   * When a time is picked from the JList, fill the textField boxes with relevant info about the
   * shape at that time.
   *
   * @param time the desired time for the shape's state
   */
  private void setTextFields(int time) {
    //obtain the shape's state at the given time
    ShapeState selectedState = this.selectedShape.getShapeStates().get(time);
    this.xWantedText.setText(Float.toString(selectedState.getxPosn()));
    this.yWantedText.setText(Float.toString(selectedState.getyPosn()));
    this.widthWantedText.setText(Float.toString(selectedState.getWidth()));
    this.heightWantedText.setText(Float.toString(selectedState.getHeight()));
    this.redWantedText.setText(Integer.toString(selectedState.getRed()));
    this.greenWantedText.setText(Integer.toString(selectedState.getGreen()));
    this.blueWantedText.setText(Integer.toString(selectedState.getBlue()));
    this.thetaWantedText.setText(Double.toString(selectedState.getTheta()));
    if (this.selectedShape instanceof IShapeExtended) {
      this.layerWantedText.setText(
              Integer.toString(((IShapeExtended)this.selectedShape).getShapeLayer()));
    }
  }

  @Override
  public int getTick() {
    return this.view.getTick();
  }

  @Override
  public void setScrubber(int tick) {
    this.scrubber.setValue(tick);
  }

}
