package org.jrabbit.base.input;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.jrabbit.base.graphics.misc.CursorLoader;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.jrabbit.base.math.vector.VectorPath;
import org.jrabbit.base.math.vector.VectorTransform;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/*****************************************************************************
 * MouseHandler is a static class that provides the default methods for reacting
 * to player input via the mouse. It is automatically updated in the main game
 * loop.
 * 
 * MouseHandler is designed to work with a mouse that has any number of buttons;
 * to learn the number of buttons simply use the getButtonCount() method in
 * Mouse.
 * 
 * Additionally, MouseHandler handles mouse movement interpretation. In to a
 * "motion-listener" system, where objects can be notified of mouse movements,
 * MouseHandler also stores VectorPaths that keeps track of the motion of the
 * mouse over time, both in world coordinates and screen coordinates. This path
 * has a maximum length that can be set.
 * 
 * NOTE: It's possible that a different input setup will better suit your needs.
 * If this is the case, you should be aware that by default, MouseHandler "eats"
 * all Mouse events. You can turn this activity off, however, and interpret them
 * with your own code.
 * 
 * @author Chris Molini
 *****************************************************************************/
public final class MouseHandler
{
	/**
	 * Whether or not MouseHandler should parse Mouse events.
	 **/
	private static boolean activated = true;

	/**
	 * The number of buttons on the mouse that are valid for input.
	 **/
	private static int NUM_BUTTONS = Mouse.getButtonCount();

	/**
	 * The stored data about mouse clicks.
	 **/
	private static boolean[] inputClicked = new boolean[NUM_BUTTONS];

	/**
	 * The stored data about mouse button releases.
	 **/
	private static boolean[] inputReleased = new boolean[NUM_BUTTONS];

	/**
	 * The stored data about held mouse buttons.
	 **/
	private static boolean[] inputDown = new boolean[NUM_BUTTONS];

	/**
	 * The amount the mouse wheel hase moved since the last update cycle.
	 **/
	private static int mouseWheelChange = 0;
	
	/**
	 * The location of the mouse, in screen coordinates.
	 **/
	private static Vector2f screenLocation = new Vector2f();
	
	/**
	 * The location of the mouse, in world coordinates.
	 **/
	private static Vector2f worldLocation = new Vector2f();

	/**
	 * The mouse motion path, in screen coordinates.
	 **/
	private static VectorPath screenPath = new VectorPath(10);

	/**
	 * The mouse motion path, in calculated game-world coordinates.
	 **/
	private static VectorPath worldPath = new VectorPath(10);

	/**
	 * The calculator that transforms screen coordinates into game-world
	 * coordinates. The initial value, shown here, simply copies the vector. It
	 * should be noted, however, that the default jRabbit setup redefines this
	 * upon initialization.
	 **/
	private static VectorTransform positionCalculator = new VectorTransform()
	{
		public Vector2f transform(BaseVector2f vector)
		{
			return new Vector2f(vector.x(), vector.y());
		}
	};

	/**
	 * The listeners that will be notified of wheel and button input events.
	 **/
	private static ArrayList<MouseListener> listeners = 
			new ArrayList<MouseListener>();

	/**
	 * The listeners that will be notified of movement events.
	 **/
	private static ArrayList<MouseMotionListener> motionListeners = 
			new ArrayList<MouseMotionListener>();

	/**
	 * The listeners that will be notified of all LWJGL Mouse events.
	 **/
	private static ArrayList<MouseEventListener> eventListeners = 
			new ArrayList<MouseEventListener>();

	/*************************************************************************
	 * Determines whether or not Mouse events should be processed. Use this to
	 * turn off MouseHandler calculations if you want to use a different input
	 * system for handling Keyboard events.
	 * 
	 * @param active
	 *            Whether or not the MouseHandler should process Mouse events.
	 *************************************************************************/
	public static void activate(boolean active)
	{
		activated = active;
	}

	/*************************************************************************
	 * Learns if the MouseHandler is active.
	 * 
	 * @return True if Mouse events are being processed, false if not.
	 *************************************************************************/
	public static boolean activated()
	{
		return activated;
	}

	/*************************************************************************
	 * Accesses the list of Mouse listeners.
	 * 
	 * NOTE: This is the means that should be used to add and remove new 
	 * MouseListeners.
	 * 
	 * @return All of the objects currently listening for input changes.
	 *************************************************************************/
	public static Collection<MouseListener> listeners()
	{
		return listeners;
	}

	/*************************************************************************
	 * Accesses the list of Mouse motion listeners.
	 * 
	 * NOTE: This is the means that should be used to add and remove new 
	 * MouseMotionListeners.
	 * 
	 * @return All of the objects currently listening for mouse movement.
	 *************************************************************************/
	public static Collection<MouseMotionListener> motionListeners()
	{
		return motionListeners;
	}

	/*************************************************************************
	 * Accesses the list of Mouse Event listeners.
	 * 
	 * NOTE: This is the means that should be used to add and remove new 
	 * MouseEventListener.
	 * 
	 * @return All of the objects currently listening for LWJGL Mouse events.
	 *************************************************************************/
	public static Collection<MouseEventListener> eventListeners()
	{
		return eventListeners;
	}

	/*************************************************************************
	 * Checks to see if the indicated mouse button is held down.
	 * 
	 * @param button
	 *            The button number to check. Main button is 0, right is 1,
	 *            etc. in decreasing order of prominence.
	 * 
	 * @return True if the button is currently pressed, false if not.
	 *************************************************************************/
	public static boolean isButtonDown(int button)
	{
		return inputDown[button];
	}

	/*************************************************************************
	 * Checks to see if the indicated mouse button was clicked since the last
	 * update cycle.
	 * 
	 * @param button
	 *            The button number to check. Main button is 0, right is 1,
	 *            etc. in decreasing order of prominence.
	 * 
	 * @return True if the button was clicked, false if not.
	 *************************************************************************/
	public static boolean wasButtonClicked(int button)
	{
		return inputClicked[button];
	}

	/*************************************************************************
	 * Checks to see if the indicated mouse button was released since the last
	 * update cycle.
	 * 
	 * @param button
	 *            The button number to check. Main button is 0, right is 1,
	 *            etc. in decreasing order of prominence.
	 * 
	 * @return True if the button was released, false if not.
	 *************************************************************************/
	public static boolean wasButtonReleased(int button)
	{
		return inputReleased[button];
	}

	/*************************************************************************
	 * Accesses the amount the mouse wheel was rotated since the last update
	 * cycle.
	 * 
	 * @return The amount the wheel changed. The magnitude may very from mouse
	 *         to mouse.
	 *************************************************************************/
	public static int wheelChange()
	{
		return mouseWheelChange;
	}

	/*************************************************************************
	 * Learns the current location of the mouse hotspot, in either screen or
	 * game-world coordinates.
	 * 
	 * @param onscreen
	 *            True indicates the coordinates should be for the screen,
	 *            false indicates they should be in-game.
	 * 
	 * @return The location of the mouse.
	 *************************************************************************/
	public static Vector2f location(boolean onscreen)
	{
		return onscreen ? screenLocation : worldLocation;
	}

	/*************************************************************************
	 * Learns the stored mouse path, in either screen or game-world coordinates.
	 * 
	 * @param onscreen
	 *            True indicates the coordinates should be for the screen,
	 *            false indicates they should be in-game.
	 * 
	 * @return The stored path of the mouse.
	 *************************************************************************/
	public static VectorPath path(boolean onscreen)
	{
		return onscreen ? screenPath : worldPath;
	}

	/*************************************************************************
	 * Defines the length of the stored mouse path.
	 * 
	 * If the indicated length is less than the current length of the path, the
	 * path is cropped to the size desired. However, if the indicated length is
	 * longer than the path, the path is cropped to fit.
	 * 
	 * @param length
	 *            The number of locations to store as a consecutive path.
	 *************************************************************************/
	public static void setPathLength(int length)
	{
		screenPath.setMaxLength(length);
		worldPath.setMaxLength(length);
	}

	/*************************************************************************
	 * Accesses the VectorTransform used to calculate game-world coordinates
	 * from screen coordinates.
	 * 
	 * @return The position calculator.
	 *************************************************************************/
	public static VectorTransform positionCalculator()
	{
		return positionCalculator;
	}

	/*************************************************************************
	 * Redefines the object that calculates game-world coordinates from screen
	 * coordinates.
	 * 
	 * @param calculator
	 *            The new VectorTransform to use.
	 *************************************************************************/
	public static void setPositionCalculator(VectorTransform calculator)
	{
		positionCalculator = calculator;
	}

	/*************************************************************************
	 * Use this to make the cursor invisible or not.
	 * 
	 * NOTE: The in no way affects the calculations of mouse coordinates and
	 * events, it simply changes how the mouse is shown.
	 * 
	 * @param hide
	 *            Whether or not to hide the mouse.
	 *************************************************************************/
	public static void hideMouse(boolean hide)
	{
		Mouse.setGrabbed(hide);
	}

	/*************************************************************************
	 * Defines the cursor by the image at the indicated filepath. The hotspot is
	 * located at the top-left of the mouse image.
	 * 
	 * @param filepath
	 *            The location of the image to use.
	 *************************************************************************/
	public static void setCursor(String filepath)
	{
		try {
			Mouse.setNativeCursor(CursorLoader.load(filepath));
		} catch (LWJGLException e) { e.printStackTrace(); }
	}

	/*************************************************************************
	 * Defines the cursor by the image at the indicated filepath with the
	 * indicated hotspot.
	 * 
	 * @param filepath
	 *            The location of the image to use.
	 * @param hotspot
	 *            The point on the image to use as the hotspot.
	 *************************************************************************/
	public static void setCursor(String filepath, Point hotspot)
	{
		try {
			Mouse.setNativeCursor(CursorLoader.load(filepath, hotspot));
		} catch (LWJGLException e) { e.printStackTrace(); }
	}

	/*************************************************************************
	 * Advances the update cycle, clears stored input data, and builds the mouse
	 * paths and location.
	 * 
	 * If the MouseHandler is activated, it also consumes all Mouse events and
	 * updates button info.
	 *************************************************************************/
	public static void update()
	{
		Vector2f baseLocation = new Vector2f(Mouse.getX(), Mouse.getY());
		screenPath.push(WindowManager.controller().transform(baseLocation));
		screenLocation.set(screenPath.end());
		worldPath.push(positionCalculator.transform(screenLocation));
		worldLocation.set(worldPath.end());
		mouseWheelChange = 0;
		for (int i = 0; i < NUM_BUTTONS; i++)
		{
			inputClicked[i] = false;
			inputReleased[i] = false;
			inputDown[i] = Mouse.isButtonDown(i);
		}
		if (activated)
			while (Mouse.next())
			{
				int button = Mouse.getEventButton();
				boolean state = Mouse.getEventButtonState();
				int x = Mouse.getEventX();
				int y = Mouse.getEventY();
				int dX = Mouse.getEventDX();
				int dY = Mouse.getEventDY();
				int dWheel = Mouse.getDWheel();
				for(MouseEventListener eventListener : eventListeners)
					eventListener.processMouseEvent(button, state, x, y, dX, dY, 
							dWheel);
				if (button != -1)
					if (state)
					{
						inputClicked[button] = true;
						for (MouseListener mL : listeners)
							mL.buttonClicked(button);
					}
					else
					{
						inputReleased[button] = true;
						for (MouseListener mL : listeners)
							mL.buttonReleased(button);
					}
				mouseWheelChange += dWheel;
				if (mouseWheelChange != 0)
					for (MouseListener mL : listeners)
						mL.wheelMoved(mouseWheelChange);
				if (dX != 0 || dY != 0)
					for (MouseMotionListener mL : motionListeners)
						mL.mouseMoved(dX, -dY);
			}
	}
}