# 17623. 괄호
"""
[조건]

"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline
info = {
    "1": "(",
    "2": ")",
    "3": "{",
    "4": "}",
    "5": "[",
    "6": "]",
}
t = int(input())

dp = [""] * (1000+1)

dp[1], dp[2], dp[3] = "12", "34", "56"

for i in range(1, 1001):
    for j in range(1, i//2+1):
        val1, val2 = dp[j] + dp[i-j], dp[i-j] + dp[j]
        val = str(min(int(val1), int(val2)))
        if not dp[i] or int(dp[i]) > int(val):
            dp[i] = val

    if (i*2) <= 1000:
        val = "1"+dp[i]+"2"
        if not dp[i*2] or int(dp[i*2]) > int(val):
            dp[i*2] = val
    if (i*3) <= 1000:
        val = "3"+dp[i]+"4"
        if not dp[i*3] or int(dp[i*3]) > int(val):
            dp[i*3] = val
    if (i*5) <= 1000:
        val = "5"+dp[i]+"6"
        if not dp[i*5] or int(dp[i*5]) > int(val):
            dp[i*5] = val

for _ in range(t):
    n = int(input())
    result = "".join(info[char] for char in dp[n])
    print(result)