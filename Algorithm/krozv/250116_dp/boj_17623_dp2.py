# 17623. 괄호
"""
dp[i]: i를 나타내는 올바른 괄호 문자열 중 dmap(X)가 최소값인 것
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline
info = {
    "(": "1",
    ")": "2",
    "{": "3",
    "}": "4",
    "[": "5",
    "]": "6",
}
t = int(input())

dp = [""] * 1001

dp[1], dp[2], dp[3] = "()", "{}", "[]"

def dmap(x):
    string = ""
    for elem in x:
        string += info[elem]
    return int(string)

def find_min_str(x):
    correct_val = []
    if x%2 == 0: correct_val.append("("+ dp[x//2] +")")
    if x%3 == 0: correct_val.append("{"+ dp[x//3] +"}")
    if x%5 == 0: correct_val.append("["+ dp[x//5] +"]")
    
    if correct_val: correct_val = [min(correct_val, key=dmap)]

    for i in range(1, x//2+1):
        val1 = dp[i] + dp[x-i]
        val2 = dp[x-i] + dp[i]
        if not correct_val or (len(correct_val[0]) >= len(val1)): 
            correct_val.append(val1)
            correct_val.append(val2)
    return min(correct_val, key=dmap)

for i in range(4, 1001):
    dp[i] = find_min_str(i)

for _ in range(t):
    n = int(input())
    print(dp[n])