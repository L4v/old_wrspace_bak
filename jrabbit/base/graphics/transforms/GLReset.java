package org.jrabbit.base.graphics.transforms;

import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * GLReset extends GLMatrixStackControl to load the identity matrix when bound.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GLReset extends GLMatrixStackControl
{
	/*************************************************************************
	 * Pushes the current matrix, and then loads its identity.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}
}
