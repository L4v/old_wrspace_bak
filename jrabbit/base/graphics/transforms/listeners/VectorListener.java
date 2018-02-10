package org.jrabbit.base.graphics.transforms.listeners;

import org.jrabbit.base.graphics.transforms.Vector2f;

/*****************************************************************************
 * A VectorListener can "watch" Vector2d objects to see if their values change.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface VectorListener
{
	/*************************************************************************
	 * If a Vector2d this object is listening to has its values changed, then
	 * this is called.
	 * 
	 * @param vector
	 *            A reference to the Vector2d that changed.
	 *************************************************************************/
	public void moved(Vector2f vector);
}
