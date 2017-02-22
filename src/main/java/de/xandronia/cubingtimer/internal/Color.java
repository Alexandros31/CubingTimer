package de.xandronia.cubingtimer.internal;

/**
 * Created by alex on 22.02.17.
 */

public enum  Color {
    White("White"), Green("Green"), Red("Red"), Orange("Orange"), Yellow("Yellow"), Blue("Blue");

    private String color;
    Color (String color) { this.color = color; }

    @Override
    public String toString() {
        return this.color;
    }
}
