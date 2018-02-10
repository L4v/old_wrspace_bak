package org.jrabbit.base.managers.window;

import java.util.LinkedList;

import org.jrabbit.base.graphics.misc.IconLoader;
import org.jrabbit.base.managers.window.controllers.WindowController;
import org.lwjgl.opengl.Display;

/*****************************************************************************
 * WindowManager is a static class that handles easy manipulation of the
 * Display.
 * 
 * The heavy-duty work is all done via the WindowManager's WindowController,
 * which can be set. All other methods are not really necessary, but are
 * provided to create a unified API.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class WindowManager
{
	/**
	 * The current WindowController. This should be set at startup and not
	 * changed.
	 **/
	private static WindowController controller;
	
	/**
	 * All currently active WindowListeners.
	 **/
	private static LinkedList<WindowListener> listeners = 
			new LinkedList<WindowListener>();

	/*************************************************************************
	 * Accesses the current WindowController.
	 * 
	 * @return The WindowController used to handle window and scene settings.
	 *************************************************************************/
	public static WindowController controller() { return controller; }

	/*************************************************************************
	 * Sets the WindowController to use to manage the Display. This should only
	 * be used at startup.
	 * 
	 * @param wC
	 *            The new WindowController.
	 *************************************************************************/
	public static void setController(WindowController wC)
	{
		controller = wC;
	}
	
	/*************************************************************************
	 * Accesses the list of WindowListeners.
	 * 
	 * NOTE: To add or remove listeners, simply add them to the list retrieved
	 * by this method. 
	 * 
	 * @return A LinkedList of all currently active WindowListeners.
	 *************************************************************************/
	public static LinkedList<WindowListener> listeners() { return listeners; }
	
	/*************************************************************************
	 * Alerts all current WindowListeners that a change has occurred in the
	 * Display and Scene settings.
	 * 
	 * @param changeType
	 * 			  The ID of the change that has occurred.
	 *************************************************************************/
	public static void alertWindowChange(int changeType)
	{
		for(WindowListener listener:listeners)
			listener.windowChanged(changeType);
	}

	/*************************************************************************
	 * Defines the title of the window.
	 * 
	 * @param title
	 *            The new title for the game.
	 *************************************************************************/
	public static void setTitle(String title)
	{
		Display.setTitle(title);
	}

	/*************************************************************************
	 * Accesses the current title.
	 * 
	 * @return The title of the Display containing the game.
	 *************************************************************************/
	public static String title()
	{
		return Display.getTitle();
	}

	/*************************************************************************
	 * Loads the image at the indicated location and sets it as the game's icon.
	 * 
	 * @param filepath
	 *            The file location of the icon image.
	 *************************************************************************/
	public static void setIcon(String filepath)
	{
		Display.setIcon(IconLoader.load(filepath));
	}

	/*************************************************************************
	 * Accesses the desktop's dimensions.
	 * 
	 * @return The width of the entire desktop, in pixels.
	 *************************************************************************/
	public static int screenWidth()
	{
		return Display.getDesktopDisplayMode().getWidth();
	}

	/*************************************************************************
	 * Accesses the desktop's dimensions.
	 * 
	 * @return The height of the entire desktop, in pixels.
	 *************************************************************************/
	public static int screenHeight()
	{
		return Display.getDesktopDisplayMode().getHeight();
	}

	/*************************************************************************
	 * Accesses the Display's dimensions.
	 * 
	 * @return The width of the Display, in pixels.
	 *************************************************************************/
	public static int displayWidth()
	{
		return Display.getDisplayMode().getWidth();
	}

	/*************************************************************************
	 * Accesses the Display's dimensions.
	 * 
	 * @return The height of the Display, in pixels.
	 *************************************************************************/
	public static int displayHeight()
	{
		return Display.getDisplayMode().getHeight();
	}
}