import java.util.Stack;

public class MancalaModel {
    private int[] pits;
    private int mancalaA;
    private int mancalaB;
    private int numStonesPerPit;
    // Tracks whose turn it is
    private boolean isPlayerA;
    private int freeTurn = 0;

    private int playerAUndoCount = 3;
    private int playerBUndoCount = 3;
    private boolean lastPlayerToMoveIsPlayerA;


    // Stack to track undo functionality

    private Stack<GameState> history;

    // Stores the style of the board
    private BoardStyle currentStyle;


    public MancalaModel(int numStonesPerPit) {
        this.numStonesPerPit = numStonesPerPit;
        // 14 pits, including Mancalas at indices 6 and 13
        pits = new int[14];
        mancalaA = 0;
        mancalaB = 0;
        isPlayerA = true;
        history = new Stack<>();
        initializeBoard();
    }


    private void initializeBoard() {
        for (int i = 0; i < pits.length; i++) {
            if (i == 6 || i == 13) {
                pits[i] = 0;
            } else {
                pits[i] = numStonesPerPit;
            }
        }
    }

    public int getMancalaA() {
        return pits[6];
    }

    public int getMancalaB() {
        return pits[13];
    }

    public int[] getPits() {
        return pits.clone();
    }

    public boolean isPlayerA() {
        return isPlayerA;
    }

    public void togglePlayer() {
        if (lastPlayerToMoveIsPlayerA != isPlayerA) {
            // Reset undo count only when the turn switches
            if (isPlayerA) {
                playerAUndoCount = 3;
            } else {
                playerBUndoCount = 3;
            }
        }
        isPlayerA = !isPlayerA;
        freeTurn = 0;
    }


    public void saveState() {
        history.push(new GameState(pits.clone(), isPlayerA, freeTurn));
    }
    public boolean lastPlayerToMoveIsPlayerA() {
        return lastPlayerToMoveIsPlayerA;
    }



    public void undo() {
        if (!history.isEmpty()) {
            GameState prevState = history.pop();
            this.pits = prevState.getPits();
            this.isPlayerA = prevState.isPlayerA();
            this.freeTurn = prevState.getFreeTurn();
            if (isPlayerA) {
                playerAUndoCount--;
            } else {
                playerBUndoCount--;
            }
        }
    }



    public void moveStones(int pitIndex) {
        // Validate pit selection based on the current player
        if ((isPlayerA && (pitIndex < 0 || pitIndex > 5)) || (!isPlayerA && (pitIndex < 7 || pitIndex > 12))) {
            throw new IllegalArgumentException("Invalid pit selection!");
        }

        // Save the current state for undo functionality
        saveState();

        int stones = pits[pitIndex];
        pits[pitIndex] = 0;
        int currentIndex = pitIndex;

        if(isPlayerA)
        {
            while (stones > 0) {
                currentIndex = (currentIndex + 1) % 14;
                System.out.println("Pit Index : "+ currentIndex);

                // Skip opponent's Mancala
                if (currentIndex == 13) continue;
                // Add stones to pits or Mancala
                pits[currentIndex]++;
                System.out.println(pits[currentIndex]);
                stones--;
                System.out.println(stones);

            }
        }else {
            while(stones > 0)
            {
               currentIndex = (currentIndex + 1 ) % 14 ;
               System.out.println("Pit Index : "+ currentIndex);
               if(currentIndex == 6) continue;
               pits[currentIndex]++;
                System.out.println(pits[currentIndex]);
               stones--;
               System.out.println(stones);
            }
        }


        // Handle special rules
        handleSpecialRules(currentIndex);

        // Track last player to move
        lastPlayerToMoveIsPlayerA = isPlayerA;
    }


    private void handleSpecialRules(int lastIndex) {
        // If last stone lands in player's own empty pit, capture stones
        if (isPlayerA && lastIndex >= 0 && lastIndex < 6 && pits[lastIndex] == 1) {
            int oppositePitIndex = 12 - lastIndex; // Opposite pit
            pits[6] += pits[oppositePitIndex] + pits[lastIndex];
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        } else if (!isPlayerA && lastIndex >= 7 && lastIndex < 13 && pits[lastIndex] == 1) {
            int oppositePitIndex = 12 - lastIndex;
            pits[13] += pits[oppositePitIndex] + pits[lastIndex];
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        }

        // If last stone lands in own Mancala, player gets another turn
        if ((isPlayerA && lastIndex == 6) || (!isPlayerA && lastIndex == 13)) {
            freeTurn = 1;
            return;
        } else {
            freeTurn = 0;
        }

        // Toggle turn to the other player
        togglePlayer();
    }

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

    public boolean isFreeTurn()
    {
        if (freeTurn >= 1)
        {
            return true;
        }else{
            return false;
        }
    }

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

    private static class GameState {
        private final int[] pits;
        private final boolean isPlayerA;
        private final int freeTurn;

        public GameState(int[] pits, boolean isPlayerA, int freeTurn) {
            this.pits = pits;
            this.isPlayerA = isPlayerA;
            this.freeTurn = freeTurn;
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
    }


    public void setBoardStyle(BoardStyle boardStyle)
    {
        currentStyle = boardStyle;
    }


    public int getPlayerAUndoCount() {
        return playerAUndoCount;
    }

    public int getPlayerBUndoCount() {
        return playerBUndoCount;
    }


    public void resetCounters() {
        freeTurn = 0;
        if (isPlayerA) {
            playerAUndoCount = 3;
        } else {
            playerBUndoCount = 3;
        }
    }


}