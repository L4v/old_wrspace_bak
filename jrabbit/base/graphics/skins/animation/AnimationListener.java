package org.jrabbit.base.graphics.skins.animation;

/*****************************************************************************
 * An AnimationListener listens to an AnimatedSkin and receives updates as its
 * animation progresses. It is alerted both on every frame change and at the end
 * of every cycle. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface AnimationListener
{
	/*************************************************************************
	 * Alerts the Listener that the frame has advanced its animation.
	 * 
	 * An AnimatedSkin uses this alert after alerting end-of-cycle (if 
	 * necessary). This allows the developer to use onCycleEnd to switch to a 
	 * different animation cycle; afterwards, this will be called with the new
	 * cycle and frame data in place.
	 * 
	 * @param cycle
	 * 			  The current active cycle that is active.
	 * @param frame
	 * 			  The new frame of the cycle.
	 *************************************************************************/
	public void onFrame(int cycle, int frame);
	
	/*************************************************************************
	 * Alerts the Listener that a complete cycle of animation has finished. With
	 * this method, the developer can make one cycle seamlessly transition into 
	 * another, cause a game effect to occur at the completion of an action, 
	 * etc. 
	 * 
	 * @param cycle
	 * 			  The cycle that has finished.
	 *************************************************************************/
	public void onCycleEnd(int cycle);
}