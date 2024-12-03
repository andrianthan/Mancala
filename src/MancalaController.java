import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MancalaController {
    private MancalaModel model;
    private MancalaView view;
    private int undoCount;
    boolean freeTurn;
    boolean turnPerformed = false;

    public MancalaController(MancalaModel model, MancalaView view)
    {
        this.model = model;
        this.view = view;
        undoCount = model.getUndoCount();
        freeTurn = false;




        view.boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pitIndex = view.getClickedPit(e.getX(), e.getY());
                if (pitIndex != -1) {
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
                    view.setStatusMessage("Click on a valid pit");
                }
            }
        });

        view.undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.isPlayerA() && model.lastPlayerToMoveIsPlayerA() && model.getPlayerAUndoCount() > 0) {
                    model.undo();
                    view.updateBoard();
                    view.undoButton.setText("Undos Left: " + model.getPlayerAUndoCount());
                    view.setStatusMessage("Player A undid a move. " + model.getPlayerAUndoCount() + " undos left.");
                } else if (!model.isPlayerA() && !model.lastPlayerToMoveIsPlayerA() && model.getPlayerBUndoCount() > 0) {
                    model.undo();
                    view.updateBoard();
                    view.undoButton.setText("Undos Left: " + model.getPlayerBUndoCount());
                    view.setStatusMessage("Player B undid a move. " + model.getPlayerBUndoCount() + " undos left.");
                } else if (model.lastPlayerToMoveIsPlayerA() && !model.isPlayerA()) {
                    view.setStatusMessage("Player B cannot undo Player A's move.");
                } else if (!model.lastPlayerToMoveIsPlayerA() && model.isPlayerA()) {
                    view.setStatusMessage("Player A cannot undo Player B's move.");
                } else {
                    view.setStatusMessage("No undos left.");
                }
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