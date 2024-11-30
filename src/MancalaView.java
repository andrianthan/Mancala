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

    JLabel mancalaALabel;
    JLabel mancalaBLabel;

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
        int pitWidth = 60;
        int pitHeight = 60;
        int gap = 20;

        for(int i = 0; i < 6; i++){
            int pitX = 180 + i * (pitWidth + gap);
            int pitY = 50;
            if (x >= pitX && x <= pitX + pitWidth && y >= pitY && y <= pitY + pitHeight) {
                return i;
            }
        }

        for(int i = 0; i < 6; i++){
            int pitX = 180 + i * (pitWidth + gap);
            int pitY = getHeight() - 120;
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
        undoButton = new JButton("Undo");
        mancalaALabel = new JLabel(" Mancala A: 0");
        mancalaBLabel = new JLabel(" Mancala B: 0");
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
        mancalaALabel.setText("Mancala A: " + model.getMancalaA());
        mancalaBLabel.setText("Mancala B: " + model.getMancalaB());
        boardPanel.repaint();
    }

    public void setBoardStyle(BoardStyle boardStyle) {
        this.boardStyle = boardStyle;
    }
}