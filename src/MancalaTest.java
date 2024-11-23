public class MancalaTest {
    public static void main (String args[])
    {
        MancalaModel model = new MancalaModel(3);
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model, view);



    }
}
