package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.math.geom.Vertexed;

/*****************************************************************************
 * PrimitiveSkin is the base class for any Skin intended to draw a geometric
 * shape.
 * 
 * By default, a PrimitiveSkin has a width and height of 1 pixel; it should be
 * scaled up if it needs to be larger (as is likely the case).
 * 
 * A PrimitiveSkin is also expected to be a Vertexed object; this allows objects
 * to directly interpret the geometry data behind the PrimitiveSkin.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class PrimitiveSkin implements Skin, Vertexed
{
	/*************************************************************************
	 * By default, a PrimitiveSkin has a width and height of 1; it should be
	 * scaled to have larger dimensions.
	 * 
	 * @return The base width of the PrimitiveSkin.
	 ***************************************************************/ @Override
	public float width() { return 1f; }
	
	/*************************************************************************
	 * By default, a PrimitiveSkin has a width and height of 1; it should be
	 * scaled to have larger dimensions.
	 * 
	 * @return The base height of the PrimitiveSkin.
	 ***************************************************************/ @Override
	public float height() { return 1f; }
}