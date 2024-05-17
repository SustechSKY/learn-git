package view;


import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static JLabel statusLabel;
    private static JLabel statusLabel1;
    private static JLabel statusLabel2;

    private Chessboard chessboard;
    private DeadChessboard chessboardBlack;
    private DeadChessboard chessboardRed;

    public ChessGameFrame(int width, int height,int AILevel) {
        setTitle("中国象棋-暗棋"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT *4/5;//棋盘高度占窗口高度的4/5

        setSize(WIDTH+200, HEIGHT);//设置窗口大小
        setLocationRelativeTo(null); // 使窗口居中
        getContentPane().setBackground(Color.WHITE);//设置背景颜色
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        ImageIcon icon =new ImageIcon(getClass().getClassLoader().getResource("棋子图片\\红帅.png"));



        setIconImage(icon.getImage());
        setResizable(false);

        download d=new download();

        this.chessboardBlack = new DeadChessboard(142,568);
        chessboardBlack.setLocation(HEIGHT / 10+100+CHESSBOARD_SIZE / 2+6,HEIGHT / 10+4);
        add(chessboardBlack);

        this.chessboardRed = new DeadChessboard(142,568);
        chessboardRed.setLocation(25,HEIGHT / 10+4);
        add(chessboardRed);

        this.chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE,d,chessboardBlack,chessboardRed,AILevel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10+100, HEIGHT / 10);
        add(chessboard);



        addLabel();
        addLabel1();
        addLabel2();
        addCheatButton();
        addReNewButton();
        if(AILevel == 0)
            addSaveButton();
        addWithdrawButton();
        addBackGround();
    }


    /**
     * 在游戏窗体中添加棋盘
     */
/*    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }*/

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("p1's TURN");
        statusLabel.setLocation(WIDTH * 3 / 5+190, HEIGHT / 10-60);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        statusLabel.setForeground(Color.RED);
        add(statusLabel);
    }
    private void addLabel1() {
        statusLabel1 = new JLabel("BLACK  Point");
        statusLabel1.setLocation(WIDTH * 3 / 5+190, HEIGHT / 10+50);
        statusLabel1.setSize(300, 60);
        statusLabel1.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel1);
    }
    private void addLabel2() {
        statusLabel2 = new JLabel("RED        Point");
        statusLabel2.setLocation(WIDTH * 3 / 5+190, HEIGHT / 10+100);
        statusLabel2.setSize(300, 60);
        statusLabel2.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel2);
    }
    public static JLabel getStatusLabel() {
        return statusLabel;
    }
    public static JLabel getStatusLabel1() {
        return statusLabel1;
    }
    public static JLabel getStatusLabel2() {
        return statusLabel2;
    }
    Image CheatButtonImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("CheatButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image CheatButtonImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("CheatButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    Image CheatButtonImage3 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("CheatButton3.png")).getScaledInstance(191, 72, Image.SCALE_FAST);

    CheatButtonComponent buttonComponent;
    private void addCheatButton()
    {
        buttonComponent = new CheatButtonComponent(CheatButtonImage1, CheatButtonImage2, CheatButtonImage3,chessboard);
        buttonComponent.setLocation(WIDTH * 3 / 5+200, HEIGHT / 10 + 170);
        buttonComponent.setSize(191, 72);
        add(buttonComponent);
    }
    Image ReNewGameButtonImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("ReNewGameButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);

    Image ReNewGameButtonImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("ReNewGameButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    ReNewGameButtonComponent reNewGameButtonComponent;

    private void addReNewButton()
    {
        reNewGameButtonComponent = new ReNewGameButtonComponent(ReNewGameButtonImage1, ReNewGameButtonImage2, chessboard);
        reNewGameButtonComponent.setLocation(WIDTH * 3 / 5+200, HEIGHT / 10 + 270);
        reNewGameButtonComponent.setSize(191,72);
        add(reNewGameButtonComponent);
    }

    Image withdrawButtonImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("WithdrawButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);

    Image withdrawButtonImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("WithdrawButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    WithdrawButtonComponent withdrawButtonComponent;

    private void addWithdrawButton()
    {
        withdrawButtonComponent = new WithdrawButtonComponent(withdrawButtonImage1,withdrawButtonImage2,chessboard);
        withdrawButtonComponent.setLocation(WIDTH * 3 / 5+200, HEIGHT / 10 + 370);
        withdrawButtonComponent.setSize(191, 72);
        add(withdrawButtonComponent);
    }

    Image SaveGameButtonImage1 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("SaveGameButton1.png")).getScaledInstance(191, 72, Image.SCALE_FAST);

    Image SaveGameButtonImage2 = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("SaveGameButton2.png")).getScaledInstance(191, 72, Image.SCALE_FAST);
    SaveGameButtonComponent saveGameButtonComponent;

    private void addSaveButton()
    {
        saveGameButtonComponent = new SaveGameButtonComponent(SaveGameButtonImage1,SaveGameButtonImage2,this);
        saveGameButtonComponent.setLocation(WIDTH * 3 / 5+200, HEIGHT / 10 + 470);
        saveGameButtonComponent.setSize(191, 72);
        add(saveGameButtonComponent);
    }

    //    private void addreturnButton(){
//        JButton button = new JButton("return");
//        button.addActionListener((e) -> {
//            chessboard.d.count=0;
//            while(chessboard.d.getProcess()[chessboard.d.count][6]!=0)
//            chessboard.d.count++;
//            if(chessboard.d.count!=0)
//            {
//                chessboard.d.getProcess()[chessboard.d.count-1][6]=0;
//                chessboard.d.count--;
//            }
//            else {
//                JOptionPane.showMessageDialog(null,"Now is the first step!!!","Error",JOptionPane.WARNING_MESSAGE);
//            }
//            chessboard.renew1();
//            chessboard.clearDeadChessboard();
//            chessboard.restartGame();
//        });
//        button.setLocation(WIDTH * 3 / 5+200, HEIGHT / 10 + 440);
//        button.setSize(180, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }
    Image BackGround = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("BackGround.png")).getScaledInstance(920, 720, Image.SCALE_FAST);

    BackGroundComponent backGroundComponent;

    private void addBackGround()
    {
        backGroundComponent = new BackGroundComponent(BackGround,this);
        backGroundComponent.setSize(920,720);
        backGroundComponent.setLocation(0,0);
        add(backGroundComponent);
    }

    public void write() throws IOException{
        Date date = new Date();
        String time = date.toString().substring(24) + " " + date.toString().substring(4, 7) + "-" + date.toString().substring(8, 10) + " " + date.toString().substring(11,13)+"-"+date.toString().substring(14,16)+"-"+date.toString().substring(17,19);
        String dir = "archive\\"+time+".txt";
        File file=new File(dir);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);

        for(int i=0;i<chessboard.d.getChesses().length;i++)
        {
            for (int j=0;j<chessboard.d.getChesses()[i].length;j++)
                writer.write(chessboard.d.getChesses()[i][j]);
            writer.write("\n");
        }
        writer.write(chessboard.d.getWhosturn());
        writer.write(" ");
        writer.write(String.format("%d",chessboard.d.count));
        writer.write("\n");
        for (int i=0;i<chessboard.d.getProcess().length;i++)
        {
            if(chessboard.d.getProcess()[i][4]==0)
                writer.write(String.format("%d %d  %d\n",chessboard.d.getProcess()[i][0],chessboard.d.getProcess()[i][1],chessboard.d.getProcess()[i][6]));
            else writer.write(String.format("%d %d %d %d %d\n",chessboard.d.getProcess()[i][0],chessboard.d.getProcess()[i][1],chessboard.d.getProcess()[i][2],chessboard.d.getProcess()[i][3],chessboard.d.getProcess()[i][6]));
        }

    }

    public void loadGame(String path) {
        gameController.loadGameFromFile(path);
    }

}
