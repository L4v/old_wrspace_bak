package org.jrabbit.base.data.thread;

/*****************************************************************************
 * Defines the base for a thread that can have its progress tracked (in a
 * limited way) as it processes. This thread performs an "action", which is
 * executed separately from the main game. The thread will perform its
 * potentially expensive operations (say, loading 150 images and 30 sound
 * effects) while the rest of the game runs normally.
 * 
 * The most obvious use is for loading - simply extend this class to have act()
 * initialize a series of objects and increment amountComplete along the way,
 * and you're golden.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class WatchableThread extends Thread
{
	/**
	 * How "much" of the action has been finished. Should never be greater than
	 * 'total.'
	 **/
	protected float progress;

	/**
	 * Total "amount" to complete. This is the target mark for 'progress' to
	 * reach. The default is 100 (as in 100%) but it's more sensible for the
	 * developer to specify a number more correlated with what they are doing.
	 * 
	 * Example: If the thread needs to load 87 images, 'total' could be set to
	 * 87, and 'progress' could be incremented after each image is loaded.
	 **/
	protected float total = 100;

	/*************************************************************************
	 * Returns the percentage (0 to 1, not 0 to 100) of the action that is
	 * complete.
	 * 
	 * @return A number between 0 and 1 that indicates how far the loading has
	 *         progressed.
	 *************************************************************************/
	public float percentComplete()
	{
		return progress / total;
	}

	/*************************************************************************
	 * Checks whether or not the action has finished. It's a good idea to use
	 * this method to check whether or not its safe to interact with this
	 * Thread.
	 * 
	 * @return True if the action has finished, false if
	 *************************************************************************/
	public boolean complete()
	{
		return progress >= total;
	}

	/*************************************************************************
	 * Performs the action that a WatchableThread is designed to do.
	 *************************************************************************/
	protected abstract void act();

	/*************************************************************************
	 * Runs the thread.
	 * 
	 * All that this does is call act() and then flag the action as completed.
	 ***************************************************************/ @Override
	public void run()
	{
		act();
		// After act is complete, we automatically flag the
		// WatchableThread as completed.
		progress = total;
	}
}