package ppspace.engine.database;

/**
 * Base API for collection of elements of a User/Core models.
 * @author andrej.chanturidze
 *
 */
public interface IModelElements {
	
	/**
	 * Returns auto-generated name for an element of User Model.
	 */
	public String autoName();
	
	/**
	 * Creates a child collection.
	 * Elements that are adding to child collection doesn't 
	 * appear in the parent collection until "merge" operation
	 * will be performed as well as removing elements.
	 */
	public IUserModelElements createChildCollection();
	
	/**
	 * Returns a reference to a parent collection.
	 */
	public IUserModelElements getParentCollection();
	
	/**
	 * Apply collection changes to the parent collection.
	 */
	public void merge();
	
}
