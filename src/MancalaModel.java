/**
 * The MancalaModel class represents the logic for a Mancala game.
 * It manages the game board, player turns, and implements game rules
 * such as stone distribution, capturing, and undo functionality.
 *
 * Programmed by: Nathan Dinh & Andrian Than
 * Date: 2024-12-04
 */
import java.util.Stack;

public class MancalaModel {
    private int[] pits;
    private int numStonesPerPit;
    private boolean isPlayerA;
    private int freeTurn = 0;
    private int playerAUndoCount = 3;
    private int playerBUndoCount = 3;
    private boolean lastPlayerToMoveIsPlayerA;
    private Stack<GameState> history;
    private BoardStyle currentStyle;

    /**
     * Constructs a MancalaModel with the specified number of stones per pit.
     * @param numStonesPerPit The initial number of stones in each pit.
     */
    public MancalaModel(int numStonesPerPit) {
        this.numStonesPerPit = numStonesPerPit;
        pits = new int[14];
        isPlayerA = true;
        history = new Stack<>();
        initializeBoard();
    }

    /**
     * Initializes the game board with the specified number of stones per pit
     * and sets the Mancalas to empty.
     */
    private void initializeBoard() {
        for (int i = 0; i < pits.length; i++) {
            pits[i] = (i == 6 || i == 13) ? 0 : numStonesPerPit;
        }
    }

    /**
     * Gets the number of stones in Player A's Mancala.
     * @return The number of stones in Mancala A.
     */
    public int getMancalaA() {
        return pits[6];
    }

    /**
     * Gets the number of stones in Player B's Mancala.
     * @return The number of stones in Mancala B.
     */
    public int getMancalaB() {
        return pits[13];
    }

    /**
     * Gets a copy of the current state of the pits.
     * @return An array representing the pits.
     */
    public int[] getPits() {
        return pits.clone();
    }

    /**
     * Checks if it's Player A's turn.
     * @return True if it's Player A's turn, false otherwise.
     */
    public boolean isPlayerA() {
        return isPlayerA;
    }

    /**
     * Toggles the current player.
     */
    public void togglePlayer() {
        isPlayerA = !isPlayerA;
        freeTurn = 0;
    }

    /**
     * Resets the undo count for the opponent player.
     */
    private void resetOpponentUndoCount() {
        if (isPlayerA) {
            playerBUndoCount = 3;
        } else {
            playerAUndoCount = 3;
        }
    }

    /**
     * Prepares for a new move by resetting counters and saving the game state.
     */
    private void beginNewMove() {
        if (lastPlayerToMoveIsPlayerA != isPlayerA) {
            resetOpponentUndoCount();
        }
        saveState();
    }

    /**
     * Saves the current state of the game for undo functionality.
     */
    public void saveState() {
        history.push(new GameState(pits.clone(), isPlayerA, freeTurn, lastPlayerToMoveIsPlayerA));
    }

    /**
     * Checks if the last move was made by Player A.
     * @return True if the last move was made by Player A, false otherwise.
     */
    public boolean lastPlayerToMoveIsPlayerA() {
        return lastPlayerToMoveIsPlayerA;
    }

    /**
     * Undoes the last move by restoring the previous game state.
     */
    public void undo() {
        if (!history.isEmpty()) {
            GameState prevState = history.pop();
            pits = prevState.getPits();
            isPlayerA = prevState.isPlayerA();
            freeTurn = prevState.getFreeTurn();
            lastPlayerToMoveIsPlayerA = prevState.getLastPlayerToMoveIsPlayerA();

            if (isPlayerA) {
                playerAUndoCount--;
            } else {
                playerBUndoCount--;
            }
        }
    }

    /**
     * Executes a move by redistributing stones from the selected pit.
     * @param pitIndex The index of the pit to play.
     * @throws IllegalArgumentException If the selected pit is invalid.
     */
    public void moveStones(int pitIndex) {
        if ((isPlayerA && (pitIndex < 0 || pitIndex > 5)) || (!isPlayerA && (pitIndex < 7 || pitIndex > 12))) {
            throw new IllegalArgumentException("Invalid pit selection!");
        }

        beginNewMove();

        int stones = pits[pitIndex];
        pits[pitIndex] = 0;
        int currentIndex = pitIndex;

        while (stones > 0) {
            currentIndex = (currentIndex + 1) % 14;

            // Skip opponent's Mancala
            if (isPlayerA && currentIndex == 13) continue;
            if (!isPlayerA && currentIndex == 6) continue;

            pits[currentIndex]++;
            stones--;
        }

        lastPlayerToMoveIsPlayerA = isPlayerA;

        handleSpecialRules(currentIndex);
    }

    /**
     * Applies special rules such as capturing stones and granting free turns.
     * @param lastIndex The index of the last pit where a stone was placed.
     */
    private void handleSpecialRules(int lastIndex) {
        if (isPlayerA && lastIndex >= 0 && lastIndex < 6 && pits[lastIndex] == 1) {
            int oppositePitIndex = 12 - lastIndex;
            pits[6] += pits[oppositePitIndex] + pits[lastIndex];
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        } else if (!isPlayerA && lastIndex >= 7 && lastIndex < 13 && pits[lastIndex] == 1) {
            int oppositePitIndex = 12 - lastIndex;
            pits[13] += pits[oppositePitIndex] + pits[lastIndex];
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        }

        if ((isPlayerA && lastIndex == 6) || (!isPlayerA && lastIndex == 13)) {
            freeTurn = 1;
        } else {
            freeTurn = 0;
            togglePlayer();
        }
    }

    /**
     * Checks if the game is over.
     * @return True if all pits on one side are empty, false otherwise.
     */
    public boolean isGameOver() {
        boolean playerAPitsEmpty = true;
        boolean playerBPitsEmpty = true;

        for (int i = 0; i < 6; i++) {
            if (pits[i] > 0) playerAPitsEmpty = false;
        }
        for (int i = 7; i < 13; i++) {
            if (pits[i] > 0) playerBPitsEmpty = false;
        }

        return playerAPitsEmpty || playerBPitsEmpty;
    }

    /**
     * Checks if the current player has a free turn.
     * @return True if the player has a free turn, false otherwise.
     */
    public boolean isFreeTurn() {
        return freeTurn >= 1;
    }

    /**
     * Collects all remaining stones from the pits and places them in the Mancalas.
     */
    public void collectRemainingStones() {
        for (int i = 0; i < 6; i++) {
            pits[6] += pits[i];
            pits[i] = 0;
        }

        for (int i = 7; i < 13; i++) {
            pits[13] += pits[i];
            pits[i] = 0;
        }
    }

    /**
     * Sets the style of the board.
     * @param boardStyle The desired board style.
     */
    public void setBoardStyle(BoardStyle boardStyle) {
        currentStyle = boardStyle;
    }

    /**
     * Gets the remaining undo count for Player A.
     * @return The number of undos left for Player A.
     */
    public int getPlayerAUndoCount() {
        return playerAUndoCount;
    }

    /**
     * Gets the remaining undo count for Player B.
     * @return The number of undos left for Player B.
     */
    public int getPlayerBUndoCount() {
        return playerBUndoCount;
    }

    /**
     * Resets the undo counters and free turn state.
     */
    public void resetCounters() {
        freeTurn = 0;
        if (isPlayerA) {
            playerAUndoCount = 3;
        } else {
            playerBUndoCount = 3;
        }
    }

    /**
     * A nested class representing the state of the game for undo functionality.
     */
    private static class GameState {
        private final int[] pits;
        private final boolean isPlayerA;
        private final int freeTurn;
        private final boolean lastPlayerToMoveIsPlayerA;

        /**
         * Constructs a GameState with the specified parameters.
         * @param pits The current state of the pits.
         * @param isPlayerA The current player.
         * @param freeTurn The number of free turns available.
         * @param lastPlayerToMoveIsPlayerA Whether the last move was made by Player A.
         */
        public GameState(int[] pits, boolean isPlayerA, int freeTurn, boolean lastPlayerToMoveIsPlayerA) {
            this.pits = pits;
            this.isPlayerA = isPlayerA;
            this.freeTurn = freeTurn;
            this.lastPlayerToMoveIsPlayerA = lastPlayerToMoveIsPlayerA;
        }

        public int[] getPits() {
            return pits.clone();
        }

        public boolean isPlayerA() {
            return isPlayerA;
        }

        public int getFreeTurn() {
            return freeTurn;
        }

        public boolean getLastPlayerToMoveIsPlayerA() {
            return lastPlayerToMoveIsPlayerA;
        }
    }
}
