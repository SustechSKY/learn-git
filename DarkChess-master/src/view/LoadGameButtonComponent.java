package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class LoadGameButtonComponent extends JComponent {
    Image Image1;
    Image Image2;
    int state = 1;
    Menu thisMenu;

    public LoadGameButtonComponent(Image image1, Image image2) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setLayout(null);
        this.setFocusable(true);
        this.Image1 = image1;
        this.Image2 = image2;
    }

    public void setState(int state) {
        this.state = state;
    }
    public void setThisMenu(Menu menu)
    {
        this.thisMenu = menu;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Load Game is clicked");
            JFileChooser chooser = new JFileChooser();
            File dir = new File("archive");
            chooser.setCurrentDirectory(dir);
            int flag = chooser.showOpenDialog(this);
            if(flag == JFileChooser.APPROVE_OPTION)
                System.out.println("选择了时间为 " + chooser.getSelectedFile().getName()+" 的存档");
            File file = chooser.getSelectedFile();
            String path = file.getPath();
            if(path.substring(path.length()-4).equals(".txt")) {
                System.out.println(path);
                ChessGameFrame mainFrame = new ChessGameFrame(720, 720,0);
                mainFrame.loadGame(path);
                mainFrame.setVisible(true);
                thisMenu.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Error 101","Error",JOptionPane.WARNING_MESSAGE);//最后一个是前面的图标，我的建议是warning就挺好
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
        if (state == 1)
            g.drawImage(Image1, 0, 0, Image1.getWidth(this), Image1.getHeight(this), this);
        else if(state == 2)
            g.drawImage(Image2, 0, 0, Image2.getWidth(this), Image2.getHeight(this), this);
    }
}