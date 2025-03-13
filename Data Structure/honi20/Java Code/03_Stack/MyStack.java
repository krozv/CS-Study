import java.io.*;
import java.util.*;

public class MyStack<E> {
	private static final int DEFAULT_CAPACITY = 6; // 최소 용량 크기
	private Object[] arr; // 요소를 담을 내부 배열
	private int top; // 스택의 가장 마지막 요소를 가리키는 포인터

	public MyStack() {
		this.arr = new Object[DEFAULT_CAPACITY];
		this.top = -1;
	}

	public boolean isFull() {
		return top == arr.length - 1;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	private void resize() {
		int arr_capacity = arr.length - 1;

		// 용량이 꽉찬 경우
		if (top == arr_capacity) {
			int new_capacity = arr.length * 2;

			arr = Arrays.copyOf(arr, new_capacity);
			return;
		}

		// 용량에 비해 데이터 양이 적은 경우
		if (top < (arr_capacity / 2)) {
			int half_capacity = arr.length / 2;

			arr = Arrays.copyOf(arr, Math.max(half_capacity, DEFAULT_CAPACITY));
			return;
		}
	}

	public E push(E value) {
		if (isFull())
			resize();

		arr[++top] = value;

		return value;
	}

	@SuppressWarnings("unchecked")
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();

		E value = (E) arr[top];

		arr[top--] = null;

		resize();

		return value;
	}

	@SuppressWarnings("unchecked")
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();

		return (E) arr[top];
	}

	public int search(E value) {
		for (int i = top; i >= 0; i--) {
			if (arr[i].equals(value)) {
				return top - i + 1;
			}
		}

		return -1;
	}

	@Override
	public String toString() {
		return Arrays.toString(arr);
	}

	public static void main(String[] args) {
		MyStack<Integer> stack = new MyStack<>();

		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);

		System.out.println(stack); // [1, 2, 3, 4, null]

		System.out.println(stack.peek()); // 4

		System.out.println(stack.search(4)); // 1
		System.out.println(stack.search(3)); // 2

		stack.pop();
		stack.pop();
		stack.pop();

		System.out.println(stack); // [1, null, null, null, null, null]
	}
}
