package org.jrabbit.standard.game.objects.particles.generic;

import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * ExplosionParticle extends SprayParticle to simplify the process of creating
 * a certain type of particle effect - in this case, a particle sent straight 
 * out from its origin at a random angle. This effect is useful for things like 
 * fireworks, explosions, impact effects, and any "burst"-like affect.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ExplosionParticle extends SprayParticle 
{
	/*************************************************************************
	 * Creates an Explosion Particle with the indicated settings.
	 * 
	 * @param speed
	 * 			  How quickly the particle moves away from its origin.
	 * @param scaleRate
	 * 			  The rate at which the particle scales. It's probably best to
	 * 			  make this a negative value (so the particle shrinks).
	 * @param spinRate
	 * 			  The rate at which the particle spins.
	 * @param lifetime
	 * 			  The length of time the particle should remain alive. The 
	 * 			  particle gradually fades out during this period.
	 *************************************************************************/
	public ExplosionParticle(float speed, float scaleRate, float spinRate, 
			float lifetime)
	{
		super(Resources.random().nextFloat() * 360, speed, scaleRate, spinRate, 
				lifetime);
	}
}