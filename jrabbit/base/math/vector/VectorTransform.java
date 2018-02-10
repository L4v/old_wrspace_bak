package org.jrabbit.base.math.vector;

import org.jrabbit.base.graphics.transforms.Vector2f;

/*****************************************************************************
 * A VectorTransform is (fairly self-evidently) an object that creates a
 * Vector2f based upon another that is supplied.
 * 
 * This has a variety of possible uses, mostly involving value conversion. For
 * example, VectorTransform is implemented to transform Mouse coordinates into
 * scene coordinates in the WindowController.
 * 
 * When implementing this, the vector that is supplied to the transform should
 * not be affected by this transformation.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface VectorTransform
{
	/*************************************************************************
	 * Converts a Vector2f. The supplied vector should not be changed by this
	 * method.
	 * 
	 * @param vector
	 * 			  The base vector.
	 * 
	 * @return A new Vector2f based on the original.
	 *************************************************************************/
	public Vector2f transform(BaseVector2f vector);
}