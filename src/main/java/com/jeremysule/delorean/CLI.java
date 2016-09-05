package com.jeremysule.delorean;

import com.jeremysule.delorean.data.Observation;
import com.jeremysule.delorean.data.TemporalDataStore;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Jeremy.
 */
public class CLI {
    public static enum Command {
        CREATE,
        UPDATE,
        DELETE,
        GET,
        LATEST,
        QUIT,
        ;


    }

    private static final TDSService tdsService = new TDSService(new TemporalDataStore());

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (true){
            String line = in.nextLine();
            String argLines[] = line.split(" ");
            Command command = Command.valueOf(argLines[0]);
            if (command == null ){
                //todo: malformed line
                throw new RuntimeException("ERR Bad command. Quit");
            }
            if (command.equals(Command.QUIT)){
                return;
            }
            handleRequest(command, argLines);
        }

    }

    private static void handleRequest(Command command, String[] argLines) {
        try {
            switch (command) {
                case CREATE:
                    expectArguments(4,argLines);
                    String data_create = tdsService.create(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]), argLines[3]);
                    handleData(data_create);
                    break;
                case UPDATE:
                    expectArguments(4,argLines);
                    String data_update = tdsService.update(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]), argLines[3]);
                    handleData(data_update);
                    break;
                case DELETE:
                    expectArgumentsDELETE(argLines);
                    String data_delete;
                    if (argLines.length == 2 ) {
                        data_delete = tdsService.delete(Integer.parseInt(argLines[1]));
                    } else {
                        data_delete = tdsService.delete(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]));
                    }
                    handleData(data_delete);
                    break;
                case GET:
                    expectArguments(3,argLines);
                    String data_get = tdsService.get(Integer.parseInt(argLines[1]), Long.parseLong(argLines[2]));
                    handleData(data_get);
                    break;
                case LATEST:
                    expectArguments(2,argLines);
                    Observation observation_latest = tdsService.latest(Integer.parseInt(argLines[1]));
                    handleObservation(observation_latest);
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERR " + e.getMessage());
        }
    }

    private static void handleObservation(Observation observation_latest) {
        System.out.println(String.format("OK %d %s",observation_latest.timestamp, observation_latest.data));
    }

    private static void handleData(String data) {
        System.out.println("OK " + data);
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

}
