import java.util.Stack;

public class MancalaModel {
    private int[][] pits;
    private int[] mancalas;
    private int currentPlayer;
    private Stack<int[][]> undoStack;

    public MancalaModel() {
        pits = new int[2][6];
        mancalas = new int[2];
        currentPlayer = 0; // 0 for Player A, 1 for Player B
        undoStack = new Stack<>();
        initializeBoard(3); // Default 3 stones per pit
    }

    public void initializeBoard(int stones) {
        if (stones < 3 || stones > 4) return;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                pits[i][j] = stones;
            }
        }
        mancalas[0] = 0;
        mancalas[1] = 0;
    }

    public int[][] getPits() {
        return pits;
    }

    public int[] getMancalas() {
        return mancalas;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void makeMove(int pitIndex) {
        // Save current state for undo
        undoStack.push(copyBoard());

        int stones = pits[currentPlayer][pitIndex];
        pits[currentPlayer][pitIndex] = 0;

        int row = currentPlayer;
        int col = pitIndex;

        while (stones > 0) {
            col++;
            if (col == 6) {
                if (row == currentPlayer) {
                    mancalas[row]++;
                    stones--;
                    if (stones == 0) {
                        return; // Free turn if last stone is in Mancala
                    }
                }
                row = 1 - row; // Switch row
                col = -1; // Reset column to -1, will become 0 in next iteration
            } else {
                pits[row][col]++;
                stones--;
            }
        }

        currentPlayer = 1 - currentPlayer; // Switch player if no free turn
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            int[][] previousState = undoStack.pop();
            for (int i = 0; i < 2; i++) {
                System.arraycopy(previousState[i], 0, pits[i], 0, 6);
            }
        }
    }

    private int[][] copyBoard() {
        int[][] copy = new int[2][6];
        for (int i = 0; i < 2; i++) {
            System.arraycopy(pits[i], 0, copy[i], 0, 6);
        }
        return copy;
    }
}
