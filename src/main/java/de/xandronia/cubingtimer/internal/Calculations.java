package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by alex on 23.02.17.
 */

public class Calculations {

    public Solve newBestAdd(Solve New_Solve, Solve Previous_Best) {
        if ((Previous_Best == null) ||
                (New_Solve.getTime().compareTo(Previous_Best.getTime()) < 0)) return New_Solve;
        return Previous_Best;
    }

    public Solve newBestDel(Solve Deleted_Solve, Solve Previous_Best, ArrayList<Solve> Solves) {
        Solve New_Best;
        if (Deleted_Solve.getTime().compareTo(Previous_Best.getTime()) > 0) return Previous_Best;
        else {
            New_Best = Solves.get(0);
            for (Solve Current : Solves) {
                if (Current.getTime().compareTo(New_Best.getTime()) < 0) New_Best = Current;
            }
        }
        return New_Best;
    }

    public Duration calculateAverage(ArrayList<Solve> Solves, ArrayList<Solve> Excluded) {
        int counter = 0;
        Duration Sum = Duration.ZERO;
        for (Solve Current : Solves) {
            if (!Excluded.contains(Current)) {
                Sum.plus(Current.getTime());
                counter++;
            }
        }
        return Sum.dividedBy((counter));
    }

    public ArrayList<Solve> excludingSolves(ArrayList<Solve> Solves) {
        int size = Solves.size();
        int amount;
        switch (size) {
            case 5: amount = 2;
            case 12: amount = 2;
            case 50: amount = 6;
            case 100: amount = 10;
            case 1000: amount = 100;
            default: amount = 0;
        }
        ArrayList<Solve> Excluded = new ArrayList<>(amount);
        for (int i = amount/2; i > 0; i--) {
            Solve tmp = getBestSolve(Solves);
            Solves.remove(tmp);
            Excluded.add(tmp);
        }
        for (int i = amount/2; i > 0; i--) {
            Solve tmp = getWorstSolve(Solves);
            Solves.remove(tmp);
            Excluded.add(tmp);
        }
        return Excluded;
    }

    private Solve getBestSolve(ArrayList<Solve> Solves) {
        Solve tmp = Solves.get(0);
        for (Solve Current : Solves) {
            if (Current.getTime().compareTo(tmp.getTime()) < 0 ) tmp = Current;
        }
        return tmp;
    }

    private Solve getWorstSolve(ArrayList<Solve> Solves) {
        Solve tmp = Solves.get(0);
        for (Solve Current : Solves) {
            if (Current.getTime().compareTo(tmp.getTime()) > 0 ) tmp = Current;
        }
        return tmp;
    }
}