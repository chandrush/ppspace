package ppspace.engine.database;

import java.util.Collection;

public interface INamedElementsCollection<T extends INamedElement> {
	
	public T get(String name);
	
	public void add(T element);
	
	public void remove(String name);
	
	public boolean contains(String name);
	
	public Collection<T> all();
}
