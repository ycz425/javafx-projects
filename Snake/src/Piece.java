public class Piece {

    private int x;
    private int y;
    private boolean stall;

    public Piece(int x, int y){
        this.x = x;
        this.y = y;
        stall = false;
    }
    public Piece(int x, int y, boolean stall){
        this.x = x;
        this.y = y;
        this.stall = stall;
    }

    public boolean stalling(){return stall;}
    public void setStall(boolean stall){this.stall = stall;}

    public int getX(){return x;}
    public int getY(){return y;}

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
}
