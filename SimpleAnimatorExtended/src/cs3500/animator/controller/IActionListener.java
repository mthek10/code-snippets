package cs3500.animator.controller;

/**
 * Acts as the Controller for IView's VisualView specifically to trigger action on ticks.
 */
public interface IActionListener {

  /**
   * Gets called when an action is Triggered. Decides what action to perform.
   */
  void action();


}
