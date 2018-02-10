package org.jrabbit.base.graphics.transforms;

import java.util.ArrayList;

import org.jrabbit.base.graphics.transforms.listeners.ScalarListener;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A Scalar is an object that scales (and flips) the OpenGL Modelview Matrix.
 * 
 * A Scalar can have listeners added to it. These listeners are alerted whenever
 * the Scalar changes.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Scalar implements GLTransform
{
	/**
	 * The multiplier that controls horizontal flipping.
	 **/
	protected int hFlip;

	/**
	 * The multiplier that controls vertical flipping.
	 **/
	protected int vFlip;

	/**
	 * The amount to scale the x-dimension.
	 **/
	protected float xScale;

	/**
	 * The amount to scale the x-dimension.
	 **/
	protected float yScale;

	/**
	 * The list of all objects listening to this Scalar's changes.
	 **/
	private ArrayList<ScalarListener> listeners;

	/*************************************************************************
	 * Creates a Scalar that does not change the OpenGL ModelviewMatrix and has
	 * an empty list of listeners.
	 *************************************************************************/
	public Scalar()
	{
		hFlip = 1;
		vFlip = 1;
		xScale = 1;
		yScale = 1;
		listeners = new ArrayList<ScalarListener>();
	}

	/*************************************************************************
	 * Adds a listener to the Scalar. This listener will be alerted whenever a
	 * change occurs.
	 * 
	 * @param listener
	 *            The listener to add.
	 *************************************************************************/
	public void addListener(ScalarListener listener)
	{
		listeners.add(listener);
	}

	/*************************************************************************
	 * Removes a listener from the Scalar.
	 * 
	 * @param listener
	 *            The listener to remove.
	 *************************************************************************/
	public void removeListener(ScalarListener listener)
	{
		listeners.remove(listener);
	}

	/*************************************************************************
	 * Alerts all listeners that the Scalar has been flipped.
	 *************************************************************************/
	protected void alertFlip()
	{
		for (ScalarListener listener : listeners)
		{
			listener.flipped(this);
		}
	}

	/*************************************************************************
	 * Alerts all listeners that the Scalar has been scaled.
	 *************************************************************************/
	protected void alertScale()
	{
		for (ScalarListener listener : listeners)
		{
			listener.scaled(this);
		}
	}

	/*************************************************************************
	 * Sets the Scalar to the default values.
	 *************************************************************************/
	public void reset()
	{
		setScale(1, 1);
		setFlip(false, false);
	}

	/*************************************************************************
	 * Makes this Scalar have the same scaling factors as the other.
	 * 
	 * @param scalar
	 *            The Scalar to copy.
	 *************************************************************************/
	public void set(Scalar scalar)
	{
		xScale = scalar.xScale;
		yScale = scalar.yScale;
		hFlip = scalar.hFlip;
		vFlip = scalar.vFlip;
		alertScale();
		alertFlip();
	}

	/*************************************************************************
	 * Redefines the flipping factors.
	 * 
	 * @param hFlip
	 *            Whether or not to flip horizontally.
	 * @param vFlip
	 *            Whether or not to flip vertically.
	 *************************************************************************/
	public void setFlip(boolean hFlip, boolean vFlip)
	{
		this.hFlip = hFlip ? -1 : 1;
		this.vFlip = vFlip ? -1 : 1;
		alertFlip();
	}

	/*************************************************************************
	 * Learns if the scalar has been flipped horizontally.
	 * 
	 * @return True if flipped, false if not.
	 *************************************************************************/
	public boolean isFlippedHorizontally()
	{
		return hFlip != 1;
	}

	/*************************************************************************
	 * Flips the scalar horizontally.
	 *************************************************************************/
	public void flipHorizontally()
	{
		hFlip *= -1;
		alertFlip();
	}

	/*************************************************************************
	 * Redefines the scalar's flipping.
	 * 
	 * @param flip
	 *            Whether or not the scalar should flip horizontally.
	 *************************************************************************/
	public void setFlipHorizontally(boolean flip)
	{
		hFlip = flip ? -1 : 1;
		alertFlip();
	}

	/*************************************************************************
	 * Learns if the scalar has been flipped vertically.
	 * 
	 * @return True if flipped, false if not.
	 *************************************************************************/
	public boolean isFlippedVertically()
	{
		return vFlip != -1;
	}

	/*************************************************************************
	 * Flips the scalar vertically.
	 *************************************************************************/
	public void flipVertically()
	{
		vFlip *= -1;
		alertFlip();
	}

	/*************************************************************************
	 * Redefines the scalar's flipping.
	 * 
	 * @param flip
	 *            Whether or not the scalar should flip vertically.
	 *************************************************************************/
	public void setFlipVertically(boolean flip)
	{
		vFlip = flip ? -1 : 1;
		alertFlip();
	}

	/*************************************************************************
	 * Obtains the x-transform used on OpenGL.
	 * 
	 * @return The float used to scale the x dimension of the Modelview Matrix.
	 *************************************************************************/
	public float transformX()
	{
		return xScale * hFlip;
	}

	/*************************************************************************
	 * Obtains the y-transform used on OpenGL.
	 * 
	 * @return The float used to scale the y dimension of the Modelview Matrix.
	 *************************************************************************/
	public float transformY()
	{
		return yScale * vFlip;
	}

	/*************************************************************************
	 * Multiplies the x scale.
	 * 
	 * @param ratio
	 *            The amount to multiply the x scale factor.
	 *************************************************************************/
	public void scaleXBy(float ratio)
	{
		xScale *= ratio;
		alertScale();
	}

	/*************************************************************************
	 * Multiplies the y scale.
	 * 
	 * @param ratio
	 *            The amount to multiply the y scale factor.
	 *************************************************************************/
	public void scaleYBy(float ratio)
	{
		yScale *= ratio;
		alertScale();
	}

	/*************************************************************************
	 * Multiplies the scalar.
	 * 
	 * @param ratio
	 *            The amount to multiply the scaling factors by.
	 *************************************************************************/
	public void scaleBy(float ratio)
	{
		xScale *= ratio;
		yScale *= ratio;
		alertScale();
	}

	/*************************************************************************
	 * Multiplies the values of this scalar by those of the target.
	 * 
	 * @param scalar
	 * 			  The scalar to multiply by.
	 *************************************************************************/
	public void scaleBy(Scalar scalar)
	{
		xScale *= scalar.xScale;
		yScale *= scalar.yScale;
		hFlip *= scalar.hFlip;
		vFlip *= scalar.vFlip;
		alertScale();
	}

	/*************************************************************************
	 * If an object is continuously scaled over time at a constant rate, its
	 * apparent increase does not appear to be linear. When its size is less
	 * than 1, it appears to shrink very quickly, but when its size is greater
	 * than 1, it appears to grow much more slowly. Using this method will
	 * smooth the process.
	 * 
	 * NOTE: This method is a bit inexact.
	 * 
	 * @param amount
	 *            The amount to increase the scale by.
	 *************************************************************************/
	public void scaleLinear(float amount)
	{
		xScale += amount * Math.sqrt(xScale);
		yScale += amount * Math.sqrt(yScale);
		alertScale();
	}

	/*************************************************************************
	 * Redefines the scaling factors.
	 * 
	 * @param scale
	 *            The new amount to scale both the x and y dimensions by.
	 *************************************************************************/
	public void setScale(float scale)
	{
		xScale = scale;
		yScale = scale;
		alertScale();
	}

	/*************************************************************************
	 * Redefines the scaling factors.
	 * 
	 * @param xScale
	 *            The new amount to scale the x dimension by.
	 * @param yScale
	 *            The new amount to scale the y dimension by.
	 *************************************************************************/
	public void setScale(float xScale, float yScale)
	{
		this.xScale = xScale;
		this.yScale = yScale;
		alertScale();
	}

	/*************************************************************************
	 * Redefines the x scaling factor.
	 * 
	 * @param xScale
	 *            The new scaling factor.
	 *************************************************************************/
	public void setXScale(float xScale)
	{
		this.xScale = xScale;
		alertScale();
	}

	/*************************************************************************
	 * Redefines the y scaling factor.
	 * 
	 * @param yScale
	 *            The new scaling factor.
	 *************************************************************************/
	public void setYScale(float yScale)
	{
		this.yScale = yScale;
		alertScale();
	}

	/*************************************************************************
	 * Returns the overall amount that the Scalar scales the Modelview Matrix.
	 * 
	 * If the x and y scaling factors are different, this returns the square
	 * root of their product.
	 * 
	 * @return The amount scaled.
	 *************************************************************************/
	public float scale()
	{
		return (float) Math.sqrt(xScale * yScale);
	}

	/*************************************************************************
	 * Accesses the x scaling factor.
	 * 
	 * @return The ratio by which the Scalar scales the x dimension.
	 *************************************************************************/
	public float xScale()
	{
		return xScale;
	}

	/*************************************************************************
	 * Accesses the y scaling factor.
	 * 
	 * @return The ratio by which the Scalar scales the y dimension.
	 *************************************************************************/
	public float yScale()
	{
		return yScale;
	}

	/*************************************************************************
	 * It's not recommended to have the scaling factors be zero or negative;
	 * this causes odd behavior. Scaling may still technically work, but it's
	 * better to simply use positive scaling and use flipping to achieve
	 * reversing.
	 * 
	 * @return Whether or not either scaling factor is less than or equal to 0.
	 *************************************************************************/
	public boolean improperlyScaled()
	{
		return xScale <= 0 || yScale <= 0;
	}

	/*************************************************************************
	 * Scales and flips the ModelviewMatrix.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glScalef(xScale * hFlip, yScale * vFlip, 1f);
	}

	/*************************************************************************
	 * Only scales the ModelviewMatrix (does not flip).
	 *************************************************************************/
	public void bindScale()
	{
		GL11.glScalef(xScale, yScale, 1f);
	}

	/*************************************************************************
	 * Only flips the ModelviewMatrix (does not scale).
	 *************************************************************************/
	public void bindFlip()
	{
		GL11.glScalef(hFlip, vFlip, 1f);
	}

	/*************************************************************************
	 * Scales and flips the ModelviewMatrix by the inverse of the value used to
	 * bind().
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glScalef(hFlip / xScale, vFlip / yScale, 1f);
	}

	/*************************************************************************
	 * As equals() in Object, but if the supplied object is a color, it checks
	 * to see if their color values are the same.
	 * 
	 * @param other
	 *            The object to check against.
	 * 
	 * @return Whether or not this object is equivalent to the target.
	 ***************************************************************/ @Override
	public boolean equals(Object other)
	{
		if (other instanceof Scalar)
		{
			Scalar o = (Scalar) other;
			return ((o.xScale == xScale) && (o.yScale == yScale)
					&& (o.hFlip == hFlip) && (o.vFlip == vFlip));
		}
		return false;
	}

	/*************************************************************************
	 * Obtains the Scalar as a String.
	 * 
	 * @return A String representation of the Scalar.
	 ***************************************************************/ @Override
	public String toString()
	{
		return "[" + (xScale * hFlip) + " x " + (yScale * vFlip) + "]";
	}
}