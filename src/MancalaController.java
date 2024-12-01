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
                if(pitIndex != -1) {
                    try{
                        model.moveStones(pitIndex);
                        view.updateBoard();
                        turnPerformed = true;
                        if(model.isGameOver())
                        {
                            view.handleGameOver();
                        }else if(model.isFreeTurn())
                        {
                            view.setFreeTurnMessage("Free Turns: 1");
                        }

                    } catch (IllegalArgumentException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        view.undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoCount < 3 && turnPerformed == true)
                {
                    model.undo();
                    view.updateBoard();
                    undoCount++;
                    turnPerformed = false;
                    view.undoButton.setText("Undos Left: " + (3 - undoCount));
                    view.setStatusMessage("Move undone. " + (3 - undoCount) + " undos left.");
                }else if (!turnPerformed)
                {
                    view.setStatusMessage("You must make a move before undoing again.");
                }else if(undoCount >= 3)
                {
                    view.setStatusMessage("You've reached the undo limit.");
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