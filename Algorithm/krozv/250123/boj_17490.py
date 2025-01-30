# 17490. 일감호에 다리 놓기
"""
[조건]
N개의 강의동, K개의 돌
N, M, K
돌의 개수 St

인접하는 강의동은 집합으로
해당 집합에서 최소 돌 개수의 합 <= K
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

N, M, K = map(int, input().split())
St = [0] + list(map(int, input().split())) # t번째 강의동에서 필요한 돌 개수
print(St)
adj = [[0]*(N+1) for _ in range(N+1)]

for i in range(1, N+1):
    if 0<i-1<N+1: adj[i][i-1] = 1
    if 0<i+1<N+1:adj[i][i+1] = 1

for _ in range(M):
    a, b = map(int, input().split())
    adj[a][b] = 0
    adj[b][a] = 0

def visit(start, node):
    for next, value in enumerate(adj[node]):
        # 다음 노드가 방문가능하고, 출발지와 다를 경우
        print(node, next)
        if value and start != node and not visited[next]:
            visited[next] = 1e8
            visited[start] = min(visited[start], St[next])
            adj[start][next] = 1
            visit(node, next)

# 방문 가능 여부 표시
visited = [0] * (N+1)

for i in range(1, N+1):
    for node, value in enumerate(adj[i]):
        if value and not visited[node]:
            visited[node] = St[i]
            visit(i, node)


print(adj, visited)

