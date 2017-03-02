package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by alex on 24.02.17.
 */

public class Average {

    private final ArrayList<Solve> solves;

    private final ArrayList<Solve> excludedSolves;

    private final Duration time;

    private State state;

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

    public State getState() {
        return this.state;
    }

    /* Constructor */

    public Average(final ArrayList<Solve> solves, final ArrayList<Solve> excluded, final Duration time) {
        this.solves = solves;
        this.excludedSolves = excluded;
        this.time = time;
        this.state = State.OK;
        if (this.time.equals(Duration.ZERO)) this.state = State.DNF;
    }
}
