import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Font;

import static constant.ReversiConst.*;

public class GameInfo extends JPanel {
    JLabel black;
    JLabel white;

    public GameInfo (){
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(GAMEINFO_WIDTH, GAMEINFO_WIDTH));

        
        UtilityButtons ub = new UtilityButtons();
        ub.setPreferredSize(new Dimension(UTIL_BTN_PANEL_WIDTH, UTIL_BTN_PANEL_HEIGHT));
        this.add(ub, BorderLayout.NORTH);

        black = new JLabel("BLACK : 2 ");        
        white = new JLabel("WHITE : 2 ");

        black.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
        white.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
        
        Box labelBox = new Box(BoxLayout.Y_AXIS);
        labelBox.add(black);
        labelBox.add(white);
        add(labelBox);
    }
    
    public void setStoneNum(int b, int w){
        black.setText("BLACK : " + b);
        white.setText("WHITE : " + w);
    }
}

class UtilityButtons extends JPanel implements ActionListener{

    UtilityButtons(){
        this.setBackground(Color.WHITE);
        setLayout(new GridLayout(1,3));
        
        // ボタンサイズ調整用のパネル
        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBackground(Color.WHITE);
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(Color.WHITE);

        JButton homeButton = new JButton("HOME");
        homeButton.setActionCommand("go_to_home");
        homeButton.addActionListener(this);

        add(emptyPanel1);
        add(emptyPanel2);
        add(homeButton);

    }

    
    @Override
    // ボタンを追加した際にはここに機能を追加する。
    public void actionPerformed(ActionEvent e){}
}