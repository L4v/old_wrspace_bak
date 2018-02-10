package org.jrabbit.standard.game.objects.particles.base;

/*****************************************************************************
 * An AnimatedParticle is a Particle that loops through the available Skins in 
 * its parent, providing simple, looping Animation.
 * 
 * To create more advanced animations, it's recommended that you build your own.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimatedParticle extends Particle
{
	/**
	 * This counter keeps track of how long the animation has been on its 
	 * current frame.
	 **/
	protected int frameCounter;
	
	/**
	 * Indicates how many microseconds the animation is supposed to linger on 
	 * each frame.
	 **/
	protected int frameInterval;

	/*************************************************************************
	 * Creates an animation that loops through the Skins of its parent at the 
	 * indicated speed.
	 * 
	 * NOTE: The animation begins at frame 0.
	 * 
	 * @param interval
	 * 			  The number of microseconds the animation will linger on each
	 *            frame of animation.
	 *************************************************************************/
	public AnimatedParticle(int interval)
	{
		this(interval, 0);
	}

	/*************************************************************************
	 * Creates an animation that loops through the Skins of its parent at the 
	 * indicated speed, beginning at the indicated frame.
	 * 
	 * @param interval
	 *            The number of microseconds the animation will linger on each
	 *            frame of animation.
	 * @param startFrame
	 *            The beginning frame.
	 *************************************************************************/
	public AnimatedParticle(int interval, int startFrame)
	{
		skinID = startFrame;
		this.frameInterval = interval;
	}

	/*************************************************************************
	 * Updates the animation.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		frameCounter += delta;
		skinID = (skinID + (frameCounter / frameInterval)) % 
				parent.skins().length;
		frameCounter %= frameInterval;
	}
}