package com.jeremysule.delorean.data;

import java.util.Optional;

/**
 * Stores Temporal data with id->timestamp->data
 *
 * For aata validation, see {@link com.jeremysule.delorean.Utils}}
 */
public interface ITemporalDataStore {

    /**
     * Creates a new record for the id and timestamps. Creates the history if it didn't exits. Replaces any existing value.
     * @param id
     * @param timestamp
     * @param data
     * @return data that was successfully inserted
     */
    String put(int id, long timestamp, String data);

    /**
     * Return the data for that ID at that particular point in time.
     *
     * @param id
     * @param timestanp
     * @return Empty if no hisotry is present for that ID at all or at that timestamp. Value at that point, otherwise.
     */
    Optional<String> get(int id, long timestanp);

    /**
     * Returns the most recent data for that id, or null if the id has no history.
     *
     * @param id
     * @return
     */
    Optional<String> get(int id);

    /**
     * Remove all history for that ID.
     * @param id
     * @return Most recent before removing data if available, see {@link ITemporalDataStore#get(int)}
     */
    Optional<String> remove(int id);

    /**
     * Removes history for that ID from that timestamp forward. Removes entire history if given timestamp predates current history
     * @param id
     * @param timestamp
     * @return New value for that timestamp, or previous value is removes all history, empty if no history for that id
     */
    Optional<String> remove(int id, long timestamp);

    /**
     * Check if there is hisotry that a given ID.
     * @param id
     * @return
     */
    boolean hasHistory(int id);


}
