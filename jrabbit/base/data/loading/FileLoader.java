package org.jrabbit.base.data.loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/*****************************************************************************
 * A convenience object for accessing data from a supplied File.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class FileLoader implements Loader
{
	/**
	 * The File being accessed.
	 **/
	protected File file;

	/*************************************************************************
	 * Creates a FileLoader that accesses the File indicated by the supplied
	 * path.
	 * 
	 * @param filepath
	 * 			  The path to the desired File.
	 ***********************************************************************/
	public FileLoader(String filepath)
	{
		this(new File(filepath));
	}
	
	/*************************************************************************
	 * Creates a FileLoader that accesses the supplied File.
	 * 
	 * @param file
	 * 			  The File to retrieve data from.
	 *************************************************************************/
	public FileLoader(File file)
	{
		this.file = file;
	}

	/*************************************************************************
	 * Accesses a String path to the file.
	 * 
	 * @return A String representation of the filepath.
	 ***************************************************************/ @Override
	public String path()
	{
		return file.getAbsolutePath();
	}

	/*************************************************************************
	 * Accesses the File's data via an InputStream.
	 * 
	 * @return An input stream of data from the file.
	 ***************************************************************/ @Override
	public InputStream stream()
	{
		InputStream stream = null;
		try
		{
			stream =  new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return stream;
	}

	/*************************************************************************
	 * Creates a URL to access the file.
	 * 
	 * @return A URL describing the location of the File.
	 ***************************************************************/ @Override
	public URL url()
	{
		URL url = null;
		try
		{
			url = file.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		return url;
	}

	/*************************************************************************
	 * Returns the File.
	 * 
	 * @return A reference to the file being accessed.
	 ***************************************************************/ @Override
	public File file()
	{
		return file;
	}

	/*************************************************************************
	 * Returns the type of the File.
	 * 
	 * @return The file type. NOTE: This returns in all caps.
	 ***************************************************************/ @Override
	public String type()
	{
		String path = path();
		return path.substring(path.lastIndexOf('.') + 1).toUpperCase();
	}
}