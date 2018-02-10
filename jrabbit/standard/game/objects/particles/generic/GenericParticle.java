package org.jrabbit.standard.game.objects.particles.generic;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.standard.game.objects.particles.base.Particle;

/*****************************************************************************
 * A GenericParticle is a particle along the lines of most particle systems; it
 * has a velocity and acceleration , rotation rate, scaling rate, and fade rate.
 * It is destroyed when either its scaling or its transparency reaches 0.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GenericParticle extends Particle
{
	/**
	 * The particle's velocity.
	 **/
	protected Vector2f velocity;
	
	/**
	 * The particle's acceleration.
	 **/
	protected Vector2f acceleration;
	
	/**
	 * The particle's rate of rotation.
	 **/
	protected float spinRate;
	
	/**
	 * The particle's scaling rate.
	 **/
	protected float scaleRate;
	
	/**
	 * The rate at which the particle fades out of existence.
	 **/
	protected float fadeRate;

	/*************************************************************************
	 * Creates a generic particle with the indicated settings.
	 * 
	 * @param vX
	 *            The x-velocity of the particle.
	 * @param vY
	 *            They y-velocity of the particle.
	 * @param aX
	 *            The x-acceleration of the particle.
	 * @param aY
	 *            The y-acceleration of the particle.
	 * @param scaleRate
	 *            The rate at which the particle is scaled over time.
	 * @param spinRate
	 *            The rate at which the particle is rotated.
	 * @param lifetime
	 *            The duration of the fadeout of the particle, in seconds.
	 *************************************************************************/
	public GenericParticle(float vX, float vY, float aX, float aY,
			float scaleRate, float spinRate, float lifetime)
	{
		this(new Vector2f(vX, vY), new Vector2f(aX, aY), scaleRate, spinRate, 
				lifetime);
	}
	
	/*************************************************************************
	 * Creates a GenericParticle with the indicated settings.
	 * 
	 * @param velocity
	 *            The particle's velocity.
	 * @param acceleration
	 *            The particle's acceleration.
	 * @param scaleRate
	 *            The rate at which the particle is scaled over time.
	 * @param spinRate
	 *            The rate at which the particle is rotated.
	 * @param lifetime
	 *            The duration of the fadeout of the particle, in seconds.
	 *************************************************************************/
	public GenericParticle(Vector2f velocity, Vector2f acceleration, 
			float scaleRate, float spinRate, float lifetime)
	{
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.scaleRate = scaleRate;
		this.spinRate = spinRate;
		fadeRate = -0.0001f / lifetime;
	}
	
	/*************************************************************************
	 * Updates the GenericParticle based on its acceleration/velocity, scale 
	 * rate, spin rate, and fade rate.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		velocity.add(acceleration, delta);
		location.add(velocity, delta);
		rotation.rotate(spinRate * delta);
		scalar.scaleLinear(scaleRate * delta);
		color.shiftAlpha(fadeRate * delta);
		if(color.alpha() <= 0 || scalar.improperlyScaled())
			kill();
	}
	
	/*************************************************************************
	 * Adjusts this GenericParticle so that it is appropriately influenced by 
	 * its parent.
	 * 
	 * NOTE: This changes the angle of the GenericParticle's trajectory by the
	 * rotation of the parent ParticleSprite.
	 ***************************************************************/ @Override
	protected void appendToParent()
	{
		super.appendToParent();
		velocity.setPolar((float) Math.toRadians(velocity.angle() + 
				parent.rotation().degrees()), velocity.magnitude());
	}
}