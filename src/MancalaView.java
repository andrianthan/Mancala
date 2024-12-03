import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        for(int i = 0; i < 6; i++){
            int pitX = 80 + i * (pitWidth + gap);
            int pitY = 50;
            if (x >= pitX && x <= pitX + pitWidth && y >= pitY && y <= pitY + pitHeight) {
                return i;
            }
        }

        for(int i = 0; i < 6; i++){
            int pitX = 80 + i * (pitWidth + gap);
            int pitY = getHeight() - 110;
            if(x>= pitX && x <= pitX + pitWidth && y >= pitY && y <= pitY + pitHeight) {
                return i+6;
            }
        }
        return -1;
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


    public MancalaModel getModel()
    {
        return model;
    }

    public int getWindowHeight()
    {
        return height;
    }

    public int getWindowWidth()
    {
        return width;
    }

    //update board view when player makes a move
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

    public void setBoardStyle(BoardStyle boardStyle) {
        this.boardStyle = boardStyle;
    }

    public void setStatusMessage(String message)
    {
        this.message = message;
        statusMessage.setText(message);
        updateBoard();
    }

    public void setFreeTurnMessage(String message)
    {
        freeTurn = message;
        freeTurnMessage.setText(freeTurn);
    }

    public void handleGameOver() {
        model.collectRemainingStones();
        updateBoard();
        int mancalaA = model.getMancalaA();
        int mancalaB = model.getMancalaB();
        String winner = mancalaA > mancalaB ? "A" : mancalaA < mancalaB ? "B" : "No one! It's a tie!";
        setStatusMessage("Game Over! Player " + winner + " wins!");
    }

}