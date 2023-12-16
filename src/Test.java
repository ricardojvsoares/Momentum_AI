import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        GameMomentumAB game = new GameMomentumAB(
                    "0 0 0 0 0 0 0 " +
                            "0 0 0 0 0 0 0 " +
                            "0 0 0 0 0 0 2 " +
                            "0 0 0 0 0 0 2 " +
                            "0 0 0 0 0 0 2 " +
                            "0 0 0 0 0 0 0 " +
                            "0 0 0 0 0 0 0 "
        );

        game.setMyColor(1);
        ArrayList<Move> suc = game.expandAB();
        for(Move m : suc){
            System.out.println("________");
            System.out.println(m);
            System.out.println(m.getNode().getH());
        }


    }
}
