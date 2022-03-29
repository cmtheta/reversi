
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;

import static constant.ReversiConst.*;

public class Main {
    ReversiModel model;
    public Main(){
        JFrame frame = new JFrame();
        frame.setTitle("REVERSI");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT+35);  // バーの分（35pixel）を加えている.
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Box layoutbox = new Box(BoxLayout.LINE_AXIS);
        
        model = new ReversiModel();
        GameInfo gi = new GameInfo();
        GameBoard gb = new GameBoard(model, gi);
        
        layoutbox.add(gb);
        layoutbox.add(gi);

        frame.add(layoutbox);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new Main();
    }
}
