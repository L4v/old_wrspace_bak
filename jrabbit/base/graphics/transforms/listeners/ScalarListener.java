package org.jrabbit.base.graphics.transforms.listeners;

import org.jrabbit.base.graphics.transforms.Scalar;

/*****************************************************************************
 * A ScalarListener can "watch" Scalar objects to see if their values change.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ScalarListener
{
	/*************************************************************************
	 * If a Scalar this object is listening to has its scale changed, this is
	 * called.
	 * 
	 * @param scalar
	 *            A reference to the Scalar that was scaled.
	 *************************************************************************/
	public void scaled(Scalar scalar);

	/*************************************************************************
	 * If a Scalar this object is listening to is flipped either horizontally or
	 * vertically, this is called.
	 * 
	 * @param scalar
	 *            A reference to the Scalar that was flipped.
	 *************************************************************************/
	public void flipped(Scalar scalar);
}
