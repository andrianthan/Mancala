import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MancalaView extends JFrame {
    private MancalaModel model;
    private BoardStyle boardStyle;
    JPanel boardPanel;
    JButton undoButton;
    //need to add buttons and labels
    JButton pit1;
    JButton pit2;
    JButton pit3;
    JButton pit4;
    JButton pit5;
    JButton pit6;
    JButton pit7;
    JButton pit8;
    JButton pit9;
    JButton pit10;
    JButton pit11;
    JButton pit12;

    JButton firstStyleButton;
    JButton secondStyleButton;
    JPanel stylePanel;

    int width = 800;
    int height =500;

    public MancalaView(MancalaModel model) {
        this.model = model;
        setupGUI();
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
        controlPanel.add(undoButton);
        add(controlPanel, BorderLayout.SOUTH);
        pit1 = new JButton("Pit 1");
        pit2 = new JButton("Pit 2");
        pit3 = new JButton("Pit 3");
        pit4 = new JButton("Pit 4");
        pit5 = new JButton("Pit 5");
        pit6 = new JButton("Pit 6");
        pit7 = new JButton("Pit 7");
        pit8 = new JButton("Pit 8");
        pit9 = new JButton("Pit 9");
        pit10 = new JButton("Pit 10");
        pit11 = new JButton("Pit 11");
        pit12 = new JButton("Pit 12");

        boardPanel.add(pit1);
        boardPanel.add(pit2);
        boardPanel.add(pit3);
        boardPanel.add(pit4);
        boardPanel.add(pit5);
        boardPanel.add(pit6);
        boardPanel.add(pit7);
        boardPanel.add(pit8);
        boardPanel.add(pit9);
        boardPanel.add(pit10);
        boardPanel.add(pit11);
        boardPanel.add(pit12);

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
        boardPanel.repaint();
    }

    public void setBoardStyle(BoardStyle boardStyle) {
        this.boardStyle = boardStyle;
    }
}
