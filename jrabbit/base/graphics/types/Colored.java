package org.jrabbit.base.graphics.types;

import org.jrabbit.base.graphics.transforms.Color;

/*****************************************************************************
 * An object that is colored has (obviously) a Color that affects rendering.
 * This interface provides a way to access that Color and change its settings.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Colored
{
	/*************************************************************************
	 * Accesses the object's color settings.
	 * 
	 * @return The Color that controls the object's hue and transparency.
	 *************************************************************************/
	public Color color();
}