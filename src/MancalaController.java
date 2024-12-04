public class MancalaController {
    private MancalaModel model;
    private MancalaView view;

    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;
    }

    public void makeMove(int player, int pitIndex) {
        if (model.getCurrentPlayer() == player) {
            model.makeMove(pitIndex);
            view.updateBoard(model.getPits(), model.getMancalas());
        }
    }

    public void undoMove() {
        model.undo();
        view.updateBoard(model.getPits(), model.getMancalas());
    }
}
