package input;

import Util.Slot;

import java.io.IOException;
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

    public void close() {
        this.scanner.close();
    }
}
