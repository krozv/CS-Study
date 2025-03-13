# 23760. 까다로운 아이들과 선물 상자
"""

"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

N, M = map(int, input().split()) # N: 선물 상자의 수, M: 아이들 수
c = list(map(int, input().split())) # 선물 상자에 들어있는 선물의 개수
w = list(map(int, input().split())) # 아이들이 원하는 선물의 개수
b = list(map(int, input().split())) # 아이들의 배려심

def make_tree():
    
    pass

def find_gift(b):
    """
    b번째로 많은 선물의 개수를 Return
    """
    pass

for idx, bi in enumerate(b):
    """
    bi: i번째 아이의 배려심
    wi: i번째 아이가 원하는 선물의 개수
    """
    wi = w[idx]
    ci = find_gift(bi)


