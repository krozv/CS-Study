# 1010. 다리놓기
"""
dp[i][j]: 서쪽의 i번째 사이트에서 동쪽의 j번째 사이트로 다리를 놓을 수 있는 경우의 수
dp[i][j] = dp[i-1][0 ~ j-1]의 합
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

t = int(input())
for _ in range(t):
    n, m = map(int, input().split())
    dp = [[0] * m for _ in range(n)]
    
    dp[0] = [1 for i in range(m)]

    for i in range(1, n):
        for j in range(m):
            dp[i][j] = sum(dp[i-1][:j])

    print(sum(dp[n-1]))
