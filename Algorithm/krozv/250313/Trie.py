# 트라이

class TrieNode:
    def __init__(self, char=None):
        self.char = char
        self.children = {}
        self.is_end_of_word = False
        print(self.char)
    
class Trie:
    def __init__(self):
        self.root = TrieNode()
    
    def insert(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                node.children[char] = TrieNode(char=char)
            node = node.children[char]
        node.is_end_of_word = True

if __name__ == "__main__":
    trie = Trie()
    trie.insert("can")
    trie.insert("car")
    trie.insert("cart")