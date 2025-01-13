# 14916. 거스름돈
"""
[조건]
거스름돈 n인 경우 최소 동전의 개수
동전은 2원과 5원
거슬러줄 수 없으면 -1
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

n = int(input())
dp = [1e8] * (n+1)
dp[0], dp[1] = 0, -1

i = 2
while i <= n:
    if (i-2)>=0 and dp[i-2] != -1: dp[i] = min(dp[i-2]+1, dp[i])
    if (i-5)>=0 and dp[i-5] != -1: dp[i] = min(dp[i-5]+1, dp[i])
    if dp[i] == 1e8: dp[i] = -1
    i += 1
print(dp[n])

