package org.jrabbit.base.data.thread;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;

/*****************************************************************************
 * This class attempts to do the same thing as a WatchableThread, but it allows
 * you to alter OpenGL resources while doing so. Attempting to do so in the
 * default WatchableThread will not work, since the two threads have separate
 * OpenGL contexts.
 * 
 * To mingle contexts, the Thread instantiates a SharedDrawable to mingle
 * contexts with that of the Display (i.e., the main OpenGL context).
 * Unfortunately, this functionality is not universally supported on all
 * graphics cards, and so this has a chance to fail. If the attempt fails, a
 * flag is set, and external operations can check for success or failure.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class WatchableGLThread extends WatchableThread
{
	/**
	 * Indicates that the Thread has not come to the point where it can
	 * determine success or failure.
	 **/
	public static final int CONTEXT_SHARING_UNDECIDED = 0;

	/**
	 * Indicates that context sharing has been a success.
	 **/
	public static final int CONTEXT_SHARING_SUCCESS = 1;

	/**
	 * Indicates that context sharing failed, and the thread has been unable to
	 * process events.
	 */
	public static final int CONTEXT_SHARING_FAILURE = -1;

	/**
	 * The object used to share contexts.
	 **/
	protected SharedDrawable sharedDrawable;

	/**
	 * The current indicator of that status of context sharing.
	 **/
	private int contextSharingResult = CONTEXT_SHARING_UNDECIDED;

	/*************************************************************************
	 * First attempts to share contexts with the Display. If context sharing
	 * fails, the Thread doesn't attempt to process act(), but instead exits
	 * immediately.
	 ***************************************************************/ @Override
	public void run()
	{
		shareContext();
		if (contextSharingSuccess())
			act();
		releaseContext();
		progress = total;
	}

	/*************************************************************************
	 * Attempts to share contexts, settings the flag on either success or
	 * failure.
	 *************************************************************************/
	protected void shareContext()
	{
		try
		{
			sharedDrawable = new SharedDrawable(Display.getDrawable());
			sharedDrawable.makeCurrent();
			contextSharingResult = CONTEXT_SHARING_SUCCESS;
		}
		catch (LWJGLException e)
		{
			contextSharingResult = CONTEXT_SHARING_FAILURE;
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Gets rid of the shared context if it is not present any more.
	 *************************************************************************/
	protected void releaseContext()
	{
		try {
			if (sharedDrawable != null)
				sharedDrawable.releaseContext();
		} catch (LWJGLException e) { e.printStackTrace(); }
	}

	/*************************************************************************
	 * Accesses the context sharing flag.
	 * 
	 * @return The current value of the flag. This will be either
	 *         CONTEXT_SHARING_UNDECIDED, CONTEXT_SHARING_SUCCESS, or
	 *         CONTEXT_SHARING_FAILURE.
	 *************************************************************************/
	public int contextSharingResult()
	{
		return contextSharingResult;
	}

	/*************************************************************************
	 * Checks if context sharing succeeded.
	 * 
	 * @return True if the context sharing succeeded and events are being
	 *         processed as planned.
	 *************************************************************************/
	public boolean contextSharingSuccess()
	{
		return contextSharingResult == CONTEXT_SHARING_SUCCESS;
	}

	/*************************************************************************
	 * Checks if context sharing failed.
	 * 
	 * @return True if the context sharing failed and events cannot be
	 *         processed.
	 *************************************************************************/
	public boolean contextSharingFailed()
	{
		return contextSharingResult == CONTEXT_SHARING_FAILURE;
	}
}