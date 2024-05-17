package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SaveGameButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    ChessGameFrame frame;
    int state = 1;

    public SaveGameButtonComponent(Image image1, Image image2, ChessGameFrame frame) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
        this.frame = frame;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Click Save Game");
            try {
                frame.write();
                JOptionPane.showMessageDialog(null,"Your game is saved.","Saving",JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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