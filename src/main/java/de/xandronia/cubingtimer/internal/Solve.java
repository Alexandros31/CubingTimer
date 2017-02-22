package de.xandronia.cubingtimer.internal;

import javafx.stage.Stage;

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

    public void setState(State state) {
        this.State = state;
        if (this.State == de.xandronia.cubingtimer.internal.State.plusTwo) {
            this.Time = Time.plusSeconds(2);
        }
    }

    /* Constructor */

    public Solve(Duration time, String scrmable) {
        this.Time = time;
        this.Scramble = scrmable;
        this.State = de.xandronia.cubingtimer.internal.State.OK;
    }
}
