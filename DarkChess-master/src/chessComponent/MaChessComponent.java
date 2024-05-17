package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class MaChessComponent extends ChessComponent{
    public MaChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
            rank = 3;
            value = 5;
        if (this.getChessColor() == ChessColor.RED) {
            name = "红马";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红马.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        } else {
            name = "黑馬";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑馬.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        }
    }

}
