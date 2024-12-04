import java.awt.*;

public class ClassicBoardStyle implements BoardStyle {
    @Override
    public void applyStyle(MancalaView view) {
        view.getContentPane().setBackground(Color.ORANGE);
    }
}
