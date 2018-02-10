package org.jrabbit.base.managers.window.controllers;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.managers.window.WindowUtils;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.jrabbit.base.managers.window.WindowListener.*;

/*****************************************************************************
 * BaseWindowController extends WindowController to provide the default
 * implementations for window operations and handle alerting WindowListeners.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class BaseWindowController extends WindowController
{
	/**
	 * The width of the scene when not embedded. This is used to steady embed
	 * calls and to make resetting from an embed easier.
	 **/
	protected int fullSceneWidth;

	/**
	 * The height of the scene when not embedded. This is used to steady embed
	 * calls and to make resetting from an embed easier.
	 **/
	protected int fullSceneHeight;

	/**
	 * The offset to apply to the mouse X coordinate for scene coordinate
	 * transformation.
	 **/
	protected float sceneOffsetX;

	/**
	 * The offset to apply to the mouse Y coordinate for scene coordinate
	 * transformation.
	 **/
	protected float sceneOffsetY;

	/**
	 * The amount to multiply the mouse X coordinate for scene coordinate
	 * transformation.
	 **/
	protected float sceneMultX = 1;

	/**
	 * The amount to multiply the mouse Y coordinate for scene coordinate
	 * transformation.
	 **/
	protected float sceneMultY = -1;

	/**
	 * A flag that determines whether or not the view is embedded.
	 **/
	protected boolean embedded;

	/**
	 * The stored X value of how to offset the embedded view.
	 **/
	protected int embedOffsetX;

	/**
	 * The stored Y value of how to offset the embedded view.
	 **/
	protected int embedOffsetY;

	/**
	 * The width of the available display.
	 **/
	protected int displayWidth;

	/**
	 * The height of the available display.
	 **/
	protected int displayHeight;

	/*************************************************************************
	 * Destroys the Display, wiping the OpenGL context and ending all LWJGL
	 * operations.
	 ***************************************************************/ @Override
	public void destroy()
	{
		Display.destroy();
	}

	/*************************************************************************
	 * Sets the Display to have the indicated fullscreen settings.
	 * 
	 * @param sceneWidth
	 *            The width of the scene to render.
	 * @param sceneHeight
	 *            The height of the scene to render.
	 * @param resolutionWidth
	 *            The width of the DisplayMode to use.
	 * @param resolutionHeight
	 *            The height of the DisplayMode to use.
	 ***************************************************************/ @Override
	public void resolveFullscreenRequest(int sceneWidth, int sceneHeight,
			int resolutionWidth, int resolutionHeight)
	{
		DisplayMode dM = WindowUtils.closestFullscreenMode(resolutionWidth,
				resolutionHeight);
		if (!isDisplaySetTo(dM.getWidth(), dM.getHeight(), true))
			try
			{
				Display.setFullscreen(true);
				Display.setDisplayMode(dM);
				WindowManager.alertWindowChange(DISPLAY_RESIZE);
				WindowManager.alertWindowChange(ENTERED_FULLSCREEN);
			}
			catch (LWJGLException e)
			{
				System.err.println("Unable to setup fullscreen mode ["
						+ resolutionWidth + "x" + resolutionHeight + "]");
				e.printStackTrace();
			}
		fullSceneWidth = sceneWidth;
		fullSceneHeight = sceneHeight;
		displayWidth = Display.getDisplayMode().getWidth();
		displayHeight = Display.getDisplayMode().getHeight();
		embedded = false;
		setView(sceneWidth, sceneHeight, resolutionWidth, resolutionHeight, 0,
				0);
	}

	/*************************************************************************
	 * Sets the Display into a window with the indicated dimensions.
	 * 
	 * @param width
	 *            The width of the desired window.
	 * @param height
	 *            The height of the desired window.
	 ***************************************************************/ @Override
	protected void resolveWindowRequest(int width, int height)
	{
		if (!isDisplaySetTo(width, height, false))
			try
			{
				Display.setFullscreen(false);
				Display.setDisplayMode(new DisplayMode(width, height));
				WindowManager.alertWindowChange(DISPLAY_RESIZE);
				WindowManager.alertWindowChange(ENTERED_WINDOW);
			}
			catch (LWJGLException e)
			{
				System.err.println("Unable to setup windowed mode [" + width
						+ "x" + height + "]");
				e.printStackTrace();
			}
		fullSceneWidth = width;
		fullSceneHeight = height;
		displayWidth = Display.getDisplayMode().getWidth();
		displayHeight = Display.getDisplayMode().getHeight();
		embedded = false;
		setView(width, height, width, height, 0, 0);
	}

	/*************************************************************************
	 * "Embeds" the active rendering area on the Display.
	 * 
	 * The size of the scene is calculated from "scene" size; the current
	 * resolution is immaterial. Therefore, if you have a fullscreen mode
	 * rendering a scene, embedding a 600x400 area will result in the same
	 * section of the scene being chosen at any resolution.
	 * 
	 * The offset values are from the middle of the screen; supplied 0 to either
	 * value will cause the scene to center on that axis. As usual, +X is to the
	 * right and +Y is down.
	 * 
	 * NOTE: You shouldn't use this method with a Windowed Display, as its
	 * double buffering causes unfortunate behavior.
	 * 
	 * @param sceneWidth
	 *            The width of the sub-scene to render.
	 * @param sceneHeight
	 *            The height of the sub-scene to render.
	 * @param offsetX
	 *            The X offset of the sub-scene to render; this is based on
	 *            scene size.
	 * @param offsetY
	 *            The Y offset of the sub-scene to render; this is based on
	 *            scene size.
	 ***************************************************************/ @Override
	public void resolveEmbedRequest(int sceneWidth, int sceneHeight,
			int offsetX, int offsetY)
	{
		embedOffsetX = offsetX;
		embedOffsetY = offsetY;
		int viewportWidth = (int) (displayWidth * (float) sceneWidth / 
				fullSceneWidth);
		int viewportHeight = (int) (displayHeight * (float) sceneHeight / 
				fullSceneHeight);
		embedded = true;
		setView(sceneWidth, sceneHeight, viewportWidth, viewportHeight,
				((displayWidth - viewportWidth) / 2) + (int) (offsetX * 
				(float) displayWidth / fullSceneWidth), ((displayHeight - 
				viewportHeight) / 2) - (int) (offsetY * (float) displayHeight 
				/ fullSceneHeight));
	}

	/*************************************************************************
	 * Resets embedding settings, making the view take up all of the available
	 * screen. Calling this results in the same scene as before any embed()
	 * calls.
	 ***************************************************************/ @Override
	public void unembed()
	{
		setView(fullSceneWidth, fullSceneHeight, displayWidth, displayHeight,
				0, 0);
	}

	/*************************************************************************
	 * Checks the state of the view.
	 * 
	 * @return Whether or not the Display view is currently embedded.
	 ***************************************************************/ @Override
	public boolean embedded()
	{
		return embedded;
	}

	/*************************************************************************
	 * Sets the viewport and scene size, and updates all appropriate settings
	 * (mouse transforming, orthogonal view).
	 * 
	 * NOTE: Unlike most other methods, the coordinates for the viewport
	 * settings are in OpenGL format. Thus, +Y is up the screen (like cartesian
	 * coordinates), the origin is the bottom left, and the size of the viewport
	 * is based on the dimensions of the current DisplayMode (not the scene
	 * being rendered).
	 * 
	 * @param sceneWidth
	 *            The new width of the scene.
	 * @param sceneHeight
	 *            The new height of the scene.
	 * @param viewportWidth
	 *            The width of the viewport.
	 * @param viewportHeight
	 *            The height of the viewport.
	 * @param viewportX
	 *            The X offset of the viewport, from the bottom left of the
	 *            screen.
	 * @param viewportY
	 *            The Y offset of the viewport, from the bottom left of the
	 *            screen.
	 ***************************************************************/ @Override
	public void setView(int sceneWidth, int sceneHeight, int viewportWidth,
			int viewportHeight, int viewportX, int viewportY)
	{
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		sceneMultX = (float) sceneWidth / viewportWidth;
		sceneMultY = -(float) sceneHeight / viewportHeight;
		sceneOffsetX = -viewportX;
		sceneOffsetY = -(viewportHeight + viewportY);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, sceneWidth, sceneHeight, 0, -1000, 1000);
		GL11.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		WindowManager.alertWindowChange(SCENE_CHANGE);
		WindowManager.alertWindowChange(VIEWPORT_CHANGE);
	}

	/*************************************************************************
	 * Translates coordinates of the LWJGL Mouse into mouse coordinates in
	 * relation to the currently rendered scene.
	 * 
	 * @param vector
	 *            The coordinates of the Mouse on the Display.
	 * 
	 * @return The coordinates of the Mouse in the scene.
	 ***************************************************************/ @Override
	public Vector2f transform(BaseVector2f vector)
	{
		return new Vector2f((sceneOffsetX + vector.x()) * sceneMultX,
				(sceneOffsetY + vector.y()) * sceneMultY);
	}
}