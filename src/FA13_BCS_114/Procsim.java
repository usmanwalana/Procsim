import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Usman on 01/03/2016.
 */
public class Procsim
{
    public void makeTimeRelative(ArrayList<Processes> processes)
    {
        processes.get(0).relativeArrivalTime = processes.get(0).arrivalTime;
        for (int looper = 1; looper < processes.size(); looper++)
        {
            processes.get(looper).relativeArrivalTime = processes.get(looper - 1).relativeArrivalTime + processes.get(looper).arrivalTime;
        }
    }
    public void output(ArrayList<Processes> processes)
    {
        System.out.println("ID\tCT\tTAT\tWT");

        for (Processes process : processes)
        {
            System.out.println(process.pId + "\t" + process.completionTime + "\t" + process.turnAroundTime + "\t" + process.waitingTime);
        }
    }
    public void finalOutput(ArrayList<Processes> processes)
    {
        float totalTimeTaken = 0,totalTimeWaiting = 0,averageTurnAroundTime = 0;
        for (Processes process : processes)
        {
            totalTimeTaken += process.turnAroundTime;
            totalTimeWaiting += process.waitingTime;
        }
        averageTurnAroundTime = totalTimeTaken / processes.size();
        System.out.println("Total Time Taken : " + totalTimeTaken);
        System.out.println("Total Time Waiting : " + totalTimeWaiting);
        System.out.println("Average TurnAround Time : " + averageTurnAroundTime);
    }
    public void outputParameters(ArrayList<Processes> processes)
    {
        System.out.println("ID\tBT\tAT\tWT");

        for (Processes process : processes)
        {
            System.out.println(process.pId + "\t" + process.burstTime + "\t" + process.arrivalTime + "\t" + process.waitingTime);
        }
    }
    public void fcfs(ArrayList<Processes> processes)
    {
        int sum = 0,difference=0;
        makeTimeRelative(processes);
        sum = processes.get(0).relativeArrivalTime + processes.get(0).burstTime;
        processes.get(0).completionTime = sum;
        for (int looper = 1; looper < processes.size(); looper++)
        {
            if(processes.get(looper).relativeArrivalTime > processes.get(looper - 1).completionTime)
            {
                difference = processes.get(looper).relativeArrivalTime - processes.get(looper - 1).completionTime;
                processes.get(looper).completionTime = processes.get(looper - 1).completionTime + difference + processes.get(looper).burstTime;
            }
            else
            {
                processes.get(looper).completionTime = processes.get(looper - 1).completionTime + processes.get(looper).burstTime;
            }
        }
        for (Processes process : processes)
        {
            process.turnAroundTime = process.completionTime - process.relativeArrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;
        }
        //output(processes);
        finalOutput(processes);

    }
    public int sjfp1(ArrayList<Processes> processes , int currentTime)
    {
        for (Processes process : processes)
        {
            if (process.relativeArrivalTime <= currentTime && process.state == 1)
            {
                process.state = 2;
            }
        }
        int selectedBurstTime = processes.get(0).burstTime;
        int p = 0;
        for (int looper = 0; looper < processes.size(); looper++)
        {
            if(processes.get(looper).state == 2)
            {
                //find process with min burst time
                if(processes.get(looper).burstTime < selectedBurstTime)
                {
                    selectedBurstTime = processes.get(looper).burstTime;
                    p =looper;
                }
            }
        }
        currentTime += selectedBurstTime;
        processes.get(p).completionTime = currentTime;
        processes.get(p).state = 0;
        processes.get(p).turnAroundTime = processes.get(p).completionTime - processes.get(p).relativeArrivalTime;
        processes.get(p).waitingTime = processes.get(p).turnAroundTime - processes.get(p).burstTime;
        return currentTime;
    }
    public void sjfnp(ArrayList<Processes> processes)
    {
        makeTimeRelative(processes);
        int temp;
        for (int looper = 0; looper < processes.size(); looper++)
        {
            for (int looper1 = 0; looper1 < processes.size() - 1; looper1++)
            {
                if(processes.get(looper1).burstTime > processes.get(looper1 + 1).burstTime)
                {
                    temp = processes.get(looper1).burstTime;
                    processes.get(looper1).burstTime = processes.get(looper1 + 1).burstTime;
                    processes.get(looper1 + 1).burstTime = temp;

                    temp = processes.get(looper1).waitingTime;
                    processes.get(looper1).waitingTime = processes.get(looper1 + 1).waitingTime;
                    processes.get(looper1 + 1).waitingTime = temp;
                }
            }
        }
        for (int looper = 0; looper < processes.size(); looper++)
        {
            processes.get(looper).turnAroundTime = processes.get(looper).burstTime + processes.get(looper).waitingTime;
            if(looper + 1 < processes.size())
            {
                processes.get(looper + 1).waitingTime = processes.get(looper).turnAroundTime;
            }
        }
        int s = processes.size() - 1;
        processes.get(s).turnAroundTime = processes.get(s).waitingTime + processes.get(s).burstTime;

        //output(processes);
        finalOutput(processes);

    }
    public void sjfp(ArrayList<Processes> processes)
    {
        makeTimeRelative(processes);
        int temp;
        for (int looper = 0; looper < processes.size(); looper++)
        {
            for (int looper1 = 0; looper1 < processes.size() - 1; looper1++)
            {
                if(processes.get(looper1).burstTime > processes.get(looper1 + 1).burstTime)
                {
                    temp = processes.get(looper1).burstTime;
                    processes.get(looper1).burstTime = processes.get(looper1 + 1).burstTime;
                    processes.get(looper1 + 1).burstTime = temp;

                    temp = processes.get(looper1).waitingTime;
                    processes.get(looper1).waitingTime = processes.get(looper1 + 1).waitingTime;
                    processes.get(looper1 + 1).waitingTime = temp;
                }
            }
        }
        for (int looper = 0; looper < processes.size(); looper++)
        {
            processes.get(looper).turnAroundTime = processes.get(looper).burstTime + processes.get(looper).waitingTime;
            if(looper + 1 < processes.size())
            {
                processes.get(looper + 1).waitingTime = processes.get(looper).turnAroundTime;
            }
        }
        int s = processes.size() - 1;
        processes.get(s).turnAroundTime = processes.get(s).waitingTime + processes.get(s).burstTime;

        //output(processes);
        finalOutput(processes);
    }
    public ArrayList<Processes> checkIfArrived(ArrayList<Processes> processes , int currentTime)
    {
        ArrayList<Processes> arrivedQueue = new ArrayList<Processes>();

        for (Processes process : processes)
        {
            if (process.relativeArrivalTime <= currentTime && process.state != 1)
            {
                process.state = 2;
                arrivedQueue.add(process);
            }
        }
        return arrivedQueue;
    }
    public ArrayList<Processes> r1(ArrayList<Processes> processes)
    {

        return processes;
    }
    public void roundRobin(ArrayList<Processes> processes , int quantumTime)
    {
        int sum = 0;
        makeTimeRelative(processes);

        for (Processes process : processes)
        {
            process.remainingTime = process.burstTime;
        }
        do
        {
            for (int looper = 0; looper < processes.size(); looper++)
            {
                if(processes.get(looper).remainingTime > quantumTime)
                {
                    processes.get(looper).remainingTime -= quantumTime;
                    for (int looper1 = 0; looper1 < processes.size(); looper1++)
                    {
                        if((looper1 != looper) && (processes.get(looper1).remainingTime !=0))
                        {
                            processes.get(looper1).waitingTime += quantumTime;
                        }
                    }
                }
                else
                {
                    for (int looper1 = 0; looper1 < processes.size(); looper1++)
                    {
                        if((looper1 != looper) && processes.get(looper1).remainingTime != 0)
                        {
                            processes.get(looper1).waitingTime += processes.get(looper).remainingTime;
                        }
                    }
                    processes.get(looper).remainingTime = 0;
                }
            }
            sum = 0;
            for (Processes process : processes)
            {
                sum += process.remainingTime;
            }
        }
        while (sum!=0);

        for (Processes process : processes)
        {
            process.turnAroundTime = process.waitingTime + process.burstTime;
        }
        //output(processes);
        finalOutput(processes);
    }
    public static void main(String[] args) throws IOException
    {
        Procsim procsim = new Procsim();

        ArrayList<Processes> processes = new ArrayList<Processes>();
        int looper = 0;
        Scanner scanner = new Scanner(System.in);
        if (args.length > 0)
        {
            try
            {
                scanner = new Scanner(new File(args[0]));
                looper = 1;
                while (scanner.hasNext())
                {
                    processes.add(new Processes(looper,scanner.nextInt(),scanner.nextInt()));
                    looper++;

                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            try
            {
                int typeOfAlgorithm = Integer.parseInt(args[1]);
                if(typeOfAlgorithm == 1)
                {
                    System.out.println("FcFs called");
                    procsim.fcfs(processes);
                }
                else if (typeOfAlgorithm == 2)
                {
                    try
                    {
                        int preamptive = Integer.parseInt(args[2]);
                        if(preamptive == 0)
                        {
                            System.out.println("Non Preamptive SJf Called");
                            procsim.sjfnp(processes);
                        }
                        else if (preamptive == 1)
                        {
                            System.out.println("Preamptive SJF Called");
                            procsim.sjfp(processes);
                        }
                    }
                    catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(typeOfAlgorithm == 3)
                {
                    try
                    {
                        System.out.println("Round Robin Called");
                        int quantumTime = Integer.parseInt(args[2]);
                        procsim.roundRobin(processes , quantumTime);
                    }
                    catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch(IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Please provide arguments");
            System.exit(1);
        }

    }
}
