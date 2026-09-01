class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startR = -1, startC = -1;
        List<int[]> litterList = new ArrayList<>();
        
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litterList.add(new int[]{r, c});
                }
            }
        }
        
        int numLitter = litterList.size();
        int targetMask = (1 << numLitter) - 1;
        
        int[][][] maxEnergy = new int[m][n][1 << numLitter];
        for (int[][] row : maxEnergy) {
            for (int[] col : row) {
                Arrays.fill(col, -1);
            }
        }
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, 0, energy, 0});
        maxEnergy[startR][startC][0] = energy;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int mask = curr[2];
            int e = curr[3];
            int steps = curr[4];
            
            if (mask == targetMask) {
                return steps;
            }
            
            if (e == 0) continue;
            
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                char tile = classroom[nr].charAt(nc);
                if (tile == 'X') continue;
                
                int nextEnergy = e - 1;
                int nextMask = mask;
                
                if (tile == 'L') {
                    for (int i = 0; i < numLitter; i++) {
                        if (litterList.get(i)[0] == nr && litterList.get(i)[1] == nc) {
                            nextMask |= (1 << i);
                            break;
                        }
                    }
                }
                
                if (tile == 'R') {
                    nextEnergy = energy;
                }
                
                if (nextEnergy > maxEnergy[nr][nc][nextMask]) {
                    maxEnergy[nr][nc][nextMask] = nextEnergy;
                    queue.offer(new int[]{nr, nc, nextMask, nextEnergy, steps + 1});
                }
            }
        }
        
        return -1;
    }
}