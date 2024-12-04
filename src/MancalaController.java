import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MancalaController {
    private MancalaModel model;
    private MancalaView view;
    boolean freeTurn;
    boolean turnPerformed = false;

    public MancalaController(MancalaModel model, MancalaView view)
    {
        this.model = model;
        this.view = view;
        freeTurn = false;




        view.boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pitIndex = view.getClickedPit(e.getX(), e.getY());
                if (pitIndex != -1) {
                    boolean isPlayerA = model.isPlayerA();
                    boolean isPlayerAPit = pitIndex >= 0 && pitIndex < 6;  // Player A's pits are from index 0 to 5
                    boolean isPlayerBPit = pitIndex >= 7 && pitIndex <= 12; // Player B's pits are from index 7 to 12

                    if ((isPlayerA && isPlayerAPit) || (!isPlayerA && isPlayerBPit)) {
                        try {
                            model.moveStones(pitIndex);
                            view.updateBoard();
                            if (model.isGameOver()) {
                                view.handleGameOver();
                            } else {
                                view.updateUndoButton(); // Update undo button after a move
                            }
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        view.setStatusMessage("You can only click on your own pits.");
                    }
                } else {
                    view.setStatusMessage("Click on a valid pit");
                }
            }
        });

        view.undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.lastPlayerToMoveIsPlayerA() && model.getPlayerAUndoCount() > 0) {
                    model.undo();
                    view.updateBoard();
                    view.setStatusMessage("Player A undid a move. " + model.getPlayerAUndoCount() + " undos left.");
                } else if (!model.lastPlayerToMoveIsPlayerA() && model.getPlayerBUndoCount() > 0) {
                    model.undo();
                    view.updateBoard();
                    view.setStatusMessage("Player B undid a move. " + model.getPlayerBUndoCount() + " undos left.");
                } else {
                    view.setStatusMessage("No undos left or cannot undo opponent's move.");
                }
                view.updateUndoButton();
            }
        });






        view.firstStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardStyle firstStyle = new FirstStyle();
                model.setBoardStyle(firstStyle);
                view.setBoardStyle(firstStyle); // Update the view's boardStyle
                view.stylePanel.setVisible(false);
                view.boardPanel.setVisible(true);
                view.updateBoard();
            }
        });

        view.secondStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setBoardStyle(new SecondStyle());
                view.setBoardStyle(new SecondStyle());
                view.stylePanel.setVisible(false);
                view.boardPanel.setVisible(true);
                view.updateBoard();
            }
        });



    }


   }