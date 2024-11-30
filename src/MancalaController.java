import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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

        view.boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pitIndex = view.getClickedPit(e.getX(), e.getY());
                if(pitIndex != -1) {
                    try{
                        model.moveStones(pitIndex);
                        view.updateBoard();
                    } catch (IllegalArgumentException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        view.undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.undo();
                view.undoButton.setVisible(true);
            }
        });

        view.firstStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardStyle firstStyle = new FirstStyle();
                model.setBoardStyle(firstStyle);
                view.setBoardStyle(firstStyle); // Update the view's boardStyle
                view.stylePanel.setVisible(false);
                view.boardPanel.setVisible(true);
                view.updateBoard();
            }
        });

        view.secondStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setBoardStyle(new SecondStyle());
                view.setBoardStyle(new SecondStyle());
                view.stylePanel.setVisible(false);
                view.boardPanel.setVisible(true);
                view.updateBoard();
            }
        });

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

    /**
     *
     *     public void updateView(){
     *         //checks
     *         if(model.isPlayerA())
     *         {
     *
     *         }else if (!(model.isPlayerA()))
     *         {
     *
     *         }
     *
     *     }
     */









}