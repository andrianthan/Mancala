import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MancalaView extends JFrame {
    private JButton[][] pitButtons;
    private JLabel[] mancalaLabels;
    private JButton undoButton;
    private MancalaController controller;

    public MancalaView() {
        setTitle("Mancala Game");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pitButtons = new JButton[2][6];
        mancalaLabels = new JLabel[2];
        JPanel boardPanel = new JPanel(new GridLayout(2, 7));

        // Create Player B's row
        for (int i = 5; i >= 0; i--) {
            pitButtons[1][i] = new JButton("B" + (i + 1));
            boardPanel.add(pitButtons[1][i]);
        }
        mancalaLabels[1] = new JLabel("Mancala B: 0", SwingConstants.CENTER);
        boardPanel.add(mancalaLabels[1]);

        // Create Player A's row
        mancalaLabels[0] = new JLabel("Mancala A: 0", SwingConstants.CENTER);
        boardPanel.add(mancalaLabels[0]);
        for (int i = 0; i < 6; i++) {
            pitButtons[0][i] = new JButton("A" + (i + 1));
            boardPanel.add(pitButtons[0][i]);
        }

        add(boardPanel, BorderLayout.CENTER);

        undoButton = new JButton("Undo");
        add(undoButton, BorderLayout.SOUTH);

        undoButton.addActionListener(e -> controller.undoMove());
    }

    public void setController(MancalaController controller) {
        this.controller = controller;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                int row = i;
                int col = j;
                pitButtons[i][j].addActionListener(e -> controller.makeMove(row, col));
            }
        }
    }

    public void updateBoard(int[][] pits, int[] mancalas) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                pitButtons[i][j].setText((i == 0 ? "A" : "B") + (j + 1) + ": " + pits[i][j]);
            }
            mancalaLabels[i].setText("Mancala " + (i == 0 ? "A" : "B") + ": " + mancalas[i]);
        }
    }
}
