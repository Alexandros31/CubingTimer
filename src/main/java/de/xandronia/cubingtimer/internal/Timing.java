package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by alex on 22.02.17.
 */

public class Timing {

    private Instant Start;

    private Instant Stop;

    public void start() {
        this.Start = Instant.now();
    } //REDUNDANT

    public Duration stop() {
        this.Stop = Instant.now();
        return  Duration.between(this.Start, this.Stop);
    }

    /* Constructor */

    public Timing() {
        this.Start = Instant.now();
        this.Stop = null;
    }
}
