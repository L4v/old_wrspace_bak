package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.primitive.PrimitivePainter;

/*****************************************************************************
 * A CircleSkin renders and contains geometric data approximating a square. To
 * make this skin approximate a rectangle of any particular dimensions, simply
 * scale is x and y dimensions unevenly.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SquareSkin extends PrimitiveSkin
{
	/**
	 * Pre-calculated vertex data describing a square of side length 1. 
	 **/
	private static final float[][] SQUARE_VERTICES = new float[][] {
												{-0.5f, 	-0.5f}, 
												{0.5f, 		-0.5f},
												{0.5f, 		0.5f},
												{-0.5f, 	0.5f}};

	/*************************************************************************
	 * SquareSkin delegates rendering to PrimitivePainter's renderRectangle() 
	 * method.
	 ***************************************************************/ @Override
	public void render()
	{
		PrimitivePainter.renderRectangle();
	}

	/*************************************************************************
	 * Accesses geometric data representing a square. The described square has a
	 * width and height of 1.
	 * 
	 * @return Vertex data approximating a square.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return SQUARE_VERTICES;
	}
}