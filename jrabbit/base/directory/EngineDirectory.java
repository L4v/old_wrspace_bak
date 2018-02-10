package org.jrabbit.base.directory;

import java.io.File;

/*****************************************************************************
 * Provides some convenience methods for managing files and folders to store
 * data. All methods are based out of a single main directory.
 * 
 * The Java Rabbit Engine automatically creates this folder on the user's
 * computer where it stores any data it needs on the computer. This ranges from
 * the native libraries required to run OpenGL, or to saved games that need to
 * persist between plays. This automatic folder creation is required for the
 * engine to run from a desktop.
 * 
 * The directory has a default name (jRabbit Data) and location (which varies by
 * OS). Both of these things can be changed at game startup, but once jRabbit is
 * fully initialized certain things are in place and changing these settings is
 * disabled.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class EngineDirectory
{
	/**
	 * Whether or not the folder should be made as hidden.
	 * 
	 * NOTE: For Linux computers, this is set to true by default.
	 **/
	private static boolean hidden = false;
	
	/**
	 * The path to the parent directory our main directory. The default value is
	 * based on OS.
	 **/
	private static String rootDirectory = defaultDirectory();

	/**
	 * The name of the folder that all jRabbit data will be contained in.
	 **/
	private static String directoryName = "jRabbit Data";

	/**
	 * When EngineDirectory is initialized, the settings for directory name are
	 * locked and this File is created from them.
	 **/
	private static File directory = null;

	/*************************************************************************
	 * Calculates the default directory location.
	 * 
	 * The computer is recognized as a Windows, Linux, or Macintosh machine,
	 * then the root directory is placed in the "application data" directory for
	 * each: ../AppData/, ../local/, and ../Application Support/, respectively.
	 * If the machine is not registered as any of these, the directory defaults
	 * to the same one the game is run from.
	 * 
	 * @return The default directory location, based upon the OS running the
	 *         game.
	 *************************************************************************/
	private static String defaultDirectory()
	{
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
			return System.getenv("APPDATA");
		else if (OS.contains("MAC"))
			return System.getProperty("user.home") + "/Library/Application "
					+ "Support";
		else if (OS.contains("NUX"))
		{
			hidden = true;
			return System.getProperty("user.home");
		}
		return System.getProperty("user.dir");
	}

	/*************************************************************************
	 * Attempts to change the location of the root directory. If the directory
	 * has already been initialized, the operation does not work.
	 * 
	 * @param path
	 *            The new location of the main engine directory.
	 * 
	 * @return True if the operation succeeded (which means the directory has
	 *         not been initialized yet), false if not.
	 *************************************************************************/
	public static boolean setDirectoryLocation(String path)
	{
		if (directory == null)
		{
			rootDirectory = path;
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to change the name of the folder where data is stored. Does not
	 * work if the directory has been initialized.
	 * 
	 * @param name
	 *            The new name for the directory.
	 * 
	 * @return True if the operation was a success, false if the directory has
	 *         already been initialized.
	 *************************************************************************/
	public static boolean setDirectoryName(String name)
	{
		if (directory == null)
		{
			directoryName = name;
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Accesses the name of the directory.
	 * 
	 * @return The name of the folder where jRabbit data is stored.
	 *************************************************************************/
	public static String directoryName() { return directoryName; }

	/*************************************************************************
	 * Retrieves the complete path to the directory.
	 * 
	 * @return The path to the directory; this is a simple combination of the
	 *         directory location and the name of the directory.
	 *************************************************************************/
	public static String directoryPath()
	{
		return rootDirectory + (hidden ? "/." : "/") + directoryName;
	}
	
	/*************************************************************************
	 * Learns whether or not the directory is set to be hidden.
	 * 
	 * @return True if the folder is hidden, false if not.
	 *************************************************************************/
	public static boolean hidden() { return hidden; }
	
	/*************************************************************************
	 * Attempts to set whether or not the directory should be null.
	 * 
	 * @param hide
	 * 			  True if the directory should be hidden, false if not.
	 * 
	 * @return True if the operation succeeded (if the directory was not 
	 *         initialized), false if not.
	 *************************************************************************/
	public static boolean setHideDirectory(boolean hide)
	{
		if (directory == null)
		{
			hidden = hide;
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Returns a File that references the main directory. If null, then the
	 * directory has not been initialized and the developer may still make
	 * changes to its location or name.
	 * 
	 * @return The File object that corresponds to the main directory.
	 *************************************************************************/
	public static File directory()
	{
		return directory;
	}

	/*************************************************************************
	 * Initializes the directory; after this the developer is not allowed to
	 * change the references to location or folder name.
	 *************************************************************************/
	public static void init()
	{
		directory = new File(directoryPath());
		if(hidden && !directory.isHidden() && System.getProperty("os.name").
				toUpperCase().contains("Win"))
			try
			{
				Process p = Runtime.getRuntime().exec("attrib +h " + 
						directory.getPath());
			    p.waitFor();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		if (!directory.exists())
			directory.mkdir();
	}

	/*************************************************************************
	 * Convenience method to create a folder or folders inside the main
	 * directory.
	 * 
	 * @param subPath
	 *            The path to the subfolder.
	 * 
	 * @return Whether or not the attempt to create the subdirectory succeeded.
	 *************************************************************************/
	public static boolean makeSubDirectory(String subPath)
	{
		return subDirectory(subPath).mkdirs();
	}

	/*************************************************************************
	 * Gets a reference to a folder within the main directory.
	 * 
	 * @param subPath
	 *            The path to the subfolder.
	 * 
	 * @return A reference to a file object pointing to a folder within the
	 *         directory.
	 *************************************************************************/
	public static File subDirectory(String subPath)
	{
		return new File(directoryPath() + '/' + subPath);
	}
}