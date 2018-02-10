package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.primitive.PrimitivePainter;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A RegPolygonSkin renders and describes an n-sided regular polygon with a base
 * width and height of 1. It uses DisplayList acceleration to hasten rendering.
 * 
 * NOTE: The number of sides being rendered should be greater than 2.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RegPolygonSkin extends PrimitiveSkin
{
	/**
	 * The current number of sides to the polygon.
	 **/
	protected int numSides;
	
	/**
	 * The current DisplayList ID. If this is 0, no DisplayList is currently 
	 * active.
	 **/
	protected int dLID;
	
	/**
	 * The calculated vertex data.
	 **/
	protected float[][] vertices;
	
	/*************************************************************************
	 * Creates a RegPolygonSkin that renders a primitive with the indicated
	 * number of sides.
	 * 
	 * @param sides
	 * 			  The number of sides for the polygon to have.
	 *************************************************************************/
	public RegPolygonSkin(int sides)
	{
		setNumberOfSides(sides);
	}
	
	/*************************************************************************
	 * Learns the current number of sides.
	 * 
	 * @return The current number of sides on the regular polygon.
	 *************************************************************************/
	public int numberOfSides()
	{
		return numSides;
	}
	
	/*************************************************************************
	 * Redefines the number of sides the polygon uses. This recalculates both
	 * the DisplayList and the vertex data.
	 * 
	 * @param numSides
	 * 			  The new number of sides to use.
	 *************************************************************************/
	public void setNumberOfSides(int numSides)
	{
		if(this.numSides != numSides)
		{
			this.numSides = numSides;
			dLID = PrimitivePainter.defineRegularPolygon(numSides);
			calculateVertices();
		}
	}
	
	/*************************************************************************
	 * Recalculates the vertex data.
	 *************************************************************************/
	protected void calculateVertices()
	{
		vertices = new float[numSides][2];
		double angleIncrement =  2 * Math.PI / numSides;
		for(int i = 0; i < numSides; i++)
		{
			vertices[i][0] = (float) (Math.sin(i * angleIncrement) / 2);
			vertices[i][1] = (float) (Math.cos(i * angleIncrement) / 2);
		}
	}

	/*************************************************************************
	 * Renders the n-sided polygon.
	 ***************************************************************/ @Override
	public void render()
	{
		GL11.glCallList(dLID);
	}

	/*************************************************************************
	 * Accesses the geometry data.
	 * 
	 * @return The calculated coordinates of the vertex of this object.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return vertices;
	}
}