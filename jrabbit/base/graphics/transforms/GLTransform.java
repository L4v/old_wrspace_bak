package org.jrabbit.base.graphics.transforms;

/*****************************************************************************
 * A GLTransform is an object that causes a state change in OpenGL to affect
 * rendering. It is intended to be "two-way," in that the affect can both be
 * bound (effecting the change) and released (undoing it).
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface GLTransform
{
	/*************************************************************************
	 * Affects the change in OpenGL's state.
	 *************************************************************************/
	public void bind();

	/*************************************************************************
	 * Reverts the state to what it was before bind() was called.
	 *************************************************************************/
	public void release();
}