package view;
import javax.swing.*;
import java.awt.*;;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;

    public Menu(int width, int height) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setTitle("Dark Chess");
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        ImageIcon icon =new ImageIcon("resource\\棋子图片\\红帅.png");
        setIconImage(icon.getImage());


        addTitle1();
        addTitle2();
        addBackground();
    }

    public void addBackground()
    {
        Image image = Toolkit.getDefaultToolkit().getImage("resource\\back1.png").getScaledInstance(500, 500, Image.SCALE_FAST);
        JComponent imageComponent = new TitleComponent(image);
        imageComponent.setSize(500, 500);
        imageComponent.setLocation(-5, -35);
        add(imageComponent);
    }
    public void addTitle1()
    {
        Image image = Toolkit.getDefaultToolkit().getImage("resource\\Title1.png").getScaledInstance(500, 200, Image.SCALE_FAST);
        JComponent imageComponent = new TitleComponent(image);
        imageComponent.setSize(500, 200);
        imageComponent.setLocation(0,-30 );
        add(imageComponent);
    }
    public void addTitle2()
    {
        Image image = Toolkit.getDefaultToolkit().getImage("resource\\Title2.png").getScaledInstance(400, 150, Image.SCALE_FAST);
        JComponent imageComponent = new TitleComponent(image);
        imageComponent.setSize(400, 150);
        imageComponent.setLocation(150,100 );
        add(imageComponent);
    }
    static class TitleComponent extends JComponent {
        Image paintImage;

        public TitleComponent(Image image) {
            this.setLayout(null);
            this.setFocusable(true);//Sets the focusable state of this Component to the specified value. This value overrides the Component's default focusability.
            this.paintImage = image;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(paintImage, 0, 0, paintImage.getWidth(this), paintImage.getHeight(this), this);
        }
    }

    Image NewGameImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("NewGameButton1.png")).getScaledInstance(200, 72, Image.SCALE_FAST);

    Image NewGameImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("NewGameButton2.png")).getScaledInstance(200, 72, Image.SCALE_FAST);
    NewGameButtonComponent newGameButton = new NewGameButtonComponent(NewGameImage1, NewGameImage2,this,500,500);
}
