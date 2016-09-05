import java.util.Optional;

/**
 * Created by Jeremy.
 */
public interface ITemporalDataStore {

    String put(int id, long timestamp, String data);

    Optional<String> get(int id, long timestanp);

    Optional<String> get(int id);

    Optional<String> remove(int id);

    boolean hasHistory(int id);


}
