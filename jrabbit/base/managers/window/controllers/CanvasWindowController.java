package org.jrabbit.base.managers.window.controllers;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.jrabbit.base.managers.window.WindowManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import static org.jrabbit.base.managers.window.WindowListener.*;

/*****************************************************************************
 * A CanvasWindowController is a bit more specialized than the
 * DesktopWindowController - it handles an LWJGL Display that is set onto an AWT
 * Canvas instead of being a standalone application.
 * 
 * This, it turns out, can be quite useful. Using this WindowController, a
 * jRabbit game can be placed in any Swing or AWT application (or applet). This
 * allows browser-based games, editors, you name it!
 * 
 * This WindowController automatically listens to its Canvas for size changes;
 * when the canvas is resized, the viewport and scene settings are automatically
 * adjusted accordingly.
 * 
 * NOTE: This WindowController does not allow multiple LWJGL views. To do so,
 * one needs to use an AWTGLCanvas, instead of Display.setParent().
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CanvasWindowController extends BaseWindowController implements
		ComponentListener
{
	/**
	 * A flag that determines whether or not the Display is currently set to a
	 * canvas.
	 **/
	protected boolean onCanvas;

	/**
	 * A flag that determines whether or not the view needs to adjust for a
	 * resized Canvas.
	 **/
	protected boolean needResize;

	/**
	 * A reference to the Canvas the Display has as a parent.
	 **/
	protected Canvas parent;

	/*************************************************************************
	 * Creates a CanvasWindowController that will plant the Display on the
	 * indicated Canvas.
	 * 
	 * @param canvas
	 *            The canvas to use as the parent of the Display.
	 *************************************************************************/
	public CanvasWindowController(Canvas canvas)
	{
		parent = canvas;
	}

	/*************************************************************************
	 * Initializes the WindowController on the initial Canvas.
	 ***************************************************************/ @Override
	public void create()
	{
		try
		{
			Display.setParent(parent);
			Display.create();

			fullSceneWidth = parent.getWidth();
			fullSceneHeight = parent.getHeight();
			fullSceneWidth = displayWidth = parent.getWidth();
			fullSceneHeight = displayHeight = parent.getHeight();

			setView(fullSceneWidth, fullSceneHeight, fullSceneWidth,
					fullSceneHeight, 0, 0);
			onCanvas = true;
			needResize = false;

			parent.addComponentListener(this);
		}
		catch (LWJGLException e)
		{
			System.err.println("Something went horribly, horribly wrong and "
					+ "we can't make the game! :(");
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Requesting a fullscreen mode will pop the Display off of the parent
	 * canvas and into its own fullscreen view. Otherwise, this functions in the
	 * same way as the fullscreen request in BaseWindowController.
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
		if (onCanvas)
			try
			{
				Display.setParent(null);
				parent.removeComponentListener(this);
				onCanvas = false;
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		super.resolveFullscreenRequest(sceneWidth, sceneHeight,
				resolutionWidth, resolutionHeight);
	}

	/*************************************************************************
	 * Any and all requests to enter a windowed state simply pop the Display
	 * back onto its parent canvas. The indicated dimensions are immaterial.
	 * 
	 * @param width
	 *            The desired width of the window (ignored).
	 * @param height
	 *            The desired height of the window (ignored).
	 ***************************************************************/ @Override
	public void resolveWindowRequest(int width, int height)
	{
		if (!onCanvas)
			try
			{
				Display.setFullscreen(false);
				Display.setParent(parent);
				onCanvas = true;
				parent.addComponentListener(this);
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		fullSceneWidth = displayWidth = parent.getWidth();
		fullSceneHeight = displayHeight = parent.getHeight();
		WindowManager.alertWindowChange(ENTERED_WINDOW);
		setView(fullSceneWidth, fullSceneHeight, fullSceneWidth,
				fullSceneHeight, 0, 0);
	}

	/*************************************************************************
	 * At the start of every render cycle, this checks to see if the
	 * CanvasWindowController needs to resize the view.
	 ***************************************************************/ @Override
	public void beginRender()
	{
		if (needResize)
		{
			resize();
			needResize = false;
		}
	}

	/*************************************************************************
	 * Adjusts the CanvasWindowController's size and view to accommodate a
	 * differently sized parent Canvas.
	 *************************************************************************/
	public void resize()
	{
		if (onCanvas)
		{
			displayWidth = parent.getWidth();
			displayHeight = parent.getHeight();
			fullSceneWidth = (int) Math.abs(displayWidth * sceneMultX);
			fullSceneHeight = (int) Math.abs(displayHeight * sceneMultY);
			if (embedded())
				super.embed(sceneWidth, sceneHeight, embedOffsetX, 
						embedOffsetY);
			else
				setView(fullSceneWidth, fullSceneHeight, fullSceneWidth,
						fullSceneHeight, 0, 0);
		}
	}

	/*************************************************************************
	 * This ComponentListener method is not needed by CanvasWindowController;
	 * it's implementation is empty.
	 * 
	 * @param e
	 *            The ComponentEvent to process.
	 ***************************************************************/ @Override
	public void componentHidden(ComponentEvent e) { }

	/*************************************************************************
	 * This ComponentListener method is not needed by CanvasWindowController;
	 * it's implementation is empty.
	 * 
	 * @param e
	 *            The ComponentEvent to process.
	 ***************************************************************/ @Override
	public void componentMoved(ComponentEvent e) { }

	/*************************************************************************
	 * Flags the CanvasWindowController that it should adjust its view on the
	 * next render.
	 * 
	 * @param e
	 *            The ComponentEvent to process.
	 ***************************************************************/ @Override
	public void componentResized(ComponentEvent e)
	{
		if (onCanvas)
			needResize = true;
	}

	/*************************************************************************
	 * This ComponentListener method is not needed by CanvasWindowController;
	 * it's implementation is empty.
	 * 
	 * @param e
	 *            The ComponentEvent to process.
	 ***************************************************************/ @Override
	public void componentShown(ComponentEvent e) { }
}