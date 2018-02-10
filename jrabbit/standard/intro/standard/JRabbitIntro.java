package org.jrabbit.standard.intro.standard;

import org.jrabbit.standard.intro.base.BaseLogoIntro;

/*****************************************************************************
 * JRabbitIntro provides a simple implementation of BaseLogoIntro that both is
 * the engine's default opening, and also provides a simple example of how to
 * create a custom logo.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class JRabbitIntro extends BaseLogoIntro
{
	/**
	 * Keeps track of the time passed between updates.
	 **/
	protected int timer;
	
	/**
	 * The duration, in clock ticks, of each phase.
	 **/
	protected int phaseInterval;
	
	/**
	 * The current phase.
	 **/
	protected int phase;
	
	/*************************************************************************
	 * Creates an Intro that will display the Java Rabbit Engine's logo and play
	 * the engine's logo tone.
	 *************************************************************************/
	public JRabbitIntro()
	{
		super("org/jrabbit/resources/jRabbit Engine Logo.png", 
				"org/jrabbit/resources/jRabbit Engine Tone.ogg");
		phaseInterval = 10000;
		logoColor.setAlpha(0);
	}
	
	/*************************************************************************
	 * Updates the logo.
	 * 
	 * This logo is multi-phase; it has several different segments of action 
	 * (none of them are very complex).
	 * 
	 * @param delta
	 * 			  The number of clock ticks that have passed since the last 
	 * 			  update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		timer += delta;
		phase += timer / phaseInterval;
		timer %= phaseInterval;
		
		// Controls transparency, fading in and out as
		// desired.
		switch(phase)
		{
			case 0:
				logoColor.setAlpha((float) timer / phaseInterval);
					break;
			case 1:
				logoColor.setAlpha(1f); 
					break;
			case 2:
				logoColor.setAlpha(1f - ((float) timer / phaseInterval));
					break;
			case 3:
				logoColor.setAlpha(0); 
					break;
			default:
				finished = true;
					break;
		}
	}
}