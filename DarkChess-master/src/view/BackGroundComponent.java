package view;

import javax.swing.*;
import java.awt.*;

public class BackGroundComponent extends JComponent {
    Image paintImage;
    ChessGameFrame frame;

    public BackGroundComponent(Image image,ChessGameFrame frame) {
        this.setLayout(null);
        this.setFocusable(true);//Sets the focusable state of this Component to the specified value. This value overrides the Component's default focusability.
        this.paintImage = image;
        repaint();
        this.frame = frame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(paintImage, 0, 0, paintImage.getWidth(this), paintImage.getHeight(this), this);
    }
}