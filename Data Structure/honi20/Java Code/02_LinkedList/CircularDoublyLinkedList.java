import java.io.*;
import java.util.*;

public class CircularDoublyLinkedList<E> {
	private Node<E> head;
	private Node<E> tail;

	private int size;

	public CircularDoublyLinkedList() {
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
			for (int i = size; i > index; i--) {
				n = n.prev;
			}
		}

		return n;
	}

	public void addFirst(E value) {
		Node<E> first = head;
		Node<E> last = tail;

		Node<E> new_node = new Node<>(null, value, first);

		++size;

		head = new_node;

		if (first == null) {
			tail = new_node;

			new_node.next = new_node;
			new_node.prev = new_node;
		} 
		else {
			first.prev = new_node;

			last.next = new_node;
			new_node.prev = last;
		}
	}

	public void addLast(E value) {
		Node<E> first = head;
		Node<E> last = tail;

		Node<E> new_node = new Node<>(last, value, null);

		++size;

		tail = new_node;

		if (last == null) {
			head = new_node;

			new_node.next = new_node;
			new_node.prev = new_node;
		} 
		else {
			last.next = new_node;

			first.prev = new_node;
			new_node.next = first;
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
		Node<E> last = tail;

		head.next = null;
		head.item = null;
		head.prev = null;

		--size;

		head = first;

		if (first == null) {
			tail = null;
		} 
		else {
			first.prev = last;
			last.next = first;
		}

		return value;
	}

	public E removeLast() {
		if (tail == null) {
			throw new NoSuchElementException();
		}

		E value = tail.item;

		Node<E> last = tail.prev;
		Node<E> first = head;

		tail.item = null;
		tail.prev = null;
		tail.next = null;

		--size;

		tail = last;

		if (last == null) {
			head = null;
		} 
		else {
			first.prev = last;
			last.next = first;
		}

		return value;
	}

	@Override
	public String toString() {
		if (head == null) {
			return "[]";
		}

		Node<E> n = head;
		String result = "[";

		for (int i = 0; i < size; i++) {
			result += n.item + ", ";
			n = n.next;
		}

		result += n.item + "]";

		return result;
	}

	public static void main(String[] args) {

		CircularDoublyLinkedList<Number> list = new CircularDoublyLinkedList<>();

		list.addFirst(3);
		list.addFirst(2);
		list.addFirst(1);
		list.addLast(4);

		list.add(3, 3.5);
		list.add(1, 1.5);

		System.out.println(list.toString()); // [1, 1.5, 2, 3.5, 3, 4, 1]

		list.removeFirst();
		list.removeFirst();
		list.removeLast();

		System.out.println(list.toString()); // [2, 3.5, 3, 2]
	}
}