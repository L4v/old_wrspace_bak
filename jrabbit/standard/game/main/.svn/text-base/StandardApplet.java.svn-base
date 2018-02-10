package org.jrabbit.standard.game.main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.jrabbit.base.managers.Resources;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.managers.window.controllers.CanvasWindowController;
import org.jrabbit.standard.game.managers.GameManager;

/*****************************************************************************
 * This very minimal class is all that is needed for a jRabbit game to run in
 * an Applet.
 * 
 * One of the design goals of the Java Rabbit Engine is that it should be 
 * extremely easy for the developer to have their game run as either an applet 
 * or as a standalone application, with as little difference in code as 
 * possible. This has been achieved by letting an Applet basically act as a 
 * "wrapper" around a game base on StandardGame, enabling the game to run in
 * a browser with as few lines of code as possible.
 * 
 * To do this, when extend StandardApplet's run command and have it create
 * a new instance of the StandardGame in question and call run on it.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class StandardApplet extends Applet
{
	/**
	 * The serialization ID.
	 **/
	private static final long serialVersionUID = 8554109709330174589L;
	
	/**
	 * Whether or not the game has been started.
	 **/
	protected boolean gameStarted;
	
	/**
	 * The canvas upon which the Display will be drawn.
	 **/
	protected Canvas canvas;
	
	/**
	 * The Thread in which the game is updated and rendered.
	 **/
	protected AppletGameThread gameThread;
	
	/*************************************************************************
	 * This  base Applet, setting the stage for the game to run.
	 ***************************************************************/ @Override 
	public final void init()
	{
		setLayout(new BorderLayout());
		try
		{
			canvas = new Canvas();
			canvas.setSize(getWidth(), getHeight());
			add(canvas, BorderLayout.CENTER);
			canvas.setFocusable(true);
			canvas.requestFocus();
			canvas.setIgnoreRepaint(true);
			setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Unable to create display!");
		}
	}

	/*************************************************************************
	 * If the game has not been started yet, this initializes it. Otherwise, 
	 * this tells the main game to resume.
	 ***************************************************************/ @Override
	public void start()
	{
		if(!gameStarted)
		{
			(gameThread = new AppletGameThread(this)).start();
			gameStarted = true;
		}
		else
			resumeGame();
	}

	/*************************************************************************
	 * Signals the main game that it should pause.
	 ***************************************************************/ @Override
	public void stop()
	{
		pauseGame();
	}
	
	/*************************************************************************
	 * Signals the main game for destruction, then waits for it to finish.
	 ***************************************************************/ @Override
	public void destroy()
	{
		exitGame();
		try {
			gameThread.join();
		} catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	/*************************************************************************
	 * This is the method called to run the applet. Initialize the game here.
	 *************************************************************************/
	protected abstract void startGame();
	
	/*************************************************************************
	 * This is called whenever the page the applet is on is left (i.e., the 
	 * browser goes to another page or tab). It is intended to pause the game.
	 *************************************************************************/
	protected abstract void pauseGame();

	/*************************************************************************
	 * This is called whenever the page the applet is on is made active (i.e., 
	 * the browser goes to it from another page or tab). It is intended to 
	 * resume the game after being paused.
	 *************************************************************************/
	protected abstract void resumeGame();

	/*************************************************************************
	 * This is called whenever the applet is destroyed. Use it to signal the 
	 * main game that it should exit.
	 *************************************************************************/
	protected abstract void exitGame();
	
	/*************************************************************************
	 * AppletGameThread manages the main game separately from the Applet's 
	 * initialization thread. This is to allow LWJGL and the game logic their 
	 * own context. 
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class AppletGameThread extends Thread
	{
		/**
		 * The StandardApplet to be managed.
		 **/
		protected StandardApplet applet;
		
		/*********************************************************************
		 * Creates an AppletGameThread that manages the supplied StandardApplet.
		 * 
		 * @param applet
		 * 			  The StandardApplet that the game should run in.
		 *********************************************************************/
		public AppletGameThread(StandardApplet applet)
		{
			this.applet = applet;
		}
		
		/*********************************************************************
		 * Initializes LWJGL and all resources, then runs the game.
		 *********************************************************************/
		public void run()
		{
			WindowManager.setController(new CanvasWindowController(
					applet.canvas));
			WindowManager.controller().create();
			Resources.create();
			GameManager.create();
			applet.startGame();
		}
	}
}