package org.jrabbit.base.managers.window.controllers;

import org.jrabbit.base.managers.window.WindowUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/*****************************************************************************
 * A DesktopWindowController is the default WindowController for, obviously,
 * games running on the desktop of computers. This is the optimal situation in
 * terms of Display management, for the Display is in its own window and has
 * total control over itself. This being the case, DesktopWindowController is
 * basically identical to BaseWindowController.
 * 
 * The default starting mode for the Display is in high-res fullscreen, but this
 * can be changed via the constructor.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DesktopWindowController extends BaseWindowController
{
	/**
	 * The initial width of the Display.
	 **/
	protected int startWidth;
	
	/**
	 * The initial height of the Display.
	 **/
	protected int startHeight;
	
	/**
	 * The initial width of the Display.
	 **/
	protected int startSceneWidth;
	
	/**
	 * The initial height of the Display.
	 **/
	protected int startSceneHeight;
	
	/**
	 * Whether or not to start in fullscreen mode.
	 **/
	protected boolean startFullscreen;
	
	/*************************************************************************
	 * Creates a new DesktopWindowController that initializes the Display in
	 * high-res fullscreen.
	 *************************************************************************/
	public DesktopWindowController()
	{
		startWidth = startHeight = startSceneWidth = startSceneHeight = 0;
		startFullscreen = true;
	}
	
	/*************************************************************************
	 * Creates a new DesktopWindowController that initializes the Display in
	 * windowed mode.
	 * 
	 * @param width
	 * 			  The width of the window to use.
	 * @param height
	 * 			  The height of the window to use.
	 *************************************************************************/
	public DesktopWindowController(int width, int height)
	{
		startWidth = startSceneWidth = width;
		startHeight = startSceneHeight = height;
		startFullscreen = false;
	}
	
	/*************************************************************************
	 * Creates a new DesktopWindowController that initializes the Display in
	 * either fullscreen or windowed mode.
	 * 
	 * @param width
	 * 			  The width of the window to use.
	 * @param height
	 * 			  The height of the window to use.
	 * @param fullscreen
	 * 			  Whether or not the Display should start in fullscreen mode.
	 *************************************************************************/
	public DesktopWindowController(int width, int height, boolean fullscreen)
	{
		startWidth = width;
		startHeight = height;
		startSceneWidth = fullscreen ? 0 : width;
		startSceneHeight = fullscreen ? 0 : height;
		startFullscreen = fullscreen;
	}
	
	/*************************************************************************
	 * Creates a new DesktopWindowController that initializes the Display in
	 * either fullscreen or windowed mode and renders the specified area.
	 * 
	 * @param width
	 * 			  The width of the window to use.
	 * @param height
	 * 			  The height of the window to use.
	 * @param sceneWidth
	 * 			  The width of the scene to render.
	 * @param sceneHeight
	 * 			  The height of the scene to render.
	 * @param fullscreen
	 * 			  Whether or not the Display should start in fullscreen mode.
	 *************************************************************************/
	public DesktopWindowController(int width, int height, int sceneWidth, 
			int sceneHeight, boolean fullscreen)
	{
		startWidth = width;
		startHeight = height;
		startSceneWidth = sceneWidth;
		startSceneHeight = sceneHeight;
		startFullscreen = fullscreen;
	}
	
	/*************************************************************************
	 * Initializes the Display in the DisplayMode indicated in the constructor
	 * of this DesktopWindowController.
	 ***************************************************************/ @Override
	public void create()
	{
		try
		{
			Display.setVSyncEnabled(true);

			if(startFullscreen)
			{
				if(startWidth != 0 && startHeight != 0)
					Display.setDisplayMode(WindowUtils.closestFullscreenMode(
							startWidth, startHeight));
			}
			else
				Display.setDisplayMode(new DisplayMode(adjustWidth(startWidth), 
						adjustHeight(startHeight)));
			Display.setFullscreen(startFullscreen);
			Display.create();
			
			DisplayMode dM = Display.getDisplayMode();
			displayWidth = dM.getWidth();
			displayHeight = dM.getHeight();
			fullSceneWidth = adjustWidth(startSceneWidth);
			fullSceneHeight = adjustHeight(startSceneHeight);
			setView(fullSceneWidth, fullSceneHeight, displayWidth,
					displayHeight, 0, 0);
		}
		catch (LWJGLException e)
		{
			System.err.println("Something went horribly, horribly wrong and "
					+ "we can't make the game! :(");
			e.printStackTrace();
			System.exit(1);
		}
	}
}