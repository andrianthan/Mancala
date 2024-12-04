import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The MancalaView class provides the graphical user interface (GUI) for the Mancala game.
 * It allows players to interact with the game by clicking pits, viewing the board, and tracking game status.
 * The class also handles displaying board styles and updates during gameplay.
 *
 * Programmed by: Karla Nguyen & Nathan Dinh
 * Date: 2024-12-04
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

    // Displays the number of stones in Mancala A
    JLabel mancalaALabel;

    // Displays the number of stones in Mancala B
    JLabel mancalaBLabel;

    // Status message of the game
    JLabel statusMessage;

    // Displays the number of free turns left
    JLabel freeTurnMessage;

    /**
     * Constructs the MancalaView and initializes the GUI.
     * Sets up listeners to detect pit clicks and user actions.
     *
     * @param model The MancalaModel instance managing the game logic.
     */
    public MancalaView(MancalaModel model) {
        this.model = model;
        setupGUI();
        // Mouse listener to detect clicks on pits
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handlePitClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * Handles pit clicks by identifying the clicked pit index.
     *
     * @param x The x-coordinate of the click.
     * @param y The y-coordinate of the click.
     */
    private void handlePitClick(int x, int y) {
        int pitIndex = getClickedPit(x, y);
        if (pitIndex != -1) {
            System.out.println("pitIndex: " + pitIndex);
        }
    }

    /**
     * Identifies which pit was clicked based on the coordinates of the click.
     *
     * @param x The x-coordinate of the click.
     * @param y The y-coordinate of the click.
     * @return The index of the clicked pit, or -1 if no pit was clicked.
     */
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

    /**
     * Sets up the GUI components, including panels, buttons, and labels.
     */
    private void setupGUI() {
        // Set up screen
        setTitle("Mancala Game");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up panels for board style selection
        stylePanel = new JPanel();
        firstStyleButton = new JButton("First Style");
        secondStyleButton = new JButton("Second Style");

        stylePanel.add(firstStyleButton);
        stylePanel.add(secondStyleButton);
        add(stylePanel, BorderLayout.NORTH);
        stylePanel.setVisible(true);
        setVisible(true);

        // Set up the board panel
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (boardStyle != null) {
                    boardStyle.drawBoard(MancalaView.this, g);
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(800, 500));
        add(boardPanel, BorderLayout.CENTER);

        // Set up the control panel
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
        message = "Current Player Turn: Player A ";
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
     * Gets the model associated with this view.
     *
     * @return The MancalaModel instance.
     */
    public MancalaModel getModel() {
        return model;
    }

    /**
     * Gets the height of the game window.
     *
     * @return The window height.
     */
    public int getWindowHeight() {
        return height;
    }

    /**
     * Gets the width of the game window.
     *
     * @return The window width.
     */
    public int getWindowWidth() {
        return width;
    }

    /**
     * Updates the board view after a move.
     */
    public void updateBoard() {
        mancalaALabel.setText("Mancala A: " + model.getMancalaA() + " | ");
        mancalaBLabel.setText("Mancala B: " + model.getMancalaB() + " | ");
        if (model.isFreeTurn()) {
            setFreeTurnMessage("Free Turns: 1 | ");
        } else {
            setFreeTurnMessage("Free Turns: 0 | ");
        }
        if (model.isGameOver()) {
            return;
        }
        if (model.isPlayerA()) {
            message = "Current Player Turn: Player A ";
        } else {
            message = "Current Player Turn: Player B ";
        }
        statusMessage.setText(message + " | ");
        boardPanel.repaint();
    }

    /**
     * Sets the board style for the game.
     *
     * @param boardStyle The BoardStyle instance to apply.
     */
    public void setBoardStyle(BoardStyle boardStyle) {
        this.boardStyle = boardStyle;
    }

    /**
     * Sets the status message displayed on the panel.
     *
     * @param message The status message to display.
     */
    public void setStatusMessage(String message) {
        this.message = message;
        statusMessage.setText(message);
        updateBoard();
    }

    /**
     * Sets the free turn message displayed on the panel.
     *
     * @param message The free turn message to display.
     */
    public void setFreeTurnMessage(String message) {
        freeTurn = message;
        freeTurnMessage.setText(freeTurn);
    }

    /**
     * Displays a game-over message and determines the winner.
     */
    public void handleGameOver() {
        model.collectRemainingStones();
        updateBoard();
        int mancalaA = model.getMancalaA();
        int mancalaB = model.getMancalaB();
        String winner = mancalaA > mancalaB ? "A" : mancalaA < mancalaB ? "B" : "No one! It's a tie!";
        setStatusMessage("Game Over! Player " + winner + " wins!");
    }

    /**
     * Updates the undo button text based on the current player and remaining undos.
     */
    public void updateUndoButton() {
        if (model.isPlayerA()) {
            undoButton.setText("Undos Left: " + model.getPlayerBUndoCount());
        } else {
            undoButton.setText("Undos Left: " + model.getPlayerAUndoCount());
        }
    }
}
