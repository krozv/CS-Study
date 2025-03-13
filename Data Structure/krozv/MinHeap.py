class MinHeap:
    def __init__(self):
        self.heap = []

    # 힙에 값 추가
    def push(self, value):
        self.heap.append(value)
        self._heapify_up()

    # 최솟값 제거 및 반환
    def pop(self):
        if self.is_empty():
            raise IndexError("힙이 비어있음")
        if len(self.heap) == 1:
            return self.heap.pop()

        root = self.heap[0]
        self.heap[0] = self.heap.pop()
        self._heapify_down()
        return root

    # 최솟값 조회 
    def smallest(self):
        if self.is_empty():
            raise IndexError("힙이 비어있음")
        return self.heap[0]

    # 힙 비어있는지 확인
    def is_empty(self):
        return len(self.heap) == 0

    # 힙 크기 조회
    def size(self):
        return len(self.heap)

    # 삽입 후 위로 정렬
    def _heapify_up(self):
        idx = len(self.heap) - 1
        while idx > 0:
            parent_idx = (idx - 1) // 2
            if self.heap[idx] < self.heap[parent_idx]:
                self.heap[idx], self.heap[parent_idx] = self.heap[parent_idx], self.heap[idx]
                idx = parent_idx
            else:
                break

    # 삭제 후 아래로 정렬
    def _heapify_down(self):
        idx = 0
        while True:
            left_idx = 2 * idx + 1
            right_idx = 2 * idx + 2
            smallest = idx

            if left_idx < len(self.heap) and self.heap[left_idx] < self.heap[smallest]:
                smallest = left_idx
            if right_idx < len(self.heap) and self.heap[right_idx] < self.heap[smallest]:
                smallest = right_idx
            if smallest != idx:
                self.heap[idx], self.heap[smallest] = self.heap[smallest], self.heap[idx]
                idx = smallest
            else:
                break

    # 내용 출력
    def __repr__(self):
        return f"Heap({self.heap})"