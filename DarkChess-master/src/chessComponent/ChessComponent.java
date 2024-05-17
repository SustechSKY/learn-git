package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.download;

import javax.swing.*;
import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    protected Image image ;
    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, download d) {
        super(chessboardPoint, location, chessColor, clickController, size,d);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制棋子填充色
        g.setColor(Color.ORANGE);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
       //绘制棋子边框
        g.setColor(Color.DARK_GRAY);
        g.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);

        if (isReversal) {
            //绘制棋子文字
            g.setColor(this.getChessColor().getColor());
            g.setFont(CHESS_FONT);
//            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
            g.drawImage(image,0,0,image.getWidth(this),image.getHeight(this),this);
            //绘制棋子被选中时状态
            if (isSelected()) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }

            if(isIfmove()){
                //绘制棋子文字
                g.setColor(this.getChessColor().getColor());
                g.setFont(CHESS_FONT);
                g.drawImage(image,0,0,image.getWidth(this),image.getHeight(this),this);
                g.setColor(Color.BLUE);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }
        }
        else if(isIfmove()){
            //绘制棋子文字
            g.setColor(this.getChessColor().getColor());
            g.setFont(CHESS_FONT);
            //绘制棋子被选中时状态
            g.setColor(Color.BLUE);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4f));
            g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        }
        if(this.ifcheat){
            if(state==1){
                g.setColor(this.getChessColor().getColor());
                g.setFont(CHESS_FONT);
//            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
                g.drawImage(image,0,0,image.getWidth(this),image.getHeight(this),this);
            }
            else {

                //绘制棋子填充色
                g.setColor(Color.ORANGE);
                g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
                //绘制棋子边框
                g.setColor(Color.DARK_GRAY);
                g.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }
        }
        if(isMoved())
        {
            g.setColor(Color.RED);
            g.drawRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
            g.drawRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);
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
            return destinationChess instanceof EmptySlotComponent||(destinationChess.isReversal&&destinationChess.getRank()<=this.getRank());
    }
}
