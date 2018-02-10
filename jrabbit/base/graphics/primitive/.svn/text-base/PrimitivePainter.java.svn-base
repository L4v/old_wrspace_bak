package org.jrabbit.base.graphics.primitive;

import java.util.HashMap;
import java.util.Map.Entry;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A convenience class for drawing solid-color, non-textured primitive shapes.
 * 
 * Wherever possible, display lists are used to accelerate rendering the
 * primitives.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class PrimitivePainter
{
	/**
	 * The display list ID that will draw a square.
	 **/
	private static int rectangleDLID = -1;

	/**
	 * The display list ID that will draw a triangle.
	 **/
	private static int triangleDLID = -1;

	/**
	 * The display list ID that draws a circle. NOTE: The circle rendered does
	 * not actually have curved edges; it is simply a 64-sided regular polygon.
	 **/
	private static int circleDLID = -1;

	/**
	 * Checks whether or not everything has been created to allow for primitive
	 * rendering.
	 **/
	private static boolean valid = false;

	/**
	 * The algorithm that renders regular polygons always results in an
	 * identical primitive for the same number of sides. This stores any created
	 * display lists so that they can be used for multiple requests.
	 **/
	private static HashMap<Integer, Integer> regPolyDLs;
	
	/**
	 * Additionally, accelerated polygons are cached in a list so that they can
	 * be accessed by a variety of objects. 
	 **/
	private static HashMap<Float[][], Integer> cachedPolygons;

	/*************************************************************************
	 * Creates all resources needed to accelerate primitive rendering.
	 *************************************************************************/
	public static void create()
	{
		if (!valid)
		{
			rectangleDLID = GL11.glGenLists(1);
			GL11.glNewList(rectangleDLID, GL11.GL_COMPILE);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
					GL11.glVertex2f(-0.5f, -0.5f);
					GL11.glVertex2f(0.5f, -0.5f);
					GL11.glVertex2f(-0.5f, 0.5f);
					GL11.glVertex2f(0.5f, 0.5f);
				GL11.glEnd();
			GL11.glEndList();

			triangleDLID = GL11.glGenLists(1);
			GL11.glNewList(triangleDLID, GL11.GL_COMPILE);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex2f(0, -0.5f);
					GL11.glVertex2f(0.5f, 0.5f);
					GL11.glVertex2f(-0.5f, 0.5f);
				GL11.glEnd();
			GL11.glEndList();

			circleDLID = GL11.glGenLists(1);
			GL11.glNewList(circleDLID, GL11.GL_COMPILE);
				renderRegPolygon(64);
			GL11.glEndList();

			regPolyDLs = new HashMap<Integer, Integer>();
			cachedPolygons = new HashMap<Float[][], Integer>();

			valid = true;
		}
	}

	/*************************************************************************
	 * Renders a rectangle with a width and height of 1 pixel. Obviously, this
	 * should probably be scaled up.
	 *************************************************************************/
	public static void renderRectangle()
	{
		GL11.glCallList(rectangleDLID);
	}

	/*************************************************************************
	 * Renders a triangle with a width and height of 1 pixel. Obviously, this
	 * should probably be scaled up.
	 *************************************************************************/
	public static void renderTriangle()
	{
		GL11.glCallList(triangleDLID);
	}

	/*************************************************************************
	 * Renders a circle with a width and height of 1 pixel. Obviously, this
	 * should probably be scaled up.
	 *************************************************************************/
	public static void renderCircle()
	{
		GL11.glCallList(circleDLID);
	}

	/*************************************************************************
	 * Renders a rectangle with the indicated width and height.
	 * 
	 * @param width
	 *            The width of the rectangle, in pixels.
	 * @param height
	 *            The height of the rectangle, in pixels.
	 *************************************************************************/
	public static void renderRectangle(float width, float height)
	{
		GL11.glScalef(width, height, 1);
		GL11.glCallList(rectangleDLID);
		GL11.glScalef(1 / width, 1 / height, 1);
	}

	/*************************************************************************
	 * Renders a triangle with the indicated width and height.
	 * 
	 * @param width
	 *            The width of the triangle, in pixels.
	 * @param height
	 *            The height of the triangle, in pixels.
	 *************************************************************************/
	public static void renderTriangle(float width, float height)
	{
		GL11.glScalef(width, height, 1);
		GL11.glCallList(triangleDLID);
		GL11.glScalef(1 / width, 1 / height, 1);
	}

	/*************************************************************************
	 * Renders a circle with the indicated width and height.
	 * 
	 * @param width
	 *            The width of the circle, in pixels.
	 * @param height
	 *            The height of the circle, in pixels.
	 *************************************************************************/
	public static void renderCircle(float width, float height)
	{
		GL11.glScalef(width, height, 1);
		GL11.glCallList(circleDLID);
		GL11.glScalef(1 / width, 1 / height, 1);
	}

	/*************************************************************************
	 * Renders a line between two points.
	 * 
	 * @param pointA
	 *            The beginning of the line.
	 * @param pointB
	 *            The end of the line.
	 ************************************************************************/
	public static void renderLine(Vector2f pointA, Vector2f pointB)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_LINES);
			pointA.vertex();
			pointB.vertex();
		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders a line between two points.
	 * 
	 * @param x1
	 *            The x-coordinate of the beginning of the line.
	 * @param y1
	 *            The y-coordinate of the beginning of the line.
	 * @param x2
	 *            The x-coordinate of the end of the line.
	 * @param y2
	 *            The y-coordinate of the end of the line.
	 ************************************************************************/
	public static void renderLine(float x1, float y1, float x2, float y2)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(x1, y1);
			GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders the indicated path as one continuous line.
	 * 
	 * @param path
	 *            The array of vectors to render.
	 *************************************************************************/
	public static void renderPath(Vector2f[] path)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_LINE_STRIP);
			for (Vector2f point : path)
				point.vertex();
		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders the indicated path as one continuous line.
	 * 
	 * @param path
	 *            The series of vectors to render.
	 *************************************************************************/
	public static void renderPath(Iterable<Vector2f> path)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_LINE_STRIP);
			for (Vector2f point : path)
				point.vertex();
		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders a regular polygon with the indicated number of sides. The polygon
	 * has a width and height of 1, so scaling operations should probably be
	 * applied before this call.
	 * 
	 * @param numSides
	 *            The number of sides for the polygon to have.
	 *************************************************************************/
	public static void renderRegPolygon(int numSides)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			double angleIncrement = 2 * Math.PI / numSides;
			for (int i = 0; i <= numSides; i++)
				GL11.glVertex2f((float) Math.sin(i * angleIncrement) / 2,
						(float) Math.cos(i * angleIncrement) / 2);
		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders a regular polygon with the indicated number of sides.
	 * 
	 * @param numSides
	 *            The number of sides for the polygon to have.
	 * @param width
	 *            The width of the polygon.
	 * @param height
	 *            The height of the polygon.
	 *************************************************************************/
	public static void renderRegPolygon(int numSides, float width, float height)
	{
		GL11.glScalef(width, height, 1);
		renderRegPolygon(numSides);
		GL11.glScalef(1 / width, 1 / height, 1);
	}

	/*************************************************************************
	 * Creates a display list that will render a polygon with the appropriate
	 * number of sides. The width and height of the rendered polygon is 1.
	 * 
	 * @param numSides
	 *            The number of sides for the polygon to have.
	 * 
	 * @return The ID of a display list that will accelerate rendering of the
	 *         indicated shape.
	 *************************************************************************/
	public static int defineRegularPolygon(int numSides)
	{
		if (regPolyDLs.containsKey(numSides))
			return regPolyDLs.get(numSides);
		int newDisplayListID = GL11.glGenLists(1);
		GL11.glNewList(newDisplayListID, GL11.GL_COMPILE);
			renderRegPolygon(numSides);
		GL11.glEndList();
		regPolyDLs.put(numSides, newDisplayListID);
		return newDisplayListID;
	}

	/*************************************************************************
	 * Renders the polygon represented by the supplied vertices. The vertices
	 * should be centered around [0, 0].
	 * 
	 * The polygon to be rendered is considered a "triangle fan" by the graphics
	 * card, with [0, 0] as the center. Thus, the shape can be convex, but
	 * cannot curve back on itself. More complex geometry should really be
	 * custom-rendered.
	 * 
	 * @param vertices
	 *            The vertice data that describes the shape.
	 *************************************************************************/
	private static void renderPolygon(float[][] vertices)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			GL11.glVertex2f(0, 0);
			for (int i = 0; i < vertices.length; i++)
				GL11.glVertex2f(vertices[i][0], vertices[i][1]);
			GL11.glVertex2f(vertices[0][0], vertices[0][1]);
		GL11.glEnd();
	}

	/*************************************************************************
	 * Obtains a display list that renders the polygon represented by the
	 * supplied vertices.
	 * 
	 * The results of this method are cached for future searches.
	 * 
	 * This method is bound by the same constraints as renderPolygon().
	 * 
	 * @param vertices
	 *            The vertices of the shape to render.
	 * 
	 * @return The ID of a display list that will accelerate rendering of the
	 *         indicated shape.
	 *************************************************************************/
	public static int definePolygon(float[][] vertices)
	{
		// Searches all cached DisplayLists for the ID.
		for(Entry<Float[][], Integer> entry : cachedPolygons.entrySet())
		{
			search:
				if(entry.getKey().length == vertices.length)
				{
					for(int i = 0; i < vertices.length; i++)
						if(vertices[i][0] != entry.getKey()[i][0] ||
								vertices[i][1] != entry.getKey()[i][1])
							break search;
					return entry.getValue();
				}
		}
		
		// Complies a new DisplayList.
		Integer newDisplayListID = GL11.glGenLists(1);
		GL11.glNewList(newDisplayListID, GL11.GL_COMPILE);
			renderPolygon(vertices);
		GL11.glEndList();
		
		Float[][] converted = new Float[vertices.length][2];
		for(int i = 0; i < vertices.length; i++)
		{
			converted[i][0] = vertices[i][0];
			converted[i][1] = vertices[i][1];
		}
		
		// Places the DisplayListID in the cache.
		cachedPolygons.put(converted, newDisplayListID);
		
		// Returns the displayList ID.
		return newDisplayListID;
	}

	/*************************************************************************
	 * Wipes all contained data.
	 *************************************************************************/
	public static void destroy()
	{
		GL11.glDeleteLists(rectangleDLID, 1);
		GL11.glDeleteLists(triangleDLID, 1);
		GL11.glDeleteLists(circleDLID, 1);
		for (Integer DLID : regPolyDLs.values())
			GL11.glDeleteLists(DLID, 1);
		regPolyDLs.clear();
		valid = false;
	}
}