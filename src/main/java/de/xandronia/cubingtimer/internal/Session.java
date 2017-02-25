package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private final static int INITIAL_COUNT = 0;

    private final static int AVERAGE_OF5 = 5;

    private String Name;

    private Mode Mode;

    private Integer Solve_Count;

    private ArrayList<Solve> Solves;

    private Calculations Operations;

    /* Singles and Averages */

    private Solve Last_Deleted;

    private Solve Current_Solve;

    private Solve Best_Solve;

    private Average Current_ao5;

    //private  Best_ao5;

    /* Getters */

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
        //Current_Solve = solve;
        //Best_Solve = Operations.newBestAdd(Current_Solve, Best_Solve);
    }

    public void delSolve(Solve solve, int index) {
        Last_Deleted = Solves.get(index);
        Solves.remove(solve);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
    }

    public void delLastSolve() {
        if (Solves.size() == 0) return;
        Last_Deleted = Solves.get(Solves.size()-1);
        Solves.remove(Solves.size()-1);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
    }

    /* private Help Methods */

    private ArrayList<Solve> getAnyAverageSolves(int averageOf) {
        int size = Solves.size();
        ArrayList<Solve> Average = new ArrayList<>(averageOf);
        for (int i = size-averageOf; i < size; i++) Average.add(Solves.get(i));
        return Average;
    }

    private void afterAdd() {
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestAdd(Current_Solve, Best_Solve);
        if (Solves.size() >= AVERAGE_OF5) {
            ArrayList<Solve> AverageOf5 = getAnyAverageSolves(AVERAGE_OF5);
            ArrayList<Solve> Excluded = Operations.excludingSolves(AverageOf5);
            Duration Time = Operations.calculateAverage(AverageOf5, Excluded);
            Current_ao5 = new  Average(AverageOf5, Excluded, Time);
        }
    }

    /* Constructor */

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
        this.Solves  = new ArrayList<>();
        this.Solve_Count = INITIAL_COUNT;
        this.Operations = new Calculations();
        this.Current_Solve = null;
        this.Best_Solve = null;
        this.Last_Deleted = null;
        this.Current_ao5 = null;
    }
}
