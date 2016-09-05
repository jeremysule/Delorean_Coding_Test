import com.jeremysule.delorean.DataStoreException;
import com.jeremysule.delorean.Utils;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Jeremy.
 */
public final class TemporalDataStore implements ITemporalDataStore {

    private static final HashMap<Integer, TreeMap<Long, String>> internalDS =
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
            Long floorKey = internalDS.get(id).floorKey(timestamp);
            if (floorKey == null) {
                return Optional.empty();
                //                throw new DataStoreException(String.format("No history for {0}, on or before {1}", id, timestamp));
            }
            return Optional.of(internalDS.get(id).get(floorKey));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> get(int id) {
        return null;
    }

    @Override
    public Optional<String> remove(int id) {
        return null;
    }

    @Override
    public boolean hasHistory(int id) {
        return false;
    }
}
