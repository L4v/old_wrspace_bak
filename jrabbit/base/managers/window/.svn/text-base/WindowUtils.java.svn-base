package org.jrabbit.base.managers.window;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/*****************************************************************************
 * WindowUtils provides some convenience methods for managing the Display.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class WindowUtils
{
	/*************************************************************************
	 * Learns what fullscreen DisplayModes are available.
	 * 
	 * @return A list of all available fullscreen modes that match the desktop's
	 *         BPP.
	 *************************************************************************/
	public static ArrayList<DisplayMode> availableFullscreenModes()
	{
		ArrayList<DisplayMode> modes = new ArrayList<DisplayMode>();

		try
		{
			for (DisplayMode dM : Display.getAvailableDisplayModes())
				if (dM.isFullscreenCapable()
						&& dM.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
					modes.add(dM);
		}
		catch (LWJGLException e)
		{
			System.err.println("Error in detecting window sizes!");
			e.printStackTrace();
		}

		return modes;
	}

	/*************************************************************************
	 * Obtains the fullscreen DisplayMode closest to the indicated values.
	 * 
	 * @param width
	 *            The desired width of the fullscreen mode.
	 * @param height
	 *            The desired height of the fullscreen mode.
	 * 
	 * @return The closest fullscreen mode possible on the current computer.
	 *************************************************************************/
	public static DisplayMode closestFullscreenMode(int width, int height)
	{
		ArrayList<DisplayModeRank> displayModes = 
			new ArrayList<DisplayModeRank>();

		try
		{
			for (DisplayMode dM : Display.getAvailableDisplayModes())
				if (dM.isFullscreenCapable()
						&& dM.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
				{
					if (dM.getWidth() == width && dM.getHeight() == height)
						return dM;
					displayModes.add(new DisplayModeRank(dM, width, height));
				}
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			return null;
		}

		System.err.println("The desired window size [" + width + "x" + height
				+ "] is unavailable, we'll try to make do!");

		DisplayModeRank choice = displayModes.get(0);
		for (DisplayModeRank dMR : displayModes)
		{
			choice = choice.chooseBetter(dMR);
		}

		System.err.println("The final window dimensions are: ["
				+ choice.displayMode.getWidth() + "x"
				+ choice.displayMode.getHeight() + "]");

		return choice.displayMode;
	}

	/*************************************************************************
	 * An internal class used to simplify finding the closest DisplayMode to an
	 * unobtainable target resolution.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	private static class DisplayModeRank
	{
		/**
		 * The amount the indicated DisplayMode is off the target width.
		 **/
		private int widthRanking;

		/**
		 * The amount the indicated DisplayMode is off the target height.
		 **/
		private int heightRanking;

		/**
		 * The DisplayMode this ranking represents.
		 **/
		private DisplayMode displayMode;

		/*********************************************************************
		 * Calculates a DisplayModeRanking from the indicated values.
		 * 
		 * @param dM
		 *            The DisplayMode to represent.
		 * @param width
		 *            The target resolution width.
		 * @param height
		 *            The target resolution height.
		 *********************************************************************/
		private DisplayModeRank(DisplayMode dM, int width, int height)
		{
			displayMode = dM;
			widthRanking = Math.abs(width - displayMode.getWidth());
			heightRanking = Math.abs(height - displayMode.getHeight());
		}

		private DisplayModeRank chooseBetter(DisplayModeRank dM)
		{
			return Math.abs(widthRanking * heightRanking) < Math
					.abs(dM.widthRanking * dM.heightRanking) ? this : dM;
		}
	}
}