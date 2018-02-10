package org.jrabbit.base.managers.window.controllers;

import org.jrabbit.base.data.DataController;
import org.jrabbit.base.graphics.types.Dimensioned;
import org.jrabbit.base.graphics.types.Viewer;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.math.vector.VectorTransform;
import org.lwjgl.opengl.Display;

/*****************************************************************************
 * WindowController is an abstract class meant to provide a unified, extensible
 * API for managing the LWJGL Display and making it interface with jRabbit.
 * 
 * There are two general ways of setting the base Display: Making it fullscreen
 * and placing it in a window. Windowed mode is fairly straightforward, but
 * fullscreen mode is more complicated, as it allows various resolutions. When
 * fullscreen, the visible resolution may be different than the desired scene to
 * render (for example, we may want to show a 1024x768 scene at 640x400
 * resolution).
 * 
 * After the display has been set, it's also possible to "embed" the view, where
 * only a portion of the screen is rendered to. This is possible in both
 * fullscreen and windowed modes. This has several potential uses, such as
 * enforcing a strict viewing area for all games. Additionally, the screen
 * outside of the embedded view can be rendered to, allowing decoration around
 * the active game.
 * 
 * Also, WindowController's responsibility goes beyond just changing settings on
 * the Display. It's also used to transform the Mouse location into screen
 * coordinates useful to the engine. Finally, it can be used to determine if a
 * screen object is viewed, permitting fast culling.
 * 
 * WindowControllers are supposed to manage alerting WindowListeners that a
 * change has occurred; when they make changes they must call {@link 
 * WindowManager#alertWindowChange(int)} indicating the change that has been
 * enacted.
 * 
 * NOTE: When indicating dimensions, supplying 0 in any value will cause that
 * value to default to the appropriate desktop dimension. So, if you say
 * setFullscreen(0, 0) and the computer monitor is 1024x768, the resulting
 * fullscreen display will be 1024x768.
 *****************************************************************************/
public abstract class WindowController implements DataController,
		VectorTransform, Viewer, Dimensioned
{
	/**
	 * The width of the "scene" viewed in the window. This does not have to be
	 * the same as the current DisplayMode's width.
	 **/
	protected int sceneWidth;

	/**
	 * The height of the "scene" viewed in the window. This does not have to be
	 * the same as the current DisplayMode's height.
	 **/
	protected int sceneHeight;

	/*************************************************************************
	 * Determines whether the LWJGL Display and the current window settings are
	 * valid.
	 * 
	 * @return Whether or not the window has been created and the LWJGL context
	 *         is usable.
	 ***************************************************************/ @Override
	public boolean valid()
	{
		return Display.isCreated();
	}

	/*************************************************************************
	 * Handles adjusting the indicated width.
	 * 
	 * @param width
	 *            The desired width to use.
	 * 
	 * @return If the supplied width was 0, returns the width of the desktop.
	 *         Otherwise, this returns the same width as it was supplied.
	 *************************************************************************/
	protected int adjustWidth(int width)
	{
		return (width == 0 ? WindowManager.screenWidth() : width);
	}

	/*************************************************************************
	 * Handles adjusting the indicated height.
	 * 
	 * @param height
	 *            The desired height to use.
	 * 
	 * @return If the supplied height was 0, returns the height of the desktop.
	 *         Otherwise, this returns the same height as it was supplied.
	 *************************************************************************/
	protected int adjustHeight(int height)
	{
		return (height == 0 ? WindowManager.screenHeight() : height);
	}

	/*************************************************************************
	 * Determines whether or not the current DisplayMode is equivalent to the
	 * one required by the indicated settings.
	 * 
	 * @param width
	 *            The width to check against.
	 * @param height
	 *            The height to check against.
	 * @param fullscreen
	 *            Whether or not the Display needs to be fullscreen.
	 * 
	 * @return True if the Display is set to the indicated settings, false if
	 *         not.
	 *************************************************************************/
	protected boolean isDisplaySetTo(int width, int height, boolean fullscreen)
	{
		return Display.getDisplayMode().getWidth() == width
				&& Display.getDisplayMode().getHeight() == height
				&& Display.isFullscreen() == fullscreen;
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
	 *************************************************************************/
	protected abstract void resolveFullscreenRequest(int sceneWidth,
			int sceneHeight, int resolutionWidth, int resolutionHeight);

	/*************************************************************************
	 * Sets the Display into a window with the indicated dimensions.
	 * 
	 * @param width
	 *            The width of the desired window.
	 * @param height
	 *            The height of the desired window.
	 *************************************************************************/
	protected abstract void resolveWindowRequest(int width, int height);

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
	 * double buffering can cause unfortunate behavior.
	 * 
	 * @param sceneWidth
	 *            The width of the sub-scene to render.
	 * @param sceneHeight
	 *            The height of the sub-scene to render.
	 * @param offsetX
	 *            The X offset of the subscene to render; this is based on scene
	 *            size.
	 * @param offsetY
	 *            The Y offset of the subscene to render; this is based on scene
	 *            size.
	 *************************************************************************/
	protected abstract void resolveEmbedRequest(int sceneWidth,
			int sceneHeight, int offsetX, int offsetY);

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
	 *************************************************************************/
	public abstract void setView(int sceneWidth, int sceneHeight,
			int viewportWidth, int viewportHeight, int viewportX, int viewportY);

	/*************************************************************************
	 * Makes the Display enter fullscreen mode with the resolution and scene
	 * dimensions of the desktop. Basically, this is "high-res" fullscreen.
	 *************************************************************************/
	public void setFullscreen()
	{
		int width = adjustWidth(0);
		int height = adjustHeight(0);
		resolveFullscreenRequest(width, height, width, height);
	}

	/*************************************************************************
	 * Makes the Display enter fullscreen mode at the indicated resolution.
	 * 
	 * If the indicated resolution is not available as a fullscreen mode, the
	 * closest available DisplayMode is chosen and an error is printed to the
	 * console.
	 * 
	 * NOTE: This method changes the resolution of the display, but the scene
	 * shown is considered to be the size of the desktop. That way, if the
	 * high-res fullscreen mode is used, and then this method is called, the
	 * game will be at a lower resolution but no change will occur to what is
	 * being rendered.
	 * 
	 * @param resolutionWidth
	 *            The width of the DisplayMode to use.
	 * @param resolutionHeight
	 *            The width of the DisplayMode to use.
	 *************************************************************************/
	public void setFullscreen(int resolutionWidth, int resolutionHeight)
	{
		resolveFullscreenRequest(adjustWidth(0), adjustHeight(0),
				adjustWidth(resolutionWidth), adjustHeight(resolutionHeight));
	}

	/*************************************************************************
	 * Makes the Display enter fullscreen mode at the indicated resolution, and
	 * sets the scene dimensions.
	 * 
	 * @param sceneWidth
	 *            The width of the scene to render.
	 * @param sceneHeight
	 *            The height of the scene to render.
	 * @param resolutionWidth
	 *            The width of the DisplayMode used to render the scene.
	 * @param resolutionHeight
	 *            The height of the DisplayMode used to render the scene.
	 *************************************************************************/
	public void setFullscreen(int sceneWidth, int sceneHeight,
			int resolutionWidth, int resolutionHeight)
	{
		resolveFullscreenRequest(adjustWidth(sceneWidth),
				adjustHeight(sceneHeight), adjustWidth(resolutionWidth),
				adjustHeight(resolutionHeight));
	}

	/*************************************************************************
	 * Places the Display into a window of the indicated dimensions.
	 * 
	 * NOTE: It's not recommended to further embed the view once it's already in
	 * a window. It's double buffered, and that can cause problems, as jRabbit
	 * does not automatically clear the color buffer.
	 * 
	 * @param width
	 *            The width of the window.
	 * @param height
	 *            The height of the window.
	 *************************************************************************/
	public void setWindowed(int width, int height)
	{
		resolveWindowRequest(adjustWidth(width), adjustHeight(height));
	}

	/*************************************************************************
	 * Embeds the view, rendering only the section of the screen that represents
	 * a scene of the indicated dimensions. The resulting viewport is centered.
	 * 
	 * NOTE: Anything already rendered on the screen stays there. This can be
	 * useful if you want to "decorate" the area around the screen,
	 * 
	 * @param sceneWidth
	 *            The width of the scene to render.
	 * @param sceneHeight
	 *            The height of the scene to render.
	 *************************************************************************/
	public void embed(int sceneWidth, int sceneHeight)
	{
		embed(adjustWidth(sceneWidth), adjustHeight(sceneHeight), 0, 0);
	}

	/*************************************************************************
	 * Embeds the view, rendering only the section of the screen that represents
	 * a scene of the indicated dimensions. The resulting viewport is offset
	 * from the center by the indicated amount.
	 * 
	 * @param sceneWidth
	 *            The width of the scene to render.
	 * @param sceneHeight
	 *            The height of the scene to render.
	 * @param offsetX
	 *            The X offset of the embedded view, in scene coordinates.
	 * @param offsetY
	 *            The Y offset of the embedded view, in scene coordinates.
	 *************************************************************************/
	public void embed(int sceneWidth, int sceneHeight, int offsetX, int offsetY)
	{
		resolveEmbedRequest(adjustWidth(sceneWidth), adjustHeight(sceneHeight),
				offsetX, offsetY);
	}

	/*************************************************************************
	 * Resets embedding settings, making the view take up all of the available
	 * screen. Calling this should result in the same scene as before any
	 * embed() calls.
	 *************************************************************************/
	public abstract void unembed();

	/*************************************************************************
	 * Checks the state of the view.
	 * 
	 * @return Whether or not the Display view is currently embedded.
	 *************************************************************************/
	public abstract boolean embedded();

	/*************************************************************************
	 * Begins a render sequence. It's quite possible that nothing is required in
	 * this method.
	 *************************************************************************/
	public void beginRender() { }

	/*************************************************************************
	 * Finishes a render and updates the jRabbit Display.
	 *************************************************************************/
	public void endRender()
	{
		Display.update();
	}

	/*************************************************************************
	 * Accesses information about the scene being rendered.
	 * 
	 * @return The width of the scene being rendered. This does not have to be
	 *         the same as the width of the Display.
	 ***************************************************************/ @Override
	public float width()
	{
		return sceneWidth;
	}

	/*************************************************************************
	 * Accesses information about the scene being rendered.
	 * 
	 * @return The height of the scene being rendered. This does not have to be
	 *         the same as the height of the Display.
	 ***************************************************************/ @Override
	public float height()
	{
		return sceneHeight;
	}

	/*************************************************************************
	 * Determines whether or not an object with the indicated location and
	 * radius is visible in the scene.
	 * 
	 * @return Whether or not the object is visible.
	 ***************************************************************/ @Override
	public boolean views(float x, float y, float radius)
	{
		return x + radius > 0 && y + radius > 0 && x - radius < sceneWidth
				&& y - radius < sceneHeight;
	}
}