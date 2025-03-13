# boj_32354. 덱 조작과 쿼리

import sys
from collections import deque
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

# 초기 덱 설정
deq = deque()

query = int(input())
for i in range(query):
    q = input().strip('\n').split(' ')
    if len(q) > 1:
        command, number = q
    else:
        command = q[0]
    