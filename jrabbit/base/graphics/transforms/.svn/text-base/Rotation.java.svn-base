package org.jrabbit.base.graphics.transforms;

import java.util.ArrayList;

import org.jrabbit.base.graphics.transforms.listeners.RotationListener;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A Rotation is a convenience class that represents the rotation of an object.
 * It has a rotation (in clockwise degrees), and some convenience methods to
 * affect it. These methods include operations like rotating by a certain amount
 * and rotating towards a specific point.
 * 
 * A Rotation can have listeners added to it. These listeners are alerted
 * whenever the Rotation changes.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Rotation implements GLTransform
{
	/**
	 * The degrees of this Rotation.
	 **/
	protected float degrees;

	/**
	 * The list of all objects listening to this Rotation's changes.
	 **/
	private ArrayList<RotationListener> listeners;

	/*************************************************************************
	 * Creates a Rotation of 0 degrees with an empty list of listeners.
	 *************************************************************************/
	public Rotation()
	{
		degrees = 0;
		listeners = new ArrayList<RotationListener>();
	}

	/*************************************************************************
	 * Creates a Rotation with an initial setting and an empty list of
	 * listeners.
	 * 
	 * @param degrees
	 *            The angle the Rotation is initially set to.
	 *************************************************************************/
	public Rotation(float degrees)
	{
		this.degrees = degrees % 360;
		listeners = new ArrayList<RotationListener>();
	}

	/*************************************************************************
	 * Creates a copy of the current Rotation.
	 * 
	 * @return A Rotation that has the same angle as this one.
	 *************************************************************************/
	public Rotation copy()
	{
		return new Rotation(degrees);
	}

	/*************************************************************************
	 * Adds a listener to the Rotation. This listener will be alerted whenever a
	 * change occurs.
	 * 
	 * @param listener
	 *            The listener to add.
	 *************************************************************************/
	public void addListener(RotationListener listener)
	{
		listeners.add(listener);
	}

	/*************************************************************************
	 * Removes a listener from the Rotation.
	 * 
	 * @param listener
	 *            The listener to remove.
	 *************************************************************************/
	public void removeListener(RotationListener listener)
	{
		listeners.remove(listener);
	}

	/*************************************************************************
	 * Alerts all listeners that the Rotation has changed.
	 *************************************************************************/
	protected void alertChange()
	{
		for (RotationListener listener : listeners)
			listener.rotated(this);
	}

	/*************************************************************************
	 * Learns the current rotation.
	 * 
	 * @return The clockwise degrees this is currently rotated.
	 *************************************************************************/
	public float degrees()
	{
		return degrees;
	}

	/*************************************************************************
	 * Learns the current rotation, in radians.
	 * 
	 * @return The clockwise radians this is currently rotated.
	 *************************************************************************/
	public float theta()
	{
		return degrees / 57.2957795130f;
	}

	/*************************************************************************
	 * Sets the Rotation to 0 degrees.
	 *************************************************************************/
	public void reset()
	{
		set(0);
	}

	/*************************************************************************
	 * Redefines the Rotation to have the same angle as another.
	 * 
	 * @param rotation
	 *            The Rotation to copy.
	 *************************************************************************/
	public void set(Rotation rotation)
	{
		set(rotation.degrees);
	}

	/*************************************************************************
	 * Redefines the rotation.
	 * 
	 * @param degrees
	 *            The new degrees of rotation.
	 *************************************************************************/
	public void set(float degrees)
	{
		this.degrees = degrees % 360;
		alertChange();
	}

	/*************************************************************************
	 * Shifts the rotation.
	 * 
	 * @param degrees
	 *            The amount of degrees to add to the current rotation.
	 *************************************************************************/
	public void rotate(float degrees)
	{
		set(this.degrees + degrees);
	}

	/*************************************************************************
	 * Defines the Rotation to have the same rotation as the angle between the
	 * two locations.
	 * 
	 * @param start
	 *            The starting coordinates.
	 * @param target
	 *            The coordinates to rotate towards.
	 *************************************************************************/
	public void rotateToFace(Vector2f start, Vector2f target)
	{
		set(start.angleTowards(target));
	}

	/*************************************************************************
	 * Similar to rotateToFace(), but gradually rotates instead of completely
	 * changing rotation.
	 * 
	 * The most obvious use for this method to to make aiming/following AI a
	 * little simpler.
	 * 
	 * @param start
	 *            The starting coordinates.
	 * @param target
	 *            The coordinates to rotate towards.
	 * @param degreesToTurn
	 *            The maximum degrees to turn.
	 *************************************************************************/
	public void rotateTowards(Vector2f start, Vector2f target,
			float degreesToTurn)
	{
		rotateTowards(start.angleTowards(target), degreesToTurn);
	}

	/*************************************************************************
	 * Rotates the Rotation closer to the target angle. If the amount left to
	 * rotate is less than the rotation allowed, then the rotation is set to the
	 * target angle.
	 * 
	 * @param targetAngle
	 *            The angle to rotate towards.
	 * @param degreesToTurn
	 *            The degrees permitted to rotate.
	 *************************************************************************/
	public void rotateTowards(float targetAngle, float degreesToTurn)
	{
		switch (getRotationDirection(targetAngle, degreesToTurn))
		{
		case -1:
			rotate(-degreesToTurn);
			break;
		case 0:
			set(targetAngle);
			break;
		case 1:
			rotate(degreesToTurn);
			break;
		}
	}

	/*************************************************************************
	 * Indicates whether or not this Rotation needs to rotate clockwise or
	 * counter-clockwise to face the indicated angle. If the target angle is
	 * less than the allowed degrees of rotation away, this method indicates
	 * that the Rotation can simply face the target angle immediately.
	 * 
	 * @param targetAngle
	 *            The angle to rotate towards.
	 * @param degreesToTurn
	 *            The degrees permitted to rotate.
	 * 
	 * @return 0 if the target angle is less than the degrees of freedom away, 1
	 *         if clockwise rotation is most efficient, and -1 if
	 *         counter-clockwise.
	 *************************************************************************/
	public int getRotationDirection(float targetAngle, float degreesToTurn)
	{
		float offset = targetAngle - this.degrees;

		if (offset < -180)
			offset += 360;
		else if (offset > 180)
			offset -= 360;

		if (offset > degreesToTurn)
			return 1;
		else if (offset < -degreesToTurn)
			return -1;
		return 0;
	}

	/*************************************************************************
	 * Creates a unit vector from this rotation's angle.
	 * 
	 * @return A new Vector2f that corresponds with this angle on the unit
	 *         circle.
	 *************************************************************************/
	public Vector2f unitVector()
	{
		float theta = theta();
		return new Vector2f((float) Math.cos(theta), (float) Math.sin(theta));
	}

	/*************************************************************************
	 * Rotates the OpenGL matrix by the degrees in this Rotation.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glRotatef(degrees, 0, 0, 1);
	}

	/*************************************************************************
	 * Rotates the OpenGL matrix by the negative of the degrees in this
	 * Rotation.
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glRotatef(-degrees, 0, 0, 1);
	}

	/*************************************************************************
	 * As equals() in Object, but if the supplied object is a Rotation, it
	 * checks to see if their angle of rotation is the same.
	 * 
	 * @param other
	 *            The object to check against.
	 * 
	 * @return Whether or not this object is equivalent to the target.
	 ***************************************************************/ @Override
	public boolean equals(Object other)
	{
		if (other instanceof Rotation)
			return (((Rotation) other).degrees == degrees);
		return false;
	}

	/*************************************************************************
	 * Obtain the Rotation as a String.
	 * 
	 * @return A String representation of the Rotation.
	 ***************************************************************/ @Override
	public String toString()
	{
		return "[" + degrees + " degrees]";
	}
}