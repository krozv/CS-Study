class Node:
    def __init__(self, level = 1):
        self.children = {} # key: 문자
        self.is_end = False
        self.count = 0

class Trie:
    def __init__(self):
        self.root = Node()
    
    # Trie 만드는 함수
    def insert(self, word: str) -> None:
        node = self.root
        for char in word:
            if char not in node.children:
                node.children[char] = Node()
            node = node.children[char]
            node.count += 1
        node.is_end = True
    
    # 탐색하는 함수
    def search(self, word: str) -> bool:
        node = self.root
        for i, char in enumerate(word):
            node = node.children[char]
            # 1개면 return
            if node.count == 1:
                return i + 1
        return len(word)
    
def solution(words):
    answer = 0
    trie = Trie()
    for word in words:
        trie.insert(word)
    for word in words:
        answer += trie.search(word)
    return answer