package de.xandronia.cubingtimer.internal;

import org.junit.Test;
import java.time.Duration;
import java.util.ArrayList;

import org.junit.Assert;

/**
 * Created by alex on 21.03.17.
 */

public class GeneralStructureTests {

    /* Session Add Tests */

    @Test
    public void createSessionList() {
        SessionList sessionList = new SessionList();
        Session session = new Session("test");
        Assert.assertEquals(session, sessionList.getCurrent());
    }

    @Test
    public void notEqualSessionsInASessionList() {
        SessionList sessionList = new SessionList();
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ZERO, "noScramble"));
        Assert.assertNotEquals(session, sessionList.getCurrent());
    }

    @Test
    public void addSolveToASession() {
        Session session = new Session("test");
        Solve solve = new Solve(Duration.ZERO, "noScramble");
        session.addSolve(solve);
        Assert.assertEquals(session.getCurrent(), solve);
    }

    @Test
    public void equalSessions() {
        Session sessionA = new Session("test1");
        Session sessionB = new Session("test2");
        sessionA.addSolve(new Solve(Duration.ofSeconds(1), "P"));
        sessionA.addSolve(new Solve(Duration.ofSeconds(2), "A"));
        sessionB.addSolve(new Solve(Duration.ofSeconds(1), "P"));
        sessionB.addSolve(new Solve(Duration.ofSeconds(2), "A"));
        Assert.assertEquals(sessionA, sessionB);
    }

    @Test
    public void rightCurrentSolve() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(1), "P"));
        Solve toBeTested = new Solve(Duration.ofSeconds(2), "A");
        session.addSolve(toBeTested);
        Assert.assertEquals(toBeTested, session.getCurrent());
    }

    @Test
    public void bestSolve() {
        Session session = new Session("test");
        Duration best = Duration.ofSeconds(1);
        Duration second = Duration.ofSeconds(2);
        session.addSolve(new Solve(second, "troll"));
        session.addSolve(new Solve(best, "lol"));
        session.addSolve(new Solve(Duration.ofSeconds(3), "loo"));
        Assert.assertEquals(session.getBest().getTime(), best);
    }

    @Test
    public void bestSolveFalse() {
        Session session = new Session("test");
        Duration best = Duration.ofSeconds(1);
        Duration second = Duration.ofSeconds(2);
        session.addSolve(new Solve(second, "troll"));
        session.addSolve(new Solve(best, "lol"));
        session.addSolve(new Solve(Duration.ofSeconds(3), "loo"));
        Assert.assertNotEquals(session.getBest().getTime(), second);
    }

    @Test
    public void correctExcludedSolvesAverageOf5() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(2), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(34), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(32), "B"));
        session.addSolve(new Solve(Duration.ofSeconds(19), "A"));
        ArrayList<Solve> excluded = new ArrayList<>();
        excluded.add(new Solve(Duration.ofSeconds(1), "A"));
        excluded.add(new Solve(Duration.ofSeconds(34), "M"));
        Assert.assertEquals(excluded, session.getCurrentAo5().getExcludedSolves());
    }

    @Test
    public void correctExcludedSolvesAverageOf5WithDNF() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(2), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(34), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(32), "B"));
        session.getSolve(3).setState(State.DNF);
        session.addSolve(new Solve(Duration.ofSeconds(19), "A"));
        ArrayList<Solve> excluded = new ArrayList<>();
        excluded.add(new Solve(Duration.ofSeconds(1), "A"));
        excluded.add(new Solve(Duration.ofSeconds(32), "B"));
        Assert.assertEquals(excluded, session.getCurrentAo5().getExcludedSolves());
    }

    @Test
    public void correctAverageOf5Time() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(2), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(34), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(32), "B"));
        session.addSolve(new Solve(Duration.ofSeconds(19), "A"));
        Duration time = Duration.ofSeconds(53).dividedBy(3);
        Assert.assertEquals(time, session.getCurrentAo5().getTime());
    }


    @Test
    public void correctAverageOf5TimeWithDNF() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(2), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(34), "M"));
        session.addSolve(new Solve(Duration.ofSeconds(32), "B"));
        session.getSolve(3).setState(State.DNF);
        session.addSolve(new Solve(Duration.ofSeconds(19), "A"));
        Duration time = Duration.ofSeconds(55).dividedBy(3);
        Assert.assertEquals(time, session.getCurrentAo5().getTime());
    }

    @Test
    public void bestAverageOf5() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(4), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(3), "1"));
        Assert.assertEquals(session.getCurrentAo5(), session.getBestAo5());
    }

    @Test
    public void bestAverageOf5Fails() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(800), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(900), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(1000), "1"));
        Assert.assertNotEquals(session.getCurrentAo5(), session.getBestAo5());
    }

    /* Session Delete Tests */

    @Test
    public void correctLastDeleted() {
        Solve toBeDeleted = new Solve(Duration.ofSeconds(9), "");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), ""));
        session.addSolve(toBeDeleted);
        session.delLastSolve();
        Assert.assertEquals(toBeDeleted, session.getLastDeleted());
    }

    @Test
    public void newBestSolve() {
        Solve best = new Solve(Duration.ofSeconds(9), "");
        Solve newBest = new Solve(Duration.ofSeconds(10), "");
        Session session = new Session("test");
        session.addSolve(newBest);
        session.addSolve(new Solve(Duration.ofSeconds(11), ""));
        session.addSolve(new Solve(Duration.ofSeconds(12), ""));
        session.addSolve(best);
        session.delLastSolve();
        Assert.assertEquals(newBest, session.getBest());
    }

}