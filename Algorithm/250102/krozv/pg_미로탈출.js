// pg_미로탈출

function solution(maps) {
    // 시작 지점 저장
    let startNode;
    let leverNode;
    const r = maps.length; 
    const c = maps[0].length; 
    for (let i=0; i<r; i++){
        for (let j=0; j<c; j++){
            if (maps[i][j] === 'S'){
                startNode = [i, j];
            }
            if (maps[i][j] === 'L'){
                leverNode = [i, j];
            }
        }
    }
    // console.log(startNode)
    function bfs(startNode, endValue) {
        let visited = Array.from({ length: r }, () => Array(c).fill(false));
        let needVisit = [];
        const directions = [[1, 0], [0, 1], [-1, 0], [0, -1]];
        needVisit.push([startNode, 0]);
        visited[startNode[0]][startNode[1]] = true;
        
        while (needVisit.length !== 0) {
            const [node, time] = needVisit.shift();
            
            const [x, y] = node;
            if (maps[x][y] === endValue){
                return time;
            }
            for (const [dx, dy] of directions) {
                const nx = x + dx;
                const ny = y + dy;

                if (
                    nx >= 0 && nx < r &&
                    ny >= 0 && ny < c &&
                    !visited[nx][ny] &&
                    maps[nx][ny] !== 'X'
                ) {
                    visited[nx][ny] = true;
                    needVisit.push([[nx, ny], time + 1]);
                }
            }
        }
        return -1;
    }
    const arrivedLeverTime = bfs(startNode, 'L')
    const totalTime = bfs(leverNode, 'E')
    
    let answer = -1;
    if (arrivedLeverTime < 0 || totalTime < 0){
    } else {
        answer = arrivedLeverTime + totalTime;
    }
    return answer
}