package javaJG.assets;

import java.util.List;
import java.util.ArrayList;

/**
 * Connects two list. With instantiating this class, two list are created:
 * Elements are added to both list at the same as well as removed. You can say
 * that these two lists are "synchronized".
 * 
 * @see javaJG.assets.attribute.Attribute
 *
 * @param <E1> class of first list
 * @param <E2> class of second list
 */
public class ConnectedList<E1, E2> {

	/**
	 * primary list
	 */
	private List<E1> list1;
	/**
	 * secondary list
	 */
	private List<E2> list2;

	public ConnectedList() {
		this(1);
	}

	/**
	 * Instantiate this class with an initial size.
	 * 
	 * @param initialSize
	 */
	public ConnectedList(int initialSize) {
		list1 = new ArrayList<>(initialSize);
		list2 = new ArrayList<>(initialSize);
	}

	/**
	 * Adds elements to both list.
	 * 
	 * @param e1 Object of first list
	 * @param e2 Object of second list
	 */
	public void put(E1 e1, E2 e2) {
		list1.add(e1);
		list2.add(e2);
	}

	/**
	 * Adds elements to both list at given index.
	 * 
	 * @param index Where to add the elements.
	 * @param e1    Object of first list
	 * @param e2    Object of second list
	 */
	public void put(int index, E1 e1, E2 e2) {
		list1.add(index, e1);
		list2.add(index, e2);
	}

	/**
	 * 
	 * @param e2 Element of second list
	 * @return Element at the index of e2 in the first list
	 */
	public E1 getPrimaryValue(E2 e2) {
		return list1.get(list2.indexOf(e2));
	}

	/**
	 * 
	 * @param e2 Element of second list
	 * @return Element at the index of e2 in the first list
	 */
	public E2 getSecondaryValue(E1 e1) {
		return list2.get(list1.indexOf(e1));
	}

	public E1 getPrimaryValue(int index) {
		return list1.get(index);
	}

	public E2 getSecondaryValue(int index) {
		return list2.get(index);
	}

	public void replacePrimaryValue(int index, E1 e1) {
		list1.remove(index);
		list1.add(index, e1);
	}

	public void replaceSecondaryValue(int index, E2 e2) {
		list2.remove(index);
		list2.add(index, e2);
	}

	/**
	 * Unlike like getPrimaryValue() this function returns every matching value as object array
	 * @param e2 
	 * @return Every matching Element of primary list
	 */
	public Object[] getMatchingPrimaryValues(E2 e2) {
		List<E1> tmp = new ArrayList<>();
		for (E2 e : list2)
			if (e.equals(e2))
				tmp.add(list1.get(list2.indexOf(e)));
		return tmp.toArray();
	}

	public Object[] getMatchingSecondaryValues(E1 e1) {
		List<E2> tmp = new ArrayList<>();
		for (E1 e : list1)
			if (e.equals(e1))
				tmp.add(list2.get(list1.indexOf(e)));
		return tmp.toArray();
	}

	public int indexOfPrimaryValue(E1 e1) {
		return list1.indexOf(e1);
	}

	public int indexOfSecondaryValue(E2 e2) {
		return list2.indexOf(e2);
	}

	public boolean containsPrimaryValue(E1 e1) {
		return list1.contains(e1);
	}

	public boolean containsSecondaryValue(E2 e2) {
		return list2.contains(e2);
	}

	public void remove(int index) {
		list1.remove(index);
		list2.remove(index);
	}

	/**
	 * Deprecated because you can say getPrimaryList().add() 
	 * which would make the lists unsynchronized
	 * @return primary list
	 * TODO replace this method
	 */
	@Deprecated
	public List<E1> getPrimaryList() {
		return list1;
	}
	/**
	 * Deprecated because you can say getSecondaryList().add() 
	 * which would make the lists unsynchronized
	 * @return secondary list
	 */
	@Deprecated
	public List<E2> getSecondaryList() {
		return list2;
	}

	public int size() {
		return list1.size();
	}

}
