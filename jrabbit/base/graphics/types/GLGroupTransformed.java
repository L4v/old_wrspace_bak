package org.jrabbit.base.graphics.types;

import org.jrabbit.base.graphics.transforms.GLGroupTransform;
import org.jrabbit.base.graphics.transforms.GLTransform;

/*****************************************************************************
 * A GLGroupTransformed object has a dynamic, ordered list of GLTransforms that
 * it uses to effect rendering. This interface provides a method to access the
 * transform.
 * 
 * A GLGroupTransformed is considered a GLTransform itself; it's expected that
 * it's bind() and release() methods will forward themselves to the 
 * GroupTransform contained by this object.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface GLGroupTransformed extends GLTransform
{
	/*************************************************************************
	 * Accesses the list of transforms.
	 * 
	 * @return The GLGroupTransform that dynamically controls OpenGL's state.
	 *************************************************************************/
	public GLGroupTransform transforms();
}