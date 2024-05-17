package view;

import chessComponent.SquareComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CheatButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    Image Image3;
    Chessboard chessboard;
    int state = 1;

    public CheatButtonComponent(Image image1, Image image2, Image image3, Chessboard chessboard) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
        this.Image3 = image3;
        this.chessboard = chessboard;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Click Cheat Button");
            if(state == 2){
                state = 3;
                repaint();
            }
            else if(state == 3) {
                state = 2;
                repaint();
            }
            SquareComponent[][] squareComponents=chessboard.getSquareComponents();
            for (int i=0;i<squareComponents.length;i++)
                for (int j=0;j<squareComponents[i].length;j++)
                    squareComponents[i][j].ifcheat=!squareComponents[i][j].ifcheat;
            chessboard.setSquareComponents(squareComponents);
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
        else if(state == 3)
            g.drawImage(Image3, 0, 0, Image2.getWidth(this), Image2.getHeight(this), this);
    }
}