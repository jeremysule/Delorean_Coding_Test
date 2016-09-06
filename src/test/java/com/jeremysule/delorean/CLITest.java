package com.jeremysule.delorean;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jeremy on 06/09/16.
 */
public class CLITest {

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void handleRequest() throws Exception {
        String[] args = {"CREATE", "0", "100", "1.5"};
        assertEquals("OK 1.5", CLI.handleRequest(CLI.Command.CREATE, args));

        args = new String[]{"UPDATE", "0", "105", "1.6"};
        assertEquals("OK 1.5", CLI.handleRequest(CLI.Command.UPDATE, args));

        args = new String[]{"GET", "0", "100"};
        assertEquals("OK 1.5", CLI.handleRequest(CLI.Command.GET, args));

        args = new String[]{"GET", "0", "110"};
        assertEquals("OK 1.6", CLI.handleRequest(CLI.Command.GET, args));

        args = new String[]{"LATEST", "0"};
        assertEquals("OK 105 1.6", CLI.handleRequest(CLI.Command.LATEST, args));

        args = new String[]{"CREATE", "1", "110", "2.5"};
        assertEquals("OK 2.5", CLI.handleRequest(CLI.Command.CREATE, args));

        args = new String[]{"UPDATE", "1", "115", "2.4"};
        assertEquals("OK 2.5", CLI.handleRequest(CLI.Command.UPDATE, args));

        args = new String[]{"UPDATE", "1", "120", "2.3"};
        assertEquals("OK 2.4", CLI.handleRequest(CLI.Command.UPDATE, args));

        args = new String[]{"UPDATE", "1", "125", "2.2"};
        assertEquals("OK 2.3", CLI.handleRequest(CLI.Command.UPDATE, args));

        args = new String[]{"LATEST", "1"};
        assertEquals("OK 125 2.2", CLI.handleRequest(CLI.Command.LATEST, args));

        args = new String[]{"GET", "1", "120"};
        assertEquals("OK 2.3", CLI.handleRequest(CLI.Command.GET, args));

        args = new String[]{"UPDATE", "1", "120", "2.35"};
        assertEquals("OK 2.3", CLI.handleRequest(CLI.Command.UPDATE, args));

        args = new String[]{"GET", "1", "122"};
        assertEquals("OK 2.35", CLI.handleRequest(CLI.Command.GET, args));

        args = new String[]{"DELETE", "1", "122"};
        assertEquals("OK 2.35", CLI.handleRequest(CLI.Command.DELETE, args));

        args = new String[]{"GET", "1", "125"};
        assertEquals("OK 2.35", CLI.handleRequest(CLI.Command.GET, args));

        args = new String[]{"DELETE", "1"};
        assertEquals("OK 2.35", CLI.handleRequest(CLI.Command.DELETE, args));

    }

    @Test
    public void handleRequest_ex1() throws Exception {

        String[] args = {"CREATE", "2", "100", "1.5"};
        assertEquals("OK 1.5", CLI.handleRequest(CLI.Command.CREATE, args));

        ex.expect(RuntimeException.class);
        ex.expectMessage("History exists for identifier 2");

        args = new String[]{"CREATE", "2", "100", "1.5"};
        CLI.handleRequest(CLI.Command.CREATE, args);


    }

    @Test
    public void handleRequest_ex2() throws Exception {


        ex.expect(RuntimeException.class);
        ex.expectMessage("No history exists for ");

        String[] args = new String[]{"LATEST", "4"};
        CLI.handleRequest(CLI.Command.LATEST, args);


    }


}