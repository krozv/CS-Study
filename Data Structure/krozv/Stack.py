class Stack:

    def __init__(self):
        self.items = []

    # 스택의 맨 위에 요소 추가
    def push(self, value):
        self.items.append(value)

    # 스택의 맨 위 요소 제거 및 반환
    def pop(self):
        if self.is_empty():
            raise IndexError("스택이 비어있음")
        return self.items.pop()
    
    # 스택이 비어있는 확인
    def is_empty(self):
        return len(self.items) == 0

    # 스택의 크기 반환
    def size(self):
        return len(self.items)

    # 스택의 내용을 문자열로 반환
    def __repr__(self):
        return f"Stack({self.items})"

# example
stk = Stack()
stk.push(10)
stk.push(20)
stk.push(30)
print(stk)

stk.pop()
print(stk)

print(stk.is_empty())

print(stk.size())
