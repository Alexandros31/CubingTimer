package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class SessionList {

    private ArrayList<Session> List;

    private Session Current;

    public SessionList() {
        final String Default_Name = Mode.T3.toString();
        final Session Default_Session = new Session(Default_Name);
        this.List.add(Default_Session);
        this.Current = List.get(0);
    }
}
