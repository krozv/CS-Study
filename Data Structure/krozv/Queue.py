class Queue:
    def __init__(self):
        self.items = []

    # 끝에 요소 추가
    def enqueue(self, value):
        self.items.append(value)

    # 앞에 요소 제거 및 반환
    def dequeue(self):
        if self.is_empty():
            raise IndexError("큐가 비어있음")
        return self.items.pop(0)

    # 큐 비어있는지
    def is_empty(self):
        return len(self.items) == 0
    
    # 큐 크기 반환
    def size(self):
        return len(self.items)

    # 큐 내용 출력
    def __repr__(self):
        return f"Queue {self.items}"
    
# example
queue = Queue()
queue.enqueue(10)
queue.enqueue(20)
queue.enqueue(30)
print(queue)

queue.dequeue()
print(queue)