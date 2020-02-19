package cs3500.animator.provider.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.model.animations.AnimationModel;
import cs3500.animator.provider.model.animations.ImmutableAnimationModelImpl;
import cs3500.animator.provider.model.shapes.Outline;
import cs3500.animator.provider.util.TickShape;

/**
 * <p>
 * A view for {@link AnimationModel} similar to {@link VisualView}.
 * </p>
 * <p>
 * Adds functionality that allows the user to:
 * <ul>
 * <li>Add/Remove shapes</li>
 * <li>Control playback</li>
 * <li>Add/Remove/Modify keyframes of a shape</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class EditorView extends VisualView {

  private JTextArea info;

  private JSpinner speed;
  private JSpinner x;
  private JSpinner y;
  private JSpinner width;
  private JSpinner height;

  private JLabel color;

  private JComboBox<Outline> outline;
  private JTextField newShapeName;

  private JList<String> shapeInput;
  private JList<TickShape> keyFrames;

  private JPanel controlPanel;
  private JPanel infoPanel;

  /**
   * Creates an EditorView with the given list of shapes, and canvas size.
   *
   * @param model        an immutable version of the animation model
   * @param canvasWidth  the width of the canvas
   * @param canvasHeight the height of the canvas
   */
  public EditorView(ImmutableAnimationModelImpl model, int canvasWidth, int canvasHeight) {
    super(model, canvasWidth, canvasHeight);

    JTextArea instructions = new JTextArea();
    instructions.setEditable(false);
    instructions.setText("Instructions for Use:\n" + "P = toggles play pause\n"
            + "B = rewinds back to the start\n" + "L = toggles looping\n" + "S = sets speed\n"
            + "C = opens a pop-up to select a new color\n"
            + "A = adds a new shape at the current tick with the selected initial values\n"
            + "R = removes the selected shape\n"
            + "+ (without shift) = adds a key frame for the selected shape at the current tick\n"
            + "- = removes the selected key frame\n" + "U = updates the selected key frame\n\n"
            + "The fields at the bottom are used to input the fields of both:\n"
            + "   - The initial state of new shapes\n"
            + "   - The data of a new key frame for a shape\n"
            + "The tick of a new shape and a new key frame is the current t" +
            "ick of the animation.\n\n"
            + "The 2 selection lists at the bottom are for selecting shapes a" +
            "nd their key frames.\n\n"
            + "The key frame list shows the tick of each key frame, \n"
            + "and is automatically updated when a different shape is selected.");

    info = new JTextArea();
    info.setEditable(false);
    info.setBorder(BorderFactory.createTitledBorder("User Info"));

    infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());
    infoPanel.add(instructions, BorderLayout.NORTH);
    infoPanel.add(info, BorderLayout.SOUTH);
    this.add(infoPanel, BorderLayout.EAST);

    controlPanel = new JPanel();
    controlPanel.setLayout(new BorderLayout());
    this.add(controlPanel, BorderLayout.SOUTH);

    speed = new JSpinner();
    speed.setBorder(BorderFactory.createTitledBorder("Speed"));
    speed.setValue(1);
    ((JSpinner.DefaultEditor) speed.getEditor()).getTextField().setColumns(5);
    x = new JSpinner();
    x.setBorder(BorderFactory.createTitledBorder("X"));
    ((JSpinner.DefaultEditor) x.getEditor()).getTextField().setColumns(5);
    y = new JSpinner();
    y.setBorder(BorderFactory.createTitledBorder("Y"));
    ((JSpinner.DefaultEditor) y.getEditor()).getTextField().setColumns(5);
    width = new JSpinner();
    width.setBorder(BorderFactory.createTitledBorder("Width"));
    ((JSpinner.DefaultEditor) width.getEditor()).getTextField().setColumns(5);
    height = new JSpinner();
    height.setBorder(BorderFactory.createTitledBorder("Height"));
    ((JSpinner.DefaultEditor) height.getEditor()).getTextField().setColumns(5);

    color = new JLabel(" ");
    color.setPreferredSize(new Dimension(50, 50));
    color.setOpaque(true);
    color.setBackground(Color.GREEN);
    color.setBorder(BorderFactory.createTitledBorder("Color"));

    newShapeName = new JTextField();
    newShapeName.setColumns(8);
    newShapeName.setBorder(BorderFactory.createTitledBorder("Name"));

    outline = new JComboBox<>();
    outline.setBorder(BorderFactory.createTitledBorder("Outline"));

    JPanel newShapePanel = new JPanel();
    newShapePanel.add(newShapeName);
    newShapePanel.add(outline);
    newShapePanel.setBorder(BorderFactory.createTitledBorder("New Shape"));

    JPanel fieldPanel = new JPanel();
    fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    fieldPanel.add(speed);
    fieldPanel.add(x);
    fieldPanel.add(y);
    fieldPanel.add(width);
    fieldPanel.add(height);
    fieldPanel.add(color);
    fieldPanel.add(newShapePanel);
    controlPanel.add(fieldPanel, BorderLayout.NORTH);

    shapeInput = new JList<>();
    shapeInput.setBorder(BorderFactory.createTitledBorder("Shapes"));
    shapeInput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shapeInput.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    shapeInput.setVisibleRowCount(1);
    keyFrames = new JList<>();
    keyFrames.setBorder(BorderFactory.createTitledBorder("Key Frames"));
    keyFrames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyFrames.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    keyFrames.setVisibleRowCount(1);

    JScrollPane shapeInputScroll = new JScrollPane(shapeInput);
    shapeInputScroll.setMaximumSize(new Dimension(1000, 300));
    JScrollPane keyFramesScroll = new JScrollPane(keyFrames);
    // keyFramesScroll.setPreferredSize(new Dimension(1000, 300));

    JPanel listPanel = new JPanel();
    listPanel.setLayout(new BorderLayout());
    listPanel.add(shapeInputScroll, BorderLayout.NORTH);
    listPanel.add(keyFramesScroll, BorderLayout.SOUTH);
    controlPanel.add(listPanel, BorderLayout.SOUTH);


    this.pack();
  }

  @Override
  public Dimension getPreferredSize() {
    double baseWidth = this.panel.getPreferredSize().getWidth();
    double baseHeight = this.panel.getPreferredSize().getHeight();
    return new Dimension((int) baseWidth + infoPanel.getWidth(),
            (int) baseHeight + controlPanel.getHeight() * 2);
  }

  @Override
  public void addKeyAction(KeyStroke key, Action action) {
    this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, key.getKeyCode());
    this.panel.getActionMap().put(key.getKeyCode(), action);
  }

  @Override
  public void setInfoText(String info) {
    this.info.setText(info);
  }

  @Override
  public void setShapeInput(List<String> shapeNames) {
    this.shapeInput.setListData(shapeNames.toArray(new String[shapeNames.size()]));
    this.shapeInput.setSelectedIndex(0);
    this.shapeInput.setVisibleRowCount(shapeNames.size() / 15);
  }

  @Override
  public void setShapeInputListener(ListSelectionListener listener) {
    this.shapeInput.addListSelectionListener(listener);
  }

  @Override
  public void setKeyFrames(List<TickShape> keyFrames) {
    this.keyFrames.setListData(keyFrames.toArray(new TickShape[keyFrames.size()]));
    this.keyFrames.setSelectedIndex(0);
    this.keyFrames.setVisibleRowCount(keyFrames.size() / 15);
  }

  @Override
  public void setColor() {
    Color color = JColorChooser.showDialog(this, "Choose a color", this.color.getBackground());
    this.color.setBackground(color);
  }

  @Override
  public int getSpeedInput() {
    // Converts from ticks / s -> ms / tick
    return 1000 / (int) this.speed.getValue();
  }

  @Override
  public String getNewShapeName() {
    return this.newShapeName.getText();
  }

  @Override
  public Outline getOutlineInput() {
    return (Outline) this.outline.getSelectedItem();
  }

  @Override
  public void setOutlineInput(List<Outline> outlines) {
    this.outline.removeAllItems();
    for (Outline outline : outlines) {
      this.outline.addItem(outline);
    }
  }

  @Override
  public int getXInput() {
    return (int) this.x.getValue();
  }

  @Override
  public int getYInput() {
    return (int) this.y.getValue();
  }

  @Override
  public int getWidthInput() {
    return (int) this.width.getValue();
  }

  @Override
  public int getHeightInput() {
    return (int) this.height.getValue();
  }

  @Override
  public Color getColorInput() {
    return this.color.getBackground();
  }

  @Override
  public String getSelectedShapeName() {
    return this.shapeInput.getSelectedValue();
  }

  @Override
  public int getSelectedKFTick() {
    return this.keyFrames.getSelectedValue().getTick();
  }

}
