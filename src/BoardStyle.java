import java.awt.*;

/**
 * The BoardStyle interface defines the skeleton for different board styles in the Mancala game.
 * Each board style implements the methods to handle board selection and to draw the board.
 * This interface supports the Strategy design pattern.
 *
 * @author Karla Nguyen & Andrian Than
 * Date: 2024-12-04
 */
public interface BoardStyle {

    /**
     * Method to handle the board selection process.
     * Each board style should implement its own confirmation or setup logic for selection.
     */
    void boardSelection();

    /**
     * Method to draw the Mancala game board.
     * Each board style should implement its own drawing logic for pits, Mancalas, and stones.
     *
     * @param view The MancalaView instance providing the game state and board dimensions.
     * @param g    The Graphics object used for rendering the board.
     */
    void drawBoard(MancalaView view, Graphics g);
}
