package org.jrabbit.standard.game.objects.base;

import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Scalar;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Dimensioned;
import org.jrabbit.base.graphics.types.Located;
import org.jrabbit.base.graphics.types.Rotated;
import org.jrabbit.base.graphics.types.Scaled;

/*****************************************************************************
 * A Spatial is a generic game object. It has no rendering capability, but
 * represents an entity in a 2-dimensional space.
 * 
 * To this end, it has a width and a height, a location, rotation, and an x-y 
 * scale.
 * 
 * Spatial is abstract; it simply provides this base functionality to whatever
 * object may need it. It should be noted that the methods to detect width and
 * height are undefined; these are dependent upon implementation.
 * 
 * A Spatial by itself is by no means Renderable, and has no interface with any
 * graphics settings or functionality.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class Spatial implements Dimensioned, Located, Rotated, Scaled
{
	/**
	 * The location of the Spatial.
	 **/
	protected Vector2f location;
	
	/**
	 * The rotation of the Spatial.
	 **/
	protected Rotation rotation;
	
	/**
	 * The scaling of the Spatial.
	 **/
	protected Scalar scalar;
	
	/*************************************************************************
	 * Creates an unrotated, unscaled Spatial at location [0, 0].
	 *************************************************************************/
	public Spatial()
	{
		location = new Vector2f();
		rotation = new Rotation();
		scalar = new Scalar();
	}
	
	/*************************************************************************
	 * Accesses the Spatial's location.
	 * 
	 * @return The Vector2f that controls the location of the Spatial.
	 ***************************************************************/ @Override
	public Vector2f location() { return location; }
		
	/*************************************************************************
	 * Accesses the Spatial's rotation.
	 * 
	 * @return The Rotation object that controls the rotation of the Spatial.
	 ***************************************************************/ @Override
	public Rotation rotation() { return rotation; }
		
	/*************************************************************************
	 * Accesses the Spatial's scaling.
	 * 
	 * @return The Scalar object that controls the scaling of the Spatial.
	 ***************************************************************/ @Override
	public Scalar scalar() { return scalar; }
	
	/*************************************************************************
	 * Accesses the object's (scaled) dimensions.
	 * 
	 * @return The scaled width of the object.
	 *************************************************************************/
	public float scaledWidth()
	{
		return width() * scalar.xScale();
	}
	
	/*************************************************************************
	 * Accesses the object's (scaled) dimensions.
	 * 
	 * @return The scaled height of the object.
	 *************************************************************************/
	public float scaledHeight()
	{
		return height() * scalar.yScale();
	}
	
	/*************************************************************************
	 * Accesses the object's (scaled) dimensions.
	 * 
	 * @return The scaled diameter of the object. This value assumes that the
	 *         Spatial can be considered a rectangle of the width and height 
	 *         found by getScaledWidth() and getScaledHeight().
	 *************************************************************************/
	public float scaledDiameter()
	{
		float width = scaledWidth();
		float height = scaledHeight();
		return (float) (Math.sqrt(width * width + height * height));
	}
}