package org.jrabbit.base.graphics.transforms.listeners;

import org.jrabbit.base.graphics.transforms.Color;

/*****************************************************************************
 * A ColorListener can "watch" Color objects to see if their values change.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ColorListener
{
	/*************************************************************************
	 * If a Color this object is listening to has its values changed, then this
	 * is called.
	 * 
	 * @param color
	 *            A reference to the Color that changed.
	 *************************************************************************/
	void changed(Color color);
}