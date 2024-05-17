package AI;
import chessComponent.SquareComponent;
import controller.ClickController;
import model.ChessColor;
import view.Chessboard;

public abstract class AI {
    protected ChessColor AIColor;
    protected ClickController clickController;
    protected Chessboard chessboard;
    protected SquareComponent[][] squareComponents;
    public AI(ClickController clickController, Chessboard chessboard)
    {
        this.clickController = clickController;
        this.chessboard = chessboard;
    }

    public void setAIColor(ChessColor AIColor) {
        this.AIColor = AIColor;
    }

    protected abstract void think();

    protected void move(int startX, int startY, int endX, int endY)
    {
        clickController.onClickai(squareComponents[startX][startY]);
        clickController.onClickai(squareComponents[endX][endY]);
    }

    protected void reverse(int x,int y){
        clickController.onClickai(squareComponents[x][y]);
    }
    abstract public void begin();
    protected void getSquareComponents()
    {
        this.squareComponents = chessboard.getSquareComponents();
    }
}
