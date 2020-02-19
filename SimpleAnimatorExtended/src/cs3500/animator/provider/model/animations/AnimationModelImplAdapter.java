package cs3500.animator.provider.model.animations;

import cs3500.animator.model.ShapeState;
import cs3500.animator.provider.model.shapes.Shape;

/**
 * Adapted Model to work with Provider's View.
 */
public class AnimationModelImplAdapter extends ImmutableAnimationModelImpl
        implements AnimationModel {

  public AnimationModelImplAdapter(cs3500.animator.model.AnimationModel ourModel) {
    super(ourModel);
  }

  @Override
  public void addShape(Shape s) {
    addShape(s.getName(), s.getClass().toGenericString());
  }


  public void addShape(String shapeName, String shapeType) {
    ourModel.addShape(shapeName, shapeType);
  }

  public void addKeyFrame(String shapeWanted, int time, int x, int y, int width,
                          int height, int r, int g, int b) {
    ShapeState state = new ShapeState(x, y, width, height, r, g, b);
    ourModel.addKeyframe(shapeWanted, time, state);
  }

  public void removeKeyFrame(String shapeWanted, int tick) {
    this.ourModel.removeKeyframe(shapeWanted, tick);
  }


  @Override
  public void removeShape(String name) {
    // Basic removal from our model
    this.ourModel.removeShape(name);
  }

  @Override
  public ImmutableAnimationModelImpl getImmutable() {
    return new ImmutableAnimationModelImpl(this.ourModel);
  }

}
