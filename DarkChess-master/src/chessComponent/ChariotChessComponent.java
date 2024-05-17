package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

/**
 * 表示黑红车
 */
public class ChariotChessComponent extends ChessComponent {

    public ChariotChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=5;
        rank=4;
        if (this.getChessColor() == ChessColor.RED) {
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红车.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            name = "红车";
        } else {
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑車.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            name = "黑車";
        }
    }

}
