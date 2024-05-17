import view.Menu;
import javax.sound.sampled.*;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(500, 500);
            menu.setVisible(true);
            int a=0;
        });
    }
}
