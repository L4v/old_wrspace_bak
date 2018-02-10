package org.jrabbit.base.math.geom;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Scalar;
import org.jrabbit.base.graphics.transforms.Vector2f;

/*****************************************************************************
 * A Geometry object represents a 2D arbitrary shape. It is designed to work
 * with all Java2D shapes.
 * 
 * The point of Geometry is to provide an advanced geometry/collision detection
 * mechanism that is fast and useful enough to be effective in a variety of
 * situations.
 * 
 * Geometry has two main components - a base shape, and its transformed child.
 * The base shape defines the unscaled, unrotated, and untranslated shape. The
 * transformed child is created to represent the shape with a scale, rotation,
 * and translation. In this way, a game object can have a complex shape, and (as
 * it moves, rotates, and scales/flips) then it can tell its Geometry to update
 * its child shape. After this update, all intersection and similar calculations
 * utilize this transformed child.
 * 
 * Despite the complexity of this setup, for reasonably simple shapes (say, a
 * 10-sided, straight-edged polygon) this is fairly fast and can allow a large
 * number of objects.
 * 
 * Additionally, a Geometry can be used to access the vertice data for its base
 * shape. This allows it to interact with other objects not designed to work
 * with Java2D Shapes (for example, Primitive rendering).
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Geometry implements Vertexed
{
	/*************************************************************************
	 * Converts a GeneralPath to its vertice data.
	 * 
	 * This works best with straight-edged paths. For curved paths, this can
	 * screw up fairly badly. However, it returns a tolerable estimate, and
	 * gives correct results for straight-edged polygons.
	 * 
	 * @param path
	 *            The GeneralPath to convert.
	 * 
	 * @return The vertices that comprise the corners of the path.
	 *************************************************************************/
	public static float[][] extractVertices(Path2D path)
	{
		ArrayList<float[]> vertices = new ArrayList<float[]>();
		float[] coords = new float[6];
		boolean pathBegun = false;
		boolean finished = false;

		PathIterator pi = path.getPathIterator(null);
		while (!pi.isDone() && !finished)
		{
			switch (pi.currentSegment(coords))
			{
			case PathIterator.SEG_MOVETO:
				finished = pathBegun;
				break;
			case PathIterator.SEG_CLOSE:
				finished = true;
			default:
				pathBegun = true;
				vertices.add(Arrays.copyOf(coords, 2));
			}
			pi.next();
		}
		return vertices.toArray(new float[vertices.size()][]);
	}

	/**
	 * The base shape of the Geometry.
	 **/
	protected Path2D shape;

	/**
	 * The updated shape that accomodates translation, rotation, and scaling.
	 **/
	protected Path2D transformedShape;

	/**
	 * The AWT transform that updates the base shape to create the transformed
	 * child shape.
	 **/
	protected AffineTransform transform;

	/**
	 * The stored vertices of the base shape. Every time the shape is redefined,
	 * these are recalculated, to make retrieving them quicker.
	 **/
	float[][] vertices;

	/*************************************************************************
	 * Creates an empty Geometry.
	 *************************************************************************/
	public Geometry()
	{
		transform = new AffineTransform();
		shape = new Path2D.Float();
		transformedShape = new Path2D.Float();
	}

	/*************************************************************************
	 * Creates a rectangular Geometry with the indicated base dimensions.
	 * 
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 *************************************************************************/
	public Geometry(float width, float height)
	{
		this();
		set(new float[][] { { -width / 2, -height / 2 },
				{ width / 2, -height / 2 }, { width / 2, height / 2 },
				{ -width / 2, height / 2 } });
	}

	/*************************************************************************
	 * Creates a Geometry with the indicated base shape.
	 * 
	 * @param shape
	 *            The Java Shape to use for the Geometry.
	 *************************************************************************/
	public Geometry(Shape shape)
	{
		this();
		set(shape);
	}

	/*************************************************************************
	 * Creates a Geometry from the supplied vertice data. The resulting Geometry
	 * is straight-edged (no curved sides).
	 * 
	 * @param vertices
	 *            The point data to use for calculating the geometry.
	 *************************************************************************/
	public Geometry(float[][] vertices)
	{
		this();
		set(vertices);
	}

	/*************************************************************************
	 * Creates a Geometry that defines its geometry from those of the indicated
	 * Vertexed.
	 * 
	 * @param vertexed
	 *            The Vertexed object to acquire geometry info from.
	 *************************************************************************/
	public Geometry(Vertexed vertexed)
	{
		this(vertexed.vertices());
	}

	/*************************************************************************
	 * Redefines the base shape to be the indicated Java AWT Shape.
	 * 
	 * This assumes that the supplied Shape is offset with its top-left corner
	 * at the origin. As such, it offsets the resulting shape so that the origin
	 * is at the center of its bounding box.
	 * 
	 * NOTE: The current transformed shape is unchanged by this method; it will
	 * be updated on the next adjust() call.
	 * 
	 * @param shape
	 *            The new base shape.
	 *************************************************************************/
	public void set(Shape shape)
	{
		set(shape, -shape.getBounds2D().getWidth() / 2, -shape.getBounds2D()
				.getHeight() / 2);
	}

	/*************************************************************************
	 * Redefines the base shape to be the indicated Java AWT Shape. The
	 * resultant base shape is offset by the indicated amount.
	 * 
	 * NOTE: The current transformed shape is unchanged by this method; it will
	 * be updated on the next adjust() call.
	 * 
	 * @param shape
	 *            The new base shape.
	 * @param offsetX
	 *            The amount to offset the shape's X coordinate.
	 * @param offsetY
	 *            The amount to offset the shape's Y coordinate.
	 *************************************************************************/
	public void set(Shape shape, double offsetX, double offsetY)
	{
		this.shape.reset();
		this.shape.append(shape, true);
		transform.setToTranslation(offsetX, offsetY);
		this.shape.transform(transform);
		vertices = extractVertices(this.shape);
	}

	/*************************************************************************
	 * Constructs a new base shape from the indicated vertex data. The format
	 * for vertices should be {{x1, y2,}, {x2, y2}...}.
	 * 
	 * The coordinates represent the base shape, and the center of the geometry
	 * will be at {0, 0}.
	 * 
	 * @param vertices
	 *            The vertex data used to build the new shape.
	 *************************************************************************/
	public void set(float[][] vertices)
	{
		Path2D.Float path = new Path2D.Float();
		path.moveTo((float) vertices[0][0], (float) vertices[0][1]);
		for (int i = 1; i < vertices.length; i++)
		{
			path.lineTo((float) vertices[i][0], (float) vertices[i][1]);
		}
		path.closePath();

		shape.reset();
		shape.append(path, true);
		this.vertices = vertices;
	}

	/*************************************************************************
	 * Redefines the Geometry based on the geometry of the indicated Vertexed.
	 * 
	 * @param vertexed
	 *            The Vertexed object that contains the needed geometry.
	 * 
	 * @see #set(float[][])
	 *************************************************************************/
	public void set(Vertexed vertexed)
	{
		set(vertexed.vertices());
	}

	/*************************************************************************
	 * Accesses the base shape that defines this geometry.
	 * 
	 * @return The "prototype" shape.
	 *************************************************************************/
	public Path2D baseShape()
	{
		return shape;
	}

	/*************************************************************************
	 * Accesses the updated shape derived from the base shape.
	 * 
	 * @return The current, transformed shape used for operations.
	 *************************************************************************/
	public Path2D shape()
	{
		return transformedShape;
	}

	/*************************************************************************
	 * Accesses the vertex data that approximates the base geometry.
	 * 
	 * @return An array of vertex coordinates that corresponds to the base
	 *         shape.
	 ***************************************************************/ @Override
	public float[][] vertices()
	{
		return vertices;
	}

	/*************************************************************************
	 * Recalculates the transformed shape so that it represents the base shape,
	 * but translated, rotated, and scaled as indicated.
	 * 
	 * NOTE: To flip the shape, simply supply a negative value for one of the
	 * scale values.
	 * 
	 * @param x
	 *            The amount to translate the shape on the x axis.
	 * @param y
	 *            The amount to translate the shape on the y axis.
	 * @param theta
	 *            The amount to rotate, in clockwise radians.
	 * @param xScale
	 *            The amount to scale the base shape on the x axis.
	 * @param yScale
	 *            The amount to scale the base shape on the y axis.
	 *************************************************************************/
	public void apply(float x, float y, float theta, float xScale, float yScale)
	{
		transformedShape.reset();
		transformedShape.append(shape, true);
		transform.setToTranslation(x, y);
		transform.rotate(theta);
		transform.scale(xScale, yScale);
		transformedShape.transform(transform);
	}

	/*************************************************************************
	 * Recalculates the transformed shape so that it represents the base shape,
	 * but translated, rotated, and scaled as indicated.
	 * 
	 * @param location
	 *            The Vector2f that indicates how far to translate.
	 * @param rotation
	 *            The Rotation that indicates how much to rotate.
	 * @param scale
	 *            The Scalar that indicates how to scale.
	 * 
	 * @see #apply(float, float, float, float, float)
	 */
	public void apply(Vector2f location, Rotation rotation, Scalar scale)
	{
		apply(location.x(), location.y(), rotation.theta(), scale.transformX(),
				scale.transformY());
	}

	/*************************************************************************
	 * Determines if the currently transformed Geometry contains the indicated
	 * point.
	 * 
	 * @param x
	 *            The x-coordinate.
	 * @param y
	 *            The y-coordinate.
	 * 
	 * @return True if the point is contained, false if not.
	 *************************************************************************/
	public boolean contains(float x, float y)
	{
		return transformedShape.contains(x, y);
	}

	/*************************************************************************
	 * Determines if the currently transformed Geometry contains the indicated
	 * point.
	 * 
	 * @param point
	 *            The Vector2f that represents the point to check for.
	 * 
	 * @see #contains(float, float)
	 * 
	 * @return True if the point is contained, false if not.
	 *************************************************************************/
	public boolean contains(Vector2f point)
	{
		return transformedShape.contains(point.x(), point.y());
	}

	/*************************************************************************
	 * Determines if the two Geometries intersect. This compares their updated,
	 * transformed shapes for collision.
	 * 
	 * @param target
	 *            The Geometry to check against.
	 * 
	 * @see #intersection(Shape)
	 * 
	 * @return True if the Geometries intersect, false if not.
	 *************************************************************************/
	public boolean intersects(Geometry target)
	{
		return intersects(target.transformedShape);
	}

	/*************************************************************************
	 * Determines if the indicated Shape intersects the transformed Shape
	 * belonging to this Geometry.
	 * 
	 * To determine if the shapes collide, first the bounding boxes of the two
	 * Shapes are compared for intersection. If those intersect, a Java Area is
	 * created as the overlap of the two Shapes, and is checked to see if it
	 * encloses any space.
	 * 
	 * @param target
	 *            The Shape to check against.
	 * 
	 * @return True if the Shapes intersect, false if not.
	 *************************************************************************/
	public boolean intersects(Shape target)
	{
		if (target.getBounds2D().intersects(transformedShape.getBounds2D()))
		{
			Area intersection = new Area(transformedShape);
			intersection.intersect(new Area(target));
			return !intersection.isEmpty();
		}
		return false;
	}

	/*************************************************************************
	 * Calculates the intersecting area of the two Geometries.
	 * 
	 * @param target
	 *            The Geometry to check against.
	 * 
	 * @see #intersection(Shape)
	 * 
	 * @return A Java Area that represents the overlapping space between the two
	 *         Shapes.
	 *************************************************************************/
	public Area intersection(Geometry target)
	{
		return intersection(target.transformedShape);
	}

	/*************************************************************************
	 * Calculates the intersecting area of the the indicated Shape and the
	 * transformed Shape of this Geometry.
	 * 
	 * @param target
	 *            The Shape to check against.
	 * 
	 * @return A Java Area that represents the overlapping space between the two
	 *         Shapes.
	 *************************************************************************/
	public Area intersection(Shape target)
	{
		if (target.getBounds2D().intersects(transformedShape.getBounds2D()))
		{
			Area intersection = new Area(transformedShape);
			intersection.intersect(new Area(target));
			return intersection;
		}
		return new Area();
	}
}