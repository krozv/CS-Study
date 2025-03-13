class Node:
    def __init__(self, value):
        self.value = value
        self.next = None # pointer

class LinkedList:
    
    def __init__(self):
        self.head = None

    # 리스트 뒤에 새로운 노드 추가
    def append(self, value):
        new_node = Node(value)
        if not self.head:
            self.head = new_node
            return
        curr_node = self.head
        while curr_node.next:
            curr_node = curr_node.next
        curr_node.next = new_node

    # 리스트 맨 앞에 새로운 노드 추가
    def prepend(self, value):
        new_node = Node(value)
        new_node.next = self.head
        self.head = new_node

    # 특정 값을 가진 노드 삭제
    def delete(self, value):
        
        # 노드가 없을 경우
        if not self.head:
            return
        
        if self.head.value == value:
            self.head = self.head.next
            return
        
        curr_node = self.head
        while curr_node.next:
            if curr_node.next.value == value:
                curr_node.next = curr_node.next.next
                return
            curr_node = curr_node.next
        
    # 출력
    def __repr__(self):
        nodes = []
        curr_node = self.head
        while curr_node:
            nodes.append(str(curr_node.value))
            curr_node = curr_node.next
        return " -> ".join(nodes)
    
# example
ll = LinkedList()
ll.append(10)
ll.append(20)
ll.append(30)
print(ll)

ll.prepend(5)
print(ll)

ll.delete(20)
print(ll)

