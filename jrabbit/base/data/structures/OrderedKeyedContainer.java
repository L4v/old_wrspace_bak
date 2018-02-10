package org.jrabbit.base.data.structures;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.Referenced;

/*****************************************************************************
 * OrderedKeyedContainer extends BaseKeyedContainter to maintain order of 
 * addition.
 * 
 * @author Chris Molini
 * 
 * @param T
 *            The type of object to contain in the Cache. Must implement
 *            Referenced.
 *****************************************************************************/
public class OrderedKeyedContainer<T extends Referenced> extends 
		BaseKeyedContainer<T>
{
	/*************************************************************************
	 * Creates an OrderedKeyedContainer that uses the supplied Factory to
	 * make objects on demand.
	 * 
	 * @param factory
	 * 			  The Factory used to create entries on the fly, from String 
	 *            data.
	 *************************************************************************/
	public OrderedKeyedContainer(Factory<T> factory)
	{
		super(factory);
	}
	
	/*************************************************************************
	 * Obtains the HashMap used to maintain the list. This overrides the method
	 * in KeyedContainer to provide a HashMap that maintains order of 
	 * addition.
	 * 
	 * @return A LinkedHashMap that will key Objects to Strings.
	 ***************************************************************/ @Override
	protected HashMap<String, T> createMap()
	{
		return new LinkedHashMap<String, T>();
	}
}