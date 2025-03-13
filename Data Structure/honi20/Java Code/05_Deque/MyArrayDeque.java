import java.io.*;
import java.util.*;

public class MyArrayDeque<E> {
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	private static final int DEFAULT_CAPACITY = 64;

	private Object[] array;
	private int size;

	private int front;
	private int rear;

	public MyArrayDeque() {
		this.array = new Object[DEFAULT_CAPACITY];
		this.size = 0;
		this.front = 0;
		this.rear = 0;
	}

	public MyArrayDeque(int capacity) {
		if(capacity < 0) {
			throw new IllegalArgumentException();
		}
		
		this.array = new Object[capacity];
		this.size = 0;
		this.front = 0;
		this.rear = 0;
	}

	private void resize(int newCapacity) {
		int arrayCapacity = array.length;
		
		newCapacity = hugeRangeCheck(arrayCapacity, newCapacity);
		Object[] newArray = new Object[newCapacity];

		for (int i = 1, j = front + 1; i <= size; i++, j++) {
			newArray[i] = array[j % arrayCapacity];
		}

		this.array = null;
		this.array = newArray;

		front = 0;
		rear = size;
	}

	private int hugeRangeCheck(int oldCapacity, int newCapacity) {
		if (MAX_ARRAY_SIZE - size <= 0) {
			throw new OutOfMemoryError("Required queue length too large");
		}

		if (newCapacity >= 0) {
			if (newCapacity - MAX_ARRAY_SIZE <= 0) {
				return newCapacity;
			}
			
			return MAX_ARRAY_SIZE;
		}
		else {
			int fiveFourthsSize = oldCapacity + (oldCapacity >>> 2);
			
			if (fiveFourthsSize <= 0 || fiveFourthsSize >= MAX_ARRAY_SIZE) {
				return MAX_ARRAY_SIZE;
			}
			
			return fiveFourthsSize;
		}
	}

	public boolean offer(E item) {
		return offerLast(item);
	}

	public boolean offerLast(E item) {
		int oldCapacity = array.length;
		
		if ((rear + 1) % oldCapacity == front) {
			resize(oldCapacity + (oldCapacity >> 1));
		}

		rear = (rear + 1) % array.length;
		array[rear] = item;
		size++;

		return true;
	}

	public boolean offerFirst(E item) {
		int oldCapacity = array.length;

		if ((front - 1 + oldCapacity) % oldCapacity == rear) {
			resize(oldCapacity + (oldCapacity >> 1));
		}

		array[front] = item;
		front = (front - 1 + array.length) % array.length;
		size++;

		return true;
	}

	public E poll() {
		return pollFirst();
	}

	@SuppressWarnings("unchecked")
	public E pollFirst() {
		if (size == 0) {
			return null;
		}
		
		int oldCapacity = array.length;
		
		front = (front + 1) % oldCapacity;
		
		E item = (E) array[front];

		array[front] = null;
		size--;

		if (oldCapacity > DEFAULT_CAPACITY && size < (oldCapacity >>> 2)) {
			resize(Math.max(DEFAULT_CAPACITY, oldCapacity >>> 1));
		}

		return item;
	}

	public E remove() {
		return removeFirst();
	}

	public E removeFirst() {
		E item = pollFirst();

		if (item == null) {
			throw new NoSuchElementException();
		}

		return item;
	}

	@SuppressWarnings("unchecked")
	public E pollLast() {
		if (size == 0) {
			return null;
		}
		
		E item = (E) array[rear];
		array[rear] = null;
		
		int oldCapacity = array.length;

		rear = (rear - 1 + oldCapacity) % oldCapacity;
		size--;

		if (oldCapacity > DEFAULT_CAPACITY && size < (oldCapacity >>> 2)) {
			resize(Math.max(DEFAULT_CAPACITY, oldCapacity >>> 1));
		}

		return item;
	}

	public E removeLast() {
		E item = pollLast();

		if (item == null) {
			throw new NoSuchElementException();
		}

		return item;
	}

	public E peek() {
		return peekFirst();
	}

	@SuppressWarnings("unchecked")
	public E peekFirst() {
		if (size == 0) {
			return null;
		}

		E item = (E) array[(front + 1) % array.length];
		
		return item;
	}

	@SuppressWarnings("unchecked")
	public E peekLast() {
		if (size == 0) {
			return null;
		}

		E item = (E) array[(rear)];
		
		return item;
	}

	public E element() {
		return getFirst();
	}

	public E getFirst() {
		E item = peek();

		if (item == null) {
			throw new NoSuchElementException();
		}
		
		return item;
	}

	public E getLast() {
		E item = peekLast();

		if (item == null) {
			throw new NoSuchElementException();
		}
		
		return item;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		
		int start = (front + 1) % array.length;
		
		for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
			if (value.equals(array[idx])) {
				return true;
			}
		}
		
		return false;
	}

	public void clear() {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}

		front = rear = size = 0;
	}
}
