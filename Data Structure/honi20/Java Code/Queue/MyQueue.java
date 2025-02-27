import java.io.*;
import java.util.*;

public class MyQueue<E> {
	private Node<E> head;
	private Node<E> tail;
	private int size;

	public MyQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	private static class Node<E> {
		private E data;
		private Node<E> next;

		Node(E data) {
			this.data = data;
			this.next = null;
		}
	}

	public boolean offer(E value) {
		Node<E> newNode = new Node<E>(value);

		if (size == 0) {
			head = newNode;
		}
		else {
			tail.next = newNode;
		}
		
		tail = newNode;
		++size;
		
		return true;

	}

	public E poll() {
		if (size == 0) {
			return null;
		}

		E element = head.data;
		Node<E> nextNode = head.next;

		head.data = null;
		head.next = null;
		head = nextNode;
		
		--size;

		return element;
	}

	public E remove() {
		E element = poll();
		
		if (element == null) {
			throw new NoSuchElementException();
		}
		
		return element;
	}

	public E peek() {
		if (size == 0) {
			return null;
		}
		
		return head.data;
	}

	public E element() {
		E element = peek();
		
		if (element == null) {
			throw new NoSuchElementException();
		}
		
		return element;
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
		
		for (Node<E> i = head; i != null; i = i.next) {
			if (value.equals(i.data)) {
				return true;
			}
		}
		
		return false;
	}

	public void clear() {
		for (Node<E> x = head; x != null;) {
			Node<E> next = x.next;
			x.data = null;
			x.next = null;
			x = next;
		}
		
		size = 0;
		head = tail = null;
	}
}
