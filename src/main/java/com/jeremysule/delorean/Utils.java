package com.jeremysule.delorean;

import java.util.regex.Pattern;

/**
 * Created by Jeremy.
 */
public class Utils {

    public static final String INVALID_ID = "Invalid identifier: {0}. Must be between 0 and 2^31-1";

    public static final String INVALID_TS = "Invalid timestamp: {0}. Must be between 0 and 2^63-1";

    public static final int MAX_DATA_LENGTH = 16;

    public static final String INVALID_Data = "Invalid data string. Must be max " + MAX_DATA_LENGTH +" non-null contiguous non-whitespace:";


    public static void validateID(int id) {
        if (id >= 0)
            return;
        throw new IllegalArgumentException(String.format(INVALID_ID,id));
    }


    public static void validateTimestamp(long ts) {
        if (ts >= 0L)
            return;
        throw new IllegalArgumentException(String.format(INVALID_TS, ts));
    }

    public static void validateData(String data){
        if (data == null
                || data.isEmpty()
                || data.length() > 16
                || data.matches(".*\\s.*")
                ){
            throw new IllegalArgumentException(INVALID_Data);
        }
    }
}
