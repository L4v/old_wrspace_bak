package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.primitive.PrimitivePainter;

/*****************************************************************************
 * A CircleSkin renders and contains geometric data approximating a circle.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CircleSkin extends PrimitiveSkin
{
	/**
	 * We approximate a circle by supplying a regular hexadecagon of diameter 1.
	 * This means circle collisions are close enough to the real thing, but
	 * geometry calculations are much faster than using Bezier curves to
	 * represent a true circle.
	 **/
	private static final float[][] CIRCLE_VERTICES = new float[][] {
			{ 0, 0.5f }, { 0.1913417f, 0.4619398f },
			{ 0.3535535f, 0.3535535f }, { 0.4619398f, 0.1913417f },
			{ 0.5f, 0 }, { 0.4619398f, -0.1913417f },
			{ 0.3535535f, -0.3535535f }, { 0.1913417f, -0.4619398f },
			{ 0, -0.5f }, { -0.1913417f, -0.4619398f },
			{ -0.3535535f, -0.3535535f }, { -0.4619398f, -0.1913417f },
			{ -0.5f, 0 }, { -0.4619398f, 0.1913417f },
			{ -0.3535535f, 0.3535535f }, { -0.1913417f, 0.4619398f } };

	/*************************************************************************
	 * CircleSkin delegates rendering to PrimitivePainter's renderCircle() 
	 * method.
	 ***************************************************************/ @Override
	public void render()
	{
		PrimitivePainter.renderCircle();
	}

	/*************************************************************************
	 * Accesses geometric data representing a circle. For speed and efficiency
	 * reasons, Bezier curves are not returned, but a heptadecagon of radius 1
	 * is returned.
	 * 
	 * @return Vertex data approximating a circle.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return CIRCLE_VERTICES;
	}
}