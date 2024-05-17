package view;

public class download {
    public char[][] chesses=new char[8][4];
    public String whosturn;
    public int[][] process=new int[10000][7];
    public int count;

    public void setChesses(int x,int y,char chess) {
        chesses[x][y]=chess;
    }

    public char[][] getChesses() {
        return chesses;
    }

    public String getWhosturn() {
        return whosturn;
    }

    public void setWhosturn(String whosturn) {
        this.whosturn = whosturn;
    }

    public int[][] getProcess() {
        return process;
    }

    public void setProcess(int[] process) {
        for(int i=0;i<7;i++)
        this.process[count][i] = process[i];
        count++;
    }
}
