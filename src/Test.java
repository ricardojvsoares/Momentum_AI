import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {





       /* GameMomentumAB game = new GameMomentumAB(
                    "0 0 0 0 0 0 1 " +
                            "0 0 0 0 0 0 1 " +
                            "0 1 0 0 0 0 0 " +
                            "0 1 0 2 2 0 0 " +
                            "0 0 2 2 2 0 0 " +
                            "0 0 0 0 0 2 0 " +
                            "2 0 0 1 0 0 1 "
        );
*/
        GameMomentumAB game = new GameMomentumAB(
                "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 " +
                        "0 0 0 0 0 0 0 "
        );


        game.setMyColor(1);
        ArrayList<Move> suc = game.expandAB();
        Move best;
        best = suc.get(0);
        for(Move m : suc){
            System.out.println(m.getNode().getH());
          /*  if(m.getNode().getH()> best.getNode().getH()){
                best = m;
            }*/
        }

        /*System.out.print("\n\n\n_____________:BEST:_____________\n");
        System.out.print(best);*/

    }
}
