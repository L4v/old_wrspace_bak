package org.jrabbit.standard.game.objects.specialized;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.animation.AnimatedSkin;
import org.jrabbit.base.graphics.skins.animation.AnimationListener;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * An AnimatedSprite is an Updateable BaseSprite that uses an AnimatedSkin to
 * render itself.
 * 
 * NOTE: This AnimatedSprite will not change over time unless its {@link 
 * #update(int)} method is repeatedly called.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimatedSprite extends BaseSprite implements Updateable,
		AnimationListener
{
	/**
	 * The currently active Animation. 
	 **/
	protected AnimatedSkin animation;

	/*************************************************************************
	 * Creates an AnimatedSprite with the indicated AnimatedSkin.
	 * 
	 * @param animation
	 * 			  The AnimatedSkin to use.
	 *************************************************************************/
	public AnimatedSprite(AnimatedSkin animation)
	{
		setAnimation(animation);
	}

	/*************************************************************************
	 * Accesses the active animation.
	 * 
	 * @return The currently active AnimatedSkin.
	 *************************************************************************/
	public AnimatedSkin animation()
	{
		return animation;
	}

	/*************************************************************************
	 * Redefines the active animation.
	 * 
	 * @param animation
	 * 			  The new AnimatedSkin to use.
	 *************************************************************************/
	public void setAnimation(AnimatedSkin animation)
	{
		if (this.animation != null)
			this.animation.removeListener(this);
		this.animation = animation;
		this.animation.addListener(this);
	}

	/*************************************************************************
	 * Alerts the AnimatedSprite that a new frame has been selected.
	 * 
	 * NOTE: By default, this method does nothing.
	 * 
	 * @param cycle
	 * 			  The cycle of animation currently active.
	 * @param frame
	 * 			  The new frame of the current cycle.
	 ***************************************************************/ @Override
	public void onFrame(int cycle, int frame) { }

	/*************************************************************************
	 * Alerts the AnimatedSprite that the AnimatedSkin has reached the end of a
	 * cycle.
	 * 
	 * NOTE: By default, this method does nothing.
	 * 
	 * @param cycle
	 * 			  The cycle of animation that has ended.
	 ***************************************************************/ @Override
	public void onCycleEnd(int cycle) { }

	/*************************************************************************
	 * Accesses the dimensions of the active AnimatedSkin.
	 * 
	 * @return The width of the current frame of animation.
	 ***************************************************************/ @Override
	public float width() { return animation.width(); }

	/*************************************************************************
	 * Accesses the dimensions of the active AnimatedSkin.
	 * 
	 * @return The height of the current frame of animation.
	 ***************************************************************/ @Override
	public float height() { return animation.height(); }

	/*************************************************************************
	 * Updates the AnimatedSkin.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		animation.update(delta);
	}

	/*************************************************************************
	 * Renders the active AnimatedSkin.
	 ***************************************************************/ @Override
	public void draw()
	{
		animation.render();
	}
}