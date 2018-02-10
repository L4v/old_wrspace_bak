package org.jrabbit.base.input;

/*****************************************************************************
 * A KeyboardListener is an interface used to provide automatic notification of
 * Keyboard events.
 * 
 * To receive notifications, add a listener to KeyboardHandler. If the object
 * receiving updates is no longer being used, remove it from the
 * KeyboardHandler.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface KeyboardListener
{
	/*************************************************************************
	 * Notifies the KeyboardListener when a key is initially pressed.
	 * 
	 * @param key
	 *            The ID of the key being pressed (as in Keyboard).
	 *************************************************************************/
	public void keyPressed(int key);

	/*************************************************************************
	 * Notifies the KeyboardListener when a key is released.
	 * 
	 * @param key
	 *            The ID of the key being released (as in Keyboard).
	 *************************************************************************/
	public void keyReleased(int key);

	/*************************************************************************
	 * Notifies the KeyboardListener when a character is typed.
	 * 
	 * @param character
	 *            The character that was typed.
	 *************************************************************************/
	public void characterEntered(char character);
}
