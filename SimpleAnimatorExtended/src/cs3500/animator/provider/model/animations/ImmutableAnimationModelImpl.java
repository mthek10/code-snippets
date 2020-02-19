package cs3500.animator.provider.model.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.model.IShape;
import cs3500.animator.provider.model.shapes.Shape;

/**
 * Provider Hybrid Code with Our Model.
 */

public class ImmutableAnimationModelImpl implements AnimationModel {

  // Not Read Only because we want to extend this class with our mutable model
  cs3500.animator.model.AnimationModel ourModel;

  public ImmutableAnimationModelImpl(cs3500.animator.model.AnimationModel ourModel) {
    this.ourModel = ourModel;
  }

  @Override
  public void addShape(Shape s) {
    throw new UnsupportedOperationException("This model is immutable");
  }

  @Override
  public void removeShape(String name) {
    throw new UnsupportedOperationException("This model is immutable");
  }

  /**
   * Returns the KeySet as an ArrayList which is the user defined lists.
   *
   * @return list or shape names in order to draw
   */
  @Override
  public List<String> currentNames() {

    // Make a list of IShapes to be sorted in order
    ArrayList<IShape> iShapeArrayList = new ArrayList<>(this.ourModel.getShapes().values());
    Collections.sort(iShapeArrayList);

    ArrayList<String> stringArrayList = new ArrayList<>();

    // Adds the shape names in order
    for (IShape iShape : iShapeArrayList) {
      stringArrayList.add(iShape.getUserShapeName());
    }

    return stringArrayList;
  }

  @Override
  public Shape getShapeByName(String name) throws IllegalArgumentException {
    return this.ourModel.getShapes().get(name);
  }

  @Override
  public int getCanvasWidth() {
    return this.ourModel.getWindowWidth();
  }

  @Override
  public int getCanvasHeight() {
    return this.ourModel.getWindowHeight();
  }

  @Override
  public boolean isOver(int tick) {
    return tick >= this.ourModel.getFinalTick();
  }

  @Override
  public ImmutableAnimationModelImpl getImmutable() {
    return this;
  }


}
