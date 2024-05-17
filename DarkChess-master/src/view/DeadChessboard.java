package view;


import chessComponent.*;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class DeadChessboard extends JComponent {
    public DeadChessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);

//        Point point1 = new Point(75,6);
//        Point point2 = new Point(75,6+71*7);
//
//        DeadChessComponent deadChessComponent1 = new DeadChessComponent(60,point1,"红帅");
//        DeadChessComponent deadChessComponent2 = new DeadChessComponent(60,point2,"红帅");
////        deadChessComponent.setLocation(75,76);
//        add(deadChessComponent1);
//        add(deadChessComponent2);
////        deadChessComponent.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    ArrayList<DeadChessComponent> list = new ArrayList<>();
    int redDeadChessCount = 0;
    int blackDeadChessCount = 0;

    Point redInitialPoint = new Point(75,6);
    Point blackInitialPoint = new Point(10,6);
    int redTurn = 0;
    int blackTurn = 0;

    public void addDeadChess(SquareComponent squareComponent)
    {
        Point initialPoint;
        int count;
        int turn;

        if(squareComponent.getChessColor() == ChessColor.RED) {
            initialPoint = redInitialPoint;
            count = redDeadChessCount;
            turn = redTurn;
            if(redDeadChessCount == 7 && turn ==0) {
                redDeadChessCount = 0;
                redTurn--;
            }
            else
                redDeadChessCount++;
        }
        else {
            initialPoint = blackInitialPoint;
            count = blackDeadChessCount;
            turn = blackTurn;
            if(blackDeadChessCount == 7 && turn == 0) {
                blackDeadChessCount = 0;
                blackTurn++;
            }
            else
                blackDeadChessCount++;
        }

        String chessName = squareComponent.getChessName();
        Point point = new Point((int)initialPoint.getX()+turn*65,(int)initialPoint.getY()+count*71);
        DeadChessComponent deadChessComponent = new DeadChessComponent(60,point,chessName);
        add(deadChessComponent);
        deadChessComponent.repaint();
        list.add(deadChessComponent);
    }

    public void clear()
    {
        redTurn = 0;
        blackTurn = 0;
        redDeadChessCount = 0;
        blackDeadChessCount = 0;
        for(DeadChessComponent e : list) {
            e.setState(0);
            e.repaint();
        }
    }
}
