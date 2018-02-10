package org.jrabbit.base.graphics.transforms;

import java.util.ArrayList;

import org.jrabbit.base.graphics.transforms.listeners.VectorListener;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * Vector2f is the general-purpose vector class of jRabbit. It has an x and a y
 * coordinate, and has quite a lot of functionality for controlling them.
 * 
 * As a GLTransform, a Vector2f is used to translate the Modelview Matrix. This
 * lets it be used as a "location" for graphical objects, as they can set their
 * coordinate values and then translate their rendering to the appropriate
 * position.
 * 
 * A Vector2f can have listeners added to it. These listeners are alerted
 * whenever the Vector2f changes.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Vector2f implements GLTransform, BaseVector2f
{
	/**
	 * The x coordinate.
	 **/
	protected float x;

	/**
	 * The y coordinate.
	 **/
	protected float y;

	/**
	 * The list of listeners to alert whenever changes occur.
	 **/
	private ArrayList<VectorListener> listeners;

	/*************************************************************************
	 * Creates a vector at [0, 0].
	 *************************************************************************/
	public Vector2f()
	{
		this(0, 0);
	}

	/*************************************************************************
	 * Creates a vector at the indicated coordinates.
	 * 
	 * @param x
	 *            The initial x coordinate.
	 * @param y
	 *            The initial y coordinate.
	 *************************************************************************/
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
		listeners = new ArrayList<VectorListener>();
	}

	/*************************************************************************
	 * Creates a copy of this Vector2f.
	 * 
	 * @return A Vector2f with identical coordinates.
	 *************************************************************************/
	public Vector2f copy()
	{
		return new Vector2f(x, y);
	}

	/*************************************************************************
	 * Adds a listener to the Vector2f. This listener will be alerted whenever a
	 * change occurs.
	 * 
	 * @param listener
	 *            The listener to add.
	 *************************************************************************/
	public void addListener(VectorListener vL)
	{
		listeners.add(vL);
	}

	/*************************************************************************
	 * Removes a listener from the Vector2f.
	 * 
	 * @param listener
	 *            The listener to remove.
	 *************************************************************************/
	public void removeListener(VectorListener vL)
	{
		listeners.remove(vL);
	}

	/*************************************************************************
	 * Alerts all listeners that the Vector2f has been moved.
	 *************************************************************************/
	protected void alertChange()
	{
		for (VectorListener listener : listeners)
		{
			listener.moved(this);
		}
	}

	/*************************************************************************
	 * Sets the Vector2f to [0, 0].
	 *************************************************************************/
	public void reset()
	{
		x = y = 0;
	}

	/*************************************************************************
	 * Learns the x coordinate.
	 * 
	 * @return The x coordinate of the vector.
	 ***************************************************************/ @Override
	public float x()
	{
		return x;
	}

	/*************************************************************************
	 * Redefines the x coordinate.
	 * 
	 * @param x
	 *            The new value for the x coordinate.
	 *************************************************************************/
	public void setX(float x)
	{
		this.x = x;
		alertChange();
	}

	/*************************************************************************
	 * Learns the y coordinate.
	 * 
	 * @return The y coordinate of the vector.
	 ***************************************************************/ @Override
	public float y()
	{
		return y;
	}

	/*************************************************************************
	 * Redefines the y coordinate.
	 * 
	 * @param y
	 *            The new value for the y coordinate.
	 *************************************************************************/
	public void setY(float y)
	{
		this.y = y;
		alertChange();
	}

	/*************************************************************************
	 * Redefines the vector.
	 * 
	 * @param x
	 *            The new x coordinate.
	 * @param y
	 *            The new y coordinate.
	 *************************************************************************/
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
		alertChange();
	}

	/*************************************************************************
	 * Redefines the vector to have the same coordinates as another.
	 * 
	 * @param vector
	 *            The vector to copy.
	 *************************************************************************/
	public void set(BaseVector2f vector)
	{
		set(vector.x(), vector.y());
	}

	/*************************************************************************
	 * Redefines the vector from instructions in polar coordinates.
	 * 
	 * @param theta
	 *            The radians of the polar angle.
	 * @param magnitude
	 *            The length of the polar vector.
	 *************************************************************************/
	public void setPolar(float theta, float magnitude)
	{
		set((float) (Math.cos(theta) * magnitude),
				(float) (Math.sin(theta) * magnitude));
	}

	/*************************************************************************
	 * Shifts the x coordinate.
	 * 
	 * @param shiftX
	 *            The amount to add.
	 *************************************************************************/
	public void addX(float shiftX)
	{
		x += shiftX;
		alertChange();
	}

	/*************************************************************************
	 * Shifts the y coordinate.
	 * 
	 * @param shiftY
	 *            The amount to add.
	 *************************************************************************/
	public void addY(float shiftY)
	{
		y += shiftY;
		alertChange();
	}

	/*************************************************************************
	 * Shifts the coordinates.
	 * 
	 * @param shiftX
	 *            The amount to add to the x coordinate.
	 * @param shiftY
	 *            The amount to add to the y coordinate.
	 *************************************************************************/
	public void add(float shiftX, float shiftY)
	{
		x += shiftX;
		y += shiftY;
		alertChange();
	}

	/*************************************************************************
	 * Shifts the vector via polar coordinates.
	 * 
	 * @param magnitude
	 *            The magnitude of the shift.
	 * @param theta
	 *            The angle of the shift.
	 *************************************************************************/
	public void addPolar(float magnitude, float theta)
	{
		add((float) (Math.cos(theta) * magnitude),
				(float) (Math.sin(theta) * magnitude));
	}

	/*************************************************************************
	 * Adds another vector's coordinates.
	 * 
	 * @param vector
	 *            The vector to add.
	 *************************************************************************/
	public void add(BaseVector2f vector)
	{
		add(vector.x(), vector.y());
	}

	/*************************************************************************
	 * Adds another vector's coordinates, scaled by the supplied multiplier.
	 * 
	 * @param vector
	 *            The vector to add.
	 * @param multiplier
	 *            The amount to multiply the vector's values.
	 *************************************************************************/
	public void add(BaseVector2f vector, float multiplier)
	{
		add(vector.x() * multiplier, vector.y() * multiplier);
	}

	/*************************************************************************
	 * Moves the vector's coordinates closer to the target coordinates by a
	 * certain amount.
	 * 
	 * Obviously, this is intended to make generic AI simpler.
	 * 
	 * @param targetX
	 *            The x coordinate to move towards.
	 * @param targetY
	 *            The y coordinate to move towards.
	 * @param distance
	 *            The maximum distance to move.
	 *************************************************************************/
	public void moveTowards(float targetX, float targetY, float distance)
	{
		double diffX = targetX - x;
		double diffY = targetY - y;
		double magnitude = Math.sqrt(diffX * diffX + diffY * diffY);

		if (magnitude < distance)
		{
			x = targetX;
			y = targetY;
		}
		else
		{
			x += diffX * distance / magnitude;
			y += diffY * distance / magnitude;
		}

		alertChange();
	}

	/*************************************************************************
	 * Moves the vector's coordinates closer to the target vector by a certain
	 * amount.
	 * 
	 * @param target
	 *            The vector to move towards.
	 * @param distance
	 *            The maximum distance to move.
	 *************************************************************************/
	public void moveTowards(BaseVector2f target, float amount)
	{
		moveTowards(target.x(), target.y(), amount);
	}

	/*************************************************************************
	 * "Shrinks" the vector by the indicated amount. The vector keeps its
	 * proportional x/y ratio, but its magnitude is lessened by the indicated
	 * amount.
	 * 
	 * NOTE: If the amount to decrease is greater than the vector's current
	 * magnitude, the vector is set to [0, 0].
	 * 
	 * @param amount
	 *            The amount to decrease.
	 *************************************************************************/
	public void decrease(float amount)
	{
		double magnitude = Math.sqrt(x * x + y * y);
		if (magnitude <= amount)
			x = y = 0;
		else if (magnitude > 0)
		{
			x -= amount * (x / magnitude);
			y -= amount * (y / magnitude);
		}
		alertChange();
	}

	/*************************************************************************
	 * "Shrinks" the x coordinate of the vector.
	 * 
	 * If the amount to shrink is larger than the magnitude of the x coordinate,
	 * the x coordinate is set to 0.
	 * 
	 * @param amount
	 *            The amount to shrink the x coordinate.
	 *************************************************************************/
	public void decreaseX(float amount)
	{
		if (Math.abs(x) < amount)
			x = 0;
		else if (x < 0)
			x += amount;
		else
			x -= amount;
		alertChange();
	}

	/*************************************************************************
	 * "Shrinks" the y coordinate of the vector.
	 * 
	 * If the amount to shrink is larger than the magnitude of the y coordinate,
	 * the y coordinate is set to 0.
	 * 
	 * @param amount
	 *            The amount to shrink the y coordinate.
	 *************************************************************************/
	public void decreaseY(float amount)
	{
		if (Math.abs(y) < amount)
			y = 0;
		else if (y < 0)
			y += amount;
		else
			y -= amount;
		alertChange();
	}

	/*************************************************************************
	 * Scales the vector.
	 * 
	 * @param amount
	 *            The amount to multiply the coordinates by.
	 *************************************************************************/
	public void multiply(float amount)
	{
		x *= amount;
		y *= amount;
		alertChange();
	}

	/*************************************************************************
	 * Divides the vector.
	 * 
	 * @param amount
	 *            The amount to divide the coordinates by.
	 *************************************************************************/
	public void divide(float amount)
	{
		x /= amount;
		y /= amount;
		alertChange();
	}

	/*************************************************************************
	 * Learns the size of the vector.
	 * 
	 * @return The magnitude of this Vector2f.
	 *************************************************************************/
	public float magnitude()
	{
		return (float) Math.sqrt(x * x + y * y);
	}

	/*************************************************************************
	 * Ensures that the vector does not have a magnitude greater than the one
	 * supplied. If the magnitude is greater, it is scaled to the appropriate
	 * size.
	 * 
	 * @param magnitude
	 *            The maximum magnitude allowed to the vector.
	 *************************************************************************/
	public void cap(float magnitude)
	{
		double proportion = Math.sqrt(x * x + y * y) / magnitude;
		if (proportion > 1)
		{
			x /= proportion;
			y /= proportion;
			alertChange();
		}
	}

	/*************************************************************************
	 * Creates a vector that represents the difference between this vector and
	 * the other.
	 * 
	 * @param vector
	 *            The vector to check against.
	 * 
	 * @return The difference between the two vectors.
	 *************************************************************************/
	public Vector2f difference(BaseVector2f vector)
	{
		return new Vector2f(x - vector.x(), y - vector.y());
	}

	/*************************************************************************
	 * Calculates the squared distance between this and another vector.
	 * 
	 * @param vector
	 *            The vector to check against.
	 * 
	 * @return The squared distance from this vector to the other.
	 *************************************************************************/
	public float distanceSquared(Vector2f vector)
	{
		return distanceSquared(vector.x(), vector.y());
	}

	/*************************************************************************
	 * Calculates the squared distance between this and the indicated
	 * coordinates.
	 * 
	 * @param x
	 *            The target x coordinate.
	 * @param y
	 *            The target y coordinate.
	 * 
	 * @return The squared distance from this vector to the target coordinates.
	 *************************************************************************/
	public float distanceSquared(float x, float y)
	{
		float diffX = this.x - x;
		float diffY = this.y - y;
		return (diffX * diffX) + (diffY * diffY);
	}

	/*************************************************************************
	 * Calculates the distance between this and another vector.
	 * 
	 * @param vector
	 *            The vector to check against.
	 * 
	 * @return The distance from this vector to the other.
	 *************************************************************************/
	public float distanceTo(BaseVector2f vector)
	{
		return distanceTo(vector.x(), vector.y());
	}

	/*************************************************************************
	 * Calculates the distance between this and the indicated coordinates.
	 * 
	 * @param x
	 *            The target x coordinate.
	 * @param y
	 *            The target y coordinate.
	 * 
	 * @return The distance from this vector to the target coordinates.
	 *************************************************************************/
	public float distanceTo(float x, float y)
	{
		float diffX = this.x - x;
		float diffY = this.y - y;
		return (float) Math.sqrt((diffX * diffX) + (diffY * diffY));
	}

	/*************************************************************************
	 * Creates a scaled version of this vector with a magnitude of 1.
	 * 
	 * @return A unit vector with the same x/y ratio as this one.
	 *************************************************************************/
	public Vector2f unitVector()
	{
		float magnitude = (float) Math.sqrt((x * x) + (y * y));
		return magnitude == 0 ? new Vector2f() : new Vector2f(x / magnitude, y
				/ magnitude);
	}

	/*************************************************************************
	 * Creates a unit vector angled towards the target.
	 * 
	 * @param target
	 *            The vector that the unit vector should be in the direction
	 *            of.
	 * 
	 * @return A vector of magnitude 1 that extends from this vector towards the
	 *         target.
	 *************************************************************************/
	public Vector2f unitVectorTowards(BaseVector2f target)
	{
		return unitVectorTowards(target.x(), target.y());
	}

	/*************************************************************************
	 * Creates a unit vector angled towards the target coordinates.
	 * 
	 * @param x
	 *            The target x coordinate.
	 * @param y
	 *            The target y coordinate.
	 * 
	 * @return A vector of magnitude 1 that extends from this vector towards the
	 *         target coordinates.
	 *************************************************************************/
	public Vector2f unitVectorTowards(float x, float y)
	{
		float diffX = x - this.x;
		float diffY = y - this.y;
		float magnitude = (float) Math.sqrt((diffX * diffX) + (diffY * diffY));
		return magnitude == 0 ? new Vector2f() : new Vector2f(
				diffX / magnitude, diffY / magnitude);
	}

	/*************************************************************************
	 * Returns the degrees of rotation this vector has when converted to polar
	 * coordinates.
	 * 
	 * @return The angle of direction of this vector, in clockwise degrees.
	 *************************************************************************/
	public float angle()
	{
		return (float) Math.toDegrees(Math.atan2(y, x));
	}

	/*************************************************************************
	 * Calculates the polar angle from this vector to the target.
	 * 
	 * @param vector
	 *            The target vector.
	 * 
	 * @return The angle, in degrees, towards the target.
	 *************************************************************************/
	public float angleTowards(BaseVector2f vector)
	{
		return angleTowards(vector.x(), vector.y());
	}

	/*************************************************************************
	 * Calculates the polar angle from this vector to the target coordinates.
	 * 
	 * @param x
	 *            The target x coordinate.
	 * @param y
	 *            The target y coordinate.
	 * 
	 * @return The angle, in degrees, towards the target.
	 *************************************************************************/
	public float angleTowards(float x, float y)
	{
		return (float) Math.toDegrees(Math.atan2(y - this.y, x - this.x));
	}

	/*************************************************************************
	 * Translates the OpenGL Modelview Matrix by the x and y values of this
	 * vector.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glTranslatef(x, y, 0);
	}

	/*************************************************************************
	 * Translates the OpenGL Modelview Matrix by the negatives of the x and y
	 * values of this vector.
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glTranslatef(-x, -y, 0);
	}

	/*************************************************************************
	 * Tells OpenGL to create a vertex at this vector's coordinates. This should
	 * be called between a glBegin() and a glEnd().
	 *************************************************************************/
	public void vertex()
	{
		GL11.glVertex2f(x, y);
	}

	/*************************************************************************
	 * As equals() in Object, but if the supplied object is a Vector2f, it
	 * checks to see if their coordinates are the same.
	 * 
	 * @param other
	 *            The object to check against.
	 * 
	 * @return Whether or not this object is equivalent to the target.
	 ***************************************************************/ @Override
	public boolean equals(Object other)
	{
		if (other instanceof BaseVector2f)
		{
			BaseVector2f o = (BaseVector2f) other;
			return ((o.x() == x) && (o.y() == y));
		}
		return false;
	}

	/*************************************************************************
	 * Obtains the Vector2f as a String.
	 * 
	 * @return A String representation of the Vector2f.
	 ***************************************************************/ @Override
	public String toString()
	{
		return "[" + x + " x " + y + "]";
	}
}