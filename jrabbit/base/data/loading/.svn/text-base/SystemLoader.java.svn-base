package org.jrabbit.base.data.loading;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;


/*****************************************************************************
 * A convenience object for accessing file data. This specifically loads data
 * from the local machine, most likely from within the java project itself.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SystemLoader implements Loader
{
	/**
	 * The file reference.
	 * 
	 * This is intended to be the reference within the top-level folder of the
	 * project.
	 **/
	private String path;

	/*************************************************************************
	 * Creates a SystemLoader that will attempt to retrieve information from the
	 * specified location.
	 * 
	 * @param path
	 *            The file location (from the top-level folder of the
	 *            project).
	 *************************************************************************/
	public SystemLoader(String path)
	{
		this.path = path;
	}

	/*************************************************************************
	 * Accesses a String path to the file.
	 * 
	 * @return A String representation of the filepath.
	 ***************************************************************/ @Override
	public String path()
	{
		return path;
	}

	/*************************************************************************
	 * Accesses the file via InputStream.
	 * 
	 * @return An input stream of data from the file.
	 ***************************************************************/ @Override
	public InputStream stream()
	{
		return SystemLoader.class.getClassLoader().getResourceAsStream(path);
	}

	/*************************************************************************
	 * Creates a URL to access the file.
	 * 
	 * @return A URL describing the file.
	 ***************************************************************/ @Override
	public URL url()
	{
		return SystemLoader.class.getClassLoader().getResource(path);
	}

	/*************************************************************************
	 * Uses a File object to reference the file.
	 * 
	 * @return A File object describing the file.
	 ***************************************************************/ @Override
	public File file()
	{
		try
		{
			return new File(url().toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*************************************************************************
	 * Returns the type of the file.
	 * 
	 * @return The file type. NOTE: This returns in all caps.
	 ***************************************************************/ @Override
	public String type()
	{
		return path.substring(path.lastIndexOf('.') + 1).toUpperCase();
	}
}
