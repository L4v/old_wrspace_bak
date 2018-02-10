package org.jrabbit.base.data.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/*****************************************************************************
 * A convenience object for loading data from the web.
 * 
 * NOTE: Some of the methods that access data can have a chance of failing -
 * things like Internet connection and web access are out of the developer's
 * control.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class URLLoader implements Loader
{
	/**
	 * The URL that data is retrieved from.
	 **/
	private URL address;

	/*************************************************************************
	 * Creates a URLLoader with the indicated, preconstructed URL.
	 * 
	 * @param address
	 *            The URL to load data from.
	 *************************************************************************/
	public URLLoader(URL address)
	{
		this.address = address;
	}

	/*************************************************************************
	 * Creates a URLLoader from the String representation of the web address.
	 * 
	 * @param webAddress
	 *            The address of the URL.
	 *            
	 * @throws MalformedURLException if the supplied web address is invalid.
	 *************************************************************************/
	public URLLoader(String webAddress) throws MalformedURLException
	{
		this(new URL(webAddress));
	}

	/*************************************************************************
	 * Accesses a String version of the URL.
	 * 
	 * @return A String representation of the web address.
	 ***************************************************************/ @Override
	public String path()
	{
		return address.getPath();
	}

	/*************************************************************************
	 * Accesses an InputStream from the URL.
	 * 
	 * @return An InputStream of data from the target web resource. If a problem
	 *         occurred reading the data, then null is returned.
	 ***************************************************************/ @Override
	public InputStream stream()
	{
		try
		{
			return address.openStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*************************************************************************
	 * Accesses the URL.
	 * 
	 * @return The URL being used to load data.
	 ***************************************************************/ @Override
	public URL url()
	{
		return address;
	}

	/*************************************************************************
	 * Accesses a file created from the URL.
	 * 
	 * @return A file pointing to the same data as the URL. This check may fail,
	 *         if it does, null is returned.
	 ***************************************************************/ @Override
	public File file()
	{
		try
		{
			return new File(address.toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*************************************************************************
	 * Learns the type of data being pointed to by the URL.
	 * 
	 * @return A String representing the type of the file, in all caps.
	 ***************************************************************/ @Override
	public String type()
	{
		String path = path();
		return path.substring(path.lastIndexOf('.') + 1).toUpperCase();
	}
}