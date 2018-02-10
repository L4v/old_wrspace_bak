package org.jrabbit.base.graphics.types;

import org.jrabbit.base.graphics.transforms.Rotation;

/*****************************************************************************
 * An object that is Rotated has a Rotation that it uses to alter rendering.
 * This interface provides a method to access the rotation.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Rotated
{
	/*************************************************************************
	 * Accesses the object's rotation.
	 * 
	 * @return The Rotation that rotates the object.
	 *************************************************************************/
	public Rotation rotation();
}