package ppspace.engine.database;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import ppspace.engine.elements.user.Node;
import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.PrecisionConfiguration;

/**
 * A set of Nodes in the User model with access by coordinates and by name.
 * @author andrej.chanturidze
 *
 */
public final class NodeSet implements INamedElementsCollection<Node> {
	
	/**
	 * Collection of the elements
	 */
	private HashMap<Integer, CollidedElementsContainer> geometryToElement;
	
	private HashMap<String, Node> nameToElement;
	
	private final PrecisionConfiguration precisionConfiguration;
	
	public NodeSet(PrecisionConfiguration precisionConfiguration)
	{
		this.precisionConfiguration = precisionConfiguration;
		
		this.geometryToElement = new HashMap<Integer, CollidedElementsContainer>();
		this.nameToElement = new HashMap<String, Node>();
	}
	
	@Override
	public Collection<Node> all() {
		
		return this.nameToElement.values();
	}
	
	public boolean ensure(Node element)
	{
		//check if the node already exists in the model
		Node existedElement = this.nameToElement.get(element.getName());
		if (existedElement != null)
		{
			return false;
		}
		else
		{
			//getting the container for element by geometry hash code
			int geometryHash = element.getVector().hashCode();
			CollidedElementsContainer collidedElementsContainer = this.geometryToElement.get(geometryHash);
			
			if (collidedElementsContainer == null)
			{
				collidedElementsContainer = new CollidedElementsContainer(geometryHash, element);
				this.geometryToElement.put(geometryHash, collidedElementsContainer);
				this.nameToElement.put(element.getName(), element);
			}
			else
			{
				collidedElementsContainer.addElement(element, this.precisionConfiguration);
				this.nameToElement.put(element.getName(), element);
			}
			return true;
		}
	}

	public boolean contains(String name) 
	{
		return this.nameToElement.containsKey(name);
	}

	@Override
	public Node get(String name) 
	{
		return this.nameToElement.get(name);
	}

	public Node get(Vector3d geometry) 
	{
		CollidedElementsContainer collidedElementsContainer = this.geometryToElement.get(geometry.hashCode());
		if (collidedElementsContainer != null)
		{
			return collidedElementsContainer.get(geometry, this.precisionConfiguration);
		} 
		else
		{
			return null;
		}
	}

	public void remove(String name) 
	{
		Node existedNode = this.nameToElement.get(name);
		if (existedNode != null)
		{
			CollidedElementsContainer setBasket = this.geometryToElement.get(existedNode.getVector().hashCode());
			setBasket.remove(existedNode.getVector(), this.precisionConfiguration);
			this.nameToElement.remove(name);
		}
	}
	
	/**
	 * The collection of the collided by hash elements.
	 */
	final class CollidedElementsContainer
	{
		private int key;
		
		private Node[] elements;
		
		/**
		 * The actual number of elements regardless to the length of elements array.
		 */
		private int size;
		
		public CollidedElementsContainer(int key, Node element) 
		{
			this.key = key;
			this.elements = new Node[1];
			
			this.elements[0] = element;
			this.size = 1;
		}
		
		public int key()
		{
			return this.key;
		}
		
		public Node[] elements()
		{
			return this.elements;
		}
		
		public boolean addElement(Node element, PrecisionConfiguration precisionConfiguration) 
		{
			//Check cases:
			//1. basketElement.Name == element.Name AND basketElement.Geometry == element.Geometry => return
			//2. basketElement.Name == element.Name AND basketElement.Geometry != element.Geometry => ElementWithTheSameNameAlreadyExistsException
			//3. basketElement.Name != element.Name AND basketElement.Geometry == element.Geometry => ElementWithTheSameGeometryAlreadyExistsException
			//4. basketElement.Name != element.Name AND basketElement.Geometry != element.Geometry => add element to collection && return
			for (int i = 0; i < this.elements.length; i++)
			{
				Node collectionElement = this.elements[i];
				
				if (collectionElement.getName().equals(element.getName()))
				{
					if (!collectionElement.getVector().equal(element.getVector(), precisionConfiguration))
					{
						//case 2:
						return false;
					}
					else
					{
						//case 1:
						return false;
					}
				}
				else
				{
					if (collectionElement.getVector().equal(element.getVector(), precisionConfiguration))
					{
						//case 3:
						return false;
					} 
					//else
						//case 4:
						//element will be added to collection
				}
			}
			
			//now we can add node to the basket
			if (this.size == this.elements.length)
			{
				Node[] newElements = Arrays.copyOf(this.elements, this.elements.length + 1);
				this.elements = newElements;
			}
			this.elements[size] = element;
			this.size++;
			
			return true;
		}
		
		public Node get(Vector3d geometry, PrecisionConfiguration precisionConfiguration) 
		{
			int i = 0;
			while (i < this.elements.length)
			{
				if (this.elements[i].getVector().equal(geometry, precisionConfiguration))
					return this.elements[i];
				i++;
			}
			return null;
		}

		public void remove(Vector3d geometry, PrecisionConfiguration precisionConfiguration)
		{
			int i = 0;
			while (i < this.elements.length && !this.elements[i].getVector().equal(geometry, precisionConfiguration))
				i++;
		
			if (i != this.elements.length && this.elements[i].getVector().equal(geometry, precisionConfiguration))
			{
				for (int j = i + 1; j < this.elements.length; j++)
				{
					this.elements[j - 1] = this.elements[j];
				}
				
				if (size > 0)
					size--;
			}
		}
	}

	@Override
	public void add(Node element) {

		this.ensure(element);		
	}

}
