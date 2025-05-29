import Driver.GameState;

import java.io.IOException;
import java.util.Objects;

import static Constants.Constants.*;
import Exception.GameBreakException;
import Exception.GameRestartException;
import Exception.GameExitException;

public class Main {

    public static void main(String[] args) throws IOException {        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.

        GameState.initialise();
        while(true) {
            try {
                if (Objects.equals(GameState.state, IN_PROGRESS)) {
                    GameState.in_progress();
                } else if (Objects.equals(GameState.state, START)) {
                    GameState.start();
                } else if (Objects.equals(GameState.state, COMPLETED)) {
                    GameState.completed();
                } else if (Objects.equals(GameState.state, FAIL)) {
                    GameState.failed();
                }
            } catch (GameBreakException e ) {
                if(e instanceof GameExitException) {
                    GameState.close();
                    return;
                }
                if (e instanceof GameRestartException) {
                    GameState.initialise();
                }
            }

        }

    }
}