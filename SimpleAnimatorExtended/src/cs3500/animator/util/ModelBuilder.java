package cs3500.animator.util;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Command;
import cs3500.animator.model.ShapeState;

/**
 * Builder Class for easy construction of AnimationModel.
 */
public class ModelBuilder implements AnimationBuilder<AnimationModel> {

  private int x;
  private int y;
  private int width;
  private int height;
  private HashMap<String, String> shapesToAdd;
  private HashMap<String, ArrayList<Command>> commandsToAddPerShape;
  private ArrayList<String> shapeBuildOrder;
  // Added for layering
  private HashMap<String, Integer> shapesLayers;

  /**
   * Creates a new ModelBuilder object where the hashmap of shapes, commands per shape and arraylis
   * of the build order are all instantiated and empty.
   */
  public ModelBuilder() {
    this.shapesToAdd = new HashMap<>();
    this.commandsToAddPerShape = new HashMap<>();
    this.shapeBuildOrder = new ArrayList<>();
    this.shapesLayers = new HashMap<>();
  }

  @Override
  public AnimationModel build() {
    return new AnimationModelImpl(this.shapesToAdd, this.commandsToAddPerShape,
            this.shapeBuildOrder, this.x, this.y, this.width, this.height, this.shapesLayers);
  }

  @Override
  public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    return this;
  }

  @Override
  public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
    // If not defined use 0 as default layer
    this.declareShape(name, type, 0);
    return this;
  }

  @Override
  public AnimationBuilder<AnimationModel> declareShape(String name, String type, int layer) {
    if (!this.shapesToAdd.keySet().contains(name)) {
      this.shapesToAdd.put(name, type);
      this.shapeBuildOrder.add(name);
      this.shapesLayers.put(name,layer);
    }

    return this;
  }

  @Override
  public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                    int h1, int r1, int g1, int b1,
                                                    int t2, int x2, int y2, int w2,
                                                    int h2, int r2, int g2, int b2) {
    // Add a motion with theta set to 0 default
    this.addMotion(name,t1,x1,y1,w1,h1,r1,g1,b1,0,t2,x2,y2,w2,h2,r2,g2,b2,0);
    return this;
  }

  @Override
  public AnimationBuilder<AnimationModel> addMotion(String name,
                                                    int t1, int x1, int y1,
                                                    int w1, int h1, int r1, int g1,
                                                    int b1, int theta1, int t2, int x2,
                                                    int y2, int w2, int h2, int r2, int g2,
                                                    int b2, int theta2) {
    // Theta included
    ShapeState s1 = new ShapeState(x1, y1, w1, h1, r1, g1, b1, theta1);
    ShapeState s2 = new ShapeState(x2, y2, w2, h2, r2, g2, b2, theta2);
    if (this.commandsToAddPerShape.get(name) == null) {
      this.commandsToAddPerShape.put(name, new ArrayList<Command>());
    }
    this.commandsToAddPerShape.get(name).add(new Command(t1, s1, t2, s2));
    return this;
  }

  //we don't need this since addShapeState is called within addCommand and is redundant
  @Override
  public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y,
                                                      int w, int h, int r, int g, int b) {
    // Add a key frame with theta set to 0 default
    this.addKeyframe(name,t,x,y,w,h,r,g,b,0);
    return this;
  }

  @Override
  public AnimationBuilder<AnimationModel> addKeyframe(String name,
                                                      int t, int x, int y,
                                                      int w, int h, int r, int g,
                                                      int b, int theta) {
    return this;
  }

}
