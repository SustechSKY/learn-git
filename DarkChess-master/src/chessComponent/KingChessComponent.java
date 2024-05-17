package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class KingChessComponent extends ChessComponent{
    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=30;
        rank=7;
        if (this.getChessColor() == ChessColor.RED) {
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红帅.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            name = "红帅";
        } else {
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑将.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            name = "黑将";
        }
    }
}
