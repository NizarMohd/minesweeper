package Driver;

import Util.Slot;
import ui.Interactor;

import java.io.IOException;
import java.util.Objects;

import static Constants.Constants.*;
import Exception.GameBreakException;

public class GameState {


    public static String state;
    public static GameMap map;

    public static int attempts;
    public static Interactor interactor;

    public static void initialise() {
        state = START;
    }
    public static void start() throws GameBreakException {
        interactor = new Interactor();
        interactor.printGreetings();
        map = new GameMap();
        map.setMapSize(interactor.getMapSize());
        map.createMap(GameMap.getMapSize());
        map.setMineSize(interactor.getMineSize(GameMap.getMapSize()));
        map.setMineField();
        attempts = 0;
        state = IN_PROGRESS;
    }

    public static void in_progress() throws GameBreakException {
        interactor.printMap();
        if (attempts >= GameMap.getMapSize() * GameMap.getMapSize() - GameMap.getMineSize()) {
            state = COMPLETED;
            return;
        }
        Slot slot = interactor.getSlotToReveal(GameMap.getMapSize());
        int result = map.findAdjacent(slot);
        if (result == -1) {
            state = FAIL;
        }
    }

    public static void completed() throws IOException {
        interactor.publishCompletion();
        state = START;
    }

    public static void failed() throws IOException {
        interactor.publishFailure();
        state = START;
    }

    public static void close() {
        interactor.close();
    }

    public static void incrementAttempt() {
        attempts++;
    }
}
