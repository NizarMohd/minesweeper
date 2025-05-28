package Driver;

import Constants.Constants;
import Util.Slot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GameMap {
    private static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    private LinkedList<LinkedList<Integer>> map;
    private static LinkedList<LinkedList<Integer>> revealMap;

    private static int mapSize ;
    private static int mineSize;

    private static HashMap<Integer, Character> alphabetMap;

    public void createMap(int size) {
        map = new LinkedList<>();
        revealMap = new LinkedList<>();
        for(int i = 0; i< size; i++) {
            map.add(new LinkedList<>());
            revealMap.add(new LinkedList<>());
            for(int j = 0; j< size; j++) {
                map.get(i).add(-1);
                revealMap.get(i).add(-1);
            }
        }

        alphabetMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            char letter = (char) (Constants.CHAR + i);
            alphabetMap.put( i + 1, letter);
        }
    }

    private String rowName(int index){
        StringBuilder rowName = new StringBuilder();
        while (index > 0) {

            int remainder = index % 26;
            if (remainder == 0) {
                remainder = 26;
                index -= 1;
            }

            rowName.append(alphabetMap.get(remainder));
            index /= 26;
        }
        return rowName.toString();
    }
    private LinkedList<LinkedList<String>> addHeadersAndColumn() {


        LinkedList<LinkedList<String>> result = new LinkedList<>();
        LinkedList<String> header = new LinkedList<>();
        header.add(Constants.SPACE);
        for(int headerCount = 1; headerCount <= revealMap.getFirst().size(); headerCount++ ) {
            header.add(Integer.toString(headerCount));
        }
        result.add(header);

        int index = 1;
        for (LinkedList<Integer> row : revealMap) {
            LinkedList<String> resRow = new LinkedList<>();
            resRow.add(rowName(index));
            index++;

            for (int value : row) {
                if(value == -1) {
                    resRow.add(Constants.UNDERSCORE);
                } else {
                    resRow.add(Integer.toString(value));
                }
            }
            result.add(resRow);
        }

        return result;

    }
    public void printMap() {
        System.out.println("Here is your minefield:");
        LinkedList<LinkedList<String>> res = addHeadersAndColumn();
        for (LinkedList<String> row : res) {
            for (String value : row) {
                System.out.print(value + Constants.SPACE);
            }
            System.out.println(); // Move to the next line after each row
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
    public int findAdjacent(Slot slot) {

        int rowIndex = slot.getFirst();
        int colIndex = slot.getSecond();
        if (map.get(rowIndex).get(colIndex) == 1) {
            return -1;
        }
        if (revealMap.get(rowIndex).get(colIndex) != -1)  {
            return 1;
        }
        int sum = 0;
        for (int d = 0; d < 8; d++) {
            int newX = rowIndex + dx[d];
            int newY = colIndex + dy[d];

            if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) {
                sum = map.get(newX).get(newY) == -1 ? sum : sum + 1;

            }
        }
        revealMap.get(rowIndex).set(colIndex, sum);
        LinkedList<Slot> neighbour = new LinkedList<>();
        if (sum == 0) {
            for (int d = 0; d < 8; d++) {
                int newX = rowIndex + dx[d];
                int newY = colIndex + dy[d];
                if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) {
                        Slot newSlot = new Slot(newX, newY);
                        findAdjacent(newSlot);
                    }
            }

        }
        GameState.incrementAttempt();
        return 1;
    }


    public static int getMapSize() {
        return mapSize;
    }

    public static int getMineSize() {
        return mineSize;
    }

    public static LinkedList<LinkedList<Integer>> getRevealMap() {
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
