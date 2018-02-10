package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.primitive.PrimitivePainter;

/*****************************************************************************
 * A TriangleSkin renders an contains vertex data for a simple triangle width a 
 * width and height of 1.
 * 
 * Unfortunately, the default triangle is static and cannot describe every 
 * possible triangle (though it can be scaled and rotated to represent any 
 * triangle with two sides of equal length). If a fully dynamic triangle needs 
 * to be described, a custom skin must be developed. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TriangleSkin extends PrimitiveSkin
{
	/**
	 * Pre-calculated vertex data describing a triangle with a width and height 
	 * of 1.
	 **/
	private static final float[][] TRIANGLE_VERTICES = new float[][] {
												{0, 		-0.5f}, 
												{0.5f, 		0.5f},
												{-0.5f, 	0.5f}};

	/*************************************************************************
	 * TRIANGLE_VERTICES delegates rendering to PrimitivePainter's 
	 * renderTriangle() method.
	 ***************************************************************/ @Override
	public void render()
	{
		PrimitivePainter.renderTriangle();
	}

	/*************************************************************************
	 * Accesses geometric data representing a triangle. The described triangle 
	 * has a width and height of 1.
	 * 
	 * @return Vertex data approximating a square.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return TRIANGLE_VERTICES;
	}
}