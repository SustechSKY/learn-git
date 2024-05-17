package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class WithdrawButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    int state = 1;
    Chessboard chessboard;

    public WithdrawButtonComponent(Image image1, Image image2, Chessboard chessboard) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
        this.chessboard = chessboard;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Withdraw is clicked");
            chessboard.d.count=0;
            while(chessboard.d.getProcess()[chessboard.d.count][6]!=0)
                chessboard.d.count++;
            if(chessboard.d.count!=0)
            {
                chessboard.d.getProcess()[chessboard.d.count-1][6]=0;
                chessboard.d.count--;
            }
            else {
                JOptionPane.showMessageDialog(null,"Now is the first step!!!","Error",JOptionPane.WARNING_MESSAGE);
            }
            chessboard.renew1();
            chessboard.clearDeadChessboard();
            chessboard.restartGame();
        }
        if (e.getID() == MouseEvent.MOUSE_ENTERED && state == 1) {
            state = 2;
            repaint();
        }
        if (e.getID() == MouseEvent.MOUSE_EXITED && state == 2) {
            state = 1;
            repaint();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(state == 1)
            g.drawImage(Image1, 0, 0, Image1.getWidth(this), Image1.getHeight(this), this);
        else if(state == 2)
            g.drawImage(Image2, 0, 0, Image2.getWidth(this), Image2.getHeight(this), this);
    }
}