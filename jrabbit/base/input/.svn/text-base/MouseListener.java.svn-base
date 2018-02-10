package org.jrabbit.base.input;

/*****************************************************************************
 * A MouseListener is an interface used to provide automatic notification of
 * Mouse events.
 * 
 * To receive notifications, add a listener to MouseHandler. If the object
 * receiving updates is no longer being used, remove it from the MouseHandler.
 * 
 * NOTE: A MouseListener does not receive updates about mouse movement. To
 * receive those, look at MouseMotionListener.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface MouseListener
{
	/*************************************************************************
	 * Notifies the MouseListener when a button is clicked.
	 * 
	 * @param button
	 *            The button being pressed.
	 *************************************************************************/
	public void buttonClicked(int button);

	/*************************************************************************
	 * Notifies the MouseListener when a button is released.
	 * 
	 * @param button
	 *            The button being released.
	 *************************************************************************/
	public void buttonReleased(int button);

	/*************************************************************************
	 * Notifies the MouseListener when the wheel is moved.
	 * 
	 * @param delta
	 *            How far the wheel has moved.
	 *************************************************************************/
	public void wheelMoved(int delta);
}
