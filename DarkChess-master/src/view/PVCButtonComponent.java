package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PVCButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    int state = 0;
    Menu thisMenu;

    public PVCButtonComponent(Image image1, Image image2) {
        int WIDTH = 500;
        int HEIGHT = 500;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
        pvpGameButton.setLocation(WIDTH*4/200,HEIGHT*15/40);
        pvpGameButton.setSize(191, 72);

        easyButton.setLocation(WIDTH*1/50,HEIGHT*15/40);
        easyButton.setSize(191, 72);

        mediumButton.setLocation(WIDTH*4/200,HEIGHT*83/160);
        mediumButton.setSize(191, 72);

        hardButton.setLocation(WIDTH*4/200,HEIGHT*53/80);
        hardButton.setSize(191, 72);

        easyButton.setThisMenu(thisMenu);
        mediumButton.setThisMenu(thisMenu);
        hardButton.setThisMenu(thisMenu);
    }

    public void setPvpState(int val)
    {
        pvpGameButton.setState(val);
    }

    public void pvpRepaint()
    {
        pvpGameButton.repaint();
    }
    public void setThisMenu(Menu thisMenu) {
        this.thisMenu = thisMenu;
        this.thisMenu.add(pvpGameButton);
        this.thisMenu.add(easyButton);
        this.thisMenu.add(mediumButton);
        this.thisMenu.add(hardButton);
        pvpGameButton.setThisMenu(thisMenu);
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("PVC is clicked");
            thisMenu.remove(this);
            thisMenu.remove(pvpGameButton);

            easyButton.setState(1);
            easyButton.repaint();
            mediumButton.setState(1);
            mediumButton.repaint();
            hardButton.setState(1);
            hardButton.repaint();

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
    Image PVPImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("PVPButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image PVPImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("PVPButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    PVPButtonComponent pvpGameButton = new PVPButtonComponent(PVPImage1, PVPImage2);
    Image easyImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("easyButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image easyImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("easyButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    EasyButtonComponent easyButton = new EasyButtonComponent(easyImage1, easyImage2);
    Image mediumImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("mediumButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image mediumImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("mediumButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    MediumButtonComponent mediumButton = new MediumButtonComponent(mediumImage1, mediumImage2);
    Image HardImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("hardButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image HardImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("hardButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    HardButtonComponent hardButton = new HardButtonComponent(HardImage1, HardImage2);

}