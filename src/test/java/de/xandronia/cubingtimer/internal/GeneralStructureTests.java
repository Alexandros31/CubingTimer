package de.xandronia.cubingtimer.internal;

import org.junit.Test;
import java.time.Duration;
import java.util.ArrayList;
import org.junit.Assert;


/**
 * Created by alex on 21.03.17.
 */

public class GeneralStructureTests extends Calculations {

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
        Assert.assertFalse(session.equals(sessionList.getCurrent()));
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
        Assert.assertFalse(session.getBest().getTime().equals(second));
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
        Assert.assertFalse(session.getCurrentAo5().equals(session.getBestAo5()));
    }

    /* Session Delete Last Solve Tests */

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

    @Test
    public void correctCurrentAo5() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(11), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        Average toBeTested = session.getCurrentAo5();
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        session.delLastSolve();
        Assert.assertEquals(toBeTested, session.getCurrentAo5());
    }

    @Test
    public void correctLastDeletedAo5() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(11), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        Average toBeTested = session.getCurrentAo5();
        session.delLastSolve();
        Assert.assertEquals(toBeTested, session.getLastDeletedAverage());
    }

    @Test
    public void keyValueDeletedFromHashMap() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(11), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        final Average deleted = session.getCurrentAo5();
        session.delLastSolve();
        Assert.assertFalse(session.getAveragesOf5().containsValue(deleted));
    }

    @Test
    public void newBestAo5Fails() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(6), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(10), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(10), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "U"));
        Average best = session.getBestAo5();
        session.addSolve(new Solve(Duration.ofSeconds(10), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "1"));
        session.delLastSolve();
        Average current = session.getCurrentAo5();
        Assert.assertFalse(best.equals(current));
    }

    @Test
    public void newBestAo5() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(6), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(10), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(100), "U"));
        Average best = session.getBestAo5();
        session.addSolve(new Solve(Duration.ofSeconds(16), "L"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "1"));
        session.delLastSolve();
        Average current = session.getBestAo5();
        Assert.assertEquals(best, current);
    }

    @Test
    public void correctCurrentAo5_2() {
        Solve del = new Solve(Duration.ofSeconds(11), "L");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        session.addSolve(del);
        session.delLastSolve();
        Session test = new Session("");
        test.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        test.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        test.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        test.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        test.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        Assert.assertEquals(test.getCurrentAo5(), session.getCurrentAo5());
    }

    @Test
    public void correctIfStatement() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(6), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(10), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(100), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(350), "U"));
        session.delLastSolve();
        // No NullPointerException
    }

    /* Session Delete Any Solve Tests */

    @Test
    public void correctAnyLastDeleted() {
        Solve toBeDeleted = new Solve(Duration.ofSeconds(9), "");
        Session session = new Session("test");
        session.addSolve(toBeDeleted);
        session.addSolve(new Solve(Duration.ofSeconds(10), ""));
        session.delSolve(toBeDeleted);
        Assert.assertEquals(toBeDeleted, session.getLastDeleted());
    }

    @Test
    public void newBestSolveAny() {
        Solve best = new Solve(Duration.ofSeconds(9), "");
        Solve newBest = new Solve(Duration.ofSeconds(10), "");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(11), ""));
        session.addSolve(new Solve(Duration.ofSeconds(12), ""));
        session.addSolve(newBest);
        session.addSolve(best);
        session.addSolve(new Solve(Duration.ofSeconds(17), ""));
        session.delSolve(best);
        Assert.assertEquals(newBest, session.getBest());
    }

    @Test
    public void deleteFirstSolve() {
        Session session = new Session("test");
        Solve first = new Solve(Duration.ofSeconds(1), "");
        session.addSolve(first);
        session.addSolve(new Solve(Duration.ofSeconds(9), ""));
        session.addSolve(new Solve(Duration.ofSeconds(8), ""));
        session.addSolve(new Solve(Duration.ofSeconds(7), ""));
        session.addSolve(new Solve(Duration.ofSeconds(6), ""));
        session.addSolve(new Solve(Duration.ofSeconds(13), ""));
        session.delSolve(first);
        Assert.assertEquals(session.getCurrentAo5(), session.getBestAo5());
    }

    @Test
    public void correctAverageByDeletingInTheMiddle() {
        Session session = new Session("test");
        Solve del = new Solve(Duration.ofSeconds(1), "");
        session.addSolve(new Solve(Duration.ofSeconds(9), ""));
        session.addSolve(new Solve(Duration.ofSeconds(8), ""));
        session.addSolve(new Solve(Duration.ofSeconds(7), ""));
        session.addSolve(del);
        session.addSolve(new Solve(Duration.ofSeconds(6), ""));
        session.addSolve(new Solve(Duration.ofSeconds(13), ""));
        session.delSolve(del);
        Assert.assertEquals(session.getCurrentAo5(), session.getBestAo5());
    }

    @Test
    public void correctCurrentAo5Any() {
        Solve del = new Solve(Duration.ofSeconds(11), "L");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(del);
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        session.delSolve(del);
        Session test = new Session("");
        test.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        test.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        test.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        test.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        test.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        Assert.assertEquals(test.getCurrentAo5(), session.getCurrentAo5());
    }

    @Test
    public void keyValueDeletedFromHashMapAny() {
        Solve solve = new Solve(Duration.ofSeconds(11), "L");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(10), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(solve);
        session.addSolve(new Solve(Duration.ofSeconds(6), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(12), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(13), "1"));
        final Average deleted = session.getAveragesOf5().get(solve);
        session.delSolve(solve);
        Assert.assertFalse(session.getAveragesOf5().containsValue(deleted));
    }

    @Test
    public void newBestAo5Any() {
        Solve solve = new Solve(Duration.ofSeconds(1), "L");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(14), "F"));
        session.addSolve(new Solve(Duration.ofSeconds(9), "A"));
        session.addSolve(new Solve(Duration.ofSeconds(8), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(7), "R"));
        session.addSolve(solve);
        session.addSolve(new Solve(Duration.ofSeconds(15), "U"));
        session.addSolve(new Solve(Duration.ofSeconds(16), "Z"));
        session.addSolve(new Solve(Duration.ofSeconds(17), "1"));
        session.delSolve(solve);
        Assert.assertEquals(Duration.ofSeconds(8), session.getBestAo5().getTime());
    }

    @Test
    public void justAGiantTest() {
        Solve solve = new Solve(Duration.ofSeconds(1), "");
        Solve solve1 = new Solve(Duration.ofSeconds(2), "");
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(14), ""));
        session.addSolve(new Solve(Duration.ofSeconds(9), ""));
        session.addSolve(new Solve(Duration.ofSeconds(8), ""));
        session.addSolve(new Solve(Duration.ofSeconds(7), ""));
        session.addSolve(solve);
        session.addSolve(new Solve(Duration.ofSeconds(15), ""));
        session.addSolve(new Solve(Duration.ofSeconds(16), ""));
        session.addSolve(new Solve(Duration.ofSeconds(17), ""));
        session.delSolve(solve);
        session.delLastSolve();
        session.addSolve(new Solve(Duration.ofSeconds(1), ""));
        session.delLastSolve();
        session.delLastSolve();
        session.addSolve(solve1);
        session.addSolve(solve);
        session.delSolve(solve);
        session.addSolve(new Solve(Duration.ofSeconds(10), ""));
        session.addSolve(new Solve(Duration.ofSeconds(9), ""));
        session.addSolve(new Solve(Duration.ofSeconds(8), ""));
        session.addSolve(new Solve(Duration.ofSeconds(7), ""));
        session.addSolve(new Solve(Duration.ofSeconds(10), ""));
        session.addSolve(new Solve(Duration.ofSeconds(6), ""));
        session.addSolve(new Solve(Duration.ofSeconds(10), ""));
        session.addSolve(new Solve(Duration.ofSeconds(11), ""));
        Session test = new Session("");
        test.addSolve(new Solve(Duration.ofSeconds(9),""));
        test.addSolve(new Solve(Duration.ofSeconds(8), ""));
        test.addSolve(new Solve(Duration.ofSeconds(7), ""));
        test.addSolve(new Solve(Duration.ofSeconds(15), ""));
        test.addSolve(new Solve(Duration.ofSeconds(2), ""));
        test.addSolve(new Solve(Duration.ofSeconds(10), ""));
        test.addSolve(new Solve(Duration.ofSeconds(9), ""));
        test.addSolve(new Solve(Duration.ofSeconds(8), ""));
        test.addSolve(new Solve(Duration.ofSeconds(7), ""));
        test.addSolve(new Solve(Duration.ofSeconds(10), ""));
        test.addSolve(new Solve(Duration.ofSeconds(6), ""));
        test.addSolve(new Solve(Duration.ofSeconds(10),""));
        Assert.assertEquals(test.getCurrentAo12(), session.getBestAo12());
    }

    /* Big Average Tests */

    @Test
    public void testCurrentAo50() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(5), "0"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "1"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "2"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "3"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "4"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "5"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "6"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "7"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "8"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "9"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "10"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "11"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "12"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "13"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "14"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "15"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "16"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "17"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "18"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "19"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "20"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "21"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "22"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "23"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "24"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "25"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "26"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "27"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "28"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "29"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "30"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "31"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "32"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "33"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "34"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "35"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "36"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "37"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "38"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "39"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "40"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "41"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "42"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "43"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "44"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "45"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "46"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "47"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "48"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "49"));
        Assert.assertEquals(Duration.ofSeconds(5), session.getCurrentAo50().getTime());
    }

    @Test
    public void testBestAo100() {
        Session session = new Session("test");
        session.addSolve(new Solve(Duration.ofSeconds(6), "0"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "1"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "2"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "3"));
        session.addSolve(new Solve(Duration.ofSeconds(6), "4"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "5"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "6"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "7"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "8"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "9"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "10"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "11"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "12"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "13"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "14"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "15"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "16"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "17"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "18"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "19"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "20"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "21"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "22"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "23"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "24"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "25"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "26"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "27"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "28"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "29"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "30"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "31"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "32"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "33"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "34"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "35"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "36"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "37"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "38"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "39"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "40"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "41"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "42"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "43"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "44"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "45"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "46"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "47"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "48"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "49"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "50"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "51"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "52"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "53"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "54"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "55"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "56"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "57"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "58"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "59"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "60"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "61"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "62"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "63"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "64"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "65"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "66"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "67"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "68"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "69"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "70"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "71"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "72"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "73"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "74"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "75"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "76"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "77"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "78"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "79"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "80"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "81"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "82"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "83"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "84"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "85"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "86"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "87"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "88"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "89"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "90"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "91"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "92"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "93"));
        session.addSolve(new Solve(Duration.ofSeconds(5), "94"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "95"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "96"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "97"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "98"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "99"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "100"));
        session.addSolve(new Solve(Duration.ofSeconds(1), "101"));
        double ao100 = ((88*5)+2);
        ao100 /= 90;
        Duration dur = Duration.ofSeconds(0);
        //Assert.assertEquals(, session.getBestAo100().getTime().toString());
        System.out.println(session.getBestAo100().getTime());
        System.out.println(ao100);
    }
}