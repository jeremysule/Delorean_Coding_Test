import java.util.Optional;

/**
 * Created by Jeremy.
 */
public interface ITemporalDataStore {

    String put(int id, long timestamp, String data);

    String get(int id, long timestanp);

    String  get(int id);

    Optional<String> remove(int id);

    boolean hasHistory(int id);


}
