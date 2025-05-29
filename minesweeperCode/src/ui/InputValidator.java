package ui;

import Driver.GameMap;
import Util.Slot;

public class InputValidator {


    public static boolean isInteger(String val) {
        try {
            if (Integer.parseInt(val) >= 0) {
                return true;
            }
        } catch( Exception e ) {
            System.out.println("Wrong input");
        }
        return false;
    }
    public static boolean isValidSize(String size) {
        return isInteger(size);
    }

    public static boolean isValidMineFieldSize(String size, int mapSize) {
       if (isInteger(size)) {
           double proportion = Double.parseDouble(size)/ mapSize ;
           if (proportion <= 0.35 ) {
               return true;
           }
       }

       System.out.println("Ensure number of mines is within 35% ( <= " + (0.35 * mapSize) + ")"  );
       return false;
    }

    public static boolean isValidSlot(String input, int mapSize) {
        try {
            Slot slot = new Slot(input);
            boolean isRowValid = slot.getFirst() <= mapSize;
            boolean isColValid = slot.getSecond() <= mapSize;
            if(!isRowValid || !isColValid) {
                System.out.println("Slot doesnt exist!");
                return false;
            }
            return isSlotUnvisited(slot);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return false;
    }

    public static boolean isSlotUnvisited(Slot slot) {
        if (GameMap.getRevealMap().get(slot.getFirst()).get(slot.getSecond()) != -1) {
            System.out.println("Slot is already visited!");
            return false;
        }
        return true;
    }

}
