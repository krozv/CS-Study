# 16906. 욱제어
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

N = int(input())
arr = list(map(int, input().split()))
arr.sort()

class Node(object):
    def __init__(self, data="", is_end=False):
        self.data = data # 문자열
        self.children = {} # 자식 노드
        self.is_end = is_end
        # print(f"{self.data} 노드임")

class Trie(object):
    def __init__(self):
        self.head = Node()
    
    # 새로운 단어 생성
    def insert(self, curr_node: Node, num: int):
        
        if curr_node.is_end or temp:
            return
        
        # 해당 길이의 단어가 있을 경우 출력
        if curr_node.data and len(curr_node.data) == num:
            if not temp:
                temp.append(curr_node.data)
                curr_node.is_end = True
            return
        
        # children이 있을 경우
        if curr_node.children:
            for idx in curr_node.children:
                if not curr_node.children[idx].is_end:
                    trie.insert(curr_node.children[idx], num)
        
        # children이 없을 경우
        else:
            for idx in ['0', '1']:
                curr_node.children[idx] = Node(data = curr_node.data + idx)
                trie.insert(curr_node.children[idx], num)

result = []
trie = Trie()

for length in arr:
   temp = []
   trie.insert(trie.head, length)
   result += temp

# 결과 출력
if len(result) == N: 
    print(1)
    print('\n'.join(result))
else: print(-1)