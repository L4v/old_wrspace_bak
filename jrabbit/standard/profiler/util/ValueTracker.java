package org.jrabbit.standard.profiler.util;

/*****************************************************************************
 * A ValueTracker extends Message to maintain a message displaying a particular 
 * value. Its message only changes whenever the value it is told to display is 
 * different than the one it currently has.
 * 
 * This class is most useful for tracking quantities or similar types of data.
 * 
 * @author Chris Molini
 * 
 * @param T
 * 			  The type of value to keep track of.
 *****************************************************************************/
public class ValueTracker<T> extends Message
{
	/**
	 * The current value being used.
	 **/
	protected T value;
	
	/*************************************************************************
	 * Creates a ValueTracker that is identified by the supplied reference and
	 * begins with an initial value of null.
	 * 
	 * @param reference
	 * 			  The String that identifies the ValueTracker.
	 *************************************************************************/
	public ValueTracker(String reference)
	{
		this(reference, null);
	}
	
	/*************************************************************************
	 * Creates a ValueTracker that is identified by the supplied reference and
	 * begins with the supplied value.
	 * 
	 * @param reference
	 * 			  The String that identifies the ValueTracker.
	 * @param value
	 * 			  The initial value to display.
	 *************************************************************************/
	public ValueTracker(String reference, T value)
	{
		super(reference);
		setValue(value);
	}
	
	/*************************************************************************
	 * Learns the current value being displayed.
	 * 
	 * @return The object that is currently being displayed in String form.
	 *************************************************************************/
	public T value() { return value; }

	/*************************************************************************
	 * If the supplied value is different that the current one, updates the 
	 * messages and references accordingly.
	 * 
	 * @param value
	 * 			  The new value to display.
	 *************************************************************************/
	public void setValue(T value)
	{
		if(value != null && !value.equals(this.value))
		{
			this.value = value;
			setMessage(reference + ": " + value);
		}
	}
}