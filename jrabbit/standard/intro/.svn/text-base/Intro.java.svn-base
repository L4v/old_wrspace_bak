package org.jrabbit.standard.intro;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.Createable;
import org.jrabbit.base.data.Destroyable;

/*****************************************************************************
 * An Intro is an object that displays itself at the beginning of a game, to
 * show some opening scene or message. Intro is designed to be as flexible as
 * possible, so that virtually any opening can be created.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Intro extends Updateable, Renderable, Createable, Destroyable
{
	/*************************************************************************
	 * This method is called when the Intro is begun being displayed. Any calls
	 * that should be made at the beginning of playback should be called here.
	 *************************************************************************/
	public void start();
	
	/*************************************************************************
	 * This method is used to determine if the Intro should stop playing and if
	 * the next one in the queue should be started.
	 * 
	 * @return True if the Intro is finished, false if it should continue 
	 *         playing.
	 *************************************************************************/
	public boolean finished();
}