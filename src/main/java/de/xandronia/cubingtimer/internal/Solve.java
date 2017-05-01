package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.Objects;

/**
 * Created by alex on 22.02.17.
 */

public class Solve {

    /* Global Variables*/

    private  Duration time;

    private final String scramble;

    private String cube;

    private Color color;

    private State state;

    /* Getters */

    public Duration getTime() {
        return this.time;
    }

    public String getScramble() {
        return this.scramble;
    }

    public String getCube() {
        return this.cube;
    }

    public Color getColor() {
        return this.color;
    }

    public State getState() {
        return this.state;
    }

    /* Setters */

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setCube(final String cube) {
        this.cube = cube;
    }

    public void setState(final State newState) {
        final State plusTwo = de.xandronia.cubingtimer.internal.State.plusTwo;
        final State Previous_State = this.state;
        this.state = newState;
        if (this.state.equals(plusTwo)
                && !Previous_State.equals(plusTwo)) {
            final Duration dur = this.time.plusSeconds(2);
            time = dur;
        } else if (Previous_State.equals(plusTwo)
                && !this.state.equals(plusTwo)) {
            final Duration dur = this.time.minusSeconds(2);
            time = dur;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Solve))return false;
        if (((Solve) object).getTime().equals(this.time)
                && ((Solve) object).getScramble().equals(this.scramble)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, scramble, color, cube, state);
    }

    /* Constructor */

    public Solve(final Duration time, final String scramble) {
        this.time = time;
        this.scramble = scramble;
        this.state = de.xandronia.cubingtimer.internal.State.OK;
        this.cube = null;
        this.color = null;
    }
}
