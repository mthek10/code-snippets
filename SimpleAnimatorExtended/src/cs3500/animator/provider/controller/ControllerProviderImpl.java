package cs3500.animator.provider.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.model.animations.AnimationModelImplAdapter;
import cs3500.animator.provider.model.shapes.Outline;
import cs3500.animator.provider.view.AnimationView;

/**
 * A controller used to control an AnimationView type from provider with hybrid model.
 */
public class ControllerProviderImpl implements AnimationController, ListSelectionListener {

  AnimationModelImplAdapter animationModel;
  AnimationView animationView;
  Timer timer;
  boolean looping = false;

  /**
   * Constructs a Controller with the hybrid model + provider view and maps the provider view keys.
   * @param animationModel model hyrid
   * @param animationView provider view
   */
  public ControllerProviderImpl(AnimationModelImplAdapter animationModel,
                                AnimationView animationView) {
    this.animationModel = animationModel;
    this.animationView = animationView;


    /*
      Map Key Strokes to Functionality
     */

    // Using Method Reference and actionFactory to map key stroke P to toggle play/pause
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("P"),
            actionFactory(this::togglePlay)
    );


    // Using Lambda and actionFactory to Map Keystroke B to an Action for Restarting
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("B"),
            actionFactory(() -> {
              animationView.reset();
              animationView.refresh();
            })
    );

    // Using Method Reference and actionFactory to map key stroke S to set Speed
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("L"),
            actionFactory(this::toggleLoop)
    );

    // Using Lambda and actionFactory to map key stroke S to set Speed
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("S"),
            actionFactory(() -> {
              setSpeed(animationView.getSpeedInput());
            })
    );

    // Using Method Reference and actionFactory to map key stroke C to Choosing Color
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("C"),
            actionFactory(animationView::setColor)
    );

    // Using Lambda and actionFactory to Map Keystroke R to an Action for Removing Shape
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("R"), actionFactory(() -> {
      animationModel.removeShape(animationView.getSelectedShapeName());
      animationView.setShapeInput(animationModel.currentNames());
      animationView.setKeyFrames(new ArrayList<>());
      animationView.refresh();
    })
    );

    // Using Lambda and actionFactory to Map Keystroke A to an Action for Adding Shape and KeyFrame
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("A"),
            actionFactory(() -> {
              String shapeType = animationView.getOutlineInput().toString();
              String shapeName = animationView.getNewShapeName();
              int time = animationView.getTick();
              int x = animationView.getXInput();
              int y = animationView.getYInput();
              int w = animationView.getWidthInput();
              int h = animationView.getHeightInput();
              int r = animationView.getColorInput().getRed();
              int g = animationView.getColorInput().getGreen();
              int b = animationView.getColorInput().getBlue();

              // Add Shape
              animationModel.addShape(shapeName, shapeType);

              // Add KeyFrame
              animationModel.addKeyFrame(shapeName, time, x, y, w, h, r, g, b);

              animationView.setShapeInput(animationModel.currentNames());
              // sets Key Frames without showing duplicates
              this.animationView.setKeyFrames(this.animationModel.getShapeByName(
                      this.animationView.getSelectedShapeName()).getKeyFrames());

              animationView.refresh();
            })
    );

    // Using Lambda and actionFactory to Map Keystroke = to an Action for Adding KeyFrame
    this.animationView.addKeyAction(KeyStroke.getKeyStroke('='),
            actionFactory(() -> {
              animationModel.addKeyFrame(animationView.getSelectedShapeName(),
                      animationView.getTick(),
                      animationView.getXInput(),
                      animationView.getYInput(),
                      animationView.getWidthInput(),
                      animationView.getHeightInput(),
                      animationView.getColorInput().getRed(),
                      animationView.getColorInput().getGreen(),
                      animationView.getColorInput().getBlue());

              // Update view
              animationView.setKeyFrames(
                      animationModel.getShapeByName(
                              animationView.getSelectedShapeName()).getKeyFrames());
              animationView.refresh();
            })
    );

    // Using Lambda and actionFactory to Map Keystroke - to an Action for Removing KeyFrame
    this.animationView.addKeyAction(KeyStroke.getKeyStroke('-'),
            actionFactory(() -> {
              // Remove Key Frame From Model
              animationModel.removeKeyFrame(animationView.getSelectedShapeName(),
                      animationView.getSelectedKFTick());
              // Update View
              animationView.setKeyFrames(
                      animationModel.getShapeByName(
                              animationView.getSelectedShapeName()).getKeyFrames());
              animationView.refresh();


            })
    );

    // Using Lambda and actionFactory to Map Keystroke U to Updating Selected Key Frame
    this.animationView.addKeyAction(KeyStroke.getKeyStroke("U"),
            actionFactory(() -> {
              // Remove Key Frame From Model
              animationModel.removeKeyFrame(animationView.getSelectedShapeName(),
                      animationView.getSelectedKFTick());

              // Add New Key Frame To Model with the same key frame tick
              animationModel.addKeyFrame(animationView.getSelectedShapeName(),
                      animationView.getSelectedKFTick(),
                      animationView.getXInput(),
                      animationView.getYInput(),
                      animationView.getWidthInput(),
                      animationView.getHeightInput(),
                      animationView.getColorInput().getRed(),
                      animationView.getColorInput().getGreen(),
                      animationView.getColorInput().getBlue());

              // Update View
              animationView.setKeyFrames(
                      animationModel.getShapeByName(
                              animationView.getSelectedShapeName()).getKeyFrames());
              animationView.refresh();


            })
    );

    // set Shape Input
    this.animationView.setShapeInput(this.animationModel.currentNames());

    // set Shape Input Listener
    this.animationView.setShapeInputListener(this);

    // set Outline Input
    ArrayList<Outline> outlineArrayList = new ArrayList<Outline>();
    outlineArrayList.addAll(Arrays.asList(Outline.values()));
    this.animationView.setOutlineInput(outlineArrayList);

  }

  @Override
  public void run() {

    animationView.display();

    // TIMER TO REFRESH DISPLAY
    timer = new Timer(10, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (animationModel.isOver(animationView.getTick())) {
          if (looping) {
            animationView.reset();
            animationView.refresh();
          } else {
            timer.stop();
          }
        } else {
          // Animation Still Running
          animationView.refresh();
        }
      }
    });
    timer.start();


  }

  @Override
  public void togglePlay() {
    if (timer.isRunning()) {
      timer.stop();
    } else {
      timer.start();
    }

  }

  @Override
  public void toggleLoop() {
    if (looping) {
      looping = false;
    } else {
      looping = true;
    }
  }

  @Override
  public boolean isRunning() {
    return timer.isRunning();
  }

  @Override
  public int getSpeed() {
    return timer.getDelay();
  }

  @Override
  public void setSpeed(int rate) {
    timer.setDelay(rate);
  }

  @Override
  public boolean isLooping() {
    return looping;
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // Controller Acts as our List Selection Listener
    try {
      this.animationView.setKeyFrames(this.animationModel.getShapeByName(
              this.animationView.getSelectedShapeName()).getKeyFrames());
    } catch (NullPointerException ex) {
      // Case if adjusting
      this.animationView.setKeyFrames(new ArrayList<>());
    }

  }


  /**
   * Returns an Action with the actionPerformed overriden with the parameter.
   *
   * @param actionRespondMethod Runnable to use in actionPerformed
   * @return Action with overriden actionPerformed
   */
  private Action actionFactory(Runnable actionRespondMethod) {
    return new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {
        // Necessary
      }

      @Override
      public boolean isEnabled() {
        return true;
      }

      @Override
      public void setEnabled(boolean b) {
        // Necessary
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {
        // Necessary
      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {
        // Necessary
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        actionRespondMethod.run();
      }
    };
  }

}
