package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by alex on 22.02.17.
 */

public class Session extends Calculations {

    private final static int INITIAL_COUNT = 0;

    private final static int AVERAGE_OF5 = 5;

    private final static int AVERAGE_OF12 = 12;

    private final static int AVERAGE_OF50 = 50;

    private final static int AVERAGE_OF100 = 100;

    private final static int AVERAGE_OF1000 = 1000;

    private String Name;

    private Mode Mode;

    private Integer Solve_Count;

    private ArrayList<Solve> Solves;

    private HashMap<Solve, Average> AveragesOf5;

    private HashMap<Solve, Average> AveragesOf12;

    private HashMap<Solve, Average> AveragesOf50;

    private HashMap<Solve, Average> AveragesOf100;

    private HashMap<Solve, Average> AveragesOf1000;

    /* Singles and Averages */

    private Solve Last_Deleted;

    private Solve Current_Solve;

    private Solve Best_Solve;

    private Average lastDeletedAverage;

    private Average Current_ao5;

    private Average Current_ao12;

    private Average currentAo50;

    private Average currentAo100;

    private Average currentAo1000;

    private Average Best_ao5;

    private Average Best_ao12;

    private Average bestAo50;

    private Average bestAo100;

    private Average bestAo1000;

    /* Getters */

    public ArrayList<Solve> getSolves() {
        return this.Solves;
    }

    public HashMap<Solve, Average> getAveragesOf5() {
        return this.AveragesOf5;
    }

    public HashMap<Solve, Average> getAveragesOf12() {
        return this.AveragesOf12;
    }

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

    public Solve getCurrent() {
        return Current_Solve;
    }

    public Solve getBest() {
        return Best_Solve;
    }

    public Average getCurrentAo5() {
        return Current_ao5;
    }

    public Average getBestAo5() {
        return Best_ao5;
    }

    public Average getCurrentAo12() {
        return Current_ao12;
    }

    public Average getBestAo12() {
        return Best_ao12;
    }

    public Average getCurrentAo50() {
        return currentAo50;
    }

    public Average getBestAo100() {
        return bestAo100;
    }

    public Solve getLastDeleted() {
        return Last_Deleted;
    }

    public Average getLastDeletedAverage() {
        return lastDeletedAverage;
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
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = newBestAdd(Current_Solve, Best_Solve);
        if (Solves.size() >= AVERAGE_OF5) {
            ArrayList<Solve> AverageOf5 = getAnyAverageSolves(Solves, AVERAGE_OF5, Solves.size());
            ArrayList<Solve> Excluded = excludingSolves(AverageOf5);
            Duration Time = calculateAverage(AverageOf5, Excluded);
            Current_ao5 = new Average(AverageOf5, Excluded, Time);
            AveragesOf5.put(Current_Solve, Current_ao5);
            Best_ao5 = newBestAdd(Current_ao5, Best_ao5);
        }
        if (Solves.size() >= AVERAGE_OF12) {
            ArrayList<Solve> AverageOf12 = getAnyAverageSolves(Solves, AVERAGE_OF12, Solves.size());
            ArrayList<Solve> Excluded = excludingSolves(AverageOf12);
            Duration Time = calculateAverage(AverageOf12, Excluded);
            Current_ao12 = new Average(AverageOf12, Excluded, Time);
            AveragesOf12.put(Current_Solve, Current_ao12);
            Best_ao12 = newBestAdd(Current_ao12, Best_ao12);
        }
        if (Solves.size() >= AVERAGE_OF50) {
            ArrayList<Solve> AverageOf50 = getAnyAverageSolves(Solves, AVERAGE_OF50, Solves.size());
            ArrayList<Solve> Excluded = excludingSolves(AverageOf50);
            Duration Time = calculateAverage(AverageOf50, Excluded);
            currentAo50 = new Average(AverageOf50, Excluded, Time);
            AveragesOf50.put(Current_Solve, currentAo50);
            bestAo50 = newBestAdd(currentAo50, bestAo50);
        }
        if (Solves.size() >= AVERAGE_OF100) {
            ArrayList<Solve> AverageOf100 = getAnyAverageSolves(Solves, AVERAGE_OF100, Solves.size());
            ArrayList<Solve> Excluded = excludingSolves(AverageOf100);
            Duration Time = calculateAverage(AverageOf100, Excluded);
            currentAo100 = new Average(AverageOf100, Excluded, Time);
            AveragesOf100.put(Current_Solve, currentAo100);
            bestAo100 = newBestAdd(currentAo100, bestAo100);
        }
        if (Solves.size() >= AVERAGE_OF1000) {
            ArrayList<Solve> AverageOf1000 = getAnyAverageSolves(Solves, AVERAGE_OF1000, Solves.size());
            ArrayList<Solve> Excluded = excludingSolves(AverageOf1000);
            Duration Time = calculateAverage(AverageOf1000, Excluded);
            currentAo1000 = new Average(AverageOf1000, Excluded, Time);
            AveragesOf1000.put(Current_Solve, currentAo1000);
            bestAo1000 = newBestAdd(currentAo1000, bestAo1000);
        }
    }

    public void delSolve(Solve solve) {
        final int INDEX = Solves.indexOf(solve);
        Last_Deleted = solve;
        Solves.remove(solve);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = newBestDel(Last_Deleted, Best_Solve, Solves);
        if (Solves.size() >= AVERAGE_OF5) {
            AveragesOf5.remove(solve);
            AveragesOf5 = calculateAverages(AveragesOf5, Solves, INDEX, AVERAGE_OF5);
            Current_ao5 = AveragesOf5.get(Current_Solve);
            Best_ao5 = newBestAverage(AveragesOf5, Solves, AVERAGE_OF5);
        }
        if (Solves.size() >= AVERAGE_OF12) {
            AveragesOf12.remove(solve);
            AveragesOf12 = calculateAverages(AveragesOf12, Solves, INDEX, AVERAGE_OF12);
            Current_ao12 = AveragesOf12.get(Current_Solve);
            Best_ao12 = newBestAverage(AveragesOf12, Solves, AVERAGE_OF12);
        }
        if (Solves.size() >= AVERAGE_OF50) {
            AveragesOf50.remove(solve);
            AveragesOf50 = calculateAverages(AveragesOf50, Solves, INDEX, AVERAGE_OF50);
            currentAo50 = AveragesOf50.get(Current_Solve);
            bestAo50 = newBestAverage(AveragesOf50, Solves, AVERAGE_OF50);
        }
        if (Solves.size() >= AVERAGE_OF100) {
            AveragesOf100.remove(solve);
            AveragesOf100 = calculateAverages(AveragesOf100, Solves, INDEX, AVERAGE_OF100);
            currentAo100 = AveragesOf100.get(Current_Solve);
            bestAo100 = newBestAverage(AveragesOf100, Solves, AVERAGE_OF100);
        }
        if (Solves.size() >= AVERAGE_OF1000) {
            AveragesOf1000.remove(solve);
            AveragesOf1000 = calculateAverages(AveragesOf1000, Solves, INDEX, AVERAGE_OF1000);
            currentAo1000 = AveragesOf1000.get(Current_Solve);
            bestAo1000 = newBestAverage(AveragesOf1000, Solves, AVERAGE_OF1000);
        }
    }

    public void delLastSolve() {
        if (Solves.size() == 0) return;
        Last_Deleted = Solves.get(Solves.size()-1);
        Solves.remove(Last_Deleted);
        Solve_Count--;
        Current_Solve = Solves.get(Solves.size()-1);
        Best_Solve = newBestDel(Last_Deleted, Best_Solve, Solves);
        if (Solves.size() >= AVERAGE_OF5) {
            Current_ao5 = AveragesOf5.get(Current_Solve);
            lastDeletedAverage = AveragesOf5.get(Last_Deleted);
            AveragesOf5.remove(Last_Deleted);
            Best_ao5 = newBestDel(lastDeletedAverage, Best_ao5, AveragesOf5);
        }
        if (Solves.size() >= AVERAGE_OF12) {
            Current_ao12 = AveragesOf12.get(Current_Solve);
            lastDeletedAverage = AveragesOf12.get(Last_Deleted);
            AveragesOf12.remove(Last_Deleted);
            Best_ao12 = newBestDel(lastDeletedAverage, Best_ao12, AveragesOf12);
        }
        if (Solves.size() >= AVERAGE_OF50) {
            currentAo50 = AveragesOf50.get(Current_Solve);
            lastDeletedAverage = AveragesOf50.get(Last_Deleted);
            AveragesOf50.remove(Last_Deleted);
            bestAo50 = newBestDel(lastDeletedAverage, bestAo50, AveragesOf50);
        }
        if (Solves.size() >= AVERAGE_OF100) {
            currentAo100 = AveragesOf100.get(Current_Solve);
            lastDeletedAverage = AveragesOf100.get(Last_Deleted);
            AveragesOf100.remove(Last_Deleted);
            bestAo100 = newBestDel(lastDeletedAverage, bestAo100, AveragesOf100);
        }
        if (Solves.size() >= AVERAGE_OF1000) {
            currentAo1000 = AveragesOf1000.get(Current_Solve);
            lastDeletedAverage = AveragesOf1000.get(Last_Deleted);
            AveragesOf1000.remove(Last_Deleted);
            bestAo1000 = newBestDel(lastDeletedAverage, bestAo1000, AveragesOf1000);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Session)) return false;
        if (((Session) object).getSolves().size() != this.Solves.size()) return false;
        for (int i = 0; i < this.Solves.size(); i++) {
           if (!((Session) object).getSolves().get(i).equals(this.Solves.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Mode, Solve_Count, Solves, AveragesOf5, AveragesOf12, Last_Deleted,
                Current_Solve, Current_ao5, Current_ao12, Best_Solve, lastDeletedAverage, Best_ao5, Best_ao12);
    }

    /* Constructor */

    public Session(String name) {
        this.Name = name;
        this.Mode = de.xandronia.cubingtimer.internal.Mode.T3;
        this.Solves  = new ArrayList<>();
        this.AveragesOf5 = new HashMap<>();
        this.AveragesOf12 = new HashMap<>();
        this.AveragesOf50 = new HashMap<>();
        this.AveragesOf100 = new HashMap<>();
        this.AveragesOf1000 = new HashMap<>();
        this.Solve_Count = INITIAL_COUNT;
        this.lastDeletedAverage = null;
        this.Current_Solve = null;
        this.Best_Solve = null;
        this.Last_Deleted = null;
        this.Current_ao5 = null;
        this.Current_ao12 = null;
        this.currentAo50 = null;
        this.currentAo100 = null;
        this.currentAo1000 = null;
        this.Best_ao5 = null;
        this.Best_ao12 = null;
        this.bestAo50 = null;
        this.bestAo100 = null;
        this.bestAo1000 = null;
    }
}
