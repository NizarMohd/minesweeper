package Driver;

import Constants.Constants;
import Util.Slot;

import java.util.*;

public class GameMap {
    private static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    private LinkedList<LinkedList< Integer>> map;
    private static LinkedList<LinkedList< Integer>> revealMap;

    private static int mapSize ;
    private static int mineSize;

    private static HashMap<Integer, Character> alphabetMap;

    public void createMap(int size) {
        map = new LinkedList<>();
        revealMap = new LinkedList<>();
        for(int i = 0; i< size; i++) {
            map.add(new LinkedList<>() );
            revealMap.add(new LinkedList<>());
            for(int j = 0; j< size; j++) {
                map.get(i).add(-1);
                revealMap.get(i).add( -1);
            }
        }

        alphabetMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            char letter = (char) (Constants.CHAR + i);
            alphabetMap.put( i + 1, letter);
        }
    }

    public void setMineField() {
        for (int i =0; i< mineSize; i++) {
            Random rand  = new Random();
            int mineRow = rand.nextInt(mapSize -1);
            int mineCol =  rand.nextInt(mapSize -1);
            if (map.get(mineRow).get(mineCol) == -1 ) {
               map.get(mineRow).set(mineCol, 1);
            }
        }
    }

    private boolean isSlotAMine(int rowIndex,  int colIndex) {
        return map.get(rowIndex).get(colIndex) == 1;
    }

    public int findAdjacent(Slot slot) {
        if (isSlotAMine(slot.getFirst(), slot.getSecond())) {
            return -1;
        }
        Queue<Slot> queue = new LinkedList<>();
        queue.offer(slot);
        boolean[][] visited = new boolean[mapSize][mapSize];
        visited[slot.getFirst()][ slot.getSecond()] = true;
        GameState.incrementAttempt();
        while(!queue.isEmpty()){
            Slot node = queue.poll();
            int rowIndex = node.getFirst();
            int colIndex = node.getSecond();

            int sum = 0;
            for (int d = 0; d < 8; d++) {
                int newX = rowIndex + dx[d];
                int newY = colIndex + dy[d];
                if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) {
                    sum = map.get(newX).get(newY) == -1 ? sum : sum + 1;

                }
            }
            revealMap.get(rowIndex).set(colIndex, sum);

            if (sum == 0) {
                for (int d = 0; d < 8; d++) {
                    int newX = rowIndex + dx[d];
                    int newY = colIndex + dy[d];
                    if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) {
                        if (!visited[newX][newY])  {
                            Slot newSlot = new Slot(newX, newY);
                            queue.offer(newSlot);
                            visited[newX][newY] = true;
                            GameState.incrementAttempt();
                        }
                    }
                }
            }
        }
        return 1;
    }


    public static int getMapSize() {
        return mapSize;
    }

    public static int getMineSize() {
        return mineSize;
    }

    public static LinkedList<LinkedList< Integer>> getRevealMap() {
        return revealMap;
    }

    public static HashMap<Integer, Character> getAlphabetMap() {
        return alphabetMap;
    }

    public  void setMapSize(int mapSize) {
        GameMap.mapSize = mapSize;
    }
    public  void setMineSize(int mineSize) {
        GameMap.mineSize = mineSize;
    }

}
