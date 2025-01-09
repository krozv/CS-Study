// 숨바꼭질 3
const fs = require('fs');
const input = require("fs").readFileSync("./input.txt").toString().trim().split(" ");
// const input = fs.readFileSync("/dev/stdin").toString().trim().split(" ");

function solution() {
    // bfs
    // input: 자료형 유의해야함
    const [n, k] = input.map(Number);

    // bfs
    bfs(n, k);
    
}

function bfs(startNode, endNode) {
    let visited = new Set(); // 집합으로 처리할 것
    let needVisit = [[startNode, 0]];

    while (needVisit.length !== 0){
        const [node, time] = needVisit.shift();
        
        if (node === endNode){
            console.log(time);
            break;
        }

        if (!visited.has(node)) {
            visited.add(node);

            if (node * 2 <= 100000 && !visited[node * 2])
                needVisit.unshift([node * 2, time]); // 우선순위 가장 앞으로
            if (node + 1 <= 100000 && !visited[node + 1]) 
                needVisit.push([node + 1, time + 1]);
            if (node - 1 >= 0 && !visited[node - 1]) 
                needVisit.push([node - 1, time + 1]);
        }
    }
    return visited;
}

solution();
module.exports = solution;