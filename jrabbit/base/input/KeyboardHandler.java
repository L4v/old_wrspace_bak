package org.jrabbit.base.input;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.input.Keyboard;

/*****************************************************************************
 * KeyboardHandler is a static class that provides the default methods for
 * reacting to player input via the keyboard. It is automatically updated in the
 * main game loop.
 * 
 * NOTE: It's possible that a different input setup will better suit your needs.
 * If this is the case, you should be aware that by default, KeyboardHandler
 * "eats" all Keyboard events. You can turn this activity off, however, and
 * interpret them with your own code.
 * 
 * @author Chris Molini
 *****************************************************************************/
public final class KeyboardHandler
{
	/**
	 * Whether or not KeyboardHandler should parse Keyboard events.
	 **/
	private static boolean activated = true;

	/**
	 * The list of keys that have been pressed since the last update cycle.
	 **/
	private static ArrayList<Integer> keysPressed = new ArrayList<Integer>();

	/**
	 * The list of keys that have been released since the last update cycle.
	 **/
	private static ArrayList<Integer> keysReleased = new ArrayList<Integer>();

	/**
	 * The list of every character that has been typed.
	 **/
	private static ArrayList<Character> chars = new ArrayList<Character>();

	/**
	 * The list of objects listening to the Keyboard for input changes.
	 **/
	private static ArrayList<KeyboardListener> listeners = 
		new ArrayList<KeyboardListener>();

	/**
	 * The list of objects listening to the Keyboard for complete LWJGL events.
	 **/
	private static ArrayList<KeyEventListener> eventListeners = 
		new ArrayList<KeyEventListener>();

	/**
	 * Initializes KeyboardHandler settings.
	 **/
	static
	{
		Keyboard.enableRepeatEvents(true);
	}

	/*************************************************************************
	 * Determines whether or not Keyboard events should be processed. Use this
	 * to turn off KeyboardHandler calculations if you want to use a different
	 * input system for handling Keyboard events.
	 * 
	 * NOTE: Even if this is turned off, isKeyDown() will return the correct
	 * values, as it delegates the check to Keyboard.
	 * 
	 * @param active
	 *            Whether or not the KeyboardHandler should process Keyboard
	 *            events.
	 *************************************************************************/
	public static void activate(boolean active)
	{
		activated = active;
	}

	/*************************************************************************
	 * Learns if the KeyboardHandler is active.
	 * 
	 * @return True if Keyboard events are being processed, false if not.
	 *************************************************************************/
	public static boolean activated() { return activated; }
	
	/*************************************************************************
	 * Accesses the list of Keyboard listeners.
	 * 
	 * NOTE: This is the means that should be used to add and remove new 
	 * KeyboardListeners.
	 * 
	 * @return All of the objects currently listening for input changes.
	 *************************************************************************/
	public static Collection<KeyboardListener> listeners()
	{
		return listeners;
	}
	
	/*************************************************************************
	 * Accesses the list of Keyboard Event listeners.
	 * 
	 * NOTE: This is the means that should be used to add and remove new 
	 * KeyEventListeners.
	 * 
	 * @return All of the objects currently listening for Keyboard events.
	 *************************************************************************/
	public static Collection<KeyEventListener> eventListeners()
	{
		return eventListeners;
	}

	/*************************************************************************
	 * Checks if a key is being continuously held down.
	 * 
	 * @param key
	 *            The key to check. (Use the identifiers in Keyboard.)
	 * 
	 * @return True if the key is held down, false if not.
	 *************************************************************************/
	public static boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}

	/*************************************************************************
	 * Checks if a key was initially pressed since the last update cycle.
	 * 
	 * @param key
	 *            The key to check. (Use the identifiers in Keyboard.)
	 * 
	 * @return True if the key was first pressed since the last update, false if
	 *         not.
	 *************************************************************************/
	public static boolean wasKeyPressed(int key)
	{
		return keysPressed.contains(key);
	}

	/*************************************************************************
	 * Checks if a key was released since the last update cycle.
	 * 
	 * @param key
	 *            The key to check. (Use the identifiers in Keyboard.)
	 * 
	 * @return True if the key was released since the last update, false if not.
	 *************************************************************************/
	public static boolean wasKeyReleased(int key)
	{
		return keysReleased.contains(key);
	}

	/*************************************************************************
	 * Accesses the list of all characters that were typed since the last
	 * update.
	 * 
	 * @return Every character that has been typed.
	 *************************************************************************/
	public static ArrayList<Character> typedCharacters()
	{
		return chars;
	}

	/*************************************************************************
	 * Advances the KeyboardHandler. This clears all old stored input data (the
	 * lists of pressed and released keys and all typed characters).
	 * 
	 * Then, if activated, recalculates the input data by polling Keyboard
	 * events.
	 *************************************************************************/
	public static void update()
	{
		// Clears stored input data.
		keysPressed.clear();
		keysReleased.clear();
		chars.clear();

		if (activated)
			// Cycles through every keyboard event.
			while (Keyboard.next())
			{
				int ID = Keyboard.getEventKey();
				char c = Keyboard.getEventCharacter();
				boolean state = Keyboard.getEventKeyState();
				long time = Keyboard.getEventNanoseconds();
				for(KeyEventListener eventListener: eventListeners)
					eventListener.processKeyEvent(ID, state, c, time);
				if (state)
				{
					chars.add(c);
					for (KeyboardListener kL : listeners)
						kL.characterEntered(c);
					if (!Keyboard.isRepeatEvent())
					{
						keysPressed.add(ID);
						for (KeyboardListener kL : listeners)
							kL.keyPressed(ID);
					}
				}
				else
				{
					keysReleased.add(ID);
					for (KeyboardListener kL : listeners)
						kL.keyReleased(ID);
				}
			}
	}
}