# 십자뒤집기
"""
인접한 칸 색깔 변경
모든 칸이 흰색인 보드 -> 입력 보드로 변경하기 위한 최소 클릭 횟수는?
bfs로 완전탐색
"""
import sys
from copy import deepcopy
from collections import deque
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

def bfs(start, white_board):
    q = deque([(start, 0)])
    visited = set()
    visited.add(tuple(map(tuple, start)))
    while q:
        board, cnt = q.popleft()

        # white_board와 같을 경우 return
        if board == white_board:
            return cnt
        
        # bfs
        for i in range(3):
            for j in range(3):
                copied_board = deepcopy(board)

                # 뒤집기
                copied_board[i][j] = 1 - copied_board[i][j]
                for di, dj in [[1, 0], [0, 1], [-1, 0], [0, -1]]:
                    ni, nj = i+di, j+dj
                    if 0<=ni<3 and 0<=nj<3:
                        copied_board[ni][nj] = 1 - copied_board[ni][nj]
                
                # 방문하지 않았을 경우, 추가
                if tuple(map(tuple, copied_board)) not in visited:
                    visited.add(tuple(map(tuple, copied_board)))
                    q.append((copied_board, cnt+1))


tc = int(input())
for _ in range(tc):
    # 문자열 -> 숫자로 치환
    result = [input().strip() for _ in range(3)]
    goal = [[0]*3 for _ in range(3)]
    for i in range(3):
        for j in range(3):
            if result[i][j] == '*':
                goal[i][j] = 1
            else:
                goal[i][j] = 0
    
    white_board = [[0]*3 for _ in range(3)]

    answer = bfs(goal, white_board)
    print(answer)