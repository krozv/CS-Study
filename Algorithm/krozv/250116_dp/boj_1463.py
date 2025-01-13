# 1463. 1로 만들기
"""
[조건]
정수 X에 사용할 수 있는 연산 3가지
1. X가 3으로 나누어 떨어지면, 3으로 나눈다
2. X가 2로 나누어 떨어지면, 2로 나눈다
3. 1을 뺀다

점화식
dp[i] = dp[i-1]+1 # 1을 뺄 경우
if i % 2 == 0: dp[i] = min(dp[i], dp[i/2]+1) # 2로 나누어 떨어질 경우
if i % 3 == 0: dp[i] = min(dp[i]. dp[i/3]+1) # 3으로 나누어 떨어질 경우
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

n = int(input())
dp = [0] * (n+1)
dp[1] = 0

i = 2
while i <= n:
    dp[i] = dp[i-1] + 1
    if i % 2 == 0: dp[i] = min(dp[i], dp[i//2]+1)
    if i % 3 == 0: dp[i] = min(dp[i], dp[i//3]+1)
    i += 1
print(dp[n])