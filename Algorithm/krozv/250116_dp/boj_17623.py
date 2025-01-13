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
for _ in range(t):
    n = int(input())
    dp = [""] * (n+1)

    dp[1], dp[2], dp[3] = "12", "34", "56"

    for i in range(1, n+1):
        # i+1 범위 체크
        if (i+1) <= n:
            if dp[i+1]:
                dp[i+1] = str(min(int("12"+dp[i]), int(dp[i]+"12"), int(dp[i+1])))
            else:
                dp[i+1] = str(min(int("12"+dp[i]), int(dp[i]+"12")))
        if (i+2) <= n:
            if dp[i+2]:
                dp[i+2] = str(min(int("34"+dp[i]), int(dp[i]+"34"), int(dp[i+2])))
            else:
                dp[i+2] = str(min(int("34"+dp[i]), int(dp[i]+"34")))
        if (i+3) <= n:
            if dp[i+3]:
                dp[i+3] = str(min(int("56"+dp[i]), int(dp[i]+"56"), int(dp[i+3])))
            else:
                dp[i+3] = str(min(int("56"+dp[i]), int(dp[i]+"56")))
        if (i*2) <= n:
            if dp[i*2]:
                dp[i*2] = str(min(int("1"+dp[i]+"2"), int(dp[i*2])))
            else:
                dp[i*2] = str(int("1"+dp[i]+"2"))
        if (i*3) <= n:
            if dp[i*3]:
                dp[i*3] = str(min(int("3"+dp[i]+"4"), int(dp[i*3])))
            else:
                dp[i*3] = str(int("3"+dp[i]+"4"))
        if (i*5) <= n:
            if dp[i*5]:
                dp[i*5] = str(min(int("5"+dp[i]+"6"), int(dp[i*5])))
            else:
                dp[i*5] = str(int("5"+dp[i]+"6"))
    for elem in dp[n]:
        print(info[elem], end="")
    print()