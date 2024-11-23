import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MancalaView extends JFrame {
    private MancalaModel model;
    private BoardStyle boardStyle;
    private JPanel boardPanel;
    private JButton undoButton;
    //need to add buttons and labels

    public MancalaView(MancalaModel model) {
        this.model = model;
        setupGUI();
    }

    private void setupGUI() {
        //set up screen
        setTitle("Mancala Game");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //still need to add components for the game board (buttons, pits, mancalas)
        JPanel stylePanel = new JPanel();
        JButton firstStyleButton = new JButton("First Style");
        JButton secondStyleButton = new JButton("Second Style");

        firstStyleButton.addActionListener(e -> {
            boardStyle = new FirstStyle();
            updateBoard();
        });

        secondStyleButton.addActionListener(e -> {
            boardStyle = new SecondStyle();
            updateBoard();
        });

        stylePanel.add(firstStyleButton);
        stylePanel.add(secondStyleButton);
        add(stylePanel, BorderLayout.NORTH);

        boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(boardStyle != null) {
                    boardStyle.drawBoard(MancalaView.this);
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(800, 500));
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        undoButton = new JButton("Undo");
        controlPanel.add(undoButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void setUndoListener(ActionListener undoListener) {
        undoButton.addActionListener(undoListener);
    }

    //update board view when player makes a move
    public void updateBoard(){

    }
}
