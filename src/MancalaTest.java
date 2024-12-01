import javax.swing.*;
public class MancalaTest {
    public static void main (String[] args)
    {
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

        MancalaModel model = new MancalaModel(stonesPerPit);
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);
    }
}