import java.awt.Color;
import java.awt.Graphics;

public class FirstStyle implements BoardStyle {
    @Override
    public void boardSelection() {
        System.out.println("First style selected");
    }

    @Override
    public void drawBoard(MancalaView view, Graphics g) {
        int width = view.getWindowWidth();
        int height = view.getWindowHeight();
        int[] stones = view.getModel().getPits();
        // Background color
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        // Mancala labels
        g.setColor(Color.BLACK);
        g.drawString("A", 20, height / 2);
        g.drawString("B", width - 30, height / 2);

        // Draw Mancalas
        g.setColor(Color.CYAN);
        g.fillRect(10, height / 4, 50, height / 2); // Player A's Mancala
        g.fillRect(width - 60, height / 4, 50, height / 2); // Player B's Mancala

        // Draw pits (circles)
        int pitWidth = 60;
        int pitHeight = 60;
        int gap = 10;
        g.setColor(Color.BLUE);

        // Player A's pits (top row)
        for (int i = 0; i < 6; i++) {
            g.fillOval(80 + i * (pitWidth + gap), 50, pitWidth, pitHeight);
            g.setColor(Color.BLACK);
            g.drawString("A" + (6 - i), 80 + i * (pitWidth + gap) + 20, 45);
            g.drawString(String.valueOf(stones[i]), 80 + i * (pitWidth + gap) + 25, 90);
        }


        // Player B's pits (bottom row)
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.BLUE);
            g.fillOval(80 + i * (pitWidth + gap), height - 110, pitWidth, pitHeight);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(stones[i]), 80 + i * (pitWidth + gap) + 25, 90);
            g.drawString(String.valueOf(stones[i + 6]), 80 + i * (pitWidth + gap) + 25, height - 70);        }
    }
}

