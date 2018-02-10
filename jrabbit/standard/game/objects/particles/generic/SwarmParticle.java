package org.jrabbit.standard.game.objects.particles.generic;

import java.util.Random;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.standard.game.objects.particles.base.Particle;

/*****************************************************************************
 * A SwarmParticle is by far the most complex default particle, and the only one
 * that creates a persistent effect instead of dying over time. 
 * 
 * A SwarmParticle creates a "swarming" effect - think like flies over a piece
 * of meat. Each particle randomly wanders around a target point, moving back 
 * towards it if it gets to far away. As a group, these particles form a dynamic
 * cloud of moving points.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SwarmParticle extends Particle
{
	/**
	 * The Vector2f that the Swarm will follow.
	 **/
	protected Vector2f target;
	
	/**
	 * The maximum distance the particle can move away from its target without
	 * being forced back in.
	 **/
	protected float range;
	
	/**
	 * The current velocity of the particle.
	 **/
	protected Vector2f velocity;
	
	/**
	 * The maximum speed of the particle at any time.
	 **/
	protected float maxSpeed;
	
	/**
	 * The acceleration currently being applied.
	 **/
	protected Vector2f acceleration;
	
	/**
	 * The base magnitude of the acceleration.
	 **/
	protected float baseAccel;
	
	/**
	 * This value keeps track of the duration of each acceleration phase.
	 **/
	protected int counter;
	
	/**
	 * The base value to calculate intervals.
	 **/
	protected int intervalBase;
	
	/**
	 * The current interval between acceleration changes.
	 **/
	protected int interval;
	
	/*************************************************************************
	 * Creates a SwarmParticle with the indicated settings.
	 * 
	 * @param range
	 * 			  The distance from the target that the particle should treat as
	 * 			  it's boundary.
	 * @param speed
	 * 			  The initial speed of the particle.
	 * @param maxSpeed
	 * 			  The maximum speed the particle can travel at any time.
	 * @param baseAccel
	 * 			  The base magnitude of acceleration.
	 * @param interval
	 * 			  The interval, in microseconds, between each change in 
	 * 			  acceleration.
	 *************************************************************************/
	public SwarmParticle(float range, float speed, float maxSpeed, 
			float baseAccel, int interval)
	{
		target = new Vector2f();
		velocity = new Vector2f();
		acceleration = new Vector2f();
		this.range = range;
		this.maxSpeed = maxSpeed;
		this.baseAccel = baseAccel;
		this.intervalBase = interval;
		
		Random r = Resources.random();
		interval = (int) (intervalBase * (r.nextFloat() + 0.5f));
		velocity.setPolar(r.nextFloat() * (float) Math.PI * 2, speed);
		acceleration.setPolar(r.nextFloat() * (float) Math.PI * 2, 
				(r.nextFloat() + 0.5f) * baseAccel);
	}
	
	/*************************************************************************
	 * Adjust the particle to its parent ParticleSprite.
	 * 
	 * This also tells the SwarmParticle to follow the location of its parent.
	 ***************************************************************/ @Override
	protected void appendToParent()
	{
		super.appendToParent();
		target = parent.location();
	}
	
	/*************************************************************************
	 * Updates the particle, causing it to swarm around the target point.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter += delta;
		if(counter >= interval)
		{
			counter -= interval;
			Random r = Resources.random();
			interval = (int) (intervalBase * (r.nextFloat() + 0.5f));
			acceleration.setPolar(r.nextFloat() * (float) Math.PI * 2, 
					(r.nextFloat() + 0.5f) * baseAccel);
		}
		if(location.distanceTo(target) > range)
		{
			acceleration.set(location.unitVectorTowards(target));
			acceleration.multiply(baseAccel * 2);
		}
		velocity.add(acceleration, delta);
		velocity.cap(maxSpeed);
		location.add(velocity, delta);
	}
}