# 31498. 장난을 잘 치는 토카 양
"""
[조건]
A: 토카 시작 위치
A+C: 돌돌이 시작 위치
B: 토카가 한 번 이동할 때 이동하는 칸 수, 한 번 이동한 뒤 K만큼 감소
D: 돌돌이가 한 번 이동할 때 이동하는 칸수
- 같은 위치, 동시에 집 도착, 돌돌이가 토카를 앞지르면 -1 출력
[참고](https://jengdeuk.tistory.com/176)

"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

A, B = map(int, input().split())
C, D = map(int, input().split())
K = int(input())

start = 1
end = (A+C)//D + 1

times = (start + end) // 2

result = -1

while start <= end:
    # times, toka, doldol 계산
    times = (start + end) // 2
    if (B - times * K) < 0:
        end = times - 1
        continue
    toka = A - times * B + (times * (times - 1) / 2) * K
    doldol = (A+C) - times * D
    # 같은 위치이거나 앞지른 경우
    if doldol <= toka or (doldol <= 0 and toka <= 0):
        result = -1
        end = times - 1
    # toka 집 도착
    elif toka <= 0:
        result = times
        end = times - 1
    else:
        start = times + 1

print(result)