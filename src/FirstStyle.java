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
        int height = view.getWindowHeight()-50;
        int[] stones = view.getModel().getPits();
        // Background color
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        // Mancala pit labels
        g.setColor(Color.BLACK);
        g.drawString("Pit A", 20, height-350);
        g.drawString("Pit B", width - 50, height-350);

        // Draw Mancala pits
        g.setColor(Color.CYAN);
        g.fillRect(10, height / 4, 50, height / 2); // Player A's Mancala
        g.setColor(Color.BLACK);
        String mancalaAText = String.valueOf(view.getModel().getMancalaA());
        g.drawString(mancalaAText, 30, 230);
        g.setColor(Color.CYAN);
        g.fillRect(width - 60, height / 4, 50, height / 2); // Player B's Mancala
        g.setColor(Color.BLACK);
        String mancalaBText = String.valueOf(view.getModel().getMancalaB());
        g.drawString(mancalaBText, width-40, 230);

        // Draw pits (circles)
        int pitWidth = 60;
        int pitHeight = 60;
        int gap = 20;

        // Player A's pits (top row)
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.BLUE);
            g.fillOval(180 + i * (pitWidth + gap), 50, pitWidth, pitHeight);
            g.setColor(Color.BLACK);
            g.drawString("A" + (6 - i), 180 + i * (pitWidth + gap) + 20, 45);
            g.drawString(String.valueOf(stones[i]), 180 + i * (pitWidth + gap) + 25, 90);
        }

        // Player B's pits (bottom row)
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.RED);
            g.fillOval(180 + i * (pitWidth + gap), height - 110, pitWidth, pitHeight);
            g.setColor(Color.BLACK);
            g.drawString("B" + (i+1), 180 + i * (pitWidth + gap) + 20, height - 120);
            g.drawString(String.valueOf(stones[i + 6]), 180 + i * (pitWidth + gap) + 25, height - 70);
        }
    }
}