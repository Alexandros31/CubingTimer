package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by alex on 22.02.17.
 */

public class Session {

    private final static int AVERAGE_OF5 = 5;

    private final static int AVERAGE_OF12 = 12;

    private final static int AVERAGE_OF50 = 50;

    private final static int AVERAGE_OF100 = 100;

    private final static int AVERAGE_OF1000 = 1000;

    private String name;

    private Mode mode;

    private int solveCount = 0;

    private ArrayList<Solve> solves = new ArrayList<>();

    private HashMap<Solve, Average> averagesOf5 = new HashMap<>();

    private HashMap<Solve, Average> averagesOf12 = new HashMap<>();

    private HashMap<Solve, Average> averagesOf50 = new HashMap<>();

    private HashMap<Solve, Average> averagesOf100 = new HashMap<>();

    private HashMap<Solve, Average> averagesOf1000 = new HashMap<>();

    /* Singles and Averages */

    private Solve lastDeleted;

    private Solve currentSolve;

    private Solve bestSolve;

    private Average lastDeletedAverage;

    private Average currentAo5;

    private Average currentAo12;

    private Average currentAo50;

    private Average currentAo100;

    private Average currentAo1000;

    private Average bestAo5;

    private Average bestAo12;

    private Average bestAo50;

    private Average bestAo100;

    private Average bestAo1000;

    /* Getters */

    public ArrayList<Solve> getSolves() {
        return this.solves;
    }

    public HashMap<Solve, Average> getAveragesOf5() {
        return this.averagesOf5;
    }

    public HashMap<Solve, Average> getAveragesOf12() {
        return this.averagesOf12;
    }

    public Solve getSolve(int index) {
        return solves.get(index);
    }

    public String getName() {
        return this.name;
    }

    public Mode getMode() {
        return this.mode;
    }

    public Integer getSolveCount() {
        return this.solveCount;
    }

    public Solve getCurrent() {
        return currentSolve;
    }

    public Solve getBest() {
        return bestSolve;
    }

    public Average getCurrentAo5() {
        return currentAo5;
    }

    public Average getBestAo5() {
        return bestAo5;
    }

    public Average getCurrentAo12() {
        return currentAo12;
    }

    public Average getBestAo12() {
        return bestAo12;
    }

    public Average getCurrentAo50() {
        return currentAo50;
    }

    public Average getBestAo100() {
        return bestAo100;
    }

    public Solve getLastDeleted() {
        return lastDeleted;
    }

    public Average getLastDeletedAverage() {
        return lastDeletedAverage;
    }

    /* Setters */

    public void setName(String name) {
        this.name = name;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /* foop */

    private void adding() {

    }

    /* Adding And Deleting solves */

    public void addSolve(Solve solve) {
        solves.add(solve);
        solveCount++;
        currentSolve = solves.get(solves.size()-1);
        bestSolve = Calculations.newBestAdd(currentSolve, bestSolve);
        if (solves.size() >= AVERAGE_OF5) {
            ArrayList<Solve> AverageOf5 = Calculations.getAnyAverageSolves(solves, AVERAGE_OF5, solves.size());
            ArrayList<Solve> Excluded = Calculations.excludingSolves(AverageOf5);
            Duration Time = Calculations.calculateAverage(AverageOf5, Excluded);
            currentAo5 = new Average(AverageOf5, Excluded, Time);
            averagesOf5.put(currentSolve, currentAo5);
            bestAo5 = Calculations.newBestAdd(currentAo5, bestAo5);
        }
        if (solves.size() >= AVERAGE_OF12) {
            ArrayList<Solve> AverageOf12 = Calculations.getAnyAverageSolves(solves, AVERAGE_OF12, solves.size());
            ArrayList<Solve> Excluded = Calculations.excludingSolves(AverageOf12);
            Duration Time = Calculations.calculateAverage(AverageOf12, Excluded);
            currentAo12 = new Average(AverageOf12, Excluded, Time);
            averagesOf12.put(currentSolve, currentAo12);
            bestAo12 = Calculations.newBestAdd(currentAo12, bestAo12);
        }
        if (solves.size() >= AVERAGE_OF50) {
            ArrayList<Solve> AverageOf50 = Calculations.getAnyAverageSolves(solves, AVERAGE_OF50, solves.size());
            ArrayList<Solve> Excluded = Calculations.excludingSolves(AverageOf50);
            Duration Time = Calculations.calculateAverage(AverageOf50, Excluded);
            currentAo50 = new Average(AverageOf50, Excluded, Time);
            averagesOf50.put(currentSolve, currentAo50);
            bestAo50 = Calculations.newBestAdd(currentAo50, bestAo50);
        }
        if (solves.size() >= AVERAGE_OF100) {
            ArrayList<Solve> AverageOf100 = Calculations.getAnyAverageSolves(solves, AVERAGE_OF100, solves.size());
            ArrayList<Solve> Excluded = Calculations.excludingSolves(AverageOf100);
            Duration Time = Calculations.calculateAverage(AverageOf100, Excluded);
            currentAo100 = new Average(AverageOf100, Excluded, Time);
            averagesOf100.put(currentSolve, currentAo100);
            bestAo100 = Calculations.newBestAdd(currentAo100, bestAo100);
        }
        if (solves.size() >= AVERAGE_OF1000) {
            ArrayList<Solve> AverageOf1000 = Calculations.getAnyAverageSolves(solves, AVERAGE_OF1000, solves.size());
            ArrayList<Solve> Excluded = Calculations.excludingSolves(AverageOf1000);
            Duration Time = Calculations.calculateAverage(AverageOf1000, Excluded);
            currentAo1000 = new Average(AverageOf1000, Excluded, Time);
            averagesOf1000.put(currentSolve, currentAo1000);
            bestAo1000 = Calculations.newBestAdd(currentAo1000, bestAo1000);
        }
    }

    public void delSolve(Solve solve) {
        final int INDEX = solves.indexOf(solve);
        lastDeleted = solve;
        solves.remove(solve);
        solveCount--;
        currentSolve = solves.get(solves.size()-1);
        bestSolve = Calculations.newBestDel(lastDeleted, bestSolve, solves);
        if (solves.size() >= AVERAGE_OF5) {
            averagesOf5.remove(solve);
            averagesOf5 = Calculations.calculateAverages(averagesOf5, solves, INDEX, AVERAGE_OF5);
            currentAo5 = averagesOf5.get(currentSolve);
            bestAo5 = Calculations.newBestAverage(averagesOf5, solves, AVERAGE_OF5);
        }
        if (solves.size() >= AVERAGE_OF12) {
            averagesOf12.remove(solve);
            averagesOf12 = Calculations.calculateAverages(averagesOf12, solves, INDEX, AVERAGE_OF12);
            currentAo12 = averagesOf12.get(currentSolve);
            bestAo12 = Calculations.newBestAverage(averagesOf12, solves, AVERAGE_OF12);
        }
        if (solves.size() >= AVERAGE_OF50) {
            averagesOf50.remove(solve);
            averagesOf50 = Calculations.calculateAverages(averagesOf50, solves, INDEX, AVERAGE_OF50);
            currentAo50 = averagesOf50.get(currentSolve);
            bestAo50 = Calculations.newBestAverage(averagesOf50, solves, AVERAGE_OF50);
        }
        if (solves.size() >= AVERAGE_OF100) {
            averagesOf100.remove(solve);
            averagesOf100 = Calculations.calculateAverages(averagesOf100, solves, INDEX, AVERAGE_OF100);
            currentAo100 = averagesOf100.get(currentSolve);
            bestAo100 = Calculations.newBestAverage(averagesOf100, solves, AVERAGE_OF100);
        }
        if (solves.size() >= AVERAGE_OF1000) {
            averagesOf1000.remove(solve);
            averagesOf1000 = Calculations.calculateAverages(averagesOf1000, solves, INDEX, AVERAGE_OF1000);
            currentAo1000 = averagesOf1000.get(currentSolve);
            bestAo1000 = Calculations.newBestAverage(averagesOf1000, solves, AVERAGE_OF1000);
        }
    }

    public void delLastSolve() {
        if (solves.size() == 0) return;
        lastDeleted = solves.get(solves.size()-1);
        solves.remove(lastDeleted);
        solveCount--;
        currentSolve = solves.get(solves.size()-1);
        bestSolve = Calculations.newBestDel(lastDeleted, bestSolve, solves);
        if (solves.size() >= AVERAGE_OF5) {
            currentAo5 = averagesOf5.get(currentSolve);
            lastDeletedAverage = averagesOf5.get(lastDeleted);
            averagesOf5.remove(lastDeleted);
            bestAo5 = Calculations.newBestDel(lastDeletedAverage, bestAo5, averagesOf5);
        }
        if (solves.size() >= AVERAGE_OF12) {
            currentAo12 = averagesOf12.get(currentSolve);
            lastDeletedAverage = averagesOf12.get(lastDeleted);
            averagesOf12.remove(lastDeleted);
            bestAo12 = Calculations.newBestDel(lastDeletedAverage, bestAo12, averagesOf12);
        }
        if (solves.size() >= AVERAGE_OF50) {
            currentAo50 = averagesOf50.get(currentSolve);
            lastDeletedAverage = averagesOf50.get(lastDeleted);
            averagesOf50.remove(lastDeleted);
            bestAo50 = Calculations.newBestDel(lastDeletedAverage, bestAo50, averagesOf50);
        }
        if (solves.size() >= AVERAGE_OF100) {
            currentAo100 = averagesOf100.get(currentSolve);
            lastDeletedAverage = averagesOf100.get(lastDeleted);
            averagesOf100.remove(lastDeleted);
            bestAo100 = Calculations.newBestDel(lastDeletedAverage, bestAo100, averagesOf100);
        }
        if (solves.size() >= AVERAGE_OF1000) {
            currentAo1000 = averagesOf1000.get(currentSolve);
            lastDeletedAverage = averagesOf1000.get(lastDeleted);
            averagesOf1000.remove(lastDeleted);
            bestAo1000 = Calculations.newBestDel(lastDeletedAverage, bestAo1000, averagesOf1000);
        }
    }

    public void switchState(Solve solve, final State state) {
        if (solve.equals(solves.get(solves.size()-1))) {
            delLastSolve();
            solve.setState(state);
            addSolve(solve);
        }
    }

    /* Overrided Methods */

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Session)) return false;
        if (((Session) object).getSolves().size() != this.solves.size()) return false;
        for (int i = 0; i < this.solves.size(); i++) {
           if (!((Session) object).getSolves().get(i).equals(this.solves.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mode, solveCount, solves, averagesOf5, averagesOf12, lastDeleted,
                currentSolve, currentAo5, currentAo12, bestSolve, lastDeletedAverage, bestAo5, bestAo12);
    }

    /* Constructor */

    public Session(String name) {
        this.name = name;
        this.mode = de.xandronia.cubingtimer.internal.Mode.T3;
    }
}
