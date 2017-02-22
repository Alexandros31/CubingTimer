package de.xandronia.cubingtimer.internal;

/**
 * Created by alex on 22.02.17.
 */
public enum Mode {
    T3("Rubik's Cube"); // T3 = "Type 3"

    private String mode;
    Mode (String mode) { this.mode = mode; }

    @Override
    public String toString() {
        return this.mode;
    }
}
