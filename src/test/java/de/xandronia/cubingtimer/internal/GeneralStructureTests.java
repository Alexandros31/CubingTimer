package de.xandronia.cubingtimer.internal;

import org.junit.Test;
import java.time.Duration;
import org.junit.Assert;

/**
 * Created by alex on 21.03.17.
 */

public class GeneralStructureTests {

    @Test
    public void createSessionList() {
        SessionList sessionList = new SessionList();
        Session session = new Session("test");
        Assert.assertEquals(session, sessionList.getCurrent());
    }

    @Test
    public void createSessionList2() {
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


}