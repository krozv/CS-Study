/**
[조건]
- 벽으로 된 칸은 지나갈 수 없고, 통로로 된 칸으로만 이동할 수 있다.
- 통로들 중 출구와 레버가 한 개씩 존재한다.
- 출구는 레버를 당겨서만 열 수 있다.
- 레버를 당기지 않았더라도 출구가 있는 칸을 지날 수 있다.
- 출발 지점 -> 레버 칸으로 이동 -> 레버 당기기 -> 출구 칸으로 이동
- 시작 지점, 출구, 레버는 항상 다른 곳에 존재하며, 한 개씩만 존재한다.

[입력]
- map 정보 : 문자열로 이루어진 행 배열로 주어진다.

[출력]
- 미로를 탈출하는데 필요한 최소 시간 : 한 칸 이동하는데 1초가 걸린다.
*/
import java.io.*;
import java.util.*;

class Solution {
    static class Point {
        int row, col;
        
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
		public boolean equals(Object p) {
			if (p instanceof Point) {
				return this.row == ((Point)p).row && this.col == ((Point)p).col;
			}
			else {
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			return this.row + this.col;
		}
    }
    
    static class Node {
        Point point;
        int time;
        
        public Node(Point point, int time) {
            this.point = point;
            this.time = time;
        }
    }
    
    static final char PATH = 'O';
    static final char WALL = 'X';
    static final int[] DELTA_ROW = {-1,1,0,0};
    static final int[] DELTA_COL = {0,0,-1,1};
    
    static int mapRow, mapCol;
    static Point start, end, lever;
    
    public int solution(String[] maps) {
        int answer = -1;
        
        // map 배열을 통해 미로의  세로, 가로 길이를 구한다.
        mapRow = maps.length;
        mapCol = maps[0].length();
        
        // 시작, 출구, 레버 좌표를 찾는다.
        for (int row = 0; row < mapRow; row++) {
            for (int col = 0; col < mapCol; col++) {
                char value = maps[row].charAt(col);
                
                if (value == 'S') {
                    start = new Point(row, col);
                }
                else if (value == 'E') {
                    end = new Point(row, col);
                }
                else if (value == 'L') {
                    lever = new Point(row, col);
                }
            }
        }
        
        // 시작에서 레버까지의 최소 시간을 구한다.
        int startToLever = solve(start, lever, maps);
        
        // 레버까지 이동이 가능한 경우만 탐색
        if (startToLever != -1) {
            // 레버에서 출구까지의 최소 시간을 구한다.
            int leverToEnd = solve(lever, end, maps);
            
            if (leverToEnd != -1) {
                answer = startToLever + leverToEnd;
            }
        }
        
        return answer;
    }
    
    private int solve(Point startPos, Point endPos, String[] maps) {
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] isVisited = new boolean[mapRow][mapCol];
        
        // 출발 위치를 queue에 추가
        queue.offer(new Node(startPos, 0));
        isVisited[startPos.row][startPos.col] = true;
        
        // 빈 큐가 될 때까지 반복
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Point curPoint = node.point;
            int curTime = node.time;
            
            // 도착 위치인 경우, 현재 시간 반환하며 종료
            if (curPoint.equals(endPos)) {
                return curTime;
            }
            
            // 상하좌우로 이동 가능한 칸을 탐색한다.
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = curPoint.row + DELTA_ROW[dir];
                int nextCol = curPoint.col + DELTA_COL[dir];
                
                // 범위를 벗어나는 경우, 패스
                if (nextRow < 0 || nextCol < 0 || nextRow >= mapRow || nextCol >= mapCol) {
                    continue;
                }
                
                // 이동칸이 벽인 경우, 패스
                if (maps[nextRow].charAt(nextCol) == WALL) {
                    continue;
                }
                
                // 이미 방문한 경우, 패스
                if (isVisited[nextRow][nextCol]) {
                    continue;
                }
                
                isVisited[nextRow][nextCol] = true;
                queue.offer(new Node(new Point(nextRow, nextCol), curTime + 1));
            }
        }
        
        return -1;
    }
}