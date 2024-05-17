package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class ShiChesscomponent extends ChessComponent{
    public ShiChesscomponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=10;
        rank=6;
        if (this.getChessColor() == ChessColor.RED) {
            name = "红士";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红士.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        } else {
            name = "黑仕";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑仕.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        }
    }
}
