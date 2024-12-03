import java.awt.*;

/**
 * @author Karla Nguyen & Andrian Than
 * interface for BoardStyles
 * skeleton for the different styles
 * strategy style
 */
public interface BoardStyle {

    void boardSelection();

    void drawBoard(MancalaView view, Graphics g);
}
