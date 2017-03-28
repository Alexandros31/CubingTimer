package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by alex on 23.02.17.
 */

public class Calculations {

    /* Singles */

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

    /* Average */

    public Average newBestAdd(Average newAverage, Average previous_best) {
        if ((previous_best == null) ||
                (newAverage.getTime().compareTo(previous_best.getTime()) < 0)) return newAverage;
        return previous_best;
    }

    public Average newBestDel(Average deletedAverage, Average previousBest, HashMap<Solve, Average> averages) {
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

    public Duration calculateAverage(final ArrayList<Solve> solves, final ArrayList<Solve> excluded) {
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

    public ArrayList<Solve> excludingSolves(ArrayList<Solve> solves) {
        int size = solves.size();
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

    public HashMap<Solve, Average> calculateNewAverages(HashMap<Solve, Average> averages, final ArrayList<Solve> solves, int index) {
        final int INDEX = index;
        final Collection<Average> collection = averages.values();
        final ArrayList<Average> averagesList = new ArrayList<>(collection);
        final int AVERAGE = averagesList.get(0).getSolves().size();
        int counter = 0;
        /* Upward Direction */
        while (counter < AVERAGE || index >= solves.size()) {
            final Solve current = solves.get(index);
            final Average toBeReplaced = averages.get(current);
            final ArrayList<Solve> averageSolves = getAnyAverageSolves(solves, AVERAGE, index);
            final ArrayList<Solve> excluded = excludingSolves(averageSolves);
            final Duration time = calculateAverage(averageSolves, excluded);
            final Average average = new Average(averageSolves, excluded, time);
            averages.replace(current, toBeReplaced, average);
            index++;
            counter++;
        }
        index = INDEX;
        counter = 0;
        /* Downward Direction */
        while (counter < AVERAGE || index-AVERAGE+1 >= 0) {
            final Solve current = solves.get(index);
            final Average toBeReplaced = averages.get(current);
            final ArrayList<Solve> averageSolves = getAnyAverageSolves(solves, AVERAGE, index);
            final ArrayList<Solve> excluded = excludingSolves(averageSolves);
            final Duration time = calculateAverage(averageSolves, excluded);
            final Average average = new Average(averageSolves, excluded, time);
            averages.replace(current, toBeReplaced, average);
            index--;
            counter++;
        }
        return averages;
    }

    public ArrayList<Solve> getAnyAverageSolves(final ArrayList<Solve> solves, final int averageOf, final int index) {
        ArrayList<Solve> average = new ArrayList<>();
        final int START = index-averageOf;
        for (int i = START; i < index ; i++) average.add(solves.get(i));
        return average;
    }

    /* Private Help Methods */

    private Solve getBestSolve(final ArrayList<Solve> solves) {
        Solve tmp = solves.get(0);
        for (Solve current : solves) {
            if (current.getTime().compareTo(tmp.getTime()) < 0 ) tmp = current;
        }
        return tmp;
    }

    private Solve getWorstSolve(final ArrayList<Solve> solves) {
        if (solves.get(0).getState() == State.DNF) return solves.get(0);
        Solve tmp = solves.get(0);
        for (Solve current : solves) {
            if (current.getState() == State.DNF) return current;
            if (current.getTime().compareTo(tmp.getTime()) > 0 ) tmp = current;
        }
        return tmp;
    }

}