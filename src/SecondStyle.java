import java.awt.Color;
import java.awt.Graphics;

/**
 * The SecondStyle class represents one of the design styles that a player can select in the Mancala game.
 * This style customizes the visual representation of the game board, including pits, Mancala pits, and stones.
 *
 * Programmed by: Karla Nguyen & Nathan Dinh
 * Date: 2024-12-04
 */
public class SecondStyle implements BoardStyle {
    /**
     * Confirms the selection of the "Second Style" board design.
     * Outputs a confirmation message to the console.
     */
    @Override
    public void boardSelection() {
        System.out.println("Second style selected");
    }

    /**
     * Draws the entire game board, including the background, pits, Mancala pits, and stones.
     *
     * @param view The MancalaView instance providing the game board dimensions and state.
     * @param g    The Graphics object used to draw the board and its components.
     */
    @Override
    public void drawBoard(MancalaView view, Graphics g) {
        int width = view.getWindowWidth();
        int height = view.getWindowHeight() - 50;
        int[] stones = view.getModel().getPits();

        // Draw the background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        // Draw Mancala pit labels
        g.setColor(Color.WHITE);
        g.drawString("Pit B", 20, height - 350);
        g.drawString("Pit A", width - 50, height - 350);

        // Draw Player B's Mancala pit on the left side
        g.setColor(Color.PINK);
        g.fillRect(10, height / 4, 50, height / 2);
        g.setColor(Color.WHITE);
        String mancalaBText = String.valueOf(view.getModel().getMancalaB());
        g.drawString(mancalaBText, 30, 230);
        int mancalaBStones = view.getModel().getMancalaB();
        drawStonesInPit(g, 10, height / 4, 50, height / 2, mancalaBStones, Color.WHITE);

        // Draw Player A's Mancala pit on the right side
        g.setColor(Color.PINK);
        g.fillRect(width - 60, height / 4, 50, height / 2);
        g.setColor(Color.WHITE);
        String mancalaAText = String.valueOf(view.getModel().getMancalaA());
        g.drawString(mancalaAText, width - 40, 230);
        int mancalaAStones = view.getModel().getMancalaA();
        drawStonesInPit(g, width - 60, height / 4, 50, height / 2, mancalaAStones, Color.WHITE);

        // Pit dimensions and spacing
        int pitWidth = 80;
        int pitHeight = 80;
        int gap = 30;

        // Draw Player A's pits (bottom row, left to right A1 to A6)
        for (int i = 0; i < 6; i++) {
            drawPit(g, 80 + i * (pitWidth + gap), height - 110, pitWidth, pitHeight, stones[i], Color.GREEN);
        }

        // Draw Player B's pits (top row, left to right B6 to B1)
        for (int i = 0; i < 6; i++) {
            drawPit(g, 80 + i * (pitWidth + gap), 50, pitWidth, pitHeight, stones[12 - i], Color.MAGENTA);
        }

        // Player A's pit labels & stone counts (bottom row)
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.WHITE);
            g.drawString("A" + (i + 1), 90 + i * (pitWidth + gap) + 20, height - 120);
            g.drawString(String.valueOf(stones[i]), 90 + i * (pitWidth + gap) + 25, height - 50);
        }

        // Player B's pit labels & stone counts (top row)
        for (int i = 0; i < 6; i++) {
            g.setColor(Color.WHITE);
            g.drawString("B" + (6 - i), 90 + i * (pitWidth + gap) + 20, 45);
            g.drawString(String.valueOf(stones[12 - i]), 90 + i * (pitWidth + gap) + 25, 110);
        }
    }

    /**
     * Draws a single player's pit and its stones.
     *
     * @param g          The Graphics object used for drawing.
     * @param x          The x-coordinate of the pit.
     * @param y          The y-coordinate of the pit.
     * @param width      The width of the pit.
     * @param height     The height of the pit.
     * @param stoneCount The number of stones in the pit.
     * @param color      The color of the pit.
     */
    private void drawPit(Graphics g, int x, int y, int width, int height, int stoneCount, Color color) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        int stoneRadius = 10;
        int stonesPerRow = 3;
        for (int i = 0; i < stoneCount; i++) {
            int row = i / stonesPerRow;
            int col = i % stonesPerRow;
            int stoneX = x + 20 + col * (stoneRadius + 5);
            int stoneY = y + 10 + row * (stoneRadius + 5);
            if (stoneX + stoneRadius < x + width && stoneY + stoneRadius < y + height) {
                g.fillRect(stoneX, stoneY, stoneRadius, stoneRadius);
            }
        }
    }

    /**
     * Draws stones inside a Mancala pit.
     *
     * @param g          The Graphics object used for drawing.
     * @param x          The x-coordinate of the Mancala pit.
     * @param y          The y-coordinate of the Mancala pit.
     * @param width      The width of the Mancala pit.
     * @param height     The height of the Mancala pit.
     * @param stoneCount The number of stones in the Mancala pit.
     * @param stoneColor The color of the stones.
     */
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
