package org.jrabbit.base.graphics.skins.primitive;

import org.jrabbit.base.graphics.primitive.PrimitivePainter;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A PolygonSkin renders and geometrically represents a complex polygon. It is
 * created from supplied vertex data upon initialization.
 * 
 * The polygon described has very few limitations - it can be convex or concave,
 * have any number of vertices, have its center not be at the origin; but for
 * the rendered shape to look like the described polygon, it cannot "bend back"
 * on itself in relation to the origin. 
 * 
 * To understand what this means, first you need to know how the Polygon is 
 * rendered - it's a "Triangle fan." A vertex is rendered at [0, 0], and then
 * all of the polygon's vertices are rendered, in order, around the shape.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PolygonSkin extends PrimitiveSkin
{
	/**
	 * The calculated width of the polygon.
	 **/
	private float width;
	
	/**
	 * The calculated height of the polygon.
	 **/
	private float height;
	
	/**
	 * The ID of the compiled DisplayList to use.
	 **/
	private int dLID;
	
	/**
	 * The coordinate data.
	 **/
	private float[][] vertices;
	
	/*************************************************************************
	 * Creates a PolygonSkin that renders the indicated Polygon.
	 * 
	 * @param vertices
	 * 			  The vertex data that describes the Polygon to render.
	 *************************************************************************/
	public PolygonSkin(float[][] vertices)
	{
		setVertices(vertices);
	}
	
	/*************************************************************************
	 * Redefines the PolygonSkin.
	 * 
	 * @param vertices
	 * 			  The vertex data that describes the Polygon to render.
	 *************************************************************************/
	public void setVertices(float[][] vertices)
	{
		this.vertices = vertices;
		
		// Calculate width and height.
		width = 0;
		height = 0;
		for(int i = 0; i < vertices.length; i++)
		{
			width = Math.max(Math.abs(vertices[i][0]), width);
			height = Math.max(Math.abs(vertices[i][0]), height);
		}
		width *= 2;
		height *= 2;
		
		// Obtain the DisplayList ID.
		dLID = PrimitivePainter.definePolygon(vertices);
	}

	/*************************************************************************
	 * Learns about the dimensions of the polygon.
	 * 
	 * @return The base width of the polygon.
	 ***************************************************************/ @Override
	public float width()
	{
		return width;
	}

	/*************************************************************************
	 * Learns about the dimensions of the polygon.
	 * 
	 * @return The base height of the polygon.
	 ***************************************************************/ @Override
	public float height()
	{
		return height;
	}

	/*************************************************************************
	 * Renders the polygon.
	 ***************************************************************/ @Override
	public void render()
	{
		GL11.glCallList(dLID);
	}

	/*************************************************************************
	 * Accesses the polygon's geometry data.
	 * 
	 * @return The list of vertices describing the polygon.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return vertices;
	}
}