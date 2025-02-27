import java.io.*;
import java.util.*;

public class MyDoublyLinkedList<E> {
	private Node<E> head;
	private Node<E> tail;

	private int size;

	public MyDoublyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	private static class Node<E> {
		private E item;
		private Node<E> next;
		private Node<E> prev;

		Node(Node<E> prev, E item, Node<E> next) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
	}

	private Node<E> search(int index) {
		Node<E> n;

		// 인덱스가 시작에 가까운 경우
		if ((size / 2) > index) {
			n = head;
			for (int i = 0; i < index; i++) {
				n = n.next;
			}
		}
		// 인덱스가 끝에 가까운 경우
		else {
			n = tail;
			for (int i = size - 1; i > index; i--) {
				n = n.prev;
			}
		}

		return n;
	}

	public void addFirst(E value) {
		Node<E> first = head;

		Node<E> new_node = new Node<>(null, value, first);

		++size;

		head = new_node;

		if (first == null) {
			tail = new_node;
		} 
		else {
			first.prev = new_node;
		}
	}

	public void addLast(E value) {
		Node<E> last = tail;

		Node<E> new_node = new Node<>(last, value, null);

		++size;

		tail = new_node;

		if (last == null) {
			head = new_node;
		} 
		else {
			last.next = new_node;
		}
	}

	public boolean add(E value) {
		addLast(value);
		return true;
	}

	public void add(int index, E value) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		if (index == 0) {
			addFirst(value);
			return;
		}

		if (index == size) {
			addLast(value);
			return;
		}

		Node<E> next_node = search(index);
		Node<E> prev_node = next_node.prev;

		Node<E> new_node = new Node<>(prev_node, value, next_node);

		++size;

		prev_node.next = new_node;
		next_node.prev = new_node;
	}

	public E removeFirst() {
		if (head == null) {
			throw new NoSuchElementException();
		}

		E value = head.item;
		Node<E> first = head.next;

		head.next = null;
		head.item = null;

		--size;

		head = first;

		if (first == null) {
			tail = null;
		} 
		else {
			first.prev = null;
		}

		return value;
	}

	public E remove() {
		return removeFirst();
	}

	public E removeLast() {
		if (tail == null) {
			throw new NoSuchElementException();
		}

		E value = tail.item;
		Node<E> last = tail.prev;

		tail.item = null;
		tail.prev = null;

		--size;

		tail = last;

		if (last == null) {
			head = null;
		} 
		else {
			last.next = null;
		}

		return value;
	}

	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			return removeFirst();
		}

		if (index == size - 1) {
			return removeLast();
		}

		Node<E> del_node = search(index);
		Node<E> prev_node = del_node.prev;
		Node<E> next_node = del_node.next;

		E value = del_node.item;

		del_node.item = null;
		del_node.prev = null;
		del_node.next = null;

		--size;

		prev_node.next = next_node;
		next_node.prev = prev_node;

		return value;
	}

	public boolean remove(Object value) {

		Node<E> del_node = null;
		Node<E> i = head;

		while (i != null) {

			// 노드의 값과 매개변수 값이 같은 경우
			if (Objects.equals(i.item, value)) {
				del_node = i;
				break;
			}

			i = i.next;
		}

		// 찾는 요소가 없는 경우
		if (del_node == null) {
			return false;
		}

		// 삭제하려는 노드가 head(첫번째 요소)라면, 기존 removeFirst()를 사용
		if (del_node == head) {
			removeFirst();
			return true;
		}

		// 삭제하려는 노드가 last(마지막 요소)라면, 기존 removeLast()를 사용
		if (del_node == tail) {
			removeLast();
			return true;
		}

		Node<E> prev_node = del_node.prev;
		Node<E> next_node = del_node.next;

		del_node.item = null;
		del_node.prev = null;
		del_node.next = null;

		--size;

		prev_node.next = next_node;
		next_node.prev = prev_node;

		return true;
	}

	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return search(index).item;
	}

	public void set(int index, E value) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// search 메소드를 이용해 교체할 노드를 얻는다.
		Node<E> replace_node = search(index);

		replace_node.item = null;
		replace_node.item = value;
	}

	public int indexOf(Object value) {
		Node<E> n = head;
		int i = 0;
		while (n != null) {
			if (Objects.equals(value, n.item)) {
				return i;
			}

			i++;
			n = n.next;
		}

		return -1;
	}

	public int lastIndexOf(Object value) {
		Node<E> n = tail;
		int i = size - 1;
		while (n != null) {
			if (Objects.equals(value, n.item)) {
				return i;
			}

			i--;
			n = n.prev;
		}

		return -1;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (Node<E> i = head; i.next != null;) {
			Node<E> next_node = i.next;

			i.item = null;
			i.prev = null;
			i.next = null;

			i = next_node;
		}

		head = null;
		tail = null;

		size = 0;
	}

	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	public String toString() {
		if (head == null) {
			return "[]";
		}

		Node<E> n = head;
		String result = "[";

		while (n.next != null) {
			result += n.item + ", ";
			n = n.next;
		}

		result += n.item + "]";

		return result;
	}

	public static void main(String[] args) {

		MyDoublyLinkedList<Number> list = new MyDoublyLinkedList<>();
		list.addFirst(1);
		list.addLast(2);
		list.addLast(3);
		list.addLast(4);
		list.addLast(5);

		System.out.println(list.toString()); // [1, 2, 3, 4, 5]

		list.removeFirst();

		System.out.println(list.toString()); // [2, 3, 4, 5]

		list.removeLast();

		System.out.println(list.toString()); // [2, 3, 4]

		list.remove(2.5);

		System.out.println(list.toString()); // [2, 3, 4]

		System.out.println(list.get(2)); // 4
		list.set(1, 3.5);

		System.out.println(list.toString()); // [2, 3.5, 4]
	}
}