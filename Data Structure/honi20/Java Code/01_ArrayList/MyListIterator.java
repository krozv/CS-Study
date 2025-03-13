
public interface MyListIterator<T> {
	T next();

	boolean hasNext();

	T previos();

	boolean hasPrevios();

	void add(Object element);

	void remove();
}
