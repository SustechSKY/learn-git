package AI;

import chessComponent.SquareComponent;
import controller.ClickController;
import view.Chessboard;

import java.util.ArrayList;
import java.util.Random;

public class EasyAI extends AI{
    private int x=0,y=0;
    private Random random = new Random();
    public EasyAI(ClickController clickController, Chessboard chessboard)
    {
        super(clickController, chessboard);
    }
    private void randomCoordinates()
    {
        x = random.nextInt(8);
        y = random.nextInt(4);
    }

    public void begin()
    {
        getSquareComponents();
        boolean canReverse = false;
        for (int i = 0; i < squareComponents.length; i++) {
            boolean flag = false;
            for (int j = 0; j < squareComponents[i].length; j++) {
                if(!squareComponents[i][j].isReversal()) {
                    canReverse = true;
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
        }
        if(!canReverse)
            think();
        else
        {
            int decide = random.nextInt(2);
            if(decide == 0)
                doingReverse();
            else
                think();
        }
    }

    private void doingReverse()
    {
        ArrayList<Integer> canReverseX = new ArrayList<>();
        ArrayList<Integer> canReverseY = new ArrayList<>();
        for (int i = 0; i < squareComponents.length; i++)
            for (int j = 0; j < squareComponents[i].length; j++)
                if(!squareComponents[i][j].isReversal()) {
                    canReverseX.add(i);
                    canReverseY.add(j);
                }
        int number = random.nextInt(canReverseX.size());
        reverse(canReverseX.get(number),canReverseY.get(number));
    }
    @Override
    protected void think() {
        ArrayList<Integer> canMoveToX = new ArrayList<>();
        ArrayList<Integer> canMoveToY = new ArrayList<>();
        boolean canOperate = false;
        while(!canOperate)
        {
            randomCoordinates();
            if(squareComponents[x][y].getChessColor() == AIColor)
            {
                SquareComponent squareComponent = squareComponents[x][y];
                int canMoveToCount = 0;

                if(squareComponent.getChessName().equals("红炮") || squareComponent.getChessName().equals("黑炮"))
                {
                    for (int i = 0; i < 7; i++){
                        if(i == x)
                            continue;
                        SquareComponent destinationComponent = squareComponents[i][y];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(i);
                            canMoveToY.add(y);
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        if(i == y)
                            continue;
                        SquareComponent destinationComponent = squareComponents[x][i];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(x);
                            canMoveToY.add(i);
                        }
                    }
                }
                else
                {
                    if(x-1>=0)
                    {
                        SquareComponent destinationComponent = squareComponents[x-1][y];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(x-1);
                            canMoveToY.add(y);
                        }
                    }
                    if(y-1>=0)
                    {
                        SquareComponent destinationComponent = squareComponents[x][y-1];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(x);
                            canMoveToY.add(y-1);
                        }
                    }
                    if(x+1<=7)
                    {
                        SquareComponent destinationComponent = squareComponents[x+1][y];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(x+1);
                            canMoveToY.add(y);
                        }
                    }
                    if(y+1<=3)
                    {
                        SquareComponent destinationComponent = squareComponents[x][y+1];
                        if(squareComponent.canMoveTo(chessboard.getChessComponents(),destinationComponent.getChessboardPoint(),squareComponent.getChessColor())) {
                            canMoveToCount++;
                            canMoveToX.add(x);
                            canMoveToY.add(y+1);
                        }
                    }
                }
                if(canMoveToCount != 0)
                    canOperate = true;
            }
            int number = random.nextInt(canMoveToX.size());
            move(x,y,canMoveToX.get(number),canMoveToY.get(number));
        }
    }

}
