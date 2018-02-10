package org.jrabbit.base.graphics.types;

/*****************************************************************************
 * A ScreenObject is an object that appears on the screen. Since objects can be
 * translated/rotated/scaled, it's possible for the object to be moved so that
 * it isn't visible onscreen any more. If this is the case, the object should
 * not be rendered. This interface provides a prototype for that image.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ScreenObject
{
	/*************************************************************************
	 * Determines if the object will be visible if it is rendered. This is
	 * useful for determining what not to render.
	 * 
	 * @return True if any portion of the object can be visible upon rendering,
	 *         false if not.
	 *************************************************************************/
	public boolean onscreen();
}