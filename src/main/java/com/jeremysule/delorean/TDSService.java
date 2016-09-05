package com.jeremysule.delorean;

import com.jeremysule.delorean.data.Observation;
import com.jeremysule.delorean.data.TemporalDataStore;

/**
 * CLI Service for Temporal DAta Store
 */
public class TDSService {

    public static class TDSServiceException extends RuntimeException {
        public TDSServiceException(String message) {
            super(message);
        }
    }

    private TemporalDataStore tds;

    public TDSService(TemporalDataStore tds) {
        this.tds = tds;
    }

    String create(int id, long timestamp, String data) {
        if (tds.hasHistory(id)) {
            throw new TDSServiceException("History exists for identifier " + id);
        }        return tds.put(id, timestamp, data);
    }

    String update(int id, long timestamp, String data){
        checkHasHistory(id);
        String previousData = tds.get(id,timestamp).orElseThrow( () -> new TDSServiceException("No previouse data for id " +id));
        tds.put(id, timestamp, data);
        return previousData;
    }

    String delete (int id){
        checkHasHistory(id);
        return tds.remove(id).orElseThrow( () -> new TDSServiceException("Could not process delete for id " +id));
    }

    String delete(int id, long timestamp) {
        checkHasHistory(id);
        return tds.remove(id,timestamp).orElseThrow( () -> new TDSServiceException(String.format(
                "Could not process delete for id={0} and timestamp={1}", id, timestamp
        )));
    }

    String get(int id, long timestamp) {
        checkHasHistory(id);
        return tds.get(id, timestamp).orElseThrow(() -> new TDSServiceException(String.format(
                "No prior history available for id={0} and timestamp={1}", id, timestamp
        )));

    }

    Observation latest(int id) {
        checkHasHistory(id);
        return tds.get(id).orElseThrow( () -> new TDSServiceException("Could not retrieve latest data for " +id));
    }

    private void checkHasHistory(int id) {
        if (!tds.hasHistory(id)) {
            throw new TDSServiceException("No history exists for identifier " + id);
        }
    }
}
