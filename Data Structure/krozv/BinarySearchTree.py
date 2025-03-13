class BST:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

    # 검색
    def search(self, value):
        if value == self.value:
            return True
        elif value < self.value and self.left:
            return self.left.search(value)
        elif value > self.value and self.right:
            return self.right.search(value)
        return False

    # 삽입
    def insert(self, value):
        if value < self.value:
            if self.left is None:
                self.left = BST(value)
            else:
                self.left.insert(value)
        else:
            if self.right is None:
                self.right = BST(value)
            else:
                self.right.insert(value)

    # 최솟값 찾기
    def find_min(self):
        if self.left is None:
            return self
        return self.left.find_min()
    
    # 삭제
    def delete(self, value):
        if value < self.value:
            if self.left:
                self.left = self.left.delete(value)
        elif value > self.value:
            if self.right:
                self.right = self.right.delete(value)
        else:
            # Case 1: 자식이 없는 노드 (리프 노드)
            if self.left is None and self.right is None:
                return None
            # Case 2: 자식이 하나만 있는 노드
            if self.left is None:
                return self.right
            if self.right is None:
                return self.left
            # Case 3: 자식이 둘 다 있는 노드
            min_larger_node = self.right.find_min()
            self.value = min_larger_node.value
            self.right = self.right.delete(min_larger_node.value)
        return self

    # 출력
    def print_tree(self, level=0, prefix="Root: "):
        print(" " * (level * 4) + prefix + str(self.value))
        if self.left:
            self.left.print_tree(level + 1, "L---")
        if self.right:
            self.right.print_tree(level + 1, "R---")

# 이진 탐색 트리 생성
root = BST(10)
root.insert(5)
root.insert(15)
root.insert(3)
root.insert(7)
root.insert(13)
root.insert(18)

root.print_tree()

# 검색
print(root.search(7))
print(root.search(12))

# 삭제
root.delete(5)
root.print_tree()