package de.xandronia.cubingtimer.internal;

import java.util.ArrayList;

/**
 * Created by alex on 22.02.17.
 */

public class SessionList {

    private ArrayList<Session> list;

    private Session current;

    /* Getters */

    public Session getCurrent() {
        return this.current;
    }

    /* Setters */

    public void addSession(final Session session) {
        list.add(session);
        current = list.get(list.size()-1);
    }

    public void delSession(Session session) {
        if (list.size() > 0) {
            list.remove(session);
            current = list.get(list.size()-1);
        }
    }

    /* Constructor */

    public SessionList() {
        final String defaultName = Mode.T3.toString();
        Session defaultSession = new Session(defaultName);
        this.list = new ArrayList<>();
        this.list.add(defaultSession);
        this.current = this.list.get(0);
    }
}
