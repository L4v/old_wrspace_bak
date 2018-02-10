package org.jrabbit.standard.game.managers;

import java.util.Stack;

import org.jrabbit.base.graphics.GLSettings;
import org.jrabbit.base.graphics.font.Font;
import org.jrabbit.base.graphics.font.renderer.AngelCodeRenderer;
import org.jrabbit.base.graphics.primitive.PrimitivePainter;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.standard.game.loop.GameLoop;
import org.jrabbit.standard.game.sound.SoundBoard;
import org.jrabbit.standard.game.world.World;
import org.jrabbit.standard.game.world.camera.Camera;
import org.jrabbit.standard.game.world.camera.components.SceneToCameraCoordTransform;

/*****************************************************************************
 * GameManager provides some general, universal-access controls for managing 
 * games using the standard jRabbit setup.
 * 
 * None of the content managed by this class is from the base package; 
 * GameManager is solely devoted to things in the standard game package (World,
 * GameLoop, etc).
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GameManager
{
	/**
	 * GameLoops are allowed to be "recursive"; that is, one may begin execution
	 * and looping while another is active. The new GameLoop will operate as 
	 * expected, and when it is finished, the old loop will resume where it left
	 * off. This Stack keeps track of the actively running loops.
	 **/
	private static Stack<GameLoop> gameLoops = new Stack<GameLoop>();
	
	/**
	 * The object that manages general volume and pitch settings for Sounds
	 * being played.
	 **/
	private static SoundBoard soundBoard;
	
	/*************************************************************************
	 * Accesses the Stack of GameLoops. This will probably usually have only
	 * one entry on it.
	 * 
	 * @return The list of all active GameLoops.
	 *************************************************************************/
	public static Stack<GameLoop> gameLoops() { return gameLoops; }
	
	/*************************************************************************
	 * Accesses the GameLoop that is currently operating.
	 * 
	 * @return The current GameLoop.
	 *************************************************************************/
	public static GameLoop currentLoop()
	{
		return gameLoops.peek();
	}
	
	/*************************************************************************
	 * Accesses the SoundBoard.
	 * 
	 * @return The general control for volume and pitch settings.
	 *************************************************************************/
	public static SoundBoard soundBoard() { return soundBoard; }
	
	/*************************************************************************
	 * Redefines the current SoundBoard.
	 * 
	 * @param soundboard
	 * 			  The new SoundBoard to use for Sound equalization.
	 *************************************************************************/
	public static void setSoundBoard(SoundBoard soundboard)
	{
		soundBoard = soundboard;
	}
	
	/*************************************************************************
	 * Accesses the current World.
	 * 
	 * @return The current World reference of the GameLoop.
	 *************************************************************************/
	public static World world() { return currentLoop().world(); }
	
	/*************************************************************************
	 * Redefines the current World.
	 * 
	 * @param world
	 * 			  The new World to use for updating and rendering.
	 *************************************************************************/
	public static void setWorld(World world) { currentLoop().setWorld(world); }
	
	/*************************************************************************
	 * Accesses the currently active Camera.
	 * 
	 * @return The active Camera of the active World.
	 *************************************************************************/
	public static Camera camera() { return world().camera(); }
	
	/*************************************************************************
	 * Initializes all settings used by the standard jRabbit implementation.
	 *************************************************************************/
	public static void create()
	{
		GLSettings.default2D();
		PrimitivePainter.create();
		soundBoard = new SoundBoard();
		MouseHandler.setPositionCalculator(new SceneToCameraCoordTransform());
		AngelCodeRenderer arialRenderer = new AngelCodeRenderer("org/jrabbit/" +
				"resources/Arial.fnt", "org/jrabbit/resources/Arial.png");
		arialRenderer.useAcceleration(true);
		Resources.fonts().add(new Font("Arial", arialRenderer));
		Resources.fonts().setDefaultFont("Arial");
	}
}