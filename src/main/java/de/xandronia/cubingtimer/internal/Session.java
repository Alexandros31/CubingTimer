package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private String Name;

    private Mode Mode;

    private Integer Solve_Count;

    private ArrayList Solves;

    private Calculations Operations;

    /* Times and Averages */

    private Solve Last_Deleted;

    private Solve Current_Solve;

    private Solve Best_Solve;

    /* Getters and Setters */

    public Solve getSolve(int index) {
        return (Solve) Solves.get(index);
    }

    public void addSolve(Solve solve) {
        Solves.add(solve);
        Solve_Count++;
        Current_Solve = solve;
        Best_Solve = Operations.newBestAdd(Current_Solve, Best_Solve);
    }

    public void delSolve(Integer index) {
        Last_Deleted = (Solve) Solves.get(index);
        Solves.remove(index);
        Solve_Count--;
        Current_Solve = (Solve) Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
    }

    public void delLastSolve() {
        if (Solves.size() == 0) return;
        Last_Deleted = (Solve) Solves.get(Solves.size()-1);
        Solves.remove(Solves.size()-1);
        Solve_Count--;
        Current_Solve = (Solve) Solves.get(Solves.size()-1);
        Best_Solve = Operations.newBestDel(Last_Deleted, Best_Solve, Solves);
    }

    /* Constructor */

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
        this.Solves  = new ArrayList<Solve>();
        this.Solve_Count = 0;
        this.Operations = new Calculations();
        this.Current_Solve = null;
        this.Best_Solve = null;
        this.Last_Deleted = null;

    }
}
