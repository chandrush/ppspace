package ppspace.engine.database;

import java.util.Collection;
import java.util.HashMap;

/**
 * An interface for simple collection of elements accessible by key.
 * @author andrej.chanturidze
 */
public class NamedElementsCollection<T extends INamedElement> implements INamedElementsCollection<T>  {

	private HashMap<String, T> elements;
	
	public NamedElementsCollection() {

		this.elements = new HashMap<String, T>();
	}
	
	public T get(String name)
	{
		return this.elements.get(name);
	}
	
	public void add(T element)
	{
		this.elements.put(element.getName(), element);
	}

	public void remove(String name)
	{
		this.elements.remove(name);
	}
	
	public boolean contains(String name)
	{
		return this.elements.containsKey(name);
	}
	
	public Collection<T> all()
	{
		return this.elements.values();
	}
}
