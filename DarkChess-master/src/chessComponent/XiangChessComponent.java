package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class XiangChessComponent extends ChessComponent{
    public XiangChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=5;
        rank=5;
        if (this.getChessColor() == ChessColor.RED) {
            name = "红相";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红相.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        } else {
            name = "黑象";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑象.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        }
    }
}
