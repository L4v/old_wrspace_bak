package org.jrabbit.standard.profiler.util;

import java.util.Arrays;

/*****************************************************************************
 * Like Counter, Timer extends MessageCounter to provide a particular type of 
 * data to a ProfilerEntity. However, instead of recording the number of times
 * a particular event occurs in an interval, a Timer averages the amount of time
 * a particular process takes.
 * 
 * Basically, a Timer is structured so that it only really needs to use two 
 * methods: {@link #begin()} and {@link #end()}. By calling begin()
 * and end(), Timer calculates the amount of time taken between the two calls.
 * Each time this occurs, the Timer fills a spot in the buffer with the recorded
 * time. If the buffer is filled, all of its values are averaged. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Timer extends Message
{
	/*************************************************************************
	 * Learns the tag associated with the supplied resolution.
	 * 
	 * @param resolution
	 * 			  The number of units per second.
	 * 
	 * @return The sequence of characters that defines the units being dealt 
	 *         with. E.g.: If resolution is 1000 (i.e., the resolution is in
	 *         milliseconds), the returned String is "ms" to indicate this.
	 *************************************************************************/
	protected static String unitTag(int resolution)
	{
		switch(resolution)
		{
			case 1: return " s";
			case 1000: return " ms";
			case 1000000: return " us";
			case 1000000000: return " ns";
			default: return " units";
		}
	}
	
	/**
	 * The buffer of recorded times (in nanoseconds)
	 **/
	protected long[] buffer;

	/**
	 * The current slot in the buffer that the Timer is filling.
	 **/
	protected int bufferPosition;

	/**
	 * The time, in nanoseconds, that the current process began at. If the Timer
	 * is not in the middle of recording, this is set to -1.
	 **/
	protected long beginTime;

	/**
	 * The average time recorded, in seconds. This value is only ever calculated
	 * when the entire buffer is filled.
	 **/
	protected double averageTime;

	/**
	 * The number of units per second at which the Timer is to display its 
	 * average recorded time.
	 **/
	protected int resolution;

	/**
	 * The size of the decimal that the message will display.
	 **/
	protected int decimalSize;

	/*************************************************************************
	 * Creates Timer with the indicated identifier. The Timer uses a buffer of
	 * size 60.
	 * 
	 * @param reference
	 * 			  The String to identify this Timer.
	 *************************************************************************/
	public Timer(String reference)
	{
		this(reference, 60);
	}

	/*************************************************************************
	 * Creates a Timer with the indicated identifier and buffer size.
	 * 
	 * @param reference
	 * 			  The String to identify this Timer.
	 * @param bufferSize
	 * 			  The number of times to record before calculating their 
	 *            average.
	 *************************************************************************/
	public Timer(String reference, int bufferSize)
	{
		super(reference);
		setBufferSize(bufferSize);
		resolution = 1000;
		decimalSize = 2;
		beginTime = -1;
	}

	/*************************************************************************
	 * Learns the current resolution of the timer.
	 * 
	 * @return The number of Timer units in a second.
	 *************************************************************************/
	public int resolution()
	{
		return resolution;
	}

	/*************************************************************************
	 * Redefines the Timer's resolution.
	 * 
	 * @param resolution
	 * 			  The new number of units in a second to use.
	 *************************************************************************/
	public void setResolution(int resolution)
	{
		this.resolution = resolution;
		updateMessage();
	}

	/*************************************************************************
	 * Learns the size of the decimal this Timer uses to display its recorded
	 * times.
	 * 
	 * @return The number of digits after the decimal this Timer displays.
	 *************************************************************************/
	public int decimalSize()
	{
		return decimalSize;
	}

	/*************************************************************************
	 * Redefines the size of the decimal this timer uses.
	 * 
	 * @param decimalSize
	 * 			  The number of digits after the decimal to display.
	 *************************************************************************/
	public void setDecimalSize(int decimalSize)
	{
		this.decimalSize = decimalSize;
		updateMessage();
	}

	/*************************************************************************
	 * Resizes the buffer.
	 * 
	 * NOTE: This truncates the current buffer. If the buffer is resized to less
	 * that the current
	 * 
	 * @param size
	 * 			  The number of recordings to make 
	 *************************************************************************/
	public void setBufferSize(int size)
	{
		buffer = buffer == null ? new long[size] : Arrays.copyOf(buffer, size);
		if(bufferPosition >= size)
			calculateAverage();
	}

	/*************************************************************************
	 * Learns the average amount of time that the process has been taking.
	 * 
	 * @return The average number of seconds that the process being recorded has
	 *         taken.
	 *************************************************************************/
	public double averageTime()
	{
		return averageTime;
	}

	/*************************************************************************
	 * Alerts the Timer that it should start recording the duration of a new
	 * process.
	 * 
	 * NOTE: If the Timer is in the middle of recording a time, it automatically
	 * calls {@link #end()}.
	 *************************************************************************/
	public void begin()
	{
		if(beginTime != -1)
			end();
		beginTime = System.nanoTime();
	}

	/*************************************************************************
	 * Alerts the Timer that the end of the currently recorded process has 
	 * occurred, and that it should update its recorded times.
	 * 
	 * If the entire buffer is filled, the average of all of their values is
	 * calculated, and the message is updated to reflect this average.
	 *************************************************************************/
	public void end()
	{
		if(beginTime != -1)
		{
			buffer[bufferPosition] = System.nanoTime() - beginTime;
			bufferPosition++;
			beginTime = -1;
			if(bufferPosition >= buffer.length)
				calculateAverage();
		}
	}
	
	/*************************************************************************
	 * Calculates the average time of all entries in the buffer, and resets
	 * the buffer position back to 0.
	 *************************************************************************/
	public void calculateAverage()
	{
		long totalTime = 0;
		for(int i = 0; i < bufferPosition; i++)
			totalTime += buffer[i];
		averageTime = totalTime / (buffer.length * 1000000000.0);
		bufferPosition = 0;
		updateMessage();
	}

	/*************************************************************************
	 * Updates the message to display the average recorded time.
	 *************************************************************************/
	protected void updateMessage()
	{
		double decimalFactor = Math.pow(10, decimalSize);
		setMessage(reference + ": " + (int) (averageTime * resolution * 
				decimalFactor) / decimalFactor + unitTag(resolution));
	}
}