package org.jrabbit.base.core.types;

/*****************************************************************************
 * An Updateable is an object that continually performs actions over time.
 * 
 * Most default objects in jRabbit do not automatically implement this; instead
 * the developer can add this functionality to any object he wishes to control
 * itself.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Updateable
{
	/*************************************************************************
	 * Updates the object.
	 * 
	 * @param delta
	 *            The time passed since the last update. The default units are
	 *            in microseconds.
	 *************************************************************************/
	public void update(int delta);
}