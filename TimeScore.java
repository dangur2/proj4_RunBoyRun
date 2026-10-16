public class TimeScore {
    private int time;
    private int extraScore;
    private int second;

    public void calculate(){
        time ++;
        second = time / 60;
    }
    public void addScore(int x){
        extraScore += x;
    }
    public int getTime(){
        return second;
    }
    public int getScore(){
        return second / 2 + extraScore;
    }
}
