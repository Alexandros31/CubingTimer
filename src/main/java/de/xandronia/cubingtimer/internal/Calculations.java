package de.xandronia.cubingtimer.internal;

import com.sun.istack.internal.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by alex on 23.02.17.
 */

public class Calculations {

    /* Singles */

    public static Solve newBestAdd(Solve newolve, Solve previousBest) {
        if ((previousBest == null) ||
                (newolve.getTime().compareTo(previousBest.getTime()) < 0)) return newolve;
        return previousBest;
    }

    public static Solve newBestDel(Solve deletedSolve, Solve previousBest, ArrayList<Solve> Solves) {
        Solve newBest;
        if (deletedSolve.getTime().compareTo(previousBest.getTime()) > 0) return previousBest;
        else {
            newBest = Solves.get(0);
            for (Solve Current : Solves) {
                if (Current.getTime().compareTo(newBest.getTime()) < 0) newBest = Current;
            }
        }
        return newBest;
    }

    /* Average */

    public static Average newBestAdd(Average newAverage, Average previousbest) {
        if ((previousbest == null) ||
                (newAverage.getTime().compareTo(previousbest.getTime()) < 0)) return newAverage;
        return previousbest;
    }

    public static Average newBestDel(Average deletedAverage, Average previousBest, HashMap<Solve, Average> averages) {
        Average newBest;
        if (deletedAverage.getTime().compareTo(previousBest.getTime()) > 0) return previousBest;
        else {
            newBest = previousBest;
            for (Average current : averages.values()) {
                if (current.getTime().compareTo(newBest.getTime()) < 0) newBest = current;
            }
        }
        return newBest;
    }

    public static Duration calculateAverage(final ArrayList<Solve> solves, final ArrayList<Solve> excluded) {
        long counter = 0;
        Duration sum = Duration.ZERO;
        for (Solve current : solves) {
            if (!excluded.contains(current) && current.getState() != State.DNF) {
                Duration tmp = sum.plus(current.getTime());
                sum = tmp;
                counter++;
            } else if (!excluded.contains(current) && current.getState() == State.DNF) return Duration.ZERO;
        }
        return sum.dividedBy((counter));
    }

    public static ArrayList<Solve> excludingSolves(ArrayList<Solve> solves) {
        final int size = solves.size();
        int amount;
        switch (size) {
            case 5: amount = 2; break;
            case 12: amount = 2; break;
            case 50: amount = 6; break;
            case 100: amount = 10; break;
            case 1000: amount = 100; break;
            default: amount = 0;
        }
        ArrayList<Solve> excluded = new ArrayList<>();
        for (int i = amount/2; i > 0; i--) {
            Solve tmp = getBestSolve(solves);
            excluded.add(tmp);
            solves.remove(tmp);
        }
        for (int i = amount/2; i > 0; i--) {
            Solve tmp = getWorstSolve(solves);
            excluded.add(tmp);
            solves.remove(tmp);
        }
        return excluded;
    }

    public static HashMap<Solve, Average> calculateAverages(HashMap<Solve, Average> averages, final ArrayList<Solve> solves, int index, final int SIZE) {
        for (int i = index; i < solves.size(); i++) {
            if (i >= SIZE) {
                ArrayList<Solve> aof = getAnyAverageSolves(solves, SIZE, i+1);
                ArrayList<Solve> excluded = excludingSolves(aof);
                Duration time = calculateAverage(aof, excluded);
                Average average = new Average(aof, excluded, time);
                averages.replace(solves.get(i), average);
            }
        }
        return averages;
    }

    public static ArrayList<Solve> getAnyAverageSolves(final ArrayList<Solve> solves, final int averageOf, final int index) {
        ArrayList<Solve> average = new ArrayList<>();
        final int START = index-averageOf;
        for (int i = START; i < index ; i++) {
            average.add(solves.get(i));
        }
        return average;
    }

    public static Average newBestAverage(HashMap<Solve, Average> averages, ArrayList<Solve> solves, int of) {
        Average best = averages.get(solves.get(of-1));
        for (int i = of-1; i < solves.size(); i++) {
            if (averages.get(solves.get(i)).getTime().compareTo(best.getTime()) < 0) best = averages.get(solves.get(i));
        }
        return best;
    }

    /* Private Help Methods */

    private static Solve getBestSolve(final ArrayList<Solve> solves) {
        Solve tmp = solves.get(0);
        for (Solve current : solves) {
            if (current.getTime().compareTo(tmp.getTime()) < 0 ) tmp = current;
        }
        return tmp;
    }

    private static Solve getWorstSolve(final ArrayList<Solve> solves) {
        if (solves.get(0).getState() == State.DNF) return solves.get(0);
        Solve tmp = solves.get(0);
        for (Solve current : solves) {
            if (current.getState() == State.DNF) return current;
            if (current.getTime().compareTo(tmp.getTime()) > 0 ) tmp = current;
        }
        return tmp;
    }

}