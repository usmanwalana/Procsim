/**
 * Created by Usman on 02/03/2016.
 */
public class Processes
{
    public int pId;
    public int arrivalTime;
    public int relativeArrivalTime;
    public int burstTime;
    public int completionTime;
    public int turnAroundTime;
    public int waitingTime;
    public int remainingTime;
    public int state;
    //state = 1 means processed
    //state = 2 means arrived
    //state = 3 means ready

    public Processes(int pId , int burstTime , int arrivalTime)
    {
        this.pId = pId;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
    }
}
