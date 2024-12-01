import java.util.Stack;

public class MancalaModel {
    private int[] pits;
    private int mancalaA;
    private int mancalaB;
    private int numStonesPerPit;
    // Tracks whose turn it is
    private boolean isPlayerA;
    private int freeTurn = 0;

    private int undoCount = 0;
    // Stack to track undo functionality
    private Stack<GameState> history;

    // Stores the style of the board
    private BoardStyle currentStyle;


    public MancalaModel(int numStonesPerPit) {
        this.numStonesPerPit = numStonesPerPit;
        // 12 pits, 6 for each player
        pits = new int[12];
        mancalaA = 0;
        mancalaB = 0;
        isPlayerA = true;
        history = new Stack<>();
        initializeBoard();
    }


    private void initializeBoard() {
        for (int i = 0; i < pits.length; i++) {
            pits[i] = numStonesPerPit;
        }
    }

    public int getMancalaA() {
        return mancalaA;
    }

    public int getMancalaB() {
        return mancalaB;
    }

    public int[] getPits() {
        return pits.clone();
    }

    public boolean isPlayerA() {
        return isPlayerA;
    }

    public void togglePlayer() {
        isPlayerA = !isPlayerA;
        resetCounters();
    }

    public void saveState() {
        history.push(new GameState(pits.clone(), mancalaA, mancalaB, isPlayerA));
    }

    public void undo() {
        if (!history.isEmpty()) {
            GameState prevState = history.pop();
            this.pits = prevState.getPits();
            this.mancalaA = prevState.getMancalaA();
            this.mancalaB = prevState.getMancalaB();
            this.isPlayerA = prevState.isPlayerA();
        }
    }

    public void moveStones(int pitIndex) {
        if ((isPlayerA && pitIndex >= 6) || (!isPlayerA && pitIndex < 6)) {
            throw new IllegalArgumentException("Invalid pit selection!");
        }

        // Save current state for undo functionality
        saveState();

        int stones = pits[pitIndex];
        pits[pitIndex] = 0;
        int currentIndex = pitIndex;

        while (stones > 0) {
            currentIndex = (currentIndex + 1) % 14;

            // Skip opponent's Mancala
            if (isPlayerA && currentIndex == 13) continue;
            if (!isPlayerA && currentIndex == 6) continue;

            // Add stones to pits or Mancala
            if (currentIndex == 6) {
                mancalaA++;
            } else if (currentIndex == 13) {
                mancalaB++;
            } else {
                pits[currentIndex]++;
            }

            stones--;
        }

        // Handle special rules
        handleSpecialRules(currentIndex);
    }

    private void handleSpecialRules(int lastIndex) {
        // If last stone lands in player's own empty pit, capture stones
        if (isPlayerA && lastIndex >= 0 && lastIndex < 6 && pits[lastIndex] == 1) {
            int oppositePitIndex = 11 - lastIndex; // Opposite pit
            mancalaA += pits[oppositePitIndex] + 1;
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        } else if (!isPlayerA && lastIndex >= 6 && lastIndex < 12 && pits[lastIndex] == 1) {
            // Opposite pit
            int oppositePitIndex = 11 - lastIndex;
            mancalaB += pits[oppositePitIndex] + 1;
            pits[oppositePitIndex] = 0;
            pits[lastIndex] = 0;
        }

        // If last stone lands in own Mancala, player gets another turn
        if ((isPlayerA && lastIndex == 6) || (!isPlayerA && lastIndex == 13)) {
            freeTurn = 1;
            return;
        }else{
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
            if (pits[i + 6] > 0) playerBPitsEmpty = false;
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
            mancalaA += pits[i];
            pits[i] = 0;
        }

        for (int i = 6; i < 12; i++) {
            mancalaB += pits[i];
            pits[i] = 0;
        }
    }

    private static class GameState {
        private final int[] pits;
        private final int mancalaA;
        private final int mancalaB;
        private final boolean isPlayerA;

        public GameState(int[] pits, int mancalaA, int mancalaB, boolean isPlayerA) {
            this.pits = pits;
            this.mancalaA = mancalaA;
            this.mancalaB = mancalaB;
            this.isPlayerA = isPlayerA;
        }

        public int[] getPits() {
            return pits.clone();
        }


        public int getMancalaA() {
            return mancalaA;
        }

        public int getMancalaB() {
            return mancalaB;
        }

        public boolean isPlayerA() {
            return isPlayerA;
        }

    }

    public void setBoardStyle(BoardStyle boardStyle)
    {
        currentStyle = boardStyle;
    }

    public int getUndoCount(){
        return undoCount;
    }

    public void resetCounters()
    {
        freeTurn = 0;
        undoCount = 3;
    }

}