package de.xandronia.cubingtimer.internal;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by alex on 22.02.17.
 */

public class Timing {

    private Instant start;

    private Instant stop;

    public Duration stop() {
        this.stop = Instant.now();
        return  Duration.between(this.start, this.stop);
    }

    /* Constructor */

    public Timing() {
        this.start = Instant.now();
        this.stop = null;
    }
}
