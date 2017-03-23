package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.util.Objects;

/**
 * Created by alex on 22.02.17.
 */

public class Solve {

    /* Global Variables*/

    private final Duration Time;

    private final String Scramble;

    private String Cube;

    private Color Color;

    private State State;

    /* Getters */

    public Duration getTime() {
        return this.Time;
    }

    public String getScramble() {
        return this.Scramble;
    }

    public String getCube() {
        return this.Cube;
    }

    public Color getColor() {
        return this.Color;
    }

    public State getState() {
        return this.State;
    }

    /* Setters */

    public void setColor(final Color color) {
        this.Color = color;
    }

    public void setCube(final String cube) {
        this.Cube = cube;
    }

    public void setState(final State New_State) {
        final State plusTwo = de.xandronia.cubingtimer.internal.State.plusTwo;
        final State Previous_State = this.State;
        this.State = New_State;
        if (this.State.equals(plusTwo)
                && !Previous_State.equals(plusTwo)) {
            this.Time.plusSeconds(2);
        } else if (Previous_State.equals(plusTwo)
                && !this.State.equals(plusTwo)) {
            this.Time.minusSeconds(2);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Solve))return false;
        if (((Solve) object).getTime().equals(this.Time)
                && ((Solve) object).getScramble().equals(this.Scramble)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Time, Scramble, Color, Cube, State);
    }

    /* Constructor */

    public Solve(final Duration time, final String scramble) {
        this.Time = time;
        this.Scramble = scramble;
        this.State = de.xandronia.cubingtimer.internal.State.OK;
        this.Cube = null;
        this.Color = null;
    }
}
