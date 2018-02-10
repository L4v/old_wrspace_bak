package org.jrabbit.standard.game.loop;

import org.jrabbit.base.core.types.*;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.standard.profiler.ProfilerDisplay;
import org.jrabbit.standard.profiler.entities.standard.ProfilerMessage;
import org.jrabbit.standard.profiler.entities.standard.ProfilerMessages;
import org.jrabbit.standard.profiler.entities.standard.SystemInfo;
import org.jrabbit.standard.profiler.util.Counter;
import org.jrabbit.standard.profiler.util.Timer;
import org.jrabbit.standard.profiler.util.ValueTracker;

/*****************************************************************************
 * A GameLoopProfiler is an object that calculates and displays code profiling
 * information. It extends ProfilerDisplay, but adds numerous controls to allow
 * a GameLoop to measure particular operations much more effectively.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class GameLoopProfiler extends ProfilerDisplay implements 
		Updateable
{
	/*************************************************************************
	 * Signals that the game has completed a cycle.
	 *************************************************************************/
	public abstract void advanceFPS();

	/*************************************************************************
	 * Signals that the game is going to update its logic.
	 *************************************************************************/
	public abstract void beginLogic();

	/*************************************************************************
	 * Signals that the game has finished all purely logic-based calls.
	 *************************************************************************/
	public abstract void endLogic();

	/*************************************************************************
	 * Signals that the game is beginning a render.
	 *************************************************************************/
	public abstract void beginRender();

	/*************************************************************************
	 * Signals that the game has finished rendering.
	 *************************************************************************/
	public abstract void endRender();

	/*************************************************************************
	 * Signals that the game is beginning its sleep phase, in order to keep a
	 * steady frame rate and conserve system resources.
	 *************************************************************************/
	public abstract void beginSleep();

	/*************************************************************************
	 * Signals that the game has finished sleeping.
	 *************************************************************************/
	public abstract void endSleep();

	/*************************************************************************
	 * GameLoopProfiler.Default provides a default implementation. GameLoops
	 * automatically use this unless otherwise specified.
	 *************************************************************************/
	public static class Default extends GameLoopProfiler
	{
		/**
		 * This keeps track of the frames per second.
		 **/
		protected Counter fps;

		/**
		 * This keeps track of how long updating the game takes.
		 **/
		protected Timer logic;

		/**
		 * This keeps track of how long rendering the game takes.
		 **/
		protected Timer rendering;

		/**
		 * This keeps track of how much free time per cycle the game has.
		 **/
		protected Timer free;

		/**
		 * Keeps track of how many Images have been loaded.
		 **/
		protected ValueTracker<Integer> imageCounter;

		/**
		 * Keeps track of how many fonts have been loaded.
		 **/
		protected ValueTracker<Integer> fontCounter;

		/**
		 * Keeps track of how many sounds have been loaded. This does not
		 * include music.
		 **/
		protected ValueTracker<Integer> soundCounter;

		/**
		 * This displays general info about the system the game is running on.
		 **/
		protected SystemInfo system;
		
		/*********************************************************************
		 * Creates the default GameLoopProfiler.
		 *********************************************************************/
		public Default()
		{
			contents().add(new ProfilerMessage(fps = new Counter("FPS")),
					new ProfilerMessages("Gameloop", 
						logic = new Timer("Logic"),
						rendering = new Timer("Rendering"),
						free = new Timer("Free")),
					new ProfilerMessages("Resources",
							imageCounter = new ValueTracker<Integer>(
								"Images", Resources.images().size()),
							fontCounter = new ValueTracker<Integer>(
								"Fonts", Resources.fonts().size()),
								soundCounter = new ValueTracker<Integer>(
								"Sounds", Resources.sounds().size())),
					system = new SystemInfo());
		}

		/*********************************************************************
		 * Updates all necessary ProfilerEntities.
		 * 
		 * @param delta
		 * 			  The number of clock ticks that have passed.
		 ***********************************************************/ @Override
		public void update(int delta)
		{
			imageCounter.setValue(Resources.images().size());
			fontCounter.setValue(Resources.fonts().size());
			soundCounter.setValue(Resources.sounds().size());
			system.update(delta);
		}

		/*********************************************************************
		 * Signals that the game has completed a cycle.
		 ***********************************************************/ @Override
		public void advanceFPS()
		{
			fps.advance();
		}

		/*********************************************************************
		 * Signals that the game is going to update its logic.
		 ***********************************************************/ @Override
		public void beginLogic()
		{
			logic.begin();
		}

		/*********************************************************************
		 * Signals that the game has finished all purely logic-based calls.
		 ***********************************************************/ @Override
		public void endLogic()
		{
			logic.end();
		}

		/*********************************************************************
		 * Signals that the game is beginning a render.
		 ***********************************************************/ @Override
		public void beginRender()
		{
			rendering.begin();
		}

		/*********************************************************************
		 * Signals that the game has finished rendering.
		 ***********************************************************/ @Override
		public void endRender()
		{
			rendering.end();
		}

		/*********************************************************************
		 * Signals that the game is beginning its sleep phase, in order to keep
		 * a steady frame rate and conserve system resources.
		 ***********************************************************/ @Override
		public void beginSleep()
		{
			free.begin();
		}

		/*********************************************************************
		 * Signals that the game has finished sleeping.
		 ***********************************************************/ @Override
		public void endSleep()
		{
			free.end();
		}
	}
}