
public interface MyList<T> {
	boolean add(T value);

	void add(int index, T value);

	boolean remove(Object value); // 요소를 삭제

	T remove(int index);

	T get(int index);

	void set(int index, T value);

	boolean contains(Object value);

	int indexOf(Object value);

	int lastIndexOf(Object o);

	int size();

	boolean isEmpty();

	public void clear();
}
