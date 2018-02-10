package org.jrabbit.base.data;

/*****************************************************************************
 * ChangeListener is a very basic, very simple Interface that allows objects to
 * be alerted when something changes. No indication of what has changed is
 * given, it's expected that this be used in very simple cases when such info is
 * not needed.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface ChangeListener
{
	/*************************************************************************
	 * Alerts the ChangeListener that something it is listening to has changed.
	 *************************************************************************/
	public void alertChange();
}