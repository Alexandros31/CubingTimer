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
    }

    public Duration stop() {
        this.Stop = Instant.now();
        final Duration Time = Duration.between(this.Start, this.Stop);
        return Time;
    }
}
