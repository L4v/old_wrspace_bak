package org.jrabbit.standard.intro.base;

import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.input.KeyboardHandler;
import org.jrabbit.base.input.MouseHandler;
import org.jrabbit.base.managers.window.WindowManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * BaseLogoIntro extends ImageAudioIntro to add some default functionality. The
 * end result is an Intro that displays an Image at the center of the screen
 * while a sound plays. Both the color of the main Image and of the background
 * are completely controllable. Obviously, this type of Intro lends itself most
 * easily to some sort of logo display; thus the name.
 * 
 * NOTE: Extensions of this class still need to control how the Intro updates
 * itself and changes over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class BaseLogoIntro extends ImageAudioIntro
{
	/**
	 * The Color at which to render the main image.
	 **/
	protected Color logoColor;
	
	/**
	 * The Color that the background will be cleared to.
	 **/
	protected Color backgroundColor;
	
	/**
	 * This flag is used to determine if the Intro has finished displaying.
	 **/
	protected boolean finished;

	/*************************************************************************
	 * Creates an BaseLogoIntro that uses the Image and SoundData retrieved 
	 * from the indicated system locations.
	 * 
	 * NOTE: The logo Color is set to opaque white, and the background color is
	 * set to black.
	 * 
	 * @param imageFilepath
	 * 			  The system location of the source image.
	 * @param soundFilepath
	 * 			  The system location of the source audio.
	 *************************************************************************/
	public BaseLogoIntro(String imageFilepath, String soundFilepath)
	{
		this(new SystemLoader(imageFilepath), new SystemLoader(soundFilepath));
	}

	/*************************************************************************
	 * Creates an BaseLogoIntro that uses the Image and SoundData retrieved 
	 * from the supplied Loaders.
	 * 
	 * NOTE: The logo Color is set to opaque white, and the background color is
	 * set to black.
	 * 
	 * @param imageFilepath
	 * 			  The loader that accesses the desired source image.
	 * @param soundFilepath
	 * 			  The loader that accesses the desired source sound.
	 *************************************************************************/
	public BaseLogoIntro(Loader imageLoader, Loader soundLoader)
	{
		super(imageLoader, soundLoader);
		logoColor = new Color();
		backgroundColor = new Color(0, 0, 0);
	}

	/*************************************************************************
	 * This begins the Intro by starting playback of the  sound.
	 ***************************************************************/ @Override
	public void start()
	{
		sound.audio().playAsSoundEffect(1f, 1f, false);
	}
	
	/*************************************************************************
	 * Clears the background to the desired Color, then renders the logo Image
	 * at the center of the screen with the indicated Color.
	 ***************************************************************/ @Override
	public void render()
	{
		GL11.glLoadIdentity();
		GL11.glTranslatef(WindowManager.controller().width() / 2, 
				WindowManager.controller().height() / 2, 0);
		backgroundColor.bindClear();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		logoColor.bind();
		image.render();
	}

	/*************************************************************************
	 * Determines if the Intro has finished displaying.
	 * 
	 * @return True if any of the following are satisfied:
	 *               1: The "finished" flag is set to true.
	 *               2: The left mouse button was clicked.
	 *               3: The Enter, Escape, or Space keys were pressed.
	 ***************************************************************/ @Override
	public boolean finished()
	{
		return finished || MouseHandler.wasButtonClicked(0) 
			|| KeyboardHandler.wasKeyPressed(Keyboard.KEY_ESCAPE)
			|| KeyboardHandler.wasKeyPressed(Keyboard.KEY_SPACE)
			|| KeyboardHandler.wasKeyPressed(Keyboard.KEY_RETURN);
	}
}