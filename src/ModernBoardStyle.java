import java.awt.*;

public class ModernBoardStyle implements BoardStyle {
    @Override
    public void applyStyle(MancalaView view) {
        view.getContentPane().setBackground(Color.LIGHT_GRAY);
    }
}
