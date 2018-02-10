package org.jrabbit.base.graphics.transforms;

/*****************************************************************************
 * A GLControlledReset extends GLReset to provide controls over its affect - 
 * that is to say, it only resets/restores the current OpenGL Matrix it is 
 * enabled; if not, no changes to OpenGL occur.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GLControlledReset extends GLReset
{
	/**
	 * Whether or not the GLControlledReset is enabled.
	 **/
	protected boolean enabled;
	
	/*************************************************************************
	 * Determines if the GLControlledReset is active or not.
	 * 
	 * @return True if enabled, false if not.
	 *************************************************************************/
	public boolean enabled() { return enabled; }
	
	/*************************************************************************
	 * Changes if the GLControlledReset is enabled.
	 * 
	 * @param enabled
	 * 			  Whether or not this should be active.
	 *************************************************************************/
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	/*************************************************************************
	 * If enabled, this pushes the current OpenGL Matrix and calls 
	 * glLoadIdentity().
	 ***************************************************************/ @Override
	public void bind()
	{
		if(enabled)
			super.bind();
	}
	
	/*************************************************************************
	 * If enabled, this pops the current OpenGL Matrix.
	 ***************************************************************/ @Override
	public void release()
	{
		if(enabled)
			super.release();
	}
}