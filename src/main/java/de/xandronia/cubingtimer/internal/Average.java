package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by alex on 24.02.17.
 */

public class Average {

    private ArrayList<Solve> solves;

    private ArrayList<Solve> excludedSolves;

    private Duration time;

    /* Getters */

    public ArrayList<Solve> getSolves() {
        return this.solves;
    }

    public ArrayList<Solve> getExcludedSolves() {
        return this.excludedSolves;
    }

    public Duration getTime() {
        return this.time;
    }

    /* Constructor */

    public Average(ArrayList<Solve> solves, ArrayList<Solve> excluded, Duration time) {
        this.solves = solves;
        this.excludedSolves = excluded;
        this.time = time;
    }

}
