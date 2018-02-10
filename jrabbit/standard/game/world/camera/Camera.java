package org.jrabbit.standard.game.world.camera;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.GLGroupTransform;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Scalar;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.GLGroupTransformed;
import org.jrabbit.base.graphics.types.Located;
import org.jrabbit.base.graphics.types.Rotated;
import org.jrabbit.base.graphics.types.Scaled;
import org.jrabbit.base.graphics.types.Viewer;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.game.world.camera.components.*;

/*****************************************************************************
 * A Camera is an object used by a World to view a particular section of the 
 * game's "space." It functions like a real-world camera; it has a location, 
 * rotation, and x/y zoom (and, for fun, it can be "shaken").
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Camera implements Viewer, Located, Rotated, Scaled, Updateable, 
		GLGroupTransformed
{
	/**
	 * The base viewpoint of the Camera.
	 **/
	protected Vector2f baseLocation;
	
	/**
	 * This is the Vector2f that is actually used in setting the view and
	 * culling objects. 
	 * 
	 * Every frame, this is set to the same values as the base location, but 
	 * then it can be affected by camera shaking. In this way, the target 
	 * viewpoint remains consistent over time, but the camera shake has free 
	 * rein.
	 **/
	protected Vector2f viewedLocation;

	/**
	 * The base rotation of the Camera.
	 **/
	protected Rotation baseRotation;
	
	/**
	 * This is the Rotation that is actually used in rotating the view and
	 * culling objects.
	 * 
	 * Every frame, this is set to the same value as the base rotation, but 
	 * then it can be affected by camera shaking.
	 **/
	protected Rotation viewedRotation;

	/**
	 * The base scaling (i.e., zoom) of the Camera.
	 **/
	protected Scalar baseScalar;
	
	/**
	 * This is the Scalar that is actually used in zooming view and
	 * culling objects.
	 * 
	 * Every frame, this is set to the same value as the base rotation, but 
	 * then it can be affected by camera shaking.
	 **/
	protected Scalar viewedScalar;
	
	/**
	 * This is the object used to handle shaking the Camera.
	 **/
	protected CameraShaker shake;

	/**
	 * This is the object used to handle culling.
	 **/
	protected CameraViewChecker viewCalc;
	
	/**
	 * The dynamic list of GLTransforms applied by the camera.
	 **/
	protected GLGroupTransform transforms;
	
	/*************************************************************************
	 * Creates a Camera at [0, 0].
	 *************************************************************************/
	public Camera() 
	{
		baseLocation = new Vector2f();
		baseRotation = new Rotation();
		baseScalar = new Scalar();
		transforms = new GLGroupTransform(new CameraTransforms.ViewCenterer(),
				viewedRotation = new CameraTransforms.ViewRotation(),
				viewedScalar = new Scalar(), 
				viewedLocation = new CameraTransforms.ViewpointVector());
		viewCalc = new CameraViewChecker(viewedRotation, viewedScalar);
		shake = new CameraShaker(viewedLocation, viewedRotation, viewedScalar);
	}
	
	/*************************************************************************
	 * Creates a Camera at the indicated coordinates.
	 * 
	 * @param x
	 * 			  The x-coordinate the Camera should initially look at.
	 * @param y
	 * 			  The y-coordinate the Camera should initially look at.
	 *************************************************************************/
	public Camera(float x, float y)
	{
		this();
		baseLocation.set(x, y);
	}
	
	/*************************************************************************
	 * Creates a Camera at the indicated coordinates.
	 * 
	 * @param target
	 * 			  The coordinates the camera should initially look at.
	 *************************************************************************/
	public Camera(BaseVector2f target)
	{
		this();
		baseLocation.set(target);
	}
	
	/*************************************************************************
	 * Accesses the viewpoint of the Camera.
	 * 
	 * @return the Vector2f that conrtols where the Camera looks at.
	 ***************************************************************/ @Override
	public Vector2f location() { return baseLocation; }
	
	/*************************************************************************
	 * Accesses the rotation control of the Camera.
	 * 
	 * @return the Rotation used to rotate the view.
	 ***************************************************************/ @Override
	public Rotation rotation() { return baseRotation; }
	
	/*************************************************************************
	 * Accesses the "zoom" control of the camera.
	 * 
	 * @return The Scalar used to handle zooming the view.
	 ***************************************************************/ @Override
	public Scalar scalar() { return baseScalar; }
	
	/*************************************************************************
	 * Accesses the dynamic list of GLTransforms.
	 * 
	 * @return The GLGroupTransform that is applied to this Camera.
	 ***************************************************************/ @Override
	public GLGroupTransform transforms() { return transforms; }
	
	/*************************************************************************
	 * Accesses the current CameraShaker.
	 * 
	 * @return The object being used to shake the Camera.
	 *************************************************************************/
	public CameraShaker shake() { return shake; }

	/*************************************************************************
	 * Redefines the CameraShaker this camera uses to handle shaking.
	 * 
	 * @param shake
	 * 			  The new CameraShaker to use.
	 *************************************************************************/
	public void setShake(CameraShaker shake)
	{
		this.shake = shake;
	}
	
	/*************************************************************************
	 * Updates the Camera and its components.
	 * 
	 * @param delta
	 * 			  The number of milliseconds that have passed since the last 
	 *            update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		viewedLocation.set(baseLocation);
		viewedRotation.set(baseRotation);
		viewedScalar.set(baseScalar);
		shake.update(delta);
		viewCalc.update(delta);
	}
	
	/*************************************************************************
	 * Applies the Camera's transforms.
	 ***************************************************************/ @Override
	public void bind()
	{
		transforms.bind();
	}
	
	/*************************************************************************
	 * Releases the Camera's transforms.
	 ***************************************************************/ @Override
	public void release()
	{
		transforms.release();
	}
	
	/*************************************************************************
	 * Determines if the object described can be viewed by this Camera.
	 * 
	 * @param x
	 * 			  The x-coordinate the object is centered at.
	 * @param y
	 * 			  The y-coordinate the object is centered at.
	 * @param radius
	 * 			  The radius the object fits within.
	 * 
	 * @return True if the object is potentially visible, false otherwise.
	 ***************************************************************/ @Override
	public boolean views(float x, float y, float radius)
	{
		return viewCalc.views(x - viewedLocation.x(), 
								y - viewedLocation.y(), 
								radius);
	}
	
	/*************************************************************************
	 * Makes this the active camera in the active World. This uses GameManager
	 * to accomplish this.
	 *************************************************************************/
	public void makeActive()
	{
		GameManager.world().setCamera(this);
	}
}