package org.jrabbit.base.data;

/*****************************************************************************
 * An object that is Destroyable contains data that needs to be flushed
 * differently than standard Garbage Collection.
 * 
 * In most cases, this means the object controls data in OpenGL that is outside
 * the domain of the JVM.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Destroyable
{
	/*************************************************************************
	 * Destroys data or controls settings that cannot be appropriately handled
	 * with garbage collection.
	 *************************************************************************/
	public void destroy();
}
