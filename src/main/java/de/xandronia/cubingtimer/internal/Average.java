package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by alex on 24.02.17.
 */

public class Average {

    private ArrayList<Solve> Solves;

    private ArrayList<Solve> Excluded_Solves;

    private Duration Time;

    /* Getters */

    public ArrayList<Solve> getSolves() {
        return this.Solves;
    }

    public ArrayList<Solve> getExcluded_Solves() {
        return this.Excluded_Solves;
    }

    public Duration getTime() {
        return this.Time;
    }

    /* Constructor */

    public Average(ArrayList<Solve> solves, ArrayList<Solve> excluded, Duration time) {
        this.Solves = solves;
        this.Excluded_Solves = excluded;
        this.Time = time;
    }

}
