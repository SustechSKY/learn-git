package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class NewGameButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    Menu thisMenu;
    int state = 1;
    public NewGameButtonComponent(Image image1, Image image2, Menu m,int WIDTH,int HEIGHT) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
        this.thisMenu = m;


        setLocation(WIDTH*1/200,HEIGHT*15/40);
        setSize(200, 72);
        thisMenu.add(this);

        loadGameButton.setLocation(WIDTH*1/50,HEIGHT*53/80);
        loadGameButton.setSize(191, 72);
        thisMenu.add(loadGameButton);

        pvcGameButton.setLocation(WIDTH*1/50,HEIGHT*53/80);
        pvcGameButton.setSize(191, 72);
        thisMenu.add(pvcGameButton);

        pvcGameButton.setThisMenu(thisMenu);
        loadGameButton.setThisMenu(thisMenu);
    }

    @Override
    protected void processMouseEvent(MouseEvent e)
    {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("New Game is clicked");
            thisMenu.remove(this);
            thisMenu.remove(loadGameButton);
            pvcGameButton.setPvpState(1);
            pvcGameButton.pvpRepaint();
            pvcGameButton.setState(1);
            pvcGameButton.repaint();
        }
        if(e.getID() == MouseEvent.MOUSE_ENTERED && state == 1) {
            state = 2;
            repaint();
        }
        if(e.getID() == MouseEvent.MOUSE_EXITED && state == 2) {
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
    Image PVCImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("PVCButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image PVCImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("PVCButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    PVCButtonComponent pvcGameButton = new PVCButtonComponent(PVCImage1, PVCImage2);
    Image LoadGameImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("LoadGameButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image LoadGameImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("LoadGameButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    LoadGameButtonComponent loadGameButton = new LoadGameButtonComponent(LoadGameImage1, LoadGameImage2);

}