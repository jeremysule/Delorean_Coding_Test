package com.jeremysule.delorean.data;

/**
 * Created by Jeremy.
 */
public class Observation {

    public final long timestamp;

    public final String data;

    public Observation(long timestamp, String data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Observation) {
            Observation other = (Observation) obj;
            return timestamp == other.timestamp && data.equals(other.data);
        }
        return false;
    }
}
