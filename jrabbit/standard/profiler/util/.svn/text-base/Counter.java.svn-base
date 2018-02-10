package org.jrabbit.standard.profiler.util;

/*****************************************************************************
 * A Counter extends Message to provide functionality for counting the number of
 * times a certain process occurs over a desired interval of time.
 * 
 * The most obvious use for this object is to allow a ProfilerEntity to count,
 * say, the number of completed frames per second. It's a simplistic bit of
 * functionality but useful nevertheless.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Counter extends Message
{
	/**
	 * This value keeps track of how many times the counter has been advanced
	 * per interval.
	 **/
	protected int counter;
	
	/**
	 * This is the time (in nanoseconds from program startup) that the current
	 * interval being recorded began.
	 **/
	protected long intervalStart;
	
	/**
	 * This is the duration (in nanoseconds) that the program should consider
	 * the interval to count over. 
	 **/
	protected long interval;
	
	/*************************************************************************
	 * Creates a Counter that is identified by the supplied indicator.
	 * 
	 * NOTE: The created Counter measures events over a period of 1 second.
	 * 
	 * @param reference
	 * 			  The identifier of this Counter.
	 *************************************************************************/
	public Counter(String reference)
	{
		this(reference, 1f);
	}
	
	/*************************************************************************
	 * Creates a Counter that is identified by the supplied reference and that
	 * records events over the indicated interval.
	 * 
	 * @param reference
	 * 			  The identifier of this Counter.
	 * @param interval
	 * 			  The number of seconds that this Counter counts over.
	 *************************************************************************/
	public Counter(String reference, float interval)
	{
		super(reference);
		setInterval(interval);
	}
	
	/*************************************************************************
	 * Learns the interval over which the Counter is currently counting.
	 * 
	 * @return The number of nanoseconds that is considered one discrete 
	 *         interval.
	 *************************************************************************/
	public long interval() { return interval; }
	
	/*************************************************************************
	 * Redefines the period over which the Counter should count.
	 * 
	 * NOTE: This resets all values used to regulate event counting.
	 * 
	 * @param seconds
	 * 			  The number of seconds that constitute the target interval.
	 *************************************************************************/
	public void setInterval(float seconds)
	{
		setInterval((long) (seconds * 1000000) * 1000);
	}

	/*************************************************************************
	 * Redefines the period over which the Counter should count.
	 * 
	 * NOTE: This resets all values used to regulate event counting.
	 * 
	 * @param nanoseconds
	 * 			  The number of nanoseconds that constitute the target interval.
	 *************************************************************************/
	public void setInterval(long nanoseconds)
	{
		interval = nanoseconds;
		intervalStart = System.nanoTime();
		counter = 0;
	}

	/*************************************************************************
	 * Updates the counter, telling it to increment its count by one. If the
	 * target interval has elapsed, the counter value is reset and the message
	 * is updated accordingly.
	 *************************************************************************/
	public void advance()
	{
		if(System.nanoTime() > intervalStart + interval)
		{
			intervalStart += interval;
			setMessage(reference + ": " + counter);
			counter = 0;
		}
		counter++;
	}
}