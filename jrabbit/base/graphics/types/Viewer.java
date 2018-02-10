package org.jrabbit.base.graphics.types;

/*****************************************************************************
 * A Viewer calculates whether or not objects will be seen when they are
 * rendered to the screen. Objects that extends this interface will probably
 * work in tandem with ScreenObjects to determine whether or not something is
 * visible.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Viewer
{
	/*************************************************************************
	 * Determines whether or not the indicated coordinates are visible, given a
	 * visibility radius.
	 * 
	 * @param x
	 *            The central x coordinate.
	 * @param y
	 *            The central y coordinate.
	 * @param radius
	 *            The radius of the visible object.
	 * 
	 * @return Whether or not the coordinates are visible.
	 *************************************************************************/
	public boolean views(float x, float y, float radius);
}