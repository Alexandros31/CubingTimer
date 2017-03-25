package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Average)) return false;
        if (!this.time.equals(((Average) object).getTime()) || !this.state.equals(((Average) object).getState())) return false;
        for (int i = 0; i < this.solves.size(); i++) {
            if (!((Average) object).getSolves().get(i).equals(this.solves.get(i))) return false;
        }
        for (int i = 0; i < this.excludedSolves.size(); i++) {
            if (!((Average) object).getExcludedSolves().get(i).equals(this.excludedSolves.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(solves, excludedSolves, time, state);
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
