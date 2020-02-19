package cs3500.animator.provider.model.shapes;

/**
 * The enum representing different outlines for shapes.
 */
public enum Outline {
  ELLIPSE("ellipse"), RECTANGLE("rectangle");

  private final String type;

  Outline(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return this.type;
  }
}
