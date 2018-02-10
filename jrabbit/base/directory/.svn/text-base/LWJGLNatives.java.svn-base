package org.jrabbit.base.directory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jrabbit.base.data.loading.SystemLoader;

/*****************************************************************************
 * The Java Rabbit Engine uses LWJGL for its rendering. To run, LWJGL requires
 * particular native libraries be on the user's computer, available for the OS.
 * 
 * These natives are automatically packaged into the jRabbit jar file. At
 * startup, a desktop game attempts to use the natives from a folder within the
 * main jRabbit directory. If the libraries are not present, they are extracted
 * into that location.
 * 
 * If the folder exists but the game crashes on startup because of a problem
 * with LWJGL, try deleting the folder where the natives are kept. On the next
 * startup, everything will be re-extracted, and it should work.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class LWJGLNatives
{
	/**
	 * The name of the folder where the natives are kept.
	 **/
	private static String folderName = "LWJGL Natives";

	/**
	 * The path to the natives that are embedded within jRabbit.
	 **/
	private static String embedLoc = "org/jrabbit/lib/natives/";

	/*************************************************************************
	 * Accesses a File reference to the library containing the natives.
	 * 
	 * @return A File that points to the location of the library to extract to.
	 *************************************************************************/
	public static File getLibrary()
	{
		return EngineDirectory.subDirectory(folderName);
	}

	/*************************************************************************
	 * Learns the name of the folder where the natives will be stored.
	 * 
	 * @return The name of the folder where the natives are extracted to.
	 *************************************************************************/
	public static String getLibraryName()
	{
		return folderName;
	}

	/*************************************************************************
	 * Changes the library name.
	 * 
	 * @param name
	 *            The new name of the folder where libraries will be stored.
	 *************************************************************************/
	public static void setLibraryName(String name)
	{
		folderName = name;
	}

	/*************************************************************************
	 * Extracts the natives to the target file. This automatically extracts the
	 * appropriate natives depending on the OS. In case the JVM is a different
	 * bitness (32 vs. 64) than the OS, both 32 bit and 64 bit natives are
	 * extracted.
	 * 
	 * @param target
	 *            The file that the natives will end up in.
	 *************************************************************************/
	private static void extractNatives(File target)
	{
		String OS = System.getProperty("os.name").toUpperCase();
		String path = target.getAbsolutePath();

		if (OS.contains("WIN"))
			extractFiles(embedLoc + "windows", path, "jinput-dx8_64.dll",
					"jinput-dx8.dll", "jinput-raw_64.dll", "jinput-raw.dll",
					"lwjgl.dll", "lwjgl64.dll", "OpenAL32.dll", "OpenAL64.dll");
		else if (OS.contains("MAC"))
			extractFiles(embedLoc + "macosx", path, "libjinput-osx.jnilib",
					"liblwjgl.jnilib", "openal.dylib");
		else if (OS.contains("NUX"))
			extractFiles(embedLoc + "linux", path, "libjinput-linux.so",
					"libjinput-linux64.so", "liblwjgl.so", "liblwjgl64.so",
					"libopenal.so", "libopenal64.so");
		else if (OS.contains("SOL"))
			extractFiles(embedLoc + "solaris", path, "liblwjgl.so",
					"liblwjgl64.so", "libopenal.so");
		else
			throw new RuntimeException("Unsupported Operating System: " + OS);
	}

	/*************************************************************************
	 * Moves the indicated files from one location to another.
	 * 
	 * @param source
	 *            The beginning location of the file.
	 * @param target
	 *            The target folder to place the file in.
	 * @param files
	 *            The list of filename to copy.
	 *************************************************************************/
	private static void extractFiles(String source, String target,
			String... files)
	{
		InputStream in = null;
		OutputStream out = null;

		// Copies every file.
		for (String file : files)
		{
			try
			{
				// Creates streams.
				in = new SystemLoader(source + "/" + file).stream();
				out = new FileOutputStream(target + "/" + file);

				// Copies 1024-byte chunks at a time.
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) != -1)
				{
					out.write(buf, 0, len);
					out.flush();
				}

				// Closes streams.
				in.close();
				out.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				System.err.println(file + " was not found and we cannot "
						+ "run the game. :(");
				System.exit(1);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.err.println("Something has gone wrong with extracting "
						+ "the OpenGL bindings and we cannot run the game. :(");
				System.exit(1);
			}
		}
	}

	/*************************************************************************
	 * Handles the extracting/loading/accessing of the required natives so that
	 * LWJGL can function.
	 *************************************************************************/
	public static void load()
	{
		File library = EngineDirectory.subDirectory(folderName);
		if (!library.exists() && library.mkdir())
			extractNatives(library);
		System.setProperty("org.lwjgl.librarypath", library.getAbsolutePath());
	}
}