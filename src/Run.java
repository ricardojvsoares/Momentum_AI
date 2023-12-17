import java.io.IOException;

import static java.lang.Thread.sleep;

public class Run {




    public static void main(String[] args) throws IOException, InterruptedException {

        Process p = Runtime.getRuntime().exec("""
                java --module-path "C:\\Program Files\\Java\\javafx-sdk-17.0.9\\lib"\s
                --add-modules javafx.controls\s
                -jar "C:\\Users\\geral\\IdeaProjects\\Momentum_44375_44594_38909\\MomentumServer.jar\"""");

        sleep(2000);


        MomentumResponse r = new MomentumResponse();
        r.begin();


        sleep(1000);


        Process p1 = Runtime.getRuntime().exec("""
                        java -jar "C:\\Users\\geral\\IdeaProjects\\Momentum_44375_44594_38909\\ManualResponse.jar\"""");
    }
}
