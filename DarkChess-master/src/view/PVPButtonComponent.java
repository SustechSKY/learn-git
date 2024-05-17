package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PVPButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    int state = 0;
    Menu thisMenu;

    public PVPButtonComponent(Image image1, Image image2) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;

    }

    public void setState(int state) {
        this.state = state;
    }

    public void setThisMenu(Menu thisMenu) {
        this.thisMenu = thisMenu;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("PVP is clicked");
            ChessGameFrame mainFrame = new ChessGameFrame(720, 720,0);
            mainFrame.setVisible(true);
            thisMenu.dispose();
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
        if (state == 1)
            g.drawImage(Image1, 0, 0, Image1.getWidth(this), Image1.getHeight(this), this);
        else if(state == 2)
            g.drawImage(Image2, 0, 0, Image2.getWidth(this), Image2.getHeight(this), this);
    }
}