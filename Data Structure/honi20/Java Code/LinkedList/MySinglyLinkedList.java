import java.io.*;
import java.util.*;

public class MySinglyLinkedList<E> {
	private Node<E> head;
	private Node<E> tail;

	private int size;

	public MySinglyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	private static class Node<E> {
		private E item; 		// 담을 데이터
		private Node<E> next; 	// 다음 Node 객체

		Node(E item, Node<E> next) {
			this.item = item;
			this.next = next;
		}
	}

	private Node<E> search(int index) {
		Node<E> n = head;
		for (int i = 0; i < index; i++) {
			n = n.next;
		}

		return n;
	}

	public void addFirst(E value) {
		Node<E> first = head;

		// 새 노드 생성
		Node<E> newNode = new Node<>(value, first);

		++size;

		// head 업데이트
		head = newNode;

		// 처음으로 요소가 추가된 경우
		if (first == null) {
			tail = newNode;
		}
	}

	public void addLast(E value) {

		Node<E> last = tail;

		Node<E> newNode = new Node<>(value, null);

		++size;

		// tail 업데이트
		tail = newNode;

		if (last == null) {
			head = newNode;
		} 
		else {
			last.next = newNode;
		}
	}

	public void add(int index, E value) {

		// 인덱스가 0보다 작거나 size보다 같거나 클 경우. 에러
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			addFirst(value);
			return;
		}

		if (index == size - 1) {
			addLast(value);
			return;
		}

		Node<E> prev_node = search(index - 1);
		Node<E> next_node = prev_node.next;

		Node<E> newNode = new Node<>(value, next_node);

		++size;

		prev_node.next = newNode;
	}

	public boolean add(E value) {
		addLast(value);
		return true;
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

		Node<E> replace_node = search(index);

		replace_node.item = null;
		replace_node.item = value;
	}

	public E removeFirst() {

		// 삭제할 요소가 아무것도 없는 경우, 에러
		if (head == null) {
			throw new RuntimeException();
		}

		E returnValue = head.item;
		Node<E> first = head.next;

		head.next = null;
		head.item = null;

		// head가 다음 노드를 가리키도록 업데이트
		head = first;

		--size;

		if (head == null) {
			tail = null;
		}

		return returnValue;
	}

	public E remove() {
		return removeFirst();
	}

	public E remove(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			return removeFirst();
		}

		Node<E> prev_node = search(index - 1);
		Node<E> del_node = prev_node.next;
		Node<E> next_node = del_node.next;

		E returnValue = del_node.item;

		del_node.next = null;
		del_node.item = null;

		--size;

		prev_node.next = next_node;

		return returnValue;
	}

	public boolean remove(Object value) {

		if (head == null) {
			throw new RuntimeException();
		}

		Node<E> prev_node = null;
		Node<E> del_node = null;
		Node<E> next_node = null;

		Node<E> i = head;

		while (i != null) {
			if (Objects.equals(i.item, value)) {
				del_node = i;
				break;
			}

			prev_node = i;

			i = i.next;
		}

		if (del_node == null) {
			return false;
		}

		if (del_node == head) {
			removeFirst();
			return true;
		}

		next_node = del_node.next;

		del_node.next = null;
		del_node.item = null;

		--size;

		prev_node.next = next_node;

		return true;
	}

	public E removeLast() {
		return remove(size - 1);
	}

	public static void main(String[] args) {
		MySinglyLinkedList<Number> l = new MySinglyLinkedList<>();

		l.add(3);
		l.add(6);
		l.add(4);
		l.add(3);
		l.add(8);
		l.add(10);
		l.add(11);
		System.out.println(l); // [3, 6, 4, 3, 8, 10, 11]

		l.add(6, 100);
		l.add(0, 101);
		l.add(1, 102);
		System.out.println(l); // [101, 102, 3, 6, 4, 3, 8, 10, 11, 100]

		l.removeFirst();
		l.removeFirst();
		l.remove(1);
		System.out.println(l); // [3, 4, 3, 8, 10, 11, 100]

		l.remove(new Integer(3));
		l.remove(new Integer(3));
		System.out.println(l); // [4, 8, 10, 11, 100]

		System.out.println(l.get(4)); // 100

		l.set(4, 999);
		System.out.println(l); // [4, 8, 10, 11, 999]
	}
}