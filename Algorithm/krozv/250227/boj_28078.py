# 28078. 중력 큐
"""
쿼리 개수 Q
b: 공
w: 가림막
"""
import sys
from collections import deque
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

# 초기 상태 선언 및 큐 설정
state = 0 # 큐 상태
queue = deque()
wall_cnt = 0 # 가림막 개수
ball_cnt = 0 # 공 개수

# 중력 영향 확인 함수
def check_gravity():
    global state, queue, wall_cnt, ball_cnt
    
    if not wall_cnt: 
        queue.clear()
        ball_cnt = 0
        return
    if state == 1:
        while queue and queue[-1] != "w":
            queue.pop()
            ball_cnt -= 1
            
    if state == 3:
        while queue and queue[0] != "w":
            queue.popleft()
            ball_cnt -= 1

# 명령어 처리 함수
def handle_command(command, object=None):
    global state, queue, wall_cnt, ball_cnt
    
    if command == "push": 
        queue.append(object)
        if object == "w":
            wall_cnt += 1
        else:
            ball_cnt += 1

    if command == "count": 
        print(ball_cnt if object == "b" else wall_cnt)

    if command == "pop": 
        if queue:
            removed_obj = queue.popleft()
            if removed_obj == "w":
                wall_cnt -= 1
            else:
                ball_cnt -= 1

    if command == "rotate": 
        if object == "r": 
            state = (state + 3) % 4
        else: 
            state = (state + 1) % 4
    # 상태 확인 후 중력 영향
    if state == 1 or state == 3: check_gravity()
    # print(command, state, queue)

query = int(input())

# 쿼리 처리
for _ in range(query):
    q = input().strip('\n').split(" ")
    if len(q) > 1:
        command, object = q
        handle_command(command, object)
    else:
        command = q[0]
        handle_command(command)
