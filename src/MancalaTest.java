import javax.swing.*;

/**
 * The MancalaTest class serves as the entry point for the Mancala game application.
 * It initializes the model, view, and controller, and allows the user to set up the game
 * with a choice of the number of stones per pit (3 or 4).
 *
 * Programmed by: Karla Nguyen
 * Date: 2024-12-04
 */
public class MancalaTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // Get the number of stones per pit (3 or 4) with validation
        int stonesPerPit = 0;
        while (stonesPerPit != 3 && stonesPerPit != 4) {
            String stoneInput = JOptionPane.showInputDialog(frame, "Enter Number of Stones (3 or 4):");
            if (stoneInput == null) { // Handle cancel
                JOptionPane.showMessageDialog(frame, "Setup cancelled. Exiting...");
                System.exit(0);
            }
            try {
                stonesPerPit = Integer.parseInt(stoneInput);
                if (stonesPerPit != 3 && stonesPerPit != 4) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter 3 or 4.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.");
            }
        }

        // Initialize the MVC components
        MancalaModel model = new MancalaModel(stonesPerPit);
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);
    }
}