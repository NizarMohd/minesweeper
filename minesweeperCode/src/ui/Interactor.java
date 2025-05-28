package ui;

import Constants.Constants;
import Driver.GameMap;
import Util.Slot;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Interactor {

    public Scanner scanner;

    public Interactor() {
        scanner = new Scanner(System.in);
    }

    public void printGreetings() {
        System.out.println("Welcome to Minesweeper!");
    }


    public int getMapSize() {
        System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        String input = scanner.nextLine();
        while (!InputValidator.isValidSize(input)) {
            System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public int getMineSize(int mapSize) {
        System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares):");
        String input = scanner.nextLine();
        while (!InputValidator.isValidMineFieldSize(input, mapSize)) {
            System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares) :");
            input = scanner.nextLine();
        };
        return Integer.parseInt(input);
    }

    public Slot getSlotToReveal(int mapSize) {
        System.out.println("Select a square to reveal (e.g. A1): ");
        String input = scanner.nextLine();
        while (!InputValidator.isValidSlot(input, mapSize)) {
            System.out.println("Select a square to reveal (e.g. A1): ");
            input = scanner.nextLine();
        };
        return new Slot(input);
    }
    
    public void publishCompletion() throws IOException {
        System.out.println("Congratulations, you have won the game!\n" +
                "Press any key to play again...");
        System.in.read();
    }

    public void publishFailure() throws IOException {
        System.out.println("Oh no, you detonated a mine! Game over.\n" +
                "Press any key to play again...");
        System.in.read();
    }

    private String rowName(int index){
        StringBuilder rowName = new StringBuilder();
        while (index > 0) {

            int remainder = index % 26;
            if (remainder == 0) {
                remainder = 26;
                index -= 1;
            }

            rowName.append(GameMap.getAlphabetMap().get(remainder));
            index /= 26;
        }
        return rowName.reverse().toString();
    }

    private LinkedList<LinkedList<String>> addHeadersAndColumn() {

        LinkedList<LinkedList<String>> result = new LinkedList<>();
        LinkedList<String> header = new LinkedList<>();
        header.add(Constants.SPACE);
        for(int headerCount = 1; headerCount <= GameMap.getMapSize(); headerCount++ ) {
            header.add(Integer.toString(headerCount));
        }
        result.add(header);

        int index = 1;
        LinkedList<LinkedList< Integer>> revealMap = GameMap.getRevealMap();
        for (int i = 0 ; i < GameMap.getMapSize(); i++) {
            LinkedList<String> resRow = new LinkedList<>();
            resRow.add(rowName(index));
            index++;
            LinkedList<Integer> row = revealMap.get(i);
            for (int j = 0; j < GameMap.getMapSize(); j++ ) {
                if(row.get(j).equals(-1)) {
                    resRow.add(Constants.UNDERSCORE);
                } else {
                    resRow.add(Integer.toString(row.get(j)));
                }
            }
            result.add(resRow);
        }

        return result;

    }

    public String formatCell(String value) {
        int remainingSpace = Constants.MAX_CELL_SPACE - value.length();
        StringBuilder valueBuilder = new StringBuilder(value);
        while(remainingSpace > 0) {
            remainingSpace--;
            valueBuilder.append(Constants.SPACE);
        }
        return valueBuilder.toString();
    }
    public void printMap() {
        System.out.println("Here is your minefield:");
        LinkedList<LinkedList<String>> res = addHeadersAndColumn();
        for (LinkedList<String> row : res) {
            for (String value : row) {
                System.out.print(formatCell(value));
            }
            System.out.println(); // Move to the next line after each row
        }
    }
    public void close() {
        this.scanner.close();
    }
}
