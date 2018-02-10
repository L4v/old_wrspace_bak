package org.jrabbit.base.data;

/*****************************************************************************
 * This interface describes an object that needs to be further defined after
 * instantiation - i.e., either it has additional data it either cannot create
 * immediately, or it may need recreation after instantiation if its contents
 * change.
 * 
 * Most jRabbit implementations of this are linked to resources in OpenGL -
 * since that data is not controlled by the JGC, we need to manage it manually.
 * 
 * Obviously, most implementations of this will probably also implement
 * Destroyable.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Createable
{
	/*************************************************************************
	 * Creates data or controls settings that cannot be appropriately handled on
	 * instantiation.
	 *************************************************************************/
	public void create();
}
