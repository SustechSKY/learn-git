package chessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class DeadChessComponent extends JComponent{
    private int state = 1;
    private int size;

    private Image image;

    String path = "resource\\棋子图片\\红车.png";

    public DeadChessComponent( int size, Point location, String name) {
        super();
        this.setLayout(null);
        this.setFocusable(true);
        this.size = size;
        setLocation(location);
        setSize(size,size);
        image =Toolkit.getDefaultToolkit().getImage("resource\\棋子图片\\"+name+".png").getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);

    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(state == 1)
            g.drawImage(image,0,0,size,size,this);

    }

}
