package org.jrabbit.standard.game.main;

import org.jrabbit.base.directory.*;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.managers.Resources;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.managers.window.controllers.DesktopWindowController;
import org.jrabbit.base.managers.window.controllers.WindowController;
import org.jrabbit.standard.game.loop.GameLoop;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.intro.IntroLoop;
import org.jrabbit.standard.intro.standard.JRabbitIntro;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;

/*****************************************************************************
 * StandardGame is the base class for any jRabbit game. It extends GameLoop, 
 * adding controls on initializing the game.
 * 
 * The game StandardGame creates is runnable from an executable jar. By default,
 * it creates its own window (by default this window is also set to fullscreen),
 * but it can be made to reside within an AWT or Swing application. (If this is
 * done, it can be integrated with a GUI. An example use of this could be an 
 * editor.)
 * 
 * It should be noted that the JVM needs to initialize LWJGL and the general 
 * jRabbit engine before and implementation of StandardGame is created and run. 
 * The init() methods provided automatically does everything required; calling 
 * any one of them will do. Thus, the contents of a main() method need only be 
 * something like:
 * 
 *                init();
 *                new Class_Extending_StandardGame().run();
 * 
 * and the game will be created and will run as desired.
 * 
 * Finally, it should be noted that StandardGame allows for a series of opening
 * intros to be played (e.g., logos and/or intro scenes).
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class StandardGame extends GameLoop
{
	/*************************************************************************
	 * Initializes the game engine using a default DesktopWindowController. This
	 * automatically creates a fullscreen game at full resolution.
	 *************************************************************************/
	public static final void init()
	{
		init(new DesktopWindowController());
	}

	/*************************************************************************
	 * Initializes the game engine using a DesktopWindowController that creates 
	 * a windowed instance of the game at the indicated dimensions.
	 * 
	 * @param width
	 * 			  The width of the window to use.
	 * @param height
	 * 			  The height of the window to use.
	 *************************************************************************/
	public static final void init(int width, int height)
	{
		init(new DesktopWindowController(width, height));
	}

	/*************************************************************************
	 * Initializes the game engine using the indicated DesktopWindowController.
	 * 
	 * @param controller
	 * 			  The WindowController that will control the Window settings and
	 *            initialize LWJGL.
	 *************************************************************************/
	public static final void init(WindowController controller)
	{
		initForOS(System.getProperty("os.name").toUpperCase());
		EngineDirectory.init();
		LWJGLNatives.load();
		WindowManager.setController(controller);
		WindowManager.controller().create();
		Resources.create();
		GameManager.create();
	}

	/*************************************************************************
	 * Initializes the game engine based upon the operating system the game is
	 * running on. This is automatically called in init().
	 * 
	 * @param OS
	 * 			  The name of the operating system, as returned by 
	 *            System.getProperty("os.name").
	 * 
	 * @see #init()
	 * @see #init(int, int)
	 * @see #init(WindowController)
	 *************************************************************************/
	protected static void initForOS(String OS)
	{
		if(OS.contains("WIN"))
			new Thread() { { setDaemon(true); }
				public void run() {
					try { Thread.sleep(Long.MAX_VALUE); }
					catch (Exception e) { } } }.start();
	}
	
	/**
	 * The default title for jRabbit games.
	 **/
	public static final String DEFAULT_TITLE = "A jRabbit Game";

	/**
	 * The location of the default icon for jRabbit games.
	 **/
	public static final String DEFAULT_ICON = "org/jrabbit/resources/jRabbit " +
			"Engine Icon.png";

	/**
	 * Whether or not the intro sequence should be displayed.
	 **/
	protected boolean showIntro;

	/*************************************************************************
	 * Creates a StandardGame with the default title and default icon.
	 *************************************************************************/
	public StandardGame()
	{
		this(DEFAULT_TITLE, DEFAULT_ICON);
	}

	/*************************************************************************
	 * Creates a StandardGame with the indicated title. The default icon is 
	 * used.
	 * 
	 * @param title
	 * 			  The name of the game to run.
	 *************************************************************************/
	public StandardGame(String title)
	{
		this(title, DEFAULT_ICON);
	}

	/*************************************************************************
	 * Creates a StandardGame with the indicated title and icon.
	 * 
	 * @param title
	 * 			  The name of the game to run.
	 * @param icon
	 * 			  The filepath to the desired icon.
	 *************************************************************************/
	public StandardGame(String title, String icon)
	{
		showIntro = true;
		WindowManager.setTitle(title);
		WindowManager.setIcon(icon);
	}

	/*************************************************************************
	 * Learns if the openings are currently set to show at startup.
	 * 
	 * @return True if the opening sequence will play, false if not.
	 *************************************************************************/
	public boolean showingIntro() { return showIntro; }

	/*************************************************************************
	 * Sets whether or not the opening Intros should play.
	 * 
	 * @param showIntro
	 * 			  True if the openings should show, false if not.
	 *************************************************************************/
	public void showIntro(boolean showIntro)
	{
		this.showIntro = showIntro;
	}

	/*************************************************************************
	 * Prepares the StandardGame for execution, and runs the opening sequence if
	 * desired.
	 ***************************************************************/
	protected void start()
	{
		super.start();
		if(showIntro)
		{
			IntroLoop intro = new IntroLoop();
			intro.queue().add(new JRabbitIntro());
			setupIntro(intro);
			intro.run();
		}
		setup();
	}

	/*************************************************************************
	 * Override this to add custom Intro displays.
	 * 
	 * NOTE: The intro specified already adds jRabbit's intro by default. To
	 * remove it, simply use intro.queue.clear(). :(
	 * 
	 * @param intro
	 * 			  The IntroLoop that is being prepared to start playing.
	 *************************************************************************/	
	protected void setupIntro(IntroLoop intro) { }

	/*************************************************************************
	 * Override this to set the initial state of the game. It is automatically 
	 * called at the beginning of the main loop.
	 *************************************************************************/
	protected abstract void setup();

	/*************************************************************************
	 * Extends {@link GameLoop#advanceGame(int)} to automatically exit the game
	 * if escape is pressed. This behavior is intended to make quick testing 
	 * easier.
	 * 
	 * @param delta
	 * 			  The number of clock cycles that have passed since the last 
	 *            update.
	 ***************************************************************/ @Override
	public void advanceGame(int delta)
	{
		if(KeyboardHandler.wasKeyPressed(Keyboard.KEY_ESCAPE))
			exit();
	}

	/*************************************************************************
	 * Exits the game, destroying LWJGL in the process.
	 ***************************************************************/ @Override
	protected void end()
	{
		super.end();
		onExit();
		AL.destroy();
		WindowManager.controller().destroy();
	}

	/*************************************************************************
	 * When jRabbit exits, this is called to give the game a chance to execute
	 * some custom exit code. (Examples: emergency saving, disconnecting from a 
	 * server, etc.)
	 *************************************************************************/
	protected void onExit(){}
}