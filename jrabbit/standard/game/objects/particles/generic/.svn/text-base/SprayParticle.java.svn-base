package org.jrabbit.standard.game.objects.particles.generic;

import org.jrabbit.base.graphics.transforms.Vector2f;

/*****************************************************************************
 * SprayParticle extends GenericParticle to simplify the process of creating a
 * certain type of particle effect - in this case, a particle shot straight out
 * from its origin at an angle. This effect is useful for things like 
 * flamethrowers, steam / smoke, and any other "spurt"-like affect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SprayParticle extends GenericParticle
{
	/*************************************************************************
	 * Creates a velocity vector from the indicated angle and speed.
	 * 
	 * @param angle
	 *            The angle of the velocity, in degrees.
	 * @param speed
	 *            The magnitude of the velocity.
	 * 
	 * @return The appropriate velocity.
	 *************************************************************************/
	protected static Vector2f genAngledVelocity(float angle, float speed)
	{
		Vector2f velocity = new Vector2f();
		velocity.setPolar((float) Math.toRadians(angle), speed);
		return velocity;
	}
	
	/*************************************************************************
	 * Creates a SprayParticle with the indicated settings.
	 * 
	 * @param angle
	 *            The angle at which the particle moves, in degrees.
	 * @param speed
	 *            The speed of the particle.
	 * @param scaleRate
	 *            The rate at which the particle is scaled over time.
	 * @param spinRate
	 *            The rate at which the particle is rotated.
	 * @param lifetime
	 *            The duration of the fadeout of the particle, in seconds.
	 *************************************************************************/
	public SprayParticle(float angle, float speed, float scaleRate, 
			float spinRate, float lifetime)
	{
		super(genAngledVelocity(angle, speed), new Vector2f(), scaleRate, 
				spinRate, lifetime);
	}
}