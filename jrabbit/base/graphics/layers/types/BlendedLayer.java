package org.jrabbit.base.graphics.layers.types;

import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.graphics.types.Blended;

/*****************************************************************************
 * BlendedLayer adds a controllable BlendOp to DefaultLayer. By doing this,
 * every object rendered by the BlendedLayer has that blending mode as its 
 * default.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class BlendedLayer extends DefaultLayer implements Blended
{
	/**
	 * The BlendOp that controls the Layer's blending mode.
	 **/
	private BlendOp blending;
	
	/*************************************************************************
	 * Creates a BlendedLayer with the indicated identifier and blending mode.
	 * 
	 * @param reference
	 * 			  The String that will identify this Layer.
	 * @param blending
	 * 			  The BlendOp that controls how this Layer is blended.
	 *************************************************************************/
	public BlendedLayer(String reference, BlendOp blending)
	{
		super(reference);
		this.blending = blending;
		transforms.add(blending);
	}

	/*************************************************************************
	 * Accesses the Layer's BlendOp.
	 * 
	 * @return The BlendOp that defines blending for the Layer.
	 ***************************************************************/ @Override
	public BlendOp blending()
	{
		return blending;
	}
}
