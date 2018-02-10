package org.jrabbit.base.input;

/*****************************************************************************
 * A KeyEventListener is used if the user wants to directly access the data from
 * LWJGL Keyboard events. KeyboardListener intentionally simplifies the API to
 * make using particular functionality easier.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface KeyEventListener
{
	/*************************************************************************
	 * Processes the described LWJGL Keyboard event.
	 * 
	 * @param key
	 * 			  The ID of the Key that caused this event.
	 * @param state
	 * 			  True if the key was pressed, false otherwise.
	 * @param keyChar
	 * 			  The character described by the key.
	 * @param time
	 * 			  The time at which the Key event occurred (in nanoseconds).
	 *************************************************************************/
	public void processKeyEvent(int key, boolean state, char keyChar, long time);
}