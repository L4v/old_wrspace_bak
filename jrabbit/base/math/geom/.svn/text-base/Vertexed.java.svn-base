package org.jrabbit.base.math.geom;

/*****************************************************************************
 * A Vertexed is an object that somehow contains or references polygonal data.
 * It provides a general method to access that data.
 * 
 * jRabbit's default geometry setups assume a general format for this polygon
 * data - an array of vertex coordinates (as floats). This format follows the
 * form:
 * 
 * 		vertices = {{x1, y1}, {x2, y2}, {x3, y3}... {xN, yN}}.
 * 
 * The described polygon is understood to be a closed loop.
 * 
 * A Vertexed object can be used to create a complex Geometry object, or to
 * define rendering instructions for the PrimitivePainter. It's also intended to
 * be a general-purpose format, as its speed and random-access capabilities
 * allow a great deal of free-form interpretation.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Vertexed
{
	/*************************************************************************
	 * Accesses the vertex data.
	 * 
	 * @return The list of coordinates that outline the polygon represented by
	 *         this object.
	 *************************************************************************/
	public float[][] vertices();
}
