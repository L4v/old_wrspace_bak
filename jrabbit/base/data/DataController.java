package org.jrabbit.base.data;

/*****************************************************************************
 * A DataController is an object that is both Createable and Destroyable - it
 * manages data over its entire life cycle, from creation to destruction.
 * 
 * Additionally, a DataController has a method to check if the Data it manages
 * is valid - a.k.a, it has been created, and not destroyed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface DataController extends Createable, Destroyable
{
	/*************************************************************************
	 * Determines whether the DataController's internal data is valid.
	 * 
	 * @return True if the object has been created and not destroyed.
	 *************************************************************************/
	public boolean valid();
}
