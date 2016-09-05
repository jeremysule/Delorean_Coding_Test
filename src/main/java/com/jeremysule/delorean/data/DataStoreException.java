package com.jeremysule.delorean.data;

/**
 * Created by Jeremy.
 */
public class DataStoreException extends RuntimeException {
    public DataStoreException(String message) {
        super(message);
    }

    public DataStoreException(String format, int id, long timestamp, String data) {
        super(String.format(format, id, timestamp, data));
    }
}
