package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import java.awt.*;

public class PaoChessComponent extends ChessComponent{
    public PaoChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
        value=5;
        rank=2;
        if (this.getChessColor() == ChessColor.RED) {
            name = "红炮";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\红炮.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        } else {
            name = "黑炮";
            image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("棋子图片\\黑炮.png")).getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        }
    }

    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination,ChessColor chesscolor) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        if(destinationChess instanceof EmptySlotComponent)return false;
        if (destinationChess.isReversal()&&destinationChess.getChessColor() == chesscolor)return false;
        if(Math.abs(destinationChess.getX()-this.getX())>0)
            if(Math.abs(destinationChess.getY()-this.getY())>0)
                return false;
            else {
                int x=this.getX(),tot=0;
                if(destinationChess.getX()>this.getX())
                {
                    x=x+this.getChesssize();
                    while(x<destinationChess.getX()){
                        if(!(chessboard[destination.getX()][destination.getY()-(x-this.getX())/this.getChesssize()] instanceof EmptySlotComponent))tot++;
                        x=x+this.getChesssize();
                    }
                }
                else{
                    x=x-this.getChesssize();
                    while(x>destinationChess.getX()){
                        if(!(chessboard[destination.getX()][destination.getY()+(this.getX()-x)/this.getChesssize()]  instanceof EmptySlotComponent))tot++;
                        x=x-this.getChesssize();
                    }
                }
                if(tot!=1)return false;
                else return true;
            }
        else {
            int y=this.getY(),tot=0;
            if(destinationChess.getY()>this.getY()){
                y=y+this.getChesssize();
                while(y<destinationChess.getY()){
                    if(!(chessboard[destination.getX()-(y-this.getY())/this.getChesssize()][destination.getY()]  instanceof EmptySlotComponent))tot++;
                    y=y+this.getChesssize();
                }
            }

            else{
                y=y-this.getChesssize();
                while(y>destinationChess.getY()){
                    if(!(chessboard[destination.getX()+(this.getY()-y)/this.getChesssize()][destination.getY()]  instanceof EmptySlotComponent))tot++;
                    y=y-this.getChesssize();
                }
            }
            if(tot!=1)return false;
            else return true;
        }

    }
}
