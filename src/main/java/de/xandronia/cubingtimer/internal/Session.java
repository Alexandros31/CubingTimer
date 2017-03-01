package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private final static int INITIAL_COUNT = 0;

    private final static int AVERAGE_OF5 = 5;

    private final static int AVERAGE_OF12 = 12;

    private String Name;

    private Mode Mode;

    private Integer Solve_Count;

    private ArrayList<Solve> Solves;

    private HashMap<Solve, Average> AveragesOf5;

    private HashMap<Solve, Average> AveragesOf12;

    private Calculations Operations;

    /* Singles and Averages */

    private Solve Last_Deleted;

    private Solve Current_Solve;

    private Solve Best_Solve;

    private Average lastDeletedAverage;

    private Average Current_ao5;

    private Average Current_ao12;

    private Average Best_ao5;

    private Average Best_ao12;

    /* Getters */

    public HashMap<Solve, Average> getAveragesOf5() {
        return this.AveragesOf5;
    }

    public HashMap<Solve, Average> getAveragesOf12() {
        return this.AveragesOf12;
    }

    public Solve getSolve(int index) {
        return Solves.get(index);
    }

    public String getName() {
        return this.Name;
    }

    public Mode getMode() {
        return this.Mode;
    }

    public Integer getSolve_Count() {
        return this.Solve_Count;
    }

    /* Setters */

    public void setName(String name) {
        this.Name = name;
    }

    public void setMode(Mode mode) {
        this.Mode = mode;
    }

    /* Adding And Deleting Solves */

    public void addSolve(Solve solve) {
        Solves.add(solve);
        Solve_Count++;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestAdd(Current_Solve, Best_Solve);
        if (Solves.size() >= AVERAGE_OF5) {
            ArrayList<Solve> AverageOf5 = Operations.getAnyAverageSolves(Solves, AVERAGE_OF5, Solves.size());
            ArrayList<Solve> Excluded = Operations.excludingSolves(AverageOf5);
            Duration Time = Operations.calculateAverage(AverageOf5, Excluded);
            Current_ao5 = new  Average(AverageOf5, Excluded, Time);
            AveragesOf5.put(Current_Solve, Current_ao5);
            Best_ao5 = Operations.newBestAdd(Current_ao5, Best_ao5);
        }
        if (Solves.size() >= AVERAGE_OF12) {
            ArrayList<Solve> AverageOf12 = Operations.getAnyAverageSolves(Solves, AVERAGE_OF12, Solves.size());
            ArrayList<Solve> Excluded = Operations.excludingSolves(AverageOf12);
            Duration Time = Operations.calculateAverage(AverageOf12, Excluded);
            Current_ao12 = new  Average(AverageOf12, Excluded, Time);
            AveragesOf12.put(Current_Solve, Current_ao12);
            Best_ao12 = Operations.newBestAdd(Current_ao12, Best_ao12);
        }
    }

    public void delSolve(Solve solve) {
        final int INDEX = Solves.indexOf(solve);
        Last_Deleted = solve;
        Solves.remove(solve);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
        if (Solves.size() >= AVERAGE_OF5-1) {
            Current_ao5 = AveragesOf5.get(Current_Solve);
            lastDeletedAverage = AveragesOf5.get(Last_Deleted);
            AveragesOf5.remove(Last_Deleted);
            Best_ao5 = Operations.newBestDel(lastDeletedAverage, Best_ao5, AveragesOf5);
            AveragesOf5 = Operations.calculateNewAverages(AveragesOf5, Solves, INDEX);
        }
        if (Solves.size() >= AVERAGE_OF12-1) {
            Current_ao12 = AveragesOf12.get(Current_Solve);
            lastDeletedAverage = AveragesOf12.get(Last_Deleted);
            AveragesOf12.remove(Last_Deleted);
            Best_ao12 = Operations.newBestDel(lastDeletedAverage, Best_ao12, AveragesOf12);
            AveragesOf12 = Operations.calculateNewAverages(AveragesOf12, Solves, INDEX);
        }
    }

    public void delLastSolve() {
        if (Solves.size() == 0) return;
        Last_Deleted = Solves.get(Solves.size()-1);
        Solves.remove(Last_Deleted);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
        if (Solves.size() >= AVERAGE_OF5-1) {
            Current_ao5 = AveragesOf5.get(Current_Solve);
            lastDeletedAverage = AveragesOf5.get(Last_Deleted);
            AveragesOf5.remove(Last_Deleted);
            Best_ao5 = Operations.newBestDel(lastDeletedAverage, Best_ao5, AveragesOf5);
        }
        if (Solves.size() >= AVERAGE_OF12-1) {
            Current_ao12 = AveragesOf12.get(Current_Solve);
            lastDeletedAverage = AveragesOf12.get(Last_Deleted);
            AveragesOf12.remove(Last_Deleted);
            Best_ao12 = Operations.newBestDel(lastDeletedAverage, Best_ao12, AveragesOf12);
        }
    }

    /* Constructor */

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
        this.Solves  = new ArrayList<>();
        this.AveragesOf5 = new HashMap<>();
        this.AveragesOf12 = new HashMap<>();
        this.Solve_Count = INITIAL_COUNT;
        this.Operations = new Calculations();
        this.lastDeletedAverage = null;
        this.Current_Solve = null;
        this.Best_Solve = null;
        this.Last_Deleted = null;
        this.Current_ao5 = null;
        this.Current_ao12 = null;
        this.Best_ao5 = null;
        this.Best_ao12 = null;
    }
}
