package org.jrabbit.standard.profiler.entities.standard;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.standard.profiler.entities.base.TextProfilerEntity;

/*****************************************************************************
 * SystemInfo displays some basic information about the JVM and computer running
 * the machine. Its most immediately useful bit of information is that it shows
 * how much memory the JVM is currently using.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SystemInfo extends TextProfilerEntity implements Updateable
{
	/**
	 * The format to show memory information in Bytes.
	 **/
	public static final int BYTES = 0;
	
	/**
	 * The format to show memory information in Kilobytes.
	 **/
	public static final int KILOBYTES = 1;
	
	/**
	 * The format to show memory information in Megabytes.
	 **/
	public static final int MEGABYTES = 2;
	
	/**
	 * The format to show memory information as a percent of system total.
	 **/
	public static final int PERCENT = 3;

	/*************************************************************************
	 * Calculates the String that represents memory usage.
	 * 
	 * @param format
	 * 			  The format in which to retrieve the info.
	 * 
	 * @return A String describing current memory usage. 
	 *************************************************************************/
	protected static String getMemUsage(int format)
	{
		long totalBytes = Runtime.getRuntime().maxMemory();
		long usedBytes = Runtime.getRuntime().totalMemory() - 
				Runtime.getRuntime().freeMemory();
		switch(format)
		{
			case BYTES: return usedBytes + " of " + totalBytes + "B";
			case KILOBYTES: return (usedBytes / 1000) + " of " + 
					(totalBytes / 1000) + " kB";
			case MEGABYTES: return (usedBytes / 1000000) + " of " + 
					(totalBytes / 1000000) + "MB";
			case PERCENT: return (int) ((usedBytes * 100) / totalBytes) + " %";
		}
		return "(unknown format)";
	}
	
	/**
	 * The format the SystemInfo is using.
	 **/
	protected int format;
	
	/**
	 * This variable keeps track of updating the SystemInfo.
	 **/
	protected int counter;
	
	/**
	 * This variable marks how long the SystemInfo should wait before
	 * recalculating the memory usage.
	 **/
	protected int interval;

	/*************************************************************************
	 * Creates a SystemInfo that shows memory information in megabytes and
	 * updates its memory display three times a second.
	 *************************************************************************/
	public SystemInfo()
	{
		this(MEGABYTES, 3333);
	}

	/*************************************************************************
	 * Creates a SystemInfo that uses the indicated format and interval.
	 * 
	 * @param format
	 * 			  The format the SystemInfo should use to display memory info.
	 * @param interval
	 * 			  The duration between each update.
	 *************************************************************************/
	public SystemInfo(int format, int interval)
	{
		super("System", 
				"-  -  System  -  -", 
					"Memory: " + getMemUsage(format), 
					"JVM: " + System.getProperty("java.version"),
					"OS: " + System.getProperty("os.name"),
					"Cores: " + Runtime.getRuntime().availableProcessors());
		this.format = format;
		this.interval = interval;
	}

	/*************************************************************************
	 * Learns the current format of the SystemInfo.
	 * 
	 * @return The integer that indicates which format the SystemInfo should 
	 *         use.
	 *         
	 * @see #BYTES
	 * @see #KILOBYTES
	 * @see #MEGABYTES
	 * @see #PERCENT
	 *************************************************************************/
	public int format() { return format; }

	/*************************************************************************
	 * Redefines the format the SystemInfo uses.
	 * 
	 * @param format
	 * 			  The new format to use.
	 *         
	 * @see #BYTES
	 * @see #KILOBYTES
	 * @see #MEGABYTES
	 * @see #PERCENT
	 *************************************************************************/
	public void setFormat(int format)
	{
		this.format = format;
	}

	/*************************************************************************
	 * Learns the current interval between updates.
	 * 
	 * @return The number of clock ticks the SystemInfo waits to update itself.
	 *************************************************************************/
	public int interval() { return interval; }

	/*************************************************************************
	 * Redefines the duration between each update.
	 * 
	 * @param interval
	 * 			  The number of clock ticks the SystemInfo should wait between
	 * 			  each update of its displayed information.
	 *************************************************************************/
	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	/*************************************************************************
	 * Recalculates memory information.
	 *************************************************************************/
	public void recalculate()
	{
		setText(1, "Memory: " + getMemUsage(format));
	}

	/*************************************************************************
	 * Updates the SystemInfo, determining if it needs to
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		counter += delta;
		if(counter >= interval)
		{
			counter %= interval;
			recalculate();
		}
	}
}