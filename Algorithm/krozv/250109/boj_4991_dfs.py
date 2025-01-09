# 4991.로봇 청소기
"""
가구 놓여진 칸으로 이동 불가
인접한 칸으로 이동 가능
같은 칸 여러 번 방문 가능
더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값
"""
import sys
from collections import deque
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

while True:
    w, h = map(int, input().split())
    if w == 0 and h == 0:
        break
    arr = [list(input().strip('\n')) for _ in range(h)]
    visited = [[0] * w for _ in range(h)]
    
    def is_impossible(visited):
        return False
    
    def dfs(start_node, dirty_node_cnt, move):
        """
        방문할 수 없는 더러운 칸이 존재할 경우에는 -1 출력 -> 같은 칸 여러 번 방문 가능한데 어떻게 해당 값을 찾지?
        더러운 칸의 개수는 10개를 넘지 않으므로, x 칸의 최대 방문 횟수가 10 초과일 경우 break
        visited 배열 필요
        start_node: (i, j)로 이루어진 tuple
        dirty_node_cnt: 총 더러운 칸 개수. 0개일 경우 break
        """
        si, sj = start_node
        visited[si][sj] += 1

        # 더러운 칸이 없을 경우
        if not dirty_node_cnt:
            return move
        
        # 청소가 불가능할 경우
        if move > (w * h) and is_impossible(visited):
            return -1
            
        # 청소 시작
        for di, dj in [[1, 0], [0, 1], [-1, 0], [0, -1]]:
            ni, nj = si+di, sj+dj
            if 0<=ni<h and 0<=nj<w and arr[ni][nj] != "x":
                visited[ni][nj] += 1
                # 더러운 칸일 경우 청소 후 카운트 감소
                # cleaned = False
                if arr[ni][nj] == "*":
                    arr[ni][nj] = "."
                    dirty_node_cnt -= 1
                    # cleaned = True
                return dfs((ni, nj), dirty_node_cnt, move+1)
        #         if cleaned:
        #             arr[ni][nj] = "*"
        #             dirty_node_cnt += 1
        # return -1
        
    start_node = tuple()
    dirty_node_cnt = 0
    for i in range(h):
        for j in range(w):
            if arr[i][j] == "o":
                start_node = (i, j)
                arr[i][j] = "."
            if arr[i][j] == "*":
                dirty_node_cnt += 1
    answer = dfs(start_node, dirty_node_cnt, 0)

    print(answer)