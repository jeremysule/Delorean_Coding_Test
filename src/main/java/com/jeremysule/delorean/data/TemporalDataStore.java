package com.jeremysule.delorean.data;

import com.jeremysule.delorean.Utils;
import com.jeremysule.delorean.data.DataStoreException;
import com.jeremysule.delorean.data.ITemporalDataStore;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Jeremy.
 */
public final class TemporalDataStore implements ITemporalDataStore {

    private final HashMap<Integer, TreeMap<Long, String>> internalDS =
            new HashMap<>();



    @Override
    public String put(int id, long timestamp, String data) {
        Utils.validateID(id);
        Utils.validateTimestamp(timestamp);
        Utils.validateData(data);

        if (internalDS.containsKey(id)) {
            TreeMap<Long, String> history = internalDS.get(id);
            history.put(timestamp,data);
        } else {
            TreeMap<Long, String> history = new TreeMap<>();
            history.put(timestamp, data);
            internalDS.put(id,history);
        }
        return get(id,timestamp).orElseThrow(() ->
                new DataStoreException( "Unable to correctly store data {0}->{1}->{2}",id, timestamp, data ));
    }

    @Override
    public Optional<String> get(int id, long timestamp) {
        Utils.validateID(id);
        Utils.validateTimestamp(timestamp);

        if (internalDS.containsKey(id)){
            Long floorKey =  internalDS.get(id).floorKey(timestamp);
            if (floorKey == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(internalDS.get(id).floorEntry(timestamp).getValue());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Observation> get(int id) {
        Utils.validateID(id);

        if (internalDS.containsKey(id)){
            Observation result = new Observation(internalDS.get(id).lastEntry().getKey(),internalDS.get(id).lastEntry().getValue());
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> remove(int id) {
        Utils.validateID(id);

        if (!internalDS.containsKey(id)){
            return Optional.empty();
        } else {
            return Optional.of(internalDS.remove(id).lastEntry().getValue());
        }
    }

    @Override
    public Optional<String> remove(int id, long timestamp) {
        Utils.validateID(id);
        Utils.validateTimestamp(timestamp);

        if (internalDS.containsKey(id)){
            if (internalDS.get(id).firstKey().equals(internalDS.get(id).ceilingKey(timestamp))) {
                return remove(id);
            }

            TreeMap<Long, String> history = internalDS.get(id);
            Long higherTS = history.ceilingKey(timestamp);
            while ( higherTS!= null) {
                history.remove(higherTS);
                higherTS = history.ceilingKey(timestamp);
            }
            return get(id,timestamp);

        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean hasHistory(int id) {
        Utils.validateID(id);

        return internalDS.containsKey(id);
    }
}
