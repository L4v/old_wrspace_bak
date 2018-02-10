package org.jrabbit.standard.game.loop;

import org.jrabbit.base.core.loop.Loop;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.game.world.World;
import org.jrabbit.standard.profiler.ProfilerDisplay;
import org.lwjgl.opengl.Display;

/*****************************************************************************
 * A GameLoop is an advanced Loop that has more defined methods for running a
 * game. It also has a built-in profiler that is useful for monitoring the 
 * game's performance as it plays.
 * 
 * A GameLoop contains a World reference that it continuously updates and 
 * renders. Essentially, the game IS that world. This reference is dynamic and
 * can switch between different worlds on the fly (allowing the user to have
 * different levels, types of gameplay, etc. very easily). However, it should be
 * noted that if a World is being updated and rendered in one game cycle, it 
 * will finish that cycle before anything happens to the newly set World.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GameLoop extends Loop
{
	/**
	 * The profiler that the GameLoop uses to determine how well the game engine
	 * is performing.
	 **/
	protected GameLoopProfiler profiler;
	
	/**
	 * The general World reference.
	 **/
	protected World world;
	
	/**
	 * The World currently being updated and rendered.
	 **/
	private World activeWorld;

	/*************************************************************************
	 * Creates a GameLoop that uses a default World and a default 
	 * GameLoopProfiler.
	 *************************************************************************/
	public GameLoop()
	{
		this(new World(), new GameLoopProfiler.Default());
	}
	
	/*************************************************************************
	 * Creates a GameLoop that starts out with the indicated World. It uses a 
	 * default GameLoopProfiler.
	 * 
	 * @param world
	 * 			  The World for the GameLoop to initially update and render.
	 *************************************************************************/
	public GameLoop(World world)
	{
		this(world, new GameLoopProfiler.Default());
	}
	
	/*************************************************************************
	 * Creates a GameLoop with the indicated World and Profiler.
	 * 
	 * @param world
	 * 			  The World for the GameLoop to initially update and render.
	 * @param profiler
	 * 			  The GameLoopProfiler used to profile the game's performance.
	 *************************************************************************/
	public GameLoop(World world, GameLoopProfiler profiler)
	{
		this.world = world;
		this.profiler = profiler;
	}
	
	/*************************************************************************
	 * Accesses this GameLoop's profiler.
	 * 
	 * @return The PofilerDisplay used to monitor this GameLoop's performance.
	 *************************************************************************/
	public ProfilerDisplay profiler() { return profiler; }
	
	/*************************************************************************
	 * Redefines the object the GameLoop uses to profile its performance.
	 * 
	 * @param profiler
	 * 			  The new GameLoopProfiler to use.
	 *************************************************************************/
	public void setProfiler(GameLoopProfiler profiler)
	{
		this.profiler = profiler;
	}

	/*************************************************************************
	 * Accesses the World the GameLoop is currently using.
	 * 
	 * @return The current gameworld.
	 *************************************************************************/
	public World world() { return world; }
	
	/*************************************************************************
	 * Redefines the current gameworld.
	 * 
	 * @param world
	 * 			  The new World to use in updating and rendering.
	 *************************************************************************/
	public void setWorld(World world)
	{
		this.world = world;
	}

	/*************************************************************************
	 * Tells the GameManager that this GameLoop is beginning, and pushes it on
	 * top of the stack.
	 ***************************************************************/ @Override
	protected void start()
	{
		GameManager.gameLoops().push(this);
	}

	/*************************************************************************
	 * Loops the game, updating and rendering as necessary.
	 ***************************************************************/ @Override
	public void run()
	{
		start();
		while(!exit && !Display.isCloseRequested())
		{
			controller.tick();
			activeWorld = world;
			update(controller.delta());
			render();
			profiler.beginSleep();
			controller.sleep();
			profiler.endSleep();
			profiler.advanceFPS();
		}
		end();
	}

	/*************************************************************************
	 * Flags the GameManager that this GameLoop has finished and should be
	 * removed from the stack.
	 ***************************************************************/ @Override
	protected void end()
	{
		GameManager.gameLoops().pop();
	}

	/*************************************************************************
	 * This method can be used to put additional logic code in GameLoop that is 
	 * independent of the current World. An example would be a check determining
	 * is the user has elected to exit the game (by pressing ESC or something
	 * similar).
	 * 
	 * @param delta
	 *            The number of clock ticks that have passed since the last
	 *            update.
	 *************************************************************************/
	public void advanceGame(int delta) { }

	/*************************************************************************
	 * Updates the game.
	 * 
	 * @param delta
	 *            The number of clock ticks that have passed since the last
	 *            update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		profiler.beginLogic();
		KeyboardHandler.update();
		MouseHandler.update();
		advanceGame(delta);
		activeWorld.update(delta);
		GameManager.soundBoard().update(delta);
		profiler.update(delta);
		profiler.endLogic();
	}

	/*************************************************************************
	 * Renders the game and profiler information, measuring the amount of time 
	 * taken.
	 ***************************************************************/ @Override
	public void render()
	{
		profiler.beginRender();
		WindowManager.controller().beginRender();
		activeWorld.render();
		profiler.render();
		WindowManager.controller().endRender();
		profiler.endRender();
	}
}