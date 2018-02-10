package org.jrabbit.base.core.loop;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

/*****************************************************************************
 * Provides methods of regulating processes.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class LoopController
{
	/**
	 * Whether or not to sleep the thread. By disabling this, the associated
	 * process will run as fast as it can (useful for simulating over a large
	 * number of iterations).
	 * 
	 * Default: true.
	 **/
	protected boolean regulate;

	/**
	 * Whether or not the update variable should be a constant, "predicted"
	 * value - i.e., resolution / fps.
	 * 
	 * Default: true.
	 **/
	protected boolean smooth;

	/**
	 * The units for the timer to use - the number of ticks in a second.
	 * 
	 * Default: 10,000 - i.e., the controller reports in microseconds.
	 **/
	protected int resolution;

	/**
	 * How many iterations we want per second.
	 * 
	 * Default: 60
	 **/
	protected int fps;

	/**
	 * Keeps track of when the time was last recorded so we can know the
	 * interval between checks.
	 **/
	protected long lastCall;

	/**
	 * The duration of the recorded interval, in the set resolution.
	 */
	protected int duration;

	/*************************************************************************
	 * Creates a default controller that will call at 60 fps and report times in
	 * microseconds.
	 *************************************************************************/
	public LoopController()
	{
		regulate = true;
		smooth = true;
		resolution = 10000;
		fps = 60;
		lastCall = Sys.getTime();
		duration = 0;
	}

	/*************************************************************************
	 * Advances the clock to record the amount of time that has passed.
	 *************************************************************************/
	public void tick()
	{
		duration = (int) (((Sys.getTime() - lastCall) * resolution) / Sys
				.getTimerResolution());
		lastCall = Sys.getTime();
	}

	/*************************************************************************
	 * Returns the desired interval of time.
	 * 
	 * If delta smoothing is on, it actually ignores the value calculated in
	 * advance() and returns a predicted, constant value instead.
	 * 
	 * @return The interval of time to update, in the set resolution.
	 *************************************************************************/
	public int delta()
	{
		return smooth ? (resolution / fps) : duration;
	}

	/*************************************************************************
	 * If the controller is allowed to regulate, this sleeps the game so as to
	 * sync to the desired fps.
	 *************************************************************************/
	public void sleep()
	{
		if (regulate)
		{
			Display.sync(fps);
		}
	}

	/*************************************************************************
	 * Sets the desired iterations per second.
	 * 
	 * @param fps
	 *            How many times we want to execute per second.
	 *************************************************************************/
	public void setFPS(int fps)
	{
		this.fps = fps;
	}

	/*************************************************************************
	 * Gets execution rate.
	 * 
	 * @return How many times we execute per second.
	 *************************************************************************/
	public int fps()
	{
		return fps;
	}

	/*************************************************************************
	 * Sets the resolution for recorded deltas. Obviously, this makes the most
	 * sense if resolution is a multiple of 10 (1000 for milliseconds, 10000 for
	 * microseconds, etc).
	 * 
	 * Recalculates the current delta to take advantage of the new resolution.
	 *************************************************************************/
	public void setResolution(int resolution)
	{
		duration *= resolution;
		duration /= this.resolution;
		this.resolution = resolution;
	}

	/*************************************************************************
	 * Gets the current resolution.
	 * 
	 * @return How many ticks are in a second.
	 *************************************************************************/
	public int resolution()
	{
		return resolution;
	}

	/*************************************************************************
	 * Controls if the game sleeps.
	 * 
	 * @param regulate
	 *            Whether or not the game should sync to the required fps.
	 *************************************************************************/
	public void setRegulate(boolean regulate)
	{
		this.regulate = regulate;
	}

	/*************************************************************************
	 * Gets if the controller is pausing execution.
	 * 
	 * @return Whether or not the game is sleeping the thread.
	 *************************************************************************/
	public boolean regulating()
	{
		return regulate;
	}

	/*************************************************************************
	 * Tells the controller to use the recorded time interval for delta() or if
	 * it should calculate the desired delta (resolution / fps).
	 * 
	 * This really only changes performance if the game drops below the desired
	 * frame rate. If it does, you have a tradeoff to make.
	 * 
	 * By keeping the delta constant, you are able to make every update keep a
	 * steady pace. This is useful if you have, say, a physics engine that must
	 * update at a certain rate (example: Phys2D). Additionally, you can
	 * actually hard-code the rate of updateables, allowing for less
	 * calculation.
	 * 
	 * However, if you allow the delta to scale, you game may perform better on
	 * a worse computer.
	 *************************************************************************/
	public void setSmooth(boolean smooth)
	{
		this.smooth = smooth;
	}

	/*************************************************************************
	 * Gets if the controller is smoothing.
	 * 
	 * @return Whether or not the update values are constant or dynamic.
	 *************************************************************************/
	public boolean smoothing()
	{
		return smooth;
	}
}