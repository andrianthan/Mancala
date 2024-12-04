import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Karla Nguyen
 * SecondStyle class that implements BoardStyle
 * one of the design styles the player can pick
 */
public class SecondStyle implements BoardStyle {
    /**
     * method to confirm board selection
     */
    @Override
    public void boardSelection() {
        System.out.println("Second style selected");
    }

    /**
     * method that draws the whole board
     * draws the background, pits, Mancala pits, and stones
     * @param view
     * @param g
     */
    @Override
    public void drawBoard(MancalaView view, Graphics g) {
        int width = view.getWindowWidth();
        int height = view.getWindowHeight() - 50;
        int[] stones = view.getModel().getPits();

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.drawString("Pit B", 20, height - 350);
        g.drawString("Pit A", width - 50, height - 350);

        // Draw Mancala B pit on the left side
        g.setColor(Color.PINK);
        g.fillRect(10, height / 4, 50, height / 2);
        g.setColor(Color.WHITE);
        String mancalaBText = String.valueOf(view.getModel().getMancalaB());
        g.drawString(mancalaBText, 30, 230);
        int mancalaBStones = view.getModel().getMancalaB();
        drawStonesInPit(g, 10, height / 4, 50, height / 2, mancalaBStones, Color.WHITE);

        // Draw Mancala A pit on the right side
        g.setColor(Color.PINK);
        g.fillRect(width - 60, height / 4, 50, height / 2);
        g.setColor(Color.WHITE);
        String mancalaAText = String.valueOf(view.getModel().getMancalaA());
        g.drawString(mancalaAText, width - 40, 230);
        int mancalaAStones = view.getModel().getMancalaA();
        drawStonesInPit(g, width - 60, height / 4, 50, height / 2, mancalaAStones, Color.WHITE);

        // Draw pits
        int pitWidth = 80;
        int pitHeight = 80;
        int gap = 30;

        // Draw Player A's pits
        for (int i = 0; i < 6; i++) {
            drawPit(g, 80 + i * (pitWidth + gap), 50, pitWidth, pitHeight, stones[i], Color.GREEN);
        }

        // Draw Player B's pits
        for (int i = 0; i < 6; i++) {
            drawPit(g, 80 + i * (pitWidth + gap), height - 110, pitWidth, pitHeight, stones[12 - i], Color.MAGENTA);
        }

        // Player A's pit labels & stone count
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.WHITE);
            g.drawString("A" + (6 - i), 90 + i * (pitWidth + gap) + 20, 45);
            g.drawString(String.valueOf(stones[i]), 90 + i * (pitWidth + gap) + 25, 110);
        }

        // Player B's pit labels & stone count (bottom row) with corrected indices
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.WHITE);
            g.drawString("B" + (i + 1), 90 + i * (pitWidth + gap) + 20, height - 120);
            g.drawString(String.valueOf(stones[12 - i]), 90 + i * (pitWidth + gap) + 25, height - 50);
        }
    }
    // helper method to draw the players' pits
    private void drawPit(Graphics g, int x, int y, int width, int height, int stoneCount, Color color){
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        int stoneRadius = 10;
        int stonesPerRow = 3;
        for(int i = 0; i < stoneCount; i++){
            int row = i / stonesPerRow;
            int col = i % stonesPerRow;
            int stoneX = x + 20 + col * (stoneRadius+5);
            int stoneY = y + 10 + row * (stoneRadius+5);
            if (stoneX + stoneRadius < x + width && stoneY + stoneRadius < y + height){
                g.fillRect(stoneX, stoneY, stoneRadius, stoneRadius);
            }
        }
    }
    // helper method to draw stones in the Mancala pit
    private void drawStonesInPit(Graphics g, int x, int y, int width, int height, int stoneCount, Color stoneColor) {
        int stoneRadius = 10;
        int padding = 5;
        int maxColumns = (width - 2 * padding) / (stoneRadius + padding);
        int maxRows = (height - 2 * padding) / (stoneRadius + padding);
        int stonesPerRow = Math.min(stoneCount, maxColumns);

        int currentX = x + padding;
        int currentY = y + padding;

        g.setColor(stoneColor);

        for (int i = 0; i < stoneCount; i++) {
            g.fillRect(currentX, currentY, stoneRadius, stoneRadius);
            currentX += stoneRadius + padding;

            if ((i + 1) % stonesPerRow == 0) { // Move to the next row
                currentX = x + padding;
                currentY += stoneRadius + padding;
            }
        }
    }
}