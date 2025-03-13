import java.io.*;
import java.util.*;

class MyArrayList<E> implements MyList<E> {

	private static final int DEFAULT_CAPACITY = 5; // 생성자로 배열이 생성될때 기본 용량
	private static final Object[] EMPTY_ELEMENTDATA = {};

	private int size; // elementData 배열의 총 요소 개수를 나타내는 변수
	Object[] elementData; // 자료를 담을 내부 배열

	// 생성자 (초기 공간 할당 X)
	public MyArrayList() {
		this.elementData = new Object[DEFAULT_CAPACITY];
		this.size = 0;
	}

	// 생성자 (초기 공간 할당 O)
	public MyArrayList(int capacity) {
		if (capacity > 0) {
			this.elementData = new Object[capacity];
		} 
		else if (capacity == 0) {
			this.elementData = new Object[DEFAULT_CAPACITY];
		} 
		else if (capacity < 0) {
			throw new RuntimeException(new IllegalAccessException("리스트 용량을 잘못 설정 하였습니다"));
		}

		this.size = 0;
	}

	// 리스트에 요소가 추가, 삭제 등의 동작이 될 때 기본적으로 호출된다.
	private void resize() {
		int element_capacity = elementData.length;

		// 용량이 꽉 찬 경우
		if (element_capacity == size) {
			int new_capacity = element_capacity * 2;

			elementData = Arrays.copyOf(elementData, new_capacity);
		}

		// 용량에 비해 데이터 양이 적은 경우
		else if ((element_capacity / 2) > size) {
			int half_capacity = element_capacity / 2;

			elementData = Arrays.copyOf(elementData, Math.max(half_capacity, DEFAULT_CAPACITY));
		}

		// 빈 배열일 경우
		else if (Arrays.equals(elementData, EMPTY_ELEMENTDATA)) {
			elementData = new Object[DEFAULT_CAPACITY];
		}
	}

	@Override
	public boolean add(Object value) {
		resize();

		elementData[size++] = value;
		return true;
	}

	@Override
	public void add(int index, Object value) {
		// 인덱스가 음수이거나, 배열 크기(size)를 벗어난 경우 예외 발생 (리스트는 데이터가 연속되어야함)
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		// 인덱스가 마지막 위치일 경우
		if (index == size) {
			add(value);
		}
		// 인덱스가 중간 위치를 가리킬 경우
		else {
			resize();

			// 요소들 한 칸 씩 뒤로 밀어 빈 공간 만들기
			for (int idx = size; idx > index; idx--) {
				elementData[idx] = elementData[idx - 1];
			}

			elementData[index] = value;
			size++;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		// 인덱스가 음수이거나, size 보다 같거나 클 경우 (size와 같다는 말은 요소 위치가 빈공간 이라는 말)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		E element = (E) elementData[index];

		// 명시적으로 요소를 null로 처리해주어야 GC가 수거해간다.
		elementData[index] = null;

		// 삭제한 요소의 뒤에 있는 모든 요소들을 한 칸씩 당겨온다.
		for (int i = index; i < size - 1; i++) {
			elementData[i] = elementData[i + 1];
			elementData[i + 1] = null;
		}

		--size;
		resize();

		return element;
	}

	@Override
	public boolean remove(Object value) {
		// 해당 요소가 몇 번째 위치에 존재하는 인덱스
		int idx = indexOf(value);

		// 삭제하고자 하는 값이 없는 경
		if (idx == -1)
			return false;

		remove(idx);

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return (E) elementData[index];
	}

	@Override
	public void set(int index, Object value) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		elementData[index] = value;
	}

	@Override
	public int indexOf(Object value) {
		// null 비교는 동등연산자로 행하기 때문에 비교 로직을 분리
		if (value == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					return i;
				}
			}
		}

		else {
			for (int i = 0; i < size; i++) {
				if (elementData[i].equals(value)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object value) {
		if (value == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i] == null) {
					return i;
				}
			}
		} 
		
		else {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i].equals(value)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		elementData = new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0 ? true : false;
	}

	@Override
	public String toString() {
		return Arrays.toString(elementData);
	}

	class ListIterator implements MyListIterator<E> {
		private int nextIndex = 0;

		@Override
		public boolean hasNext() {
			return nextIndex < size();
		}

		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			return (E) elementData[nextIndex++];
		}

		@Override
		public boolean hasPrevios() {
			return nextIndex > 0;
		}

		@Override
		@SuppressWarnings("unchecked")
		public E previos() {
			return (E) elementData[--nextIndex];
		}

		@Override
		public void add(Object element) {
			MyArrayList.this.add(nextIndex, element);
		}

		@Override
		public void remove() {
			// 이터레이터 커서 위치의 전 요소를 제거
			MyArrayList.this.remove(nextIndex - 1);
			--nextIndex;
		}
	}

	// 내부 클래스 ListIterator 객체를 만들어 반환
	public ListIterator listIterator() {
		return new ListIterator();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		MyArrayList<?> cloneList = (MyArrayList<?>) super.clone();

		// 복사한 MyArrayList의 Object 배열에 사이즈를 미리 지정하여 재생성
		cloneList.elementData = new Object[size];

		// 리스트에 저장하는 데이터는 객체(reference 타입)이기 때문에 반드시 안의 요소들도 따로따로 복사를 해줘야 한다.
		cloneList.elementData = Arrays.copyOf(elementData, size);

		return cloneList;
	}

	public Object[] toArray() {
		return Arrays.copyOf(elementData, size());
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] arr) {
		if (arr.length <= size) {
			return (T[]) Arrays.copyOf(elementData, size);
		} else {
			System.arraycopy(elementData, 0, arr, 0, size);

			// 내부 배열을 복사한 후 바로 다음에 null을 삽입
			if (arr.length > size)
				arr[size] = null;

			return arr;
		}
	}

	public static void main(String[] args) {

		MyArrayList<Number> list = new MyArrayList<>(); // 초기 capatity는 5

		list.add(1);
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(4);

		list.add(1, 1.5);

		System.out.println(list); // [1, 1.5, 2, 2, 3, 4, null, null, null, null]

		System.out.println(list.indexOf(2)); // 2
		System.out.println(list.lastIndexOf(2));// 3

		System.out.println(list.remove(0)); // 1
		System.out.println(list.remove(2)); // 2
		System.out.println(list); // [1.5, 2, 3, 4, null]

		System.out.println(list.remove(Integer.valueOf(1))); // false
		System.out.println(list.remove(Integer.valueOf(2))); // true
		System.out.println(list); // [1.5, 3, 4, null, null]

		list.clear();
		System.out.println(list); // [null, null, null, null, null]

	}
}