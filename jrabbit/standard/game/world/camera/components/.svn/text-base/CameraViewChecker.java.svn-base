package org.jrabbit.standard.game.world.camera.components;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Scalar;
import org.jrabbit.base.graphics.transforms.listeners.RotationListener;
import org.jrabbit.base.graphics.transforms.listeners.ScalarListener;
import org.jrabbit.base.graphics.types.Viewer;
import org.jrabbit.base.managers.window.WindowManager;

/*****************************************************************************
 * A CameraViewCheck handles checking to see if objects in the game world are 
 * being viewed by the camera. This allows the game to stop offscreen objects
 * from being rendered, speeding up render time.
 * 
 * This onscreen-checking is fairly optimized, but it is a bit complex.
 * Basically, the CameraViewChecker caches all variables used in calculations
 * that can be cached, and uses a "lossy" equation to determine onscreen-ness
 * that is much faster than calculating trig variables for each check.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CameraViewChecker implements Viewer, RotationListener, 
		ScalarListener, Updateable
{
	/**
	 * The rotation that determines the angle of the viewed scene.
	 **/
	protected Rotation rotation;
	
	/**
	 * The Scalar that determines the scale of the viewed scene.
	 **/
	protected Scalar scalar;
	
	/**
	 * Whether or not the CameraViewChecker should recalculate the variables it
	 * uses to determine culling.
	 **/
	boolean needsRecalc;

	/**
	 * Half the width of the viewed scene; used in culling.
	 **/
	protected float viewWidthVal = WindowManager.controller().width() / 2;

	/**
	 * Half the height of the viewed scene; used in culling.
	 **/
	protected float viewHeightVal = WindowManager.controller().height() / 2;
	
	/**
	 * A variable used to transform the unrotated coordinates into sensible
	 * values.
	 **/
	protected float xToX;
	
	/**
	 * A variable used to transform the unrotated coordinates into sensible
	 * values.
	 **/
	protected float xToY;
	
	/**
	 * A variable used to transform the unrotated coordinates into sensible
	 * values.
	 **/
	protected float yToX;
	
	/**
	 * A variable used to transform the unrotated coordinates into sensible
	 * values.
	 **/
	protected float yToY;
	
	/**
	 * The amount to scale the indicated 
	 **/
	protected float radiusScale;
	
	/*************************************************************************
	 * Creates a CameraViewChecker that uses the indicated rotation and scaling
	 * objects to calculate culling.
	 * 
	 * @param rotation
	 * 			  The Rotation to use for culling.
	 * @param scalar
	 * 			  The Scalar to use for culling.
	 *************************************************************************/
	public CameraViewChecker(Rotation rotation, Scalar scalar)
	{
		this.rotation = rotation;
		this.scalar = scalar;
		rotation.addListener(this);
		scalar.addListener(this);
		recalculate();
	}

	/*************************************************************************
	 * Determines if the object described is onscreen.
	 * 
	 * @param x
	 * 			  The relative x coordinate to the center of the view.
	 * @param y
	 * 			  The relative y coordinate to the center of the view.
	 * @param radius
	 * 			  The radius that the object fits within.
	 * 
	 * @return True if the object can conceivably be viewed, regardless of its
	 * 		   rotation, otherwise false.
	 ***************************************************************/
	@Override
	public boolean views(float x, float y, float radius)
	{
		float x2 = (x * xToX) + (y * yToX);
		float y2 = (y * yToY) + (x * xToY);
		float r2 = radius * radiusScale;
		return 	x2 + r2 > -viewWidthVal &&  x2 - r2 < viewWidthVal && 
				y2 + r2 > -viewHeightVal &&  y2 - r2 < viewHeightVal;
	}

	/*************************************************************************
	 * Flags that recalculation needs to occur, since the rotation of the view
	 * has been changed.
	 * 
	 * @param rotation
	 * 			  The Rotation that was rotated.
	 ***************************************************************/
	@Override
	public void rotated(Rotation rotation) { needsRecalc = true; }

	/*************************************************************************
	 * Flipping does not actually affect the results of culling, so this is
	 * safely ignored.
	 * 
	 * @param scalar
	 * 			  The Scalar that was flipped.
	 ***************************************************************/
	@Override
	public void flipped(Scalar scalar) { }

	/*************************************************************************
	 * Flags that recalculation needs to occur, since the scaling of the view
	 * has been changed.
	 * 
	 * @param scalar
	 * 			  The Scalar that was scaled.
	 ***************************************************************/ @Override
	public void scaled(Scalar scalar) { needsRecalc = true; }
	
	/*************************************************************************
	 * Recalculates all values used to optimize viewing checking.
	 *************************************************************************/
	public void recalculate()
	{
		float radians = -rotation.theta();
		float xMult = scalar.xScale();
		float yMult = scalar.yScale();
		radiusScale = xMult > yMult ? xMult : yMult;
		xToX = (float) (Math.cos(radians) * xMult);
		xToY = (float) (Math.sin(radians) * yMult);
		yToX = (float) (-Math.sin(radians) * xMult);
		yToY = (float) (Math.cos(radians) * yMult);
		viewWidthVal = WindowManager.controller().width() / (2 * xMult);
		viewHeightVal = WindowManager.controller().height() / (2 * yMult);
		needsRecalc = false;
	}

	/*************************************************************************
	 * Resets the viewing dimensions, and checks to see if culling variables
	 * need to be recalculated.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		viewWidthVal = WindowManager.controller().width() /
				(2 * scalar.xScale());
		viewHeightVal = WindowManager.controller().height() /
				(2 * scalar.yScale());
		if(needsRecalc)
			recalculate();
	}
}