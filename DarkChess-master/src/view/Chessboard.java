package view;

import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {


    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController;
    ;
    private final int CHESS_SIZE;

    public download d;

    private DeadChessboard blackPlate;
    private DeadChessboard redPlate;
    public void setSquareComponents(SquareComponent[][] squareComponents) {
        this.squareComponents = squareComponents;
    }
    public SquareComponent[][] getSquareComponents() {
        return squareComponents;
    }
    public Chessboard(int width, int height,download d,DeadChessboard blackPlate, DeadChessboard redPlate, int AILevel) {
        this.d=d;
        clickController = new ClickController(AILevel,this);
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
        initAllChessOnBoard();
        this.blackPlate = blackPlate;
        this.redPlate = redPlate;

    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
        squareComponent.repaint();
    }

    /**
     * 交换chess1 chess2的位置
     * @param chess1
     * @param chess2
     */
    public SquareComponent swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            addDeadChess(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE,d));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        chess1.setMoved(true);
        chess2.setMoved(true);
        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
        return chess2;
    }

    public void renew()
    {
        clickController.renew();
    }

    public void renew1()
    {
        clickController.renew1();
    }

    //FIXME:   Initialize chessboard for testing only.
    public void initAllChessOnBoard() {
        int a[][]=new int[2][7];int c[]=new int[2];
        Random random = new Random();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                int k1=random.nextInt(2);
                while(c[k1]>=16)
                {
                    k1=random.nextInt(2);
                }
                c[k1]++;
                ChessColor color = k1 == 0 ? ChessColor.RED : ChessColor.BLACK;
                SquareComponent squareComponent;
                int k2=random.nextInt(7);
                while(true)
                {
                    if(k2==0&&a[k1][k2]<5)break;
                    else if(k2==6&&a[k1][k2]<1)break;
                    else if(k2!=0&&k2!=6&&a[k1][k2]<2)break;
                    else k2=random.nextInt(7);
                }
                a[k1][k2]++;
                switch (k2)
                {
                    case 0:squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 1:squareComponent = new PaoChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 2:squareComponent = new MaChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 3:squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 4:squareComponent = new XiangChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 5:squareComponent = new ShiChesscomponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    case 6:squareComponent = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);break;
                    default:{
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE,d);}
                }
                squareComponent.setVisible(true);
                if(k1==0)
                d.setChesses(i,j,(char) ('a'+k2));
                else d.setChesses(i,j,(char)('A'+k2));
                putChessOnBoard(squareComponent);
            }
        }

    }

    /**
     * 绘制棋盘格子
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    /**
     * 通过GameController调用该方法
     * @param chessData
     */
    public void loadGame(List<String> chessData) {
        char[][] chesses=new char[8][4];
        int a=0;
        for (int i=0;i<chesses.length;i++){
            if(chessData.get(i).length()!=chesses[i].length)
            {
                a=2;break;
            }
            for (int j=0;j<chesses[i].length;j++){
                chesses[i][j]=chessData.get(i).charAt(j);
                d.setChesses(i,j,chesses[i][j]);
            }
        }
        if(a==2){
            JOptionPane.showMessageDialog(null,"Error 102","Error",JOptionPane.WARNING_MESSAGE);
            return;
        }//报错退出102
        a=initialGameByCharacters(chesses);

        if (chessData.get(8).length()==0){
            JOptionPane.showMessageDialog(null,"Error 104","Error",JOptionPane.WARNING_MESSAGE);
            return;
        }//报错退出104
        if(((chessData.get(8).charAt(0)<='G')&&(chessData.get(8).charAt(0)>='A'))||((chessData.get(8).charAt(0)<='g')&&(chessData.get(8).charAt(0)>='a'))){
            JOptionPane.showMessageDialog(null,"Error 102","Error",JOptionPane.WARNING_MESSAGE);;//报错退出102
            return;
        }
        if(a==2){
            JOptionPane.showMessageDialog(null,"Error 102","Error",JOptionPane.WARNING_MESSAGE);
        }//报错退出102
        if(a==3){
            JOptionPane.showMessageDialog(null,"Error 103","Error",JOptionPane.WARNING_MESSAGE);
            return;
        }//报错退出103

        if(chessData.get(8).charAt(0)!='p'){
            JOptionPane.showMessageDialog(null,"Error 104","Error",JOptionPane.WARNING_MESSAGE);
            return;
        }//报错退出104

        int b[][]=new int[9][5];
        for(int i=9;i<=chessData.size();i++)
        {
            if(chessData.get(i).charAt(chessData.get(i).length()-1)=='0')break;
            if(chessData.get(i).length()!=6&&chessData.get(i).length()!=9) {
                JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);//报错105
                return;
            }
            if(chessData.get(i).length()==6){
                int x=chessData.get(i).charAt(0)-'0',y=chessData.get(i).charAt(2)-'0';
                if(x>8||y>5){
                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }//105
                if(b[x][y]==0)b[x][y]=1;
                else {
                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }//105
                boolean c=squareComponents[x][y].clickController.onClick1(squareComponents[x][y]);
                if(!c){
                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if(chessData.get(i).length()==9){
                int x1=chessData.get(i).charAt(0)-'0',y1=chessData.get(i).charAt(2)-'0',x2=chessData.get(i).charAt(4)-'0',y2=chessData.get(i).charAt(6)-'0';
                if(b[x1][y1]!=1){
                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }//报错105
//                if(b[x2][y2]==0){
//                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
//                    return;
//                }//报错105
                b[x1][y1]=0;b[x2][y2]=1;
                squareComponents[x1][y1].clickController.onClick1(squareComponents[x1][y1]);
                boolean c= squareComponents[x2][y2].clickController.onClick1(squareComponents[x2][y2]);
                if(!c){
                    JOptionPane.showMessageDialog(null,"Error 105","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }//报错105
            }
        }
        if(squareComponents[0][0].clickController.p1==this.getCurrentColor()){
            if (chessData.get(8).length()<1||chessData.get(8).charAt(1)-'0'!=1){
                JOptionPane.showMessageDialog(null,"Error 104","Error",JOptionPane.WARNING_MESSAGE);
                return;
            }//报错105
        }
        else if (chessData.get(8).length()<1||chessData.get(8).charAt(1)-'0'!=2){
            JOptionPane.showMessageDialog(null,"Error 104","Error",JOptionPane.WARNING_MESSAGE);
            return;
        }//报错105
    }


    public void restartGame() {
        char[][] chesses=d.chesses;
        int a=initialGameByCharacters(chesses);

        for(int i=0;i<d.count;i++)
        {
            System.out.println(String.format("%d %d %d",i,d.getProcess()[i][6],d.count));
            if(d.getProcess()[i][5]==0){
                int x=d.getProcess()[i][0],y=d.getProcess()[i][1];
                System.out.println(d.count);
                squareComponents[x][y].clickController.onClick2(squareComponents[x][y]);
                System.out.println(d.count);
            }
            else {
                int x1=d.getProcess()[i][0],y1=d.getProcess()[i][1],x2=d.getProcess()[i][2],y2=d.getProcess()[i][3];
                squareComponents[x1][y1].clickController.onClick2(squareComponents[x1][y1]);
                squareComponents[x2][y2].clickController.onClick2(squareComponents[x2][y2]);
            }
        }
    }

    public int initialGameByCharacters(char[][] chars)
    {
        int a[][]=new int[2][7];
        SquareComponent squareComponent;
        for (int i = 0; i < squareComponents.length; i++) {
            if(squareComponents[i].length>4)return 2;
            for (int j = 0; j < squareComponents[i].length; j++) {
                switch (chars[i][j]) {
                    case 'A':
                        a[0][0]++;if(a[0][0]>5)return 3;
                        break;
                    case 'B':
                        a[0][1]++;if(a[0][1]>2)return 3;
                        break;
                    case 'C':
                        a[0][2]++;if(a[0][2]>2)return 3;
                        break;
                    case 'D':
                        a[0][3]++;if(a[0][3]>2)return 3;
                        break;
                    case 'E':
                        a[0][4]++;if(a[0][4]>2)return 3;
                        break;
                    case 'F':
                        a[0][5]++;if(a[0][5]>2)return 3;
                        break;
                    case 'G':
                        a[0][6]++;if(a[0][6]>1)return 3;
                        break;
                    case 'a':
                        a[1][0]++;if(a[1][0]>5)return 3;
                        break;
                    case 'b':
                        a[1][1]++;if(a[1][1]>2)return 3;
                        break;
                    case 'c':
                        a[1][2]++;if(a[1][2]>2)return 3;
                        break;
                    case 'd':
                        a[1][3]++;if(a[1][3]>2)return 3;
                        break;
                    case 'e':
                        a[1][4]++;if(a[1][4]>2)return 3;
                        break;
                    case 'f':
                        a[1][5]++;if(a[1][5]>2)return 3;
                        break;
                    case 'g':
                        a[1][6]++;if(a[1][6]>2)return 3;
                        break;
                    default: {
                        if(!(chars[i][j]==' '))return 3;
                    }
                }
                switch (chars[i][j])
                {
                    case 'A':squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'B':squareComponent = new PaoChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'C':squareComponent = new MaChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'D':squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'E':squareComponent = new XiangChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'F':squareComponent = new ShiChesscomponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'G':squareComponent = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.BLACK, clickController, CHESS_SIZE,d);break;
                    case 'a':squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'b':squareComponent = new PaoChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'c':squareComponent = new MaChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'd':squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'e':squareComponent = new XiangChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'f':squareComponent = new ShiChesscomponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    case 'g':squareComponent = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j),ChessColor.RED, clickController, CHESS_SIZE,d);break;
                    default:{
                        squareComponent = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE,d);}
                }
                if(!(squareComponent instanceof EmptySlotComponent))
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }
        return 0;
    }
    public void addDeadChess(SquareComponent squareComponent)
    {
        if(squareComponent.getChessColor() == ChessColor.BLACK)
            blackPlate.addDeadChess(squareComponent);
        else
            redPlate.addDeadChess(squareComponent);
    }

    public  void clearDeadChessboard()
    {
        blackPlate.clear();
        redPlate.clear();
    }
}
