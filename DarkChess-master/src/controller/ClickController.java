package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;
import javax.swing.*;
import AI.*;

public class ClickController extends JComponent {
    private final Chessboard chessboard;
    private SquareComponent first;
    private SquareComponent squareComponent1,squareComponent2;
    private int AILevel;
    AI ai;

    public ClickController(int AILevel, Chessboard chessboard) {
        this.AILevel = AILevel;

        if(AILevel == 1)
            ai = new EasyAI(this,chessboard);
        if(AILevel == 2)
            ai = new MediumAI(this,chessboard);
        if(AILevel == 3)
            ai = new HardAI(this,chessboard);

        this.chessboard = chessboard;
    }

    public ChessColor p1,p2;
    boolean ifbegin=false;

    public boolean onClick(SquareComponent squareComponent) {
        if(AILevel==0){
            //判断第一次点击
            if(!ifbegin){
                ifbegin=true;
                squareComponent.setReversal(true);
                System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

                squareComponent1=squareComponent;
                squareComponent1.setMoved(true);

                squareComponent.repaint();

                p1=squareComponent.getChessColor();
                p2=(p1==ChessColor.BLACK)?ChessColor.RED:ChessColor.BLACK;
                chessboard.setCurrentColor(p2);
                ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
                chessboard.d.setWhosturn("p2");
                ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
                ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));

                int[] a=new int[7];
                a[0]=squareComponent.getChessboardPoint().getX();
                a[1]=squareComponent.getChessboardPoint().getY();
                a[6]=1;


                chessboard.d.setProcess(a);
            }
            else
            if (first == null) {
                if(squareComponent1!=null)
                {
                    squareComponent1.setMoved(false);
                    squareComponent1.repaint();
                    squareComponent1=null;
                }

                if(squareComponent2!=null)
                {
                    squareComponent2.setMoved(false);
                    squareComponent2.repaint();
                    squareComponent2=null;
                }

                if(squareComponent instanceof EmptySlotComponent) return false;
                if (handleFirst(squareComponent)) {
                    squareComponent.setSelected(true);
                    first = squareComponent;
                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(first.canMoveTo(chessboard.getChessComponents(), chessboard.getSquareComponents()[i][j].getChessboardPoint(), first.getChessColor()))
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(true);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    first.repaint();
                }
                return false;
            } else {
                if (first == squareComponent) { // 再次点击取消选取
                    squareComponent.setSelected(false);
                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(chessboard.getSquareComponents()[i][j].isIfmove())
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    SquareComponent recordFirst = first;
                    first = null;
                    recordFirst.repaint();
                } else if (handleSecond(squareComponent)) {
                    if(squareComponent.getChessColor()==p1)
                        p2.setPoint(p2.getPoint()+squareComponent.getValue());
                    else p1.setPoint(p1.getPoint()+squareComponent.getValue());


                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(chessboard.getSquareComponents()[i][j].isIfmove())
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }

                    int[] a=new int[7];
                    a[0]=first.getChessboardPoint().getX();
                    a[1]=first.getChessboardPoint().getY();
                    a[2]=squareComponent.getChessboardPoint().getX();
                    a[3]=squareComponent.getChessboardPoint().getY();
                    if(squareComponent.getChessColor()==ChessColor.BLACK)
                        a[4]=first.getRank()+1;
                    else a[4]=first.getRank()+10;
                    if(squareComponent.getChessColor()==ChessColor.BLACK)
                        a[5]=squareComponent.getRank()+1;
                    else a[5]=squareComponent.getRank()+10;
                    a[6]=1;
                    chessboard.d.setProcess(a);

                    //repaint in swap chess method.
                    squareComponent2=chessboard.swapChessComponents(first, squareComponent);
                    squareComponent1=first;

                    boolean b=chessboard.clickController.swapPlayer();
                    first.setSelected(false);
                    first = null;

                }
            }
            return false;
        }
        else {
            //判断第一次点击
            if(!ifbegin){
                ifbegin=true;
                squareComponent.setReversal(true);
                System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

                squareComponent1=squareComponent;
                squareComponent1.setMoved(true);

                squareComponent.repaint();

                p1=squareComponent.getChessColor();
                p2=(p1==ChessColor.BLACK)?ChessColor.RED:ChessColor.BLACK;
                chessboard.setCurrentColor(p1);
                ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
                chessboard.d.setWhosturn("p2");
                ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
                ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));
                ai.setAIColor(p2);

                int[] a=new int[7];
                a[0]=squareComponent.getChessboardPoint().getX();
                a[1]=squareComponent.getChessboardPoint().getY();
                a[6]=1;

                chessboard.d.setProcess(a);

                ai.begin();
                chessboard.clickController.swapPlayer();
            }
            else
            if (first == null) {
                if(squareComponent1!=null)
                {
                    squareComponent1.setMoved(false);
                    squareComponent1.repaint();
                    squareComponent1=null;
                }

                if(squareComponent2!=null)
                {
                    squareComponent2.setMoved(false);
                    squareComponent2.repaint();
                    squareComponent2=null;
                }

                if(squareComponent instanceof EmptySlotComponent) return false;
                if (handleFirstai(squareComponent)) {
                    squareComponent.setSelected(true);
                    first = squareComponent;
                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(first.canMoveTo(chessboard.getChessComponents(), chessboard.getSquareComponents()[i][j].getChessboardPoint(), first.getChessColor()))
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(true);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    first.repaint();
                }
                return false;
            } else {
                if (first == squareComponent) { // 再次点击取消选取
                    squareComponent.setSelected(false);
                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(chessboard.getSquareComponents()[i][j].isIfmove())
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    SquareComponent recordFirst = first;
                    first = null;
                    recordFirst.repaint();
                } else if (handleSecond(squareComponent)) {
                    if(squareComponent.getChessColor()==p1)
                        p2.setPoint(p2.getPoint()+squareComponent.getValue());
                    else p1.setPoint(p1.getPoint()+squareComponent.getValue());


                    for (int i=0;i<8;i++)
                        for (int j=0;j<4;j++)
                            if(chessboard.getSquareComponents()[i][j].isIfmove())
                            {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }

                    int[] a=new int[7];
                    a[0]=first.getChessboardPoint().getX();
                    a[1]=first.getChessboardPoint().getY();
                    a[2]=squareComponent.getChessboardPoint().getX();
                    a[3]=squareComponent.getChessboardPoint().getY();
                    if(squareComponent.getChessColor()==ChessColor.BLACK)
                        a[4]=first.getRank()+1;
                    else a[4]=first.getRank()+10;
                    if(squareComponent.getChessColor()==ChessColor.BLACK)
                        a[5]=squareComponent.getRank()+1;
                    else a[5]=squareComponent.getRank()+10;
                    a[6]=1;
                    chessboard.d.setProcess(a);

                    //repaint in swap chess method.
                    squareComponent2=chessboard.swapChessComponents(first, squareComponent);
                    squareComponent1=first;

                    boolean b=chessboard.clickController.swapPlayer();
                    if(b)return b;
                    first.setSelected(false);
                    first = null;

                    ai.begin();
                    chessboard.clickController.swapPlayer();
                }
            }
            return false;
        }
    }
    public boolean onClick1(SquareComponent squareComponent) {
        //判断第一次点击
        if(!ifbegin){
            ifbegin=true;
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

            squareComponent1=squareComponent;
            squareComponent1.setMoved(true);

            squareComponent.repaint();

            p1=squareComponent.getChessColor();
            p2=(p1==ChessColor.BLACK)?ChessColor.RED:ChessColor.BLACK;
            chessboard.setCurrentColor(p2);
            ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
            chessboard.d.setWhosturn("p2");
            ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
            ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));

            int[] a=new int[7];
            a[0]=squareComponent.getChessboardPoint().getX();
            a[1]=squareComponent.getChessboardPoint().getY();
            a[6]=1;
            chessboard.d.setProcess(a);

            return true;
        }
        else
            if (first == null) {
                if (squareComponent1 != null) {
                    squareComponent1.setMoved(false);
                    squareComponent1.repaint();
                    squareComponent1 = null;
                }

                if (squareComponent2 != null) {
                    squareComponent2.setMoved(false);
                    squareComponent2.repaint();
                    squareComponent2 = null;
                }
                if (squareComponent instanceof EmptySlotComponent) return false;
                if (handleFirst(squareComponent)) {
                    first = squareComponent;
                    first.repaint();
                    return false;
                }
                return true;
            } else {
                if (handleSecond(squareComponent)) {
                    if (squareComponent.getChessColor() == p1)
                        p2.setPoint(p2.getPoint() + squareComponent.getValue());
                    else p1.setPoint(p1.getPoint() + squareComponent.getValue());

                    int[] a = new int[7];
                    a[0] = first.getChessboardPoint().getX();
                    a[1] = first.getChessboardPoint().getY();
                    a[2] = squareComponent.getChessboardPoint().getX();
                    a[3] = squareComponent.getChessboardPoint().getY();
                    if (squareComponent.getChessColor() == ChessColor.BLACK)
                        a[4] = first.getRank() + 1;
                    else a[4] = first.getRank() + 10;
                    if (squareComponent.getChessColor() == ChessColor.BLACK)
                        a[5] = squareComponent.getRank() + 1;
                    else a[5] = squareComponent.getRank() + 10;
                    a[6] = 1;
                    chessboard.d.setProcess(a);

                    //repaint in swap chess method.
                    squareComponent2 = chessboard.swapChessComponents(first, squareComponent);
                    squareComponent1 = first;

                    boolean b=chessboard.clickController.swapPlayer();
                    first = null;
                    return true;
                } else return false;
            }
    }

    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */
    public boolean onClick2(SquareComponent squareComponent) {
        //判断第一次点击
        if(!ifbegin){
            ifbegin=true;
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

            squareComponent1=squareComponent;
            squareComponent1.setMoved(true);

            squareComponent.repaint();

            p1=squareComponent.getChessColor();
            p2=(p1==ChessColor.BLACK)?ChessColor.RED:ChessColor.BLACK;
            chessboard.setCurrentColor(p2);
            ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
            chessboard.d.setWhosturn("p2");
            ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
            ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));

            return true;
        }
        else
        if (first == null) {
            if(squareComponent1!=null)
            {
                squareComponent1.setMoved(false);
                squareComponent1.repaint();
                squareComponent1=null;
            }

            if(squareComponent2!=null)
            {
                squareComponent2.setMoved(false);
                squareComponent2.repaint();
                squareComponent2=null;
            }
            if(squareComponent instanceof EmptySlotComponent) return false;
            if (handleFirst2(squareComponent)) {
                first = squareComponent;
                first.repaint();
                return false;
            }
            System.out.println(chessboard.d.count);

            return true;
        } else {
            if (handleSecond(squareComponent)) {
                if(squareComponent.getChessColor()==p1)
                    p2.setPoint(p2.getPoint()+squareComponent.getValue());
                else p1.setPoint(p1.getPoint()+squareComponent.getValue());

                //repaint in swap chess method.
                squareComponent2=chessboard.swapChessComponents(first, squareComponent);
                squareComponent1=first;

                System.out.println(chessboard.d.count);

                boolean b=chessboard.clickController.swapPlayer();
                first = null;
                return true;
            }
            else return false;
        }
    }
    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

            squareComponent1=squareComponent;
            squareComponent1.setMoved(true);

            squareComponent.repaint();

            int[] a=new int[7];
            a[0]=squareComponent.getChessboardPoint().getX();
            a[1]=squareComponent.getChessboardPoint().getY();
            a[6]=1;
            chessboard.d.setProcess(a);
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }
    public boolean onClickai(SquareComponent squareComponent) {
        //判断第一次点击
            if (first == null) {
                if (squareComponent1 != null) {
                    squareComponent1.setMoved(false);
                    squareComponent1.repaint();
                    squareComponent1 = null;
                }

                if (squareComponent2 != null) {
                    squareComponent2.setMoved(false);
                    squareComponent2.repaint();
                    squareComponent2 = null;
                }

                if (squareComponent instanceof EmptySlotComponent) return false;
                if (handleFirst(squareComponent)) {
                    squareComponent.setSelected(true);
                    first = squareComponent;
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 4; j++)
                            if (first.canMoveTo(chessboard.getChessComponents(), chessboard.getSquareComponents()[i][j].getChessboardPoint(), first.getChessColor())) {
                                chessboard.getSquareComponents()[i][j].setIfmove(true);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    first.repaint();
                }
                return false;
            } else {
                if (first == squareComponent) { // 再次点击取消选取
                    squareComponent.setSelected(false);
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 4; j++)
                            if (chessboard.getSquareComponents()[i][j].isIfmove()) {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }
                    SquareComponent recordFirst = first;
                    first = null;
                    recordFirst.repaint();
                } else if (handleSecond(squareComponent)) {
                    if (squareComponent.getChessColor() == p1)
                        p2.setPoint(p2.getPoint() + squareComponent.getValue());
                    else p1.setPoint(p1.getPoint() + squareComponent.getValue());


                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 4; j++)
                            if (chessboard.getSquareComponents()[i][j].isIfmove()) {
                                chessboard.getSquareComponents()[i][j].setIfmove(false);
                                chessboard.getSquareComponents()[i][j].repaint();
                            }

                    int[] a = new int[7];
                    a[0] = first.getChessboardPoint().getX();
                    a[1] = first.getChessboardPoint().getY();
                    a[2] = squareComponent.getChessboardPoint().getX();
                    a[3] = squareComponent.getChessboardPoint().getY();
                    if (squareComponent.getChessColor() == ChessColor.BLACK)
                        a[4] = first.getRank() + 1;
                    else a[4] = first.getRank() + 10;
                    if (squareComponent.getChessColor() == ChessColor.BLACK)
                        a[5] = squareComponent.getRank() + 1;
                    else a[5] = squareComponent.getRank() + 10;
                    a[6] = 1;
                    chessboard.d.setProcess(a);

                    //repaint in swap chess method.
                    squareComponent2 = chessboard.swapChessComponents(first, squareComponent);
                    squareComponent1 = first;

                    boolean b=chessboard.clickController.swapPlayer();
                    first.setSelected(false);
                    first = null;

                }
            }
            return false;
    }
    private boolean handleFirstai(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

            squareComponent1=squareComponent;
            squareComponent1.setMoved(true);

            squareComponent.repaint();

            int[] a=new int[7];
            a[0]=squareComponent.getChessboardPoint().getX();
            a[1]=squareComponent.getChessboardPoint().getY();
            a[6]=1;
            chessboard.d.setProcess(a);
            chessboard.clickController.swapPlayer();

            ai.begin();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }
    private boolean handleFirst2(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());

            squareComponent1=squareComponent;
            squareComponent1.setMoved(true);

            squareComponent.repaint();

            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param squareComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(SquareComponent squareComponent) {
        return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint(), first.getChessColor());
    }

    public void renew()
    {
        chessboard.initAllChessOnBoard();
        p1.setPoint(0);
        p2.setPoint(0);
        ifbegin=false;
        ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
        ChessGameFrame.getStatusLabel1().setText(String.format("BLACK  Point"));
        ChessGameFrame.getStatusLabel2().setText(String.format("RED        Point"));
    }
    public void renew1()
    {
        p1.setPoint(0);
        p2.setPoint(0);
        ifbegin=false;
        ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
        ChessGameFrame.getStatusLabel1().setText(String.format("BLACK  Point"));
        ChessGameFrame.getStatusLabel2().setText(String.format("RED        Point"));
    }
    public boolean swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        if(chessboard.getCurrentColor()==p1)
        {
            ChessGameFrame.getStatusLabel().setText(String.format("p1-%s's TURN", p1.getName()));
            chessboard.d.setWhosturn("p1");
        }
        else {
            ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
            chessboard.d.setWhosturn("p2");
        }
        ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
        ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));
        if(p1.getPoint()>=60){
            String [] options = {"new game","exit"};
            int n =  JOptionPane.showOptionDialog(null,String.format("p1-%s win!!!",p1.getName()),"Game end",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            if(n==0){
                chessboard.initAllChessOnBoard();
                chessboard.clearDeadChessboard();
                p1.setPoint(0);
                p2.setPoint(0);
                ifbegin=false;
                ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
                ChessGameFrame.getStatusLabel1().setText(String.format("BLACK  Point"));
                ChessGameFrame.getStatusLabel2().setText(String.format("RED        Point"));
            }
            else java.lang.System.exit(0);
        }
        if(p2.getPoint()>=60){
            String [] options = {"new game","exit"};
            int n =  JOptionPane.showOptionDialog(null,String.format("p2-%s win!!!",p2.getName()),"Game end",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            if(n==0){
                chessboard.initAllChessOnBoard();
                chessboard.clearDeadChessboard();
                p1.setPoint(0);
                p2.setPoint(0);
                ifbegin=false;
                ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
                ChessGameFrame.getStatusLabel1().setText(String.format("BLACK  Point"));
                ChessGameFrame.getStatusLabel2().setText(String.format("RED        Point"));
            }
            else java.lang.System.exit(0);
        }
        return false;
    }
    public boolean swapPlayer1() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        if(chessboard.getCurrentColor()==p1)
            ChessGameFrame.getStatusLabel().setText(String.format("p1-%s's TURN", p1.getName()));
        else ChessGameFrame.getStatusLabel().setText(String.format("p2-%s's TURN", p2.getName()));
        ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
        ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));
        if(p1.getPoint()>=60){
            String [] options = {"new game","exit"};
            int n =  JOptionPane.showOptionDialog(null,String.format("105(p1-%s already win)",p1.getName()),"Game end",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            if(n==0){
                chessboard.initAllChessOnBoard();
                chessboard.clearDeadChessboard();
                p1.setPoint(0);
                p2.setPoint(0);
                ifbegin=false;
                ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
                ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
                ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));
            }
            return true;
        }
        if(p2.getPoint()>=60){
            String [] options = {"new game","exit"};
            int n =  JOptionPane.showOptionDialog(null,String.format("105(p2-%s already win)",p2.getName()),"Game end",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);		//选择对话框*/
            if(n==0){
                chessboard.initAllChessOnBoard();
                chessboard.clearDeadChessboard();
                p1.setPoint(0);
                p2.setPoint(0);
                ifbegin=false;
                ChessGameFrame.getStatusLabel().setText(String.format("p1's TURN"));
                ChessGameFrame.getStatusLabel1().setText(String.format("p1-%s Point: %d",p1.getName(),p1.getPoint()));
                ChessGameFrame.getStatusLabel2().setText(String.format("p2-%s Point: %d",p2.getName(),p2.getPoint()));

            }
            return true;
        }
        return false;
    }
}
