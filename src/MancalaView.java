import javax.swing.*;
import java.awt.*;

public class MancalaView extends JFrame {
    private MancalaModel model;
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
        //still need to add components for the game board (buttons, pits, mancalas)
    }

    //update board view when player makes a move
    public void updateBoard(){

    }
}
