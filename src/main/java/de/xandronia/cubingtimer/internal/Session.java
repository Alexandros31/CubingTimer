package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private String Name;

    private Mode Mode;

    private Integer Solve_Count;

    private ArrayList<Solve> Solves;

    /* Times and Averages */

    private Solve Last_Deleted;

    private Solve Current_Solve;

    private Solve Best_Solve;

    /* Getters and Setters */

    public Solve getSolve(Integer index) {
        return this.Solves.get(index);
    }

    public void addSolve(Solve solve) {
        this.Solves.add(solve);
        this.Solve_Count++;
        this.Current_Solve = solve;
        //new bestAdd
    }

    public void delSolve(Integer index) {
        this.Last_Deleted = Solves.get(index);
        this.Solves.remove(index);
        this.Solve_Count--;
        this.Current_Solve = Solves.get(Solves.size()-1);
    }

    public void delLastSolve() {
        if (this.Solves.size() == 0) return;
        this.Last_Deleted = Solves.get(Solves.size()-1);
        this.Solves.remove(Solves.size()-1);
        this.Solve_Count--;
        this.Current_Solve = Solves.get(Solves.size()-1);
    }

    /* Constructor */

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
        this.Solve_Count = 0;
        this.Current_Solve = null;
        this.Best_Solve = null;
        this.Last_Deleted = null;
    }
}
