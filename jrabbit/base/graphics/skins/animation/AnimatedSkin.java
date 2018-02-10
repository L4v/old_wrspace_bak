package org.jrabbit.base.graphics.skins.animation;

import java.util.ArrayList;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.Skin;

/*****************************************************************************
 * An AnimatedSkin is just that - a Skin that renders changing data over time.
 * Unlike all other default Skins, it is Updateable (and needs to be updated to
 * function), so its usage must reflect this caveat. 
 * 
 * An AnimatedSkin contains an array of other Skins - these are all the possible
 * frames the animation can display. Alongside this, it maintains a 2-D array
 * of integers; these describe all possible cycles of animation ("actions," if
 * you will) that can be utilized. The format of this index is:
 * 
 * 		   {{a1, a2, ... aN},		// Action A
 * 			{b1, b2, ... bN},		// Action B
 * 			{ ... },				// Continued actions
 * 			{m1, m2, ... mN}}:		// Action M
 * 
 * Additionally, the AnimatedSkin has a 2-D array of floats; this represents the
 * "length" of each frame in proportion to the others. This array is structured
 * identically to the animation index; values in the same locations correspond
 * to one another. 
 * 
 * The AnimatedSkin has an active cycle and frame; it loops through the current
 * cycle (incrementing the frame) based upon the animation rate. The duration of
 * each frame is (base speed * duration index [cycle][frame]).
 * 
 * Additionally, the AnimatedSkin has a list of AnimationListeners that it 
 * alerts whenever it advances the frame and/or finishes a cycle.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimatedSkin implements Skin, Updateable
{
	/**
	 * The default frame duration; this is the number of update "ticks" to delay
	 * for each frame. It's set in microseconds resolution; 500 = 1/20th of a
	 * second.
	 **/
	private static int defaultSpeed = 500;

	/*************************************************************************
	 * Learns the current default animation speed.
	 * 
	 * @return The current base default interval between each frame of
	 *         animation.
	 *************************************************************************/
	public static int defaultSpeed() { return defaultSpeed; }

	/*************************************************************************
	 * Changes the default animation speed.
	 * 
	 * @param speed
	 * 			  The new default frame interval.
	 *************************************************************************/
	public static void setDefaultSpeed(int speed)
	{
		defaultSpeed = speed;
	}

	/**
	 * The internal list of all possible Skins the animation can use.
	 **/
	private Skin[] frames;
	
	/**
	 * The index of animation cycles; this describes all available animations.
	 **/
	private int[][] index;
	
	/**
	 * The index of frame durations; these correspond to the animation index.
	 * The duration of each frame when animated is:
	 * 
	 *  		duration = (speed * durations[cycle][frame]);
	 **/
	private float[][] durationIndex;
	
	/**
	 * The current active cycle.
	 **/
	private int cycle;
	
	/**
	 * The current frame in the active cycle.
	 **/
	private int frame;
	
	/**
	 * The current base animation speed.
	 **/
	private int speed;
	
	/**
	 * This variable is used to keep track of the total time elapsed on the 
	 * current frame.
	 **/
	private int counter;

	/**
	 * All of the AnimationListeners registered with this object.
	 **/
	private ArrayList<AnimationListener> listeners;

	/*************************************************************************
	 * Creates an AnimatedSkin that animates the indicated frames with the 
	 * supplied index. Every frame has equal duration, and the AnimatedSkin uses
	 * the default animation speed.
	 * 
	 * @param frames
	 * 			  The frames to use in Animation.
	 * @param index
	 * 			  The index that will be used to animated the supplied frames.
	 *************************************************************************/
	public AnimatedSkin(Skin[] frames, int[][] index)
	{
		this(frames, index, defaultSpeed);
	}

	/*************************************************************************
	 * Creates an AnimatedSkin that animates the indicated frames with the 
	 * supplied index at the indicated speed. Every frame has equal duration.
	 * 
	 * @param frames
	 * 			  The frames to use in Animation.
	 * @param index
	 * 			  The index that will be used to animated the supplied frames.
	 * @param speed
	 * 			  The base duration (in clock ticks) of each frame.
	 *************************************************************************/
	public AnimatedSkin(Skin[] frames, int[][] index, int speed)
	{
		init();
		setFrames(frames);
		setIndex(index);
		setSpeed(speed);
	}

	/*************************************************************************
	 * Creates an AnimatedSkin that animates the indicated frames with the 
	 * supplied index at the indicated speed, using the indicated index of 
	 * durations to make some frames longer or shorter than others.
	 * 
	 * @param frames
	 * 			  The frames to use in Animation.
	 * @param index
	 * 			  The index that will be used to animated the supplied frames.
	 * @param durationIndex
	 * 			  The index that will be used to manage the duration of 
	 * 			  particular frames.
	 * @param speed
	 * 			  The base duration (in clock ticks) of each frame.
	 *************************************************************************/
	public AnimatedSkin(Skin[] frames, int[][] index, float[][] durationIndex, 
			int speed)
	{
		init();
		setFrames(frames);
		setIndex(index);
		setSpeed(speed);
		setDurationIndex(durationIndex);
	}

	/*************************************************************************
	 * Initializes the default animation settings. The animation speed is set to
	 * the default and the list of AnimationListeners is initialized.
	 *************************************************************************/
	private void init()
	{
		speed = defaultSpeed;
		listeners = new ArrayList<AnimationListener>();
	}

	/*************************************************************************
	 * Adds a listener to the list of AnimationListeners.
	 * 
	 * @param listener
	 * 			  The listener to add.
	 *************************************************************************/
	public void addListener(AnimationListener listener)
	{
		listeners.add(listener);
	}

	/*************************************************************************
	 * Removes the indicated AnimationListener from the list.
	 * 
	 * @param listener
	 * 			  The AnimationListener to remove.
	 * 
	 * @return True if the removal operation succeeded, false if not.
	 *************************************************************************/
	public boolean removeListener(AnimationListener listener)
	{
		return listeners.remove(listener);
	}

	/*************************************************************************
	 * Removes all AnimationListeners from the list.
	 *************************************************************************/
	public void clearListeners()
	{
		listeners.clear();
	}

	/*************************************************************************
	 * Accesses the array of Skins used for rendering.
	 * 
	 * @return The list of Skins being used a frames of animation.
	 *************************************************************************/
	public Skin[] frames() { return frames; }

	/*************************************************************************
	 * Redefines the list of frames.
	 * 
	 * @param frames
	 * 			  The new array of Skins to use for rendering each frame of
	 * 			  animation.
	 *************************************************************************/
	public void setFrames(Skin[] frames)
	{
		this.frames = frames;
	}

	/*************************************************************************
	 * Accesses the current Skin used to render the frame.
	 * 
	 * @return The active Skin.
	 *************************************************************************/
	public Skin currentSkin()
	{
		return skinAt(cycle, frame);
	}

	/*************************************************************************
	 * Accesses a Skin used to render the animation.
	 * 
	 * @param cycle
	 *            The cycle of animation.
	 * @param frame
	 *            The frame within the indicated cycle to access.
	 * 
	 * @return The Skin used to render the indicated frame of animation.
	 *************************************************************************/
	public Skin skinAt(int cycle, int frame)
	{
		return frames[index[cycle][frame]];
	}

	/*************************************************************************
	 * Learns the current cycle number.
	 * 
	 * @return The current frame cycle within the animation index.
	 *************************************************************************/
	public int cycle() { return cycle; }

	/*************************************************************************
	 * Changes the animation cycle. The change only takes place if the cycle 
	 * number if possible (greater than 0 and within the limits of the animation
	 * index).
	 * 
	 * NOTE: This method does not alert AnimationListeners that a change has 
	 * taken place.
	 * 
	 * @param number
	 * 			  The cycle to use.
	 *************************************************************************/
	public void setCycle(int number)
	{
		if(cycle != number)
		{
			cycle = number;
			frame = counter = 0;
		}
	}

	/*************************************************************************
	 * Learns the current frame number.
	 * 
	 * @return The current frame number within the active animation cycle.
	 *************************************************************************/
	public int frame() { return frame; }

	/*************************************************************************
	 * Changes the frame of animation.
	 * 
	 * NOTE: This method does not alert AnimationListeners that a change has 
	 * taken place.
	 * 
	 * @param number
	 * 			  The frame to use.
	 *************************************************************************/
	public void setFrame(int number)
	{
		if(frame != number)
		{
			frame = number;
			counter = 0;
		}
	}

	/*************************************************************************
	 * Accesses the index of animations.
	 * 
	 * @return The 2-D integer array that defines all animation cycles usable by
	 *         the AnimatedSkin.
	 *************************************************************************/
	public int[][] index() { return index; }

	/*************************************************************************
	 * Redefines the index of animations.
	 * 
	 * NOTE: This automatically resets the duration index.
	 * 
	 * @param index
	 *            The new index that defines all animation cycles used by the
	 *            AnimatedSkin.
	 *************************************************************************/
	public void setIndex(int[][] index)
	{
		this.index = index;
		resetDurationIndex();
	}

	/*************************************************************************
	 * Learns the current base speed of the animation.
	 * 
	 * @return The base number of clock ticks each frame lasts.
	 *************************************************************************/
	public int speed() { return speed; }

	/*************************************************************************
	 * Redefines the current speed of the animation.
	 * 
	 * @param speed
	 *            The new base speed of the animation. The supplied value should
	 *            be the base number of clock ticks each frame lasts.
	 *************************************************************************/
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/*************************************************************************
	 * Accesses the duration index.
	 * 
	 * @return The index of float values that define the proportion of the 
	 *         animation speed each frame lasts.
	 *************************************************************************/
	public float[][] durationIndex() { return durationIndex; }

	/*************************************************************************
	 * Resets the durations of all frames to the current animation rate.
	 *************************************************************************/
	public void resetDurationIndex()
	{
		durationIndex = new float[index.length][];
		for (int i = 0; i < index.length; i++)
		{
			durationIndex[i] = new float[index[i].length];
			for (int j = 0; j < index[i].length; j++)
				durationIndex[i][j] = 1f;
		}
	}

	/*************************************************************************
	 * Redefines the index of frame durations.
	 * 
	 * @param durations
	 * 			  The new index of how long each frame of animation should last.
	 *************************************************************************/
	public void setDurationIndex(float[][] durations)
	{
		if (index.length <= durations.length)
			for (int i = 0; i < durations.length; i++)
				if (index[i].length <= durations[i].length)
					this.durationIndex = durations;
	}

	/*************************************************************************
	 * Advances the frame of animation, alerting any listeners as it goes.
	 *************************************************************************/
	public void advanceFrame()
	{
		frame++;
		if (frame >= index[cycle].length)
		{
			frame = 0;
			for (AnimationListener aL : listeners)
				aL.onCycleEnd(cycle);
		}
		for (AnimationListener aL : listeners)
			aL.onFrame(cycle, frame);
	}


	/*************************************************************************
	 * Updates the animation. This simply increments the time counter; if the
	 * counter passes the duration of a frame, the frame is advanced.
	 * 
	 * @param delta
	 * 			  The number of clock ticks that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter += delta;
		while (counter >= speed * durationIndex[cycle][frame])
		{
			counter -= speed * durationIndex[cycle][frame];
			if (frames != null && index != null)
				advanceFrame();
		}
	}

	/*************************************************************************
	 * Renders the current Skin.
	 ***************************************************************/ @Override
	public void render()
	{
		currentSkin().render();
	}

	/*************************************************************************
	 * Accesses the dimensions of the AnimatedSkin.
	 * 
	 * @return The width of the current skin being rendered.
	 ***************************************************************/ @Override
	public float width() { return currentSkin().width(); }

	/*************************************************************************
	 * Accesses the dimensions of the AnimatedSkin.
	 * 
	 * @return The height of the current skin being rendered.
	 ***************************************************************/ @Override
	public float height() { return currentSkin().height(); }
}