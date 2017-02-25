package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class SessionList {

    private ArrayList<Session> List;

    private Session Current;

    /* Getters */

    public Session getCurrent() {
        return this.Current;
    }

    /* Setters */

    public void addSession(Session session) {
        List.add(session);
        Current = List.get(List.size()-1);
    }

    public void delSession(Session session) {
        if (List.size() > 0) {
            List.remove(session);
            Current = List.get(List.size()-1);
        }
    }

    /* Constructor */

    public SessionList() {
        final String Default_Name = Mode.T3.toString();
        Session Default_Session = new Session(Default_Name);
        this.List = new ArrayList<>();
        this.List.add(Default_Session);
        this.Current = this.List.get(0);
    }
}
