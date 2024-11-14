import java.awt.event.ActionListener;


public class MancalaController {
    private MancalaModel model;
    private MancalaView view;
    private int undoCount;
    private boolean freeTurn;



    public MancalaController(MancalaModel model, MancalaView view)
    {
        this.model = model;
        this.view = view;
        undoCount = 0;
        freeTurn = false;

    }

    /**
     * Initializes the display of the board with stores and pits.
     * There are 6 pits for each player, totaling 12 pits for both players.
     * There are 3 stones in each pit.
     * Stores are on opposing ends of the board and are initialized empty.
     */

    public void initializeGame(){

    }

    /**
     * Handles the actions that occurs when pits are clicked.
     * Includes: Free Turns, Captures of Stones, or Invalid Moves.
     * Checks if game is over based on current/opposing player
     * number of stones on board.
     * Updates view accordingly.
     */

    public void pitClicks(){

    }

    /**
     * Handles the sequence of actions that occur when the undo button is clicked.
     * If a player has remaining undos.
     */

    public void clickUndoButton(){

    }

    /**
     * Sets the style of the board based on the board style chosen.
     */
    public void styleSelection(){

    }

    /**
     *  Initializes the game over state for a specific player.
     * If a player has no more available stones in their pits,
     * then the opposing player wins.
     */

    public void gameOver(){

    }


    /**
     * Updates board display, stone counts, and current player.
     * Checks the status of the game (number of stones that each
     * player has).
     */
    public void updateView(){

    }








}
