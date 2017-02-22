package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private String Name;

    private Mode Mode;

    private ArrayList<Solve> Times;

    public Solve getSolve(int index) {
        return this.Times.get(index);
    }

    public void addSolve(Solve solve) {
        this.Times.add(solve);
    }

    public void delSolve(int index) {
        this.Times.remove(index);
    }

    public void delLastSolve() {
        this.Times.remove(Times.size()-1);
    }

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
    }
}
