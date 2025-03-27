import java.io.*;
import java.util.*;

public class Main {
    // 7가지 테트리스 블록 (기본 모양, 0도 상태)
    // 아래는 I, O, S, Z, J, L, T 모양을 예시로 정의
    static int[][][] baseBlocks = {
        // I 블록 (4줄)
        { {0,0}, {1,0}, {2,0}, {3,0} },
        // O 블록 (2x2)
        { {0,0}, {0,1}, {1,0}, {1,1} },
        // S 블록
        { {0,1}, {0,2}, {1,0}, {1,1} },
        // Z 블록
        { {0,0}, {0,1}, {1,1}, {1,2} },
        // J 블록
        { {0,0}, {1,0}, {2,0}, {2,1} },
        // L 블록
        { {0,1}, {1,1}, {2,1}, {2,0} },
        // T 블록
        { {0,0}, {0,1}, {0,2}, {1,1} }
    };
    
    // 이동 방향: 아래, 왼쪽, 오른쪽
    static int[] dy = {1, 0, 0};
    static int[] dx = {0, -1, 1};
    
    static boolean[][] board; // 입력에서 '1'이면 이미 채워진 칸
    static int answer;

    // BFS에서 상태: (y, x) = 블록의 기준 좌표 (회전/정규화된 블록 기준)
    static class State {
        int y, x;
        public State(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static void main(String[] args) throws IOException {
        board = new boolean[20][10];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 20; i++) {
            String line = br.readLine();
            for (int j = 0; j < 10; j++) {
                board[i][j] = (line.charAt(j) == '1');
            }
        }
        answer = 0;

        // 7가지 블록 각각에 대해
        for (int b = 0; b < 7; b++) {
            // baseBlocks[b]를 clone해서 시작
            int[][] block = cloneBlock(baseBlocks[b]);
            // 최대 4번 회전 (90도씩)
            for (int rot = 0; rot < 4; rot++) {
                // 일부 블록은 중복 회전 건너뛰기
                if (checkSameBlock(b, rot)) break;
                
                // 첫 회전이 아니면 회전 수행 후 정규화
                if (rot > 0) {
                    block = rotate90(block);
                    block = normalize(block); 
                }
                
                // spawn: 0행의 모든 열 (0~9)
                for (int startX = 0; startX < 10; startX++) {
                    if (!canPlace(0, startX, block)) continue;
                    
                    // BFS 준비
                    boolean[][] visited = new boolean[20][10];
                    visited[0][startX] = true;
                    Queue<State> queue = new LinkedList<>();
                    queue.add(new State(0, startX));
                    
                    while (!queue.isEmpty()) {
                        State cur = queue.poll();
                        boolean canMove = false;
                        // 3방향 시도
                        for (int i = 0; i < 3; i++) {
                            int ny = cur.y + dy[i];
                            int nx = cur.x + dx[i];
                            if (canPlace(ny, nx, block)) {
                                canMove = true;
                                if (!visited[ny][nx]) {
                                    visited[ny][nx] = true;
                                    queue.add(new State(ny, nx));
                                }
                            }
                        }
                        // 만약 아래로 이동할 수 없다면(즉, canPlace(cur.y+1, cur.x, block)==false),
                        // 이 상태를 착지 상태로 보고 줄 제거 수 계산
                        if (!canPlace(cur.y + 1, cur.x, block)) {
                            int lines = countClearedLines(cur.y, cur.x, block);
                            answer = Math.max(answer, lines);
                        }
                    } // end BFS
                } // end spawn for
            } // end rot for
        } // end block for

        System.out.println(answer);
    }

    // 블록을 90도 회전: (r,c) -> (c, -r), 그 뒤 정규화는 따로 수행
    static int[][] rotate90(int[][] block) {
        int[][] newBlock = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int r = block[i][0], c = block[i][1];
            // 90도 회전
            int nr = c;
            int nc = -r;
            newBlock[i][0] = nr;
            newBlock[i][1] = nc;
        }
        return newBlock;
    }
    
    // 회전 후 음수 좌표가 발생할 수 있으므로, 블록을 왼쪽/위쪽으로 옮겨
    // 모든 좌표가 0 이상이 되도록 정규화
    static int[][] normalize(int[][] block) {
        int minR = Integer.MAX_VALUE, minC = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            minR = Math.min(minR, block[i][0]);
            minC = Math.min(minC, block[i][1]);
        }
        // (minR, minC)가 (0,0)이 되도록 shift
        int[][] newBlock = new int[4][2];
        for (int i = 0; i < 4; i++) {
            newBlock[i][0] = block[i][0] - minR;
            newBlock[i][1] = block[i][1] - minC;
        }
        return newBlock;
    }

    // 블록을 복사
    static int[][] cloneBlock(int[][] block) {
        int[][] newBlock = new int[4][2];
        for (int i = 0; i < 4; i++) {
            newBlock[i][0] = block[i][0];
            newBlock[i][1] = block[i][1];
        }
        return newBlock;
    }
    
    // canPlace: (topY, leftX)를 기준으로 block의 각 셀이 보드 내에 있고, 고정 블록과 충돌 없으면 true
    static boolean canPlace(int topY, int leftX, int[][] block) {
        for (int i = 0; i < 4; i++) {
            int ny = topY + block[i][0];
            int nx = leftX + block[i][1];
            if (ny < 0 || ny >= 20 || nx < 0 || nx >= 10) return false;
            if (board[ny][nx]) return false;
        }
        return true;
    }
    
    // 블록을 (topY, leftX)에 놓았을 때, 블록이 놓인 행 중 완전히 채워진 행 수를 센다.
    static int countClearedLines(int topY, int leftX, int[][] block) {
        // 임시로 배치
        int minY = 19, maxY = 0;
        for (int i = 0; i < 4; i++) {
            int ny = topY + block[i][0];
            int nx = leftX + block[i][1];
            board[ny][nx] = true;
            minY = Math.min(minY, ny);
            maxY = Math.max(maxY, ny);
        }
        int count = 0;
    outer:
        for (int row = minY; row <= maxY; row++) {
            for (int col = 0; col < 10; col++) {
                if (!board[row][col]) {
                    continue outer;
                }
            }
            count++;
        }
        // 원복
        for (int i = 0; i < 4; i++) {
            int ny = topY + block[i][0];
            int nx = leftX + block[i][1];
            board[ny][nx] = false;
        }
        return count;
    }
    
    // 일부 블록(I, O 등)은 180도, 90도 회전해도 모양이 중복될 수 있으므로 건너뛰기
    static boolean checkSameBlock(int blockIdx, int rot) {
        // 예시:
        // I 블록(인덱스=0)은 2회전(180도) 시 같은 모양
        // O 블록(인덱스=1)은 1회전(90도) 시 같은 모양
        // 필요하다면 S/Z/T/L/J 등도 추가적으로 조건을 넣을 수 있음
        if (blockIdx == 0 && rot == 2) return true; // I블록 180도
        if (blockIdx == 1 && rot == 1) return true; // O블록 90도
        return false;
    }
}
