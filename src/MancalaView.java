import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Karla Nguyen
 * class sview for Mancala game for the players to be able to view the game
 */
public class MancalaView extends JFrame {
    private MancalaModel model;
    private BoardStyle boardStyle;
    JPanel boardPanel;
    JButton undoButton;

    JButton firstStyleButton;
    JButton secondStyleButton;
    JPanel stylePanel;

    int width = 800;
    int height = 500;
    String message = "";
    String freeTurn;

    //number of stones in mancala A
    JLabel mancalaALabel;

    //number of stones in mancala B
    JLabel mancalaBLabel;

    //status message of the game
    JLabel statusMessage;

    //number of free turns left
    JLabel freeTurnMessage;

    /**
     * Mancala view constructor that boots up the GUI and detects the pits locations
     * @param model
     */
    public MancalaView(MancalaModel model) {
        this.model = model;
        setupGUI();
        // mouse listener to detect clicks on pits
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handlePitClick(e.getX(), e.getY());
            }
        });
    }

    private void handlePitClick(int x, int y) {
        int pitIndex = getClickedPit(x,y);
        if(pitIndex != -1) {
            System.out.println("pitIndex: " + pitIndex);
        }
    }

    int getClickedPit(int x, int y) {
        int pitWidth = 80;
        int pitHeight = 80;
        int gap = 30;

        // Player B's pits (Top Row: B6 to B1)
        for (int i = 0; i < 6; i++) {
            int pitX = 80 + i * (pitWidth + gap);
            int pitY = 50;
            if (x >= pitX && x <= pitX + pitWidth && y >= pitY && y <= pitY + pitHeight) {
                return 12 - i; // Indices 12 (B6) down to 7 (B1)
            }
        }

        // Player A's pits (Bottom Row: A1 to A6)
        for (int i = 0; i < 6; i++) {
            int pitX = 80 + i * (pitWidth + gap);
            int pitY = getHeight() - 110;
            if (x >= pitX && x <= pitX + pitWidth && y >= pitY && y <= pitY + pitHeight) {
                return i; // Indices 0 (A1) to 5 (A6)
            }
        }

        return -1; // If no pit was clicked
    }


    private void setupGUI() {
        //set up screen
        setTitle("Mancala Game");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //set up panels for user to select board style
        stylePanel = new JPanel();
        firstStyleButton = new JButton("First Style");
        secondStyleButton = new JButton("Second Style");

        stylePanel.add(firstStyleButton);
        stylePanel.add(secondStyleButton);
        add(stylePanel, BorderLayout.NORTH);
        stylePanel.setVisible(true);
        setVisible(true);
        //Pit buttons and setup
        boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(boardStyle != null) {
                    boardStyle.drawBoard(MancalaView.this, g);
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(800, 500));
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        undoButton = new JButton("Undos Left: 3");
        mancalaALabel = new JLabel(" Mancala A: 0");
        mancalaBLabel = new JLabel(" Mancala B: 0");
        freeTurnMessage = new JLabel();
        statusMessage = new JLabel();
        Font font = statusMessage.getFont();
        Font boldFont = new Font(font.getFontName(), font.BOLD, font.getSize());
        statusMessage.setFont(boldFont);
        statusMessage.setFont(font);
        message= "Current Player Turn: Player A ";
        freeTurn = "Free Turns: 0 | ";
        setFreeTurnMessage(freeTurn);
        setStatusMessage(message);
        controlPanel.add(statusMessage);
        controlPanel.add(freeTurnMessage);
        controlPanel.add(mancalaALabel);
        controlPanel.add(mancalaBLabel);

        controlPanel.add(undoButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * getter method to get the model
     * @return
     */
    public MancalaModel getModel()
    {
        return model;
    }

    /**
     * getter method to get the window height
     * @return
     */
    public int getWindowHeight()
    {
        return height;
    }

    /**
     * getter method to get the window width
     * @return
     */
    public int getWindowWidth()
    {
        return width;
    }

    /**
     * method to update board view when player makes a move
     */
    public void updateBoard(){
        mancalaALabel.setText("Mancala A: " + model.getMancalaA() + " | ");
        mancalaBLabel.setText("Mancala B: " + model.getMancalaB() + " | ");
        if(model.isFreeTurn())
        {
            setFreeTurnMessage("Free Turns: 1 | ");

        }else {
            setFreeTurnMessage("Free Turns: 0 | ");
        }
        if (model.isGameOver())
        {
            return;
        }
        if(model.isPlayerA())
        {
            message = "Current Player Turn: Player A ";
        }else if(!(model.isPlayerA())) {
            message = "Current Player Turn: Player B ";
        }
        statusMessage.setText(message + " | ");
        boardPanel.repaint();
    }

    /**
     * method to set the board style
     * @param boardStyle
     */
    public void setBoardStyle(BoardStyle boardStyle) {
        this.boardStyle = boardStyle;
    }

    /**
     * method to set the turn status message on the panel
     * @param message
     */
    public void setStatusMessage(String message)
    {
        this.message = message;
        statusMessage.setText(message);
        updateBoard();
    }

    /**
     * method to display how many free turns a player has on the panel
     * @param message
     */
    public void setFreeTurnMessage(String message)
    {
        freeTurn = message;
        freeTurnMessage.setText(freeTurn);
    }

    /**
     * method to display when the game is over
     */
    public void handleGameOver() {
        model.collectRemainingStones();
        updateBoard();
        int mancalaA = model.getMancalaA();
        int mancalaB = model.getMancalaB();
        String winner = mancalaA > mancalaB ? "A" : mancalaA < mancalaB ? "B" : "No one! It's a tie!";
        setStatusMessage("Game Over! Player " + winner + " wins!");
    }

    public void updateUndoButton() {
        if (model.isPlayerA()) {
            undoButton.setText("Undos Left: " + model.getPlayerBUndoCount());
        } else {
            undoButton.setText("Undos Left: " + model.getPlayerAUndoCount());
        }
    }

}