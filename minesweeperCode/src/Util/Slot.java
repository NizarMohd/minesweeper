package Util;

public class Slot {

    private final Integer first;
    private final Integer second;

    public Slot(String slot) {
        int charLength = 0;
        while(charLength < slot.length() && Character.isLetter(slot.charAt(charLength))){
                charLength += 1 ;
        }
        this.first = getIndexFromLabel(slot.substring(0, charLength)) - 1;
        this.second = Integer.parseInt(slot.substring(charLength)) -1;
    }

    public Slot(int first, int second) {

        this.first = first;
        this.second = second;
    }

    public Integer getFirst() {
        return first;
    }

    public Integer getSecond() {
        return second;
    }


    public static int getIndexFromLabel(String label) {
        int result = 0;

        for (int i = 0; i < label.length(); i++) {
            char c = label.charAt(i);
            int value = (c - 'A') + 1;
            result = result * 26 + value;
        }
        return result;
    }
}