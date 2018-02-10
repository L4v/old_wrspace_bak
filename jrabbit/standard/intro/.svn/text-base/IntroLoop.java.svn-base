package org.jrabbit.standard.intro;

import java.util.LinkedList;

import org.jrabbit.base.core.loop.Loop;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

/*****************************************************************************
 * An IntroLoop contains a sequence of Intros that it plays at the beginning of
 * a game.
 * 
 * Using IntroLoop is fairly basic; one is already provided by StandardGame. 
 * Simply add all desired Intros to it, and the game will automatically play 
 * them if enabled.
 * 
 * NOTE: By default, the Mouse is hidden while the IntroLoop plays. It is made
 * visible again when the Intro finishes.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class IntroLoop extends Loop
{
	/**
	 * The queue of remaining Intros.
	 **/
	protected LinkedList<Intro> queue;
	
	/**
	 * The current Intro being played.
	 **/
	protected Intro current;

	/*************************************************************************
	 * Creates an empty IntroLoop.
	 *************************************************************************/
	public IntroLoop()
	{
		queue = new LinkedList<Intro>();
	}

	/*************************************************************************
	 * Accesses the list of Intros to play.
	 * 
	 * @return The LinkedList of Intros in the IntroLoop.
	 *************************************************************************/
	public LinkedList<Intro> queue() { return queue; }

	/*************************************************************************
	 * Calls create() on all Intros in the queue, and hides the mouse.
	 ***************************************************************/ @Override
	public void start()
	{
		MouseHandler.hideMouse(true);
		for(Intro intro : queue)
			intro.create();
	}

	/*************************************************************************
	 * Resets all necessary settings after all Intros have finished playing.
	 ***************************************************************/ @Override
	public void end()
	{
		MouseHandler.hideMouse(false);
		GL11.glLoadIdentity();
	}

	/*************************************************************************
	 * Updates the Keyboard and Mouse handlers and all currently playing Audio,
	 * and then updates or advances the current Intro (as determined by {@link 
	 * Intro#finished()}.
	 * 
	 * @param delta
	 * 			  The number of clock ticks that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		MouseHandler.update();
		KeyboardHandler.update();
		SoundStore.get().poll(0);
		if(current == null || current.finished())
			advanceIntro();
		else
			current.update(delta);
	}

	/*************************************************************************
	 * Advances to the next Intro, destroying the current one if it is non-null.
	 * If no Intros are left in the queue, the IntroLoop exits and the full game
	 * begins.
	 *************************************************************************/
	protected void advanceIntro()
	{
		if(current != null)
			current.destroy();
		if(!queue.isEmpty() && (current = queue.pop()) != null)
			current.start();
		else
			exit();
	}

	/*************************************************************************
	 * Renders the currently active Intro.
	 ***************************************************************/ @Override
	public void render()
	{
		WindowManager.controller().beginRender();
		if(current != null)
			current.render();
		WindowManager.controller().endRender();
	}
}