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

    public Session getSession(int index) {
        return list.get(index);
    }

    /* Setters */

    public void setCurrent(final Session session) {
        this.current = session;
    }

    public void addSession(Session session) {
        list.add(session);
        current = list.get(list.size()-1);
    }

    public void delSession(Session session) {
        if (list.size() > 0) list.remove(session);
    }

    public void merge(Session session1, Session session2) {
        if (!(list.contains(session1)) || !(list.contains(session2)) || !session1.getMode().equals(session2.getMode())) return;
        delSession(session1);
        delSession(session2);
        Session bigSession;
        Session smallSession;
        if (session1.getSolves().size() < session2.getSolves().size()) {
            bigSession = session2;
            smallSession = session1;
        } else {
            bigSession = session1;
            smallSession = session2;
        }
        Session newSession = bigSession;
        final ArrayList<Solve> solves = smallSession.getSolves();
        for (Solve currentSolve : solves) {
            bigSession.addSolve(currentSolve);
        }
        addSession(newSession);
        current = newSession;
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
