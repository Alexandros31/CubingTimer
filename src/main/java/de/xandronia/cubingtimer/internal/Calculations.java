package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 23.02.17.
 */

public class Calculations {

    public Solve newBestAdd(Solve New_Solve, Solve Previous_Best) {
        Solve New_Best = Previous_Best;
        if (Previous_Best == null) return  New_Solve;
        else if (New_Solve.getTime().compareTo(Previous_Best.getTime()) < 0) New_Best = New_Solve;
        return New_Best;
    }

    public Solve newBestDel(Solve Deleted_Solve, Solve Previous_Best, ArrayList<Solve> Solves) {
        Solve New_Best = Previous_Best;
        if (Deleted_Solve.getTime().compareTo(Previous_Best.getTime()) > 0) return Previous_Best;
        else {
            New_Best = Solves.get(0);
            for (Solve Current : Solves) {
                if (Current.getTime().compareTo(New_Best.getTime()) < 0) New_Best = Current;
            }
        }
        return New_Best;
    }
}