package cs3500.animator.controller;

import java.awt.event.ActionListener;

import javax.swing.event.ChangeListener;

/**
 * An improved IActionListener interface to be more useful by taking role of all needed listeners.
 */
public interface IListener extends IActionListener, ActionListener, ChangeListener {

}
