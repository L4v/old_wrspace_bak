package org.jrabbit.standard.game.world.camera.components;

import java.util.Random;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Scalar;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * A CameraShaker "shakes" the Camera as a game progresses, giving the 
 * impression that the gameworld is being violently affected. It has two 
 * controls - a boolean for whether or not it should be active, and a value for 
 * how strong the shaking effect should be.
 * 
 * The default setup found here simply randomly shifts the viewpoint, rotates 
 * the camera's rotation, and slightly affects the camera's zoom by an amount 
 * determined shaking strength.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CameraShaker implements Updateable
{
	/**
	 * This is the viewpoint to affect.
	 **/
	protected Vector2f viewpoint;
	
	/**
	 * The rotation to affect.
	 **/
	protected Rotation rotation;
	
	/**
	 * The scaling to affect.
	 **/
	protected Scalar scalar;
	
	/**
	 * Whether or not the shaking should be considered active.
	 **/
	protected boolean active;
	
	/**
	 * How strong the affect should be.
	 **/
	protected float strength;
	
	/*************************************************************************
	 * Creates a CameraShaker that will jitter the supplied Camera.
	 * 
	 * NOTE: When initially created, shaking is enabled but the strength is set
	 * to 0.
	 * 
	 * @param viewpoint
	 * 			  The Vector2f to affect.
	 * @param rotation
	 * 			  The Rotation to affect.
	 * @param scalar
	 * 			  The Scalar to affect.
	 *************************************************************************/
	public CameraShaker(Vector2f viewpoint, Rotation rotation, Scalar scalar)
	{
		this.viewpoint = viewpoint;
		this.rotation = rotation;
		this.scalar = scalar;
		reset();
	}
	
	/*************************************************************************
	 * This resets the shaking controls to what they are initially - enabled but
	 * strength at 0.
	 *************************************************************************/
	public void reset()
	{
		active = true;
		strength = 0;
	}
	
	/*************************************************************************
	 * Accesses the shaking controls.
	 * 
	 * @return How strong the shaking effect is set to be.
	 *************************************************************************/
	public float strength() { return strength; }

	/*************************************************************************
	 * Redefines how strong the shaking should be.
	 * 
	 * Depending on the shaking implementation, different scales of "strong"
	 * might be in place; as it is, this shaking value gets somewhat out of 
	 * control at values past 5. 1-5 is a good range for shaking that should be 
	 * overtly noticeable, but levels less than 1.0 are good for subtle 
	 * "tremors."
	 * 
	 * @param strength
	 * 			  How strong the shaking should be.
	 *************************************************************************/
	public void set(float strength)
	{
		this.strength = strength;
	}
	
	/*************************************************************************
	 * Accesses the shaking controls.
	 * 
	 * @return Whether or not shaking is active.
	 *************************************************************************/
	public boolean active() { return active; }
	
	/*************************************************************************
	 * Redefines the active flag.
	 * 
	 * @param active
	 * 			  Whether or not shaking is enabled.
	 *************************************************************************/
	public void setActive(boolean active)
	{
		this.active = active;
	}

	/*************************************************************************
	 * Advances the shaking effect. If set to active, the viewpoint, rotation
	 * and scalar are shaken about.
	 * 
	 * @param delta
	 * 			  The number of microseconds that has elapsed since the last
	 *            update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(active)
		{
			Random r = Resources.random();
			viewpoint.addPolar(r.nextFloat() * strength, (float) (r.nextFloat()
					* Math.PI * 2));
			rotation.rotate((r.nextFloat() - 0.5f) * strength / 2f);
			scalar.scaleBy(1f + (r.nextFloat() - 0.5f) * strength / 50f);
		}
	}
}