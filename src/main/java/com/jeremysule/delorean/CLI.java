package com.jeremysule.delorean;

import com.jeremysule.delorean.data.Observation;
import com.jeremysule.delorean.data.TemporalDataStore;

import java.util.Scanner;

/**
 * Created by Jeremy.
 */
public class CLI {
    private static final TDSService tdsService = new TDSService(new TemporalDataStore());

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            try {
                String line = in.nextLine();
                if (handleCommandLine(line) == false)
                    return;
            } catch (RuntimeException e) {
                System.out.println("ERR " + e.getMessage());
            }
        }

    }

    /**
     * Reads the command line,  parses the command and propagate the request.
     *
     * @param line
     * @return true if we continue the program, false if we are to return
     */
    public static boolean handleCommandLine(String line) {
        String argLines[] = line.split("\\s+");
        Command command = null;
        try {
            command = Command.valueOf(argLines[0]);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Command not recognized.");
        }
        if (command.equals(Command.QUIT)) {
            return false;
        }
        String toShow = handleRequest(command, argLines);
        System.out.println(toShow);
        return true;
    }

    /**
     * Handles the request according to the command given
     *
     * @param command
     * @param argLines
     */
    public static String handleRequest(Command command, String[] argLines) {
            switch (command) {
                case CREATE:
                    expectArguments(4,argLines);
                    String data_create = tdsService.create(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]), argLines[3]);
                    return "OK " + data_create;
                case UPDATE:
                    expectArguments(4,argLines);
                    String data_update = tdsService.update(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]), argLines[3]);
                    return "OK " + data_update;
                case DELETE:
                    expectArgumentsDELETE(argLines);
                    String data_delete;
                    if (argLines.length == 2 ) {
                        data_delete = tdsService.delete(Integer.parseInt(argLines[1]));
                    } else {
                        data_delete = tdsService.delete(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]));
                    }
                    return "OK " + data_delete;
                case GET:
                    expectArguments(3,argLines);
                    String data_get = tdsService.get(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]));
                    return "OK " + data_get;
                case LATEST:
                    expectArguments(2,argLines);
                    Observation observation_latest = tdsService.latest(Integer.parseInt(argLines[1]));
                    return String.format("OK %d %s", observation_latest.timestamp, observation_latest.data);
                default:
                    throw new RuntimeException("Unknown command");
            }
    }

    private static void expectArguments(int expectedNbArguments, String[] argLines) {
        if (argLines.length != expectedNbArguments) {
            throw new RuntimeException("Incorrect number of Arguments");
        }
    }

    private static void expectArgumentsDELETE (String[] argLines) {
        if (argLines.length == 2 || argLines.length == 3) {
            return;
        } else {
            throw new RuntimeException("Incorrect number of Arguments");
        }
    }

    public enum Command {
        CREATE,
        UPDATE,
        DELETE,
        GET,
        LATEST,
        QUIT,
        ;


    }

}
