package de.xandronia.cubingtimer.internal;

import java.time.Duration;

/**
 * Created by alex on 22.02.17.
 */

public class Solve {

    /* Global Variables*/

    private Duration Time;

    private String Scramble;

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

    public void setColor(Color color) {
        this.Color = color;
    }

    public void setCube(String cube) {
        this.Cube = cube;
    }

    public void setState(State New_State) {
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

    /* Constructor */

    public Solve(Duration time, String scrmable) {
        this.Time = time;
        this.Scramble = scrmable;
        this.State = de.xandronia.cubingtimer.internal.State.OK;
    }
}
