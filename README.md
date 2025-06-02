# minesweeper


## Classes
# 1. `Constants`

## Package
`Constants`

## Class: `Constants`

The `Constants` class is a utility class that holds globally accessible static final constants used throughout the application. It is designed to improve code maintainability and prevent the use of hard-coded values.

### Fields Summary

| Name            | Type    | Value         | Description |
|-----------------|---------|---------------|-------------|
| `SPACE`         | String  | `" "`         | Represents a space character. |
| `CHAR`          | char    | `'A'`         | Starting character of the alphabet, typically used for indexing. |
| `MAX_ALPHABET`  | int     | `26`          | Maximum number of letters in the English alphabet. |
| `UNDERSCORE`    | String  | `"_"`         | Represents an underscore character, often used in identifiers or formatting. |
| `START`         | String  | `"start"`     | Represents a "start" status or state. |
| `IN_PROGRESS`   | String  | `"in_progress"` | Denotes an ongoing process or state. |
| `MIN_ALPHABET`  | int     | `1`           | Minimum index for alphabet-related operations. |
| `COMPLETED`     | String  | `"completed"` | Indicates a completed state. |
| `END`           | String  | `"end"`       | Represents an "end" state or status. |
| `FAIL`          | String  | `"FAIL"`      | Denotes a failure state. |
| `MAX_CELL_SPACE`| int     | `5`           | Maximum spacing or size for a cell, possibly in a grid or table. |
| `RESTART`       | String  | `"restart"`   | Indicates a restart command or status. |

### Purpose

This class centralizes key constants to ensure consistent usage and simplify maintenance when values need to be updated or reused across multiple parts of the codebase.

# 2. `GameMap`

## Package
`Driver`

## Class: `GameMap`

The `GameMap` class handles the core logic for creating, managing, and interacting with a minefield-style game grid. It is responsible for initializing the game board, placing mines, revealing tiles, and determining adjacent mines using BFS (Breadth-First Search).

### Key Responsibilities

- **Map Initialization**: Creates the primary game map and a reveal map with default values.
- **Mine Placement**: Randomly distributes mines across the grid, avoiding duplicates.
- **Alphabet Mapping**: Maps numbers 1–26 to characters A–Z for column labeling.
- **Mine Detection**: Checks whether a given tile contains a mine.
- **Adjacent Tile Scanning**: Implements a BFS algorithm to reveal adjacent non-mine tiles and calculate the number of nearby mines.
- **Game State Interaction**: Calls `GameState.incrementAttempt()` to track user actions.

### Fields

| Name         | Type                                    | Description |
|--------------|------------------------------------------|-------------|
| `dx`, `dy`   | `int[]`                                  | Direction vectors used for checking all 8 surrounding tiles. |
| `map`        | `LinkedList<LinkedList<Integer>>`        | Stores the mine layout of the game board. |
| `revealMap`  | `LinkedList<LinkedList<Integer>>` (static) | Tracks the revealed tiles and their values. |
| `mapSize`    | `int` (static)                           | The size of the square game grid. |
| `mineSize`   | `int` (static)                           | Total number of mines to be placed on the map. |
| `alphabetMap`| `HashMap<Integer, Character>` (static)   | Maps numerical indices to alphabet letters A–Z. |

### Key Methods

| Method | Description |
|--------|-------------|
| `createMap(int size)` | Initializes the map and revealMap with default values (-1) and sets up the alphabet mapping. |
| `setMineField()` | Randomly places mines in the grid without overlapping. |
| `isSlotAMine(int row, int col)` | Returns `true` if the slot contains a mine. |
| `findAdjacent(Slot slot)` | Reveals tiles around a given slot using BFS, counting adjacent mines. Returns `-1` if a mine is clicked, `1` otherwise. |
| `getMapSize()`, `getMineSize()` | Accessor methods for map and mine sizes. |
| `getRevealMap()` | Returns the current state of revealed tiles. |
| `getAlphabetMap()` | Returns the mapping of numbers to letters. |
| `setMapSize(int)`, `setMineSize(int)` | Setter methods for game configuration. |

### Summary

The `GameMap` class encapsulates all map-related functionality in a Minesweeper-style game. It supports dynamic board setup, mine generation, and interactive gameplay mechanics through BFS-based area revealing.

# 3. `GameState`

## Package
`Driver`

## Class: `GameState`

The `GameState` class manages the overall lifecycle and transitions of the game. It serves as a controller that orchestrates interactions between the user interface (`Interactor`), the game logic (`GameMap`), and game flow states such as starting, progressing, winning, or losing.

### Key Responsibilities

- **State Management**: Tracks and updates the current state of the game (`START`, `IN_PROGRESS`, `COMPLETED`, `FAIL`).
- **Game Initialization**: Sets up the game board, mine count, and user interface.
- **Game Progression**: Handles tile-revealing logic and transitions the game based on success or failure.
- **Game Completion/Failure Handling**: Notifies the user of the outcome and resets the game state.
- **Interaction Handling**: Coordinates with the `Interactor` to manage input/output operations.
- **Attempt Tracking**: Keeps count of how many slots the player has revealed.

### Fields

| Name          | Type            | Description |
|---------------|------------------|-------------|
| `state`       | `String`         | Represents the current state of the game (`START`, `IN_PROGRESS`, etc.). |
| `map`         | `GameMap`        | The active game map instance. |
| `attempts`    | `int`            | Number of tiles the player has revealed. |
| `interactor`  | `Interactor`     | Handles user input/output operations. |

### Key Methods

| Method | Description |
|--------|-------------|
| `initialise()` | Sets the game state to `START`. |
| `start()` | Prepares the game environment: greets the user, initializes the map, sets mine count, and starts the game. |
| `in_progress()` | Executes a single game turn, checks if the player has won or revealed a mine. |
| `completed()` | Called when the player wins the game; triggers completion message and resets state. |
| `failed()` | Called when the player reveals a mine; triggers failure message and resets state. |
| `close()` | Closes the interactor to clean up resources. |
| `incrementAttempt()` | Increases the `attempts` counter after each successful tile reveal. |

### Summary

The `GameState` class acts as the game engine's central controller, coordinating between the map logic and user interactions. It handles state transitions, user inputs, and game results, ensuring a structured flow from start to end of the game session.

# 4. `GameBreakException`

## Package
`Exception`

## Class: `GameBreakException`

The `GameBreakException` class is a custom exception that extends Java's built-in `Exception` class. It serves as a specialized exception to represent interruptions or intentional breaks in the game flow.

### Purpose

This exception is used to signal critical events that require halting the game's normal execution, such as user-initiated exits or fatal input errors, without treating them as runtime failures.

### Characteristics

- **Custom Exception**: Inherits from `Exception`, making it a checked exception that must be declared or handled.
- **No Additional Logic**: The class does not define additional fields or methods, serving purely as a marker exception for control flow.

### Summary

`GameBreakException` provides a clean and specific way to handle game-breaking conditions separately from generic Java exceptions, allowing more precise control over game interruption scenarios.

# 5: `GameExitException`

## Package
`Exception`

## Class: `GameExitException`

The `GameExitException` class is a custom exception that extends `GameBreakException`, which itself extends Java's standard `Exception` class. It represents a specific type of game interruption: a user-initiated exit.

### Purpose

This exception is used to cleanly terminate the game when the user explicitly chooses to exit (e.g., by typing a command like "exit"). By separating it from other game-breaking events, the application can handle graceful exits more effectively.

# 6. `GameRestartException`

## Package
`Exception`

## Class: `GameRestartException`

The `GameRestartException` class is a custom checked exception that extends `GameBreakException`. It is used to represent a scenario where the game is intentionally restarted, likely by user command.

### Purpose

This exception provides a clear control-flow mechanism to restart the game from its initial state without treating the action as an error. It distinguishes a restart event from other interruptions like exit or failure.

# 7. `Slot`

## Package
`Util`

## Class: `Slot`

The `Slot` class represents a coordinate or position on the game board, using either numerical indices or user-friendly labels like `"A1"` or `"C3"`. It encapsulates both row and column indices and provides logic to parse and interpret user inputs.

### Key Responsibilities

- **Coordinate Parsing**: Converts alphanumeric input (e.g. `"B2"`, `"AA5"`) into zero-based numeric indices suitable for grid operations.
- **Coordinate Representation**: Stores a pair of integers (`first`, `second`) representing the row and column.
- **Utility Method**: Provides static method to convert alphabetic labels (e.g., `"A"`, `"AB"`) into numeric indices.

### Fields

| Name      | Type     | Description |
|-----------|----------|-------------|
| `first`   | `Integer`| Row index (zero-based). |
| `second`  | `Integer`| Column index (zero-based). |

### Constructors

| Constructor | Description |
|-------------|-------------|
| `Slot(String slot)` | Parses a string like `"C3"` or `"AB12"` into numeric row and column indices. |
| `Slot(int first, int second)` | Directly creates a `Slot` with specified numeric indices. |

### Methods

| Method | Description |
|--------|-------------|
| `getFirst()` | Returns the row index. |
| `getSecond()` | Returns the column index. |
| `getIndexFromLabel(String label)` | Static method that converts a column label (e.g., `"A"`, `"AA"`) into its corresponding 1-based index. |

### Summary

The `Slot` class abstracts a board position for the game, handling both user-friendly input parsing and internal numeric representation. It bridges the gap between string-based UI inputs and logic-driven game mechanics.

# 8. `InputValidator`

## Package
`ui`

## Class: `InputValidator`

The `InputValidator` class provides static utility methods for validating user inputs related to the game configuration and interactions. It ensures that input values for map size, mine count, and slot selections meet defined rules and constraints.

### Key Responsibilities

- **Type Validation**: Checks if a string input is a non-negative integer.
- **Map Configuration Validation**: Validates map size and ensures the number of mines does not exceed 35% of the grid size.
- **Slot Validation**: Validates slot format, bounds, and visitation status before allowing further gameplay actions.

### Key Methods

| Method | Description |
|--------|-------------|
| `isInteger(String val)` | Checks whether the input string is a non-negative integer. |
| `isValidSize(String size)` | Wrapper method that delegates to `isInteger()` for validating map size. |
| `isValidMineFieldSize(String size, int mapSize)` | Ensures the mine count is a valid integer and no more than 35% of the total grid area. |
| `isValidSlot(String input, int mapSize)` | Parses the user-input slot (e.g. `"B2"`), checks bounds, and verifies it has not been revealed already. |
| `isSlotUnvisited(Slot slot)` | Checks if the given slot has not been previously revealed on the game map. |

### Summary

`InputValidator` acts as a gatekeeper for all critical user inputs in the game. It helps prevent invalid or disruptive values from affecting gameplay by enforcing rules and giving user feedback through console messages.


# Java Class Summary: `Interactor`

## Package
`ui`

## Class: `Interactor`

The `Interactor` class is responsible for all user interaction in the game, including taking inputs, validating them, displaying the minefield, and publishing game results. It serves as the main communication bridge between the user and the game logic.

### Key Responsibilities

- **User Input Handling**: Reads user input from the console, including commands and game values like grid size and tile selections.
- **Game Control Commands**: Detects special inputs like `"end"` (to exit) and `"restart"` (to restart), throwing the appropriate custom exceptions.
- **Input Validation**: Coordinates with `InputValidator` to ensure valid grid sizes, mine counts, and slot selections.
- **Game Map Display**: Formats and prints the current state of the game board to the console.
- **Game Outcome Display**: Notifies users of win/loss outcomes and prompts to play again.

### Fields

| Name     | Type     | Description |
|----------|----------|-------------|
| `scanner`| `Scanner`| Reads user input from the console. |

### Key Methods

| Method | Description |
|--------|-------------|
| `read()` | Reads and returns input; throws `GameExitException` or `GameRestartException` for special commands. |
| `printGreetings()` | Prints a welcome message at the start of the game. |
| `getMapSize()` | Prompts and validates input for the grid size. |
| `getMineSize(int mapSize)` | Prompts and validates input for the number of mines. |
| `getSlotToReveal(int mapSize)` | Prompts and validates the slot the user wants to reveal. |
| `publishCompletion()` | Prints a congratulatory message when the user wins. |
| `publishFailure()` | Prints a message when the user hits a mine. |
| `printMap()` | Formats and prints the current game board with headers and revealed values. |
| `close()` | Closes the input scanner. |

### Helper Methods

| Method | Description |
|--------|-------------|
| `rowName(int index)` | Converts a numeric index to an alphabetic row label (e.g., 1 → A, 27 → AA). |
| `addHeadersAndColumn()` | Prepares the formatted game board with row/column headers and revealed content. |
| `formatCell(String value)` | Pads each cell with spaces to ensure column alignment based on `MAX_CELL_SPACE`. |

### Summary

The `Interactor` class encapsulates all I/O and presentation logic, ensuring clean separation between game mechanics and user interaction. It guides the player through gameplay, validates inputs, handles restart/exit commands, and maintains a user-friendly display of the game state.




