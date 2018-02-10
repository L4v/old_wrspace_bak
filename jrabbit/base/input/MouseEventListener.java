package org.jrabbit.base.input;

/*****************************************************************************
 * A MouseEventListener is used if the user wants to directly access the data 
 * from LWJGL Mouse events. MouseListener and MousemotionListener intentionally 
 * simplify the API to make using particular functionality easier, so this 
 * provides a direct line to the LWJGL event data.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface MouseEventListener
{
	/*************************************************************************
	 * Processes the described LWJGL Mouse event.
	 * 
	 * @param button
	 * 			  The ID of the mouse button causing the event (if any).
	 * @param state
	 * 			  The state of the button that caused this event.
	 * @param x
	 * 			  The x-coordinate of the mouse.
	 * @param y
	 * 			  The y-coordinate of the mouse.
	 * @param dX
	 * 			  The shift in the x-coordinate of the mouse.
	 * @param dY
	 * 			  The shift in the y-coordinate of the mouse.
	 * @param dWheel
	 * 			  The amount that the mouse wheel has changed.
	 *************************************************************************/
	public void processMouseEvent(int button, boolean state, int x, int y, 
			int dX, int dY, int dWheel);
}