import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;
public class MancalaModel {
    private int[] pits;
    private int mancalaA, mancalaB;
    private int numStonesPerPit;

    public MancalaModel(int numStonesPerPit) {
        this.numStonesPerPit = numStonesPerPit;
        pits = new int[12];
        mancalaA = 0;
        mancalaB = 0;
        initializaBoard();
    }

    private void initializaBoard() {
        for (int i = 0; i < numStonesPerPit; i++) {
            pits[i] = numStonesPerPit;
        }
    }

    public int getMancalaA() {
        return mancalaA;
    }

    public int getMancalaB() {
        return mancalaB;
    }

    public void moveStones(int pitIndex, boolean isInTurn) {
        int stones = pits[pitIndex];
        pits[pitIndex] = 0;
        int currentIndex = pitIndex;
        while (stones > 0) {
            currentIndex = (currentIndex + 1) % pits.length;
            pits[currentIndex]++;
            stones--;
        }
    }
}
