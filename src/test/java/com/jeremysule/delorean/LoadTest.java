package com.jeremysule.delorean;

import com.jeremysule.delorean.data.TemporalDataStore;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;

/**
 * Created by Jeremy.
 */
public class LoadTest {
    public TDSService tdsService;

    public TemporalDataStore tds = new TemporalDataStore();

    public SecureRandom rand = new SecureRandom();

    @Before
    public void setUp(){
        tdsService = new TDSService(tds);
    }

    @Test
    public void testMaxLoad(){
        for (int i = 0; i < 10_000 ; i++) {
         tdsService.create(i,0,"0123456789abcdef");
            for (int j = 1; j < 1000 ; j++) {
                tdsService.update(i,j,"0123456789abcdef");
            }
        }
        // No failure, all good
    }
}
