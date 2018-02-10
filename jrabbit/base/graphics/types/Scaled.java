package org.jrabbit.base.graphics.types;

import org.jrabbit.base.graphics.transforms.Scalar;

/*****************************************************************************
 * An object that is Scaled has a Scalar that it uses to alter rendering. This
 * interface provides a method to access the Scalar.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Scaled
{
	/*************************************************************************
	 * Accesses the object's scaling controls.
	 * 
	 * @return The Scalar that magnifies the object.
	 *************************************************************************/
	public Scalar scalar();
}