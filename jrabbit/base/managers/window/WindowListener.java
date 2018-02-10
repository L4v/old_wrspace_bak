package org.jrabbit.base.managers.window;

/*****************************************************************************
 * A WindowListener can "listen" to the WindowManager, becoming updated whenever
 * the settings for the Display are modified.
 * 
 * A WindowListener can receive alerts for various types of changes; these cover
 * such changes as the Display being resized, the rendered scene changing, the
 * viewport being altered, etc.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface WindowListener
{
	/**
	 * Indicates that the dimensions (resolution) of the Display have changed.
	 **/
	public static final int DISPLAY_RESIZE = 0;
	
	/**
	 * Indicates that the scene being rendered has changed.
	 **/
	public static final int SCENE_CHANGE = 1;

	/**
	 * Indicates that the active viewport has changed.
	 **/
	public static final int VIEWPORT_CHANGE = 2;
	
	/**
	 * Indicates that the Display has entered fullscreen mode.
	 **/
	public static final int ENTERED_FULLSCREEN = 3;
	
	/**
	 * Indicates that the Display has entered windowed mode.
	 **/
	public static final int ENTERED_WINDOW = 4;
	
	/*************************************************************************
	 * Alerts the listener that Window settings have been altered.
	 * 
	 * @param changeType
	 *            Indicated the category of change that occurred. This value
	 *            is one of those within {@link WindowListener}.
	 *************************************************************************/
	public void windowChanged(int changeType);
}
