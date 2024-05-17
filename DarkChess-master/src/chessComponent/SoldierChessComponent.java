package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class SoldierChessComponent extends ChessComponent {

    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=1;
        rank=1;
        if (this.getChessColor() == ChessColor.RED) {
            name = "红兵";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红兵.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        } else {
            name = "黑卒";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑卒.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        }
    }
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination,ChessColor chesscolor) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        if(destinationChess.getChessColor() == chesscolor)return false;
        if (!destinationChess.isReversal()) {
            //没翻开且非空棋子不能走
            if (!(destinationChess instanceof EmptySlotComponent)) {
                return false;
            }
        }
        if(Math.abs(destinationChess.getX()-this.getX())> this.getChesssize())
            return false;
        else if(Math.abs(destinationChess.getX()-this.getX())==this.getChesssize()&&destinationChess.getY()!=this.getY())
            return false;
        else if(Math.abs(destinationChess.getY()-this.getY())>this.getChesssize())
            return false;
        else
            return destinationChess instanceof EmptySlotComponent||(destinationChess.isReversal&&(destinationChess.getRank()<=this.getRank()||destinationChess.getRank()==7));
        //todo: complete this method
    }

}
