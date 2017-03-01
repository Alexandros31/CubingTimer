package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

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
            newBest = averages.get(0);
            for (Average current : averages.values()) {
                if (current.getTime().compareTo(newBest.getTime()) < 0) newBest = current;
            }
        }
        return newBest;
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
        ArrayList<Solve> average = new ArrayList<>(averageOf);
        final int START = index-averageOf+1;
        for (int i = START; i <= index ; i++) average.add(solves.get(i));
        return average;
    }

    /* Private Help Methods */

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