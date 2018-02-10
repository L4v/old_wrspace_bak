package org.jrabbit.base.data.loading;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/*****************************************************************************
 * A general convenience object for loading data from external locations.
 * 
 * Depending on the implementation, accessing the data may fail. In these
 * instances, it's recommended that the Loader return null.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Loader
{
	/*************************************************************************
	 * Accesses the path to the object in String form.
	 * 
	 * @return A String representation of the path to the object.
	 *************************************************************************/
	public String path();

	/*************************************************************************
	 * Accesses the target data via stream.
	 * 
	 * @return An InputStream from the file.
	 *************************************************************************/
	public InputStream stream();

	/*************************************************************************
	 * Accesses the target data via URL.
	 * 
	 * @return A URL to the file.
	 *************************************************************************/
	public URL url();

	/*************************************************************************
	 * Accesses a the target data via File object.
	 * 
	 * @return A File reference to the data.
	 *************************************************************************/
	public File file();

	/*************************************************************************
	 * Learn the type of the data being accessed.
	 * 
	 * @return The type of the data being accessed. The returned String should
	 *         be in all caps for simplicity.
	 *************************************************************************/
	public String type();
}
