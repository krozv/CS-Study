# 2042. 구간 합 구하기
import sys
from math import ceil, log
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

N, M, K = map(int, input().split())

arr = []
for _ in range(N):
    arr.append(int(input()))

# tree 생성
n = 2 ** ceil(log(N, 2))
tree = [0] * (n << 1)

def init(node, start, end):
    """
    세그먼트 트리 초기화
    node: 현재 노드의 번호
    start, end: arr의 인덱스, 구간 합 범위
    """
    # 리프 노드일 경우, 트리 값 설정
    if start == end:
        tree[node] = arr[start]
        return
    
    mid = (start + end) // 2
    init(2 * node, start, mid)
    init(2 * node + 1, mid + 1, end)
    tree[node] = tree[2 * node] + tree[2 * node + 1]

init(1, 0, N-1)

def query(node, start, end, left, right):
    if right < start or end < left:
        return 0

    if left <= start and end <= right:
        return tree[node]
    
    mid = (start + end) // 2
    q1 = query(2*node, start, mid, left, right)
    q2 = query(2*node+1, mid+1, end, left, right)
    return q1 + q2

def update(node, start, end, idx, diff):
    # Idx를 포함하지 않으면 Return
    if idx < start or end < idx:
        return
    
    tree[node] += diff

    if start != end:
        mid = (start + end) // 2
        update(2 * node, start, mid, idx, diff)
        update(2 * node + 1, mid + 1, end, idx, diff)

for _ in range(M+K):
    a, b, c = map(int, input().split())
    b -= 1
    # 수 변경
    print(tree)
    if a == 1:
        diff = c - arr[b]
        arr[b] = c
        update(1, 0, N-1, b, diff)
    # 합 출력
    elif a == 2:
        c -= 1
        print(query(1, 0, N-1, b, c))
    