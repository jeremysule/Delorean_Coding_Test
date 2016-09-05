package com.jeremysule.delorean;

import com.jeremysule.delorean.data.TemporalDataStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Jeremy.
 */
public class TemporalDataStoreTest {

    private TemporalDataStore dataStore;

    @Before
    public void setUp() throws Exception {
        dataStore = new TemporalDataStore();
    }

    @After
    public void tearDown() throws Exception {
        dataStore = null ;

    }

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void testPut_InvId() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        dataStore.put(invID, 4533L, "validdata");

    }

    @Test
    public void testPut_InvTS() throws Exception {
        long invTS = -8L;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_TS, invTS));
        dataStore.put(14, invTS, "validdata");

    }

    @Test
    public void testPut_InvData() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        dataStore.put(24, 223423L, "  lkjklj  ");

    }

    @Test
    public void testPut_validData() throws Exception {
        String actual = dataStore.put(23, 98L, "data1");
        assertEquals("put should return inserted data", "data1", actual);
    }


    @Test
    public void getWithID_InvID() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        dataStore.get(invID, 4533L);
    }


    @Test
    public void getWithID_InvTS() throws Exception {
        long invTS = -8L;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_TS, invTS));
        dataStore.get(14, invTS);
    }


    @Test
    public void getWithIDAndTS_AfterPut() throws Exception {
        String expected = "mycooldata";
        String actual_put = dataStore.put(23, 98L, expected);
        assertEquals("put should return inseterd data", expected, actual_put);
        assertEquals("get on newly added value should return same data", expected, dataStore.get(23, 98L).get());
        assertEquals("get on newly added value with foor", expected, dataStore.get(23, 100L).get());
    }

    @Test
    public void getWithTS_AfterPut_Early() throws Exception {
        String expected = "mycooldata";
        dataStore.put(23, 98L, expected);

        assertEquals(Optional.empty(), dataStore.get(23, 56L));
    }

    @Test
    public void getWithTS_AfterSeveralPut() throws Exception {

        dataStore.put(23, 90L, "value1");
        dataStore.put(23, 90L, "value2");
        dataStore.put(23, 110L, "value3");

        assertEquals(Optional.empty(), dataStore.get(23, 80L));
        assertEquals("value2", dataStore.get(23, 90L).get());
        assertEquals("value2", dataStore.get(23, 100L).get());
        assertEquals("value3", dataStore.get(23, 110L).get());
        assertEquals("value3", dataStore.get(23, 3343435L).get());
    }


    @Test
    public void get_IDAlone() throws Exception {
        dataStore.put(23, 90L, "value1");
        dataStore.put(23, 90L, "value2");
        dataStore.put(23, 110L, "value3");
        dataStore.put(23, 100L, "value4");
        assertEquals("value3", dataStore.get(23).get());
    }


    @Test
    public void get_no_ID() throws Exception {
        dataStore.put(23, 90L, "value1");
        assertEquals(Optional.empty(), dataStore.get(12));
    }


    @Test
    public void removeID_InvID() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        dataStore.remove(invID);
    }

    @Test
    public void remove_IDOnly() throws Exception {
        dataStore.put(34, 100L, "value1");
        dataStore.put(34, 110L, "value2");

        assertEquals("value2", dataStore.remove(34).get());
        assertFalse(dataStore.hasHistory(34));
        assertEquals(Optional.empty(), dataStore.remove(34));
    }

    @Test
    public void removeID_TS_InvID() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        dataStore.remove(invID, 123L);
    }

    @Test
    public void removeID_TS_InvTS() throws Exception {
        long invTS = -8L;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_TS, invTS));
        dataStore.remove(14, invTS);
    }

    @Test
    public void removeID_TS_OK() throws Exception {
        dataStore.put(34, 100L, "value1");
        dataStore.put(34, 120L, "value2");
        dataStore.put(34, 130L, "value3");
        dataStore.put(34, 140L, "value4");
        dataStore.put(34, 150L, "value5");

        assertEquals("value3", dataStore.remove(34, 135L).get());
        assertTrue(dataStore.hasHistory(34));
        assertEquals("value3", dataStore.remove(34, 90L).get());
        assertFalse(dataStore.hasHistory(34));

        dataStore.put(34, 100L, "value1");
        dataStore.put(34, 120L, "value2");
        dataStore.put(34, 130L, "value3");
        dataStore.put(34, 140L, "value4");
        dataStore.put(34, 150L, "value5");

        assertTrue(dataStore.hasHistory(34));
        assertEquals("value5", dataStore.remove(34, 100L).get());
        assertFalse(dataStore.hasHistory(34));


    }


    @Test
    public void hasHistory_InvID() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        dataStore.hasHistory(invID);
    }


    @Test
    public void hasHistory() throws Exception {
        assertFalse(dataStore.hasHistory(34));

        dataStore.put(34, 130L, "value3");
        dataStore.put(34, 140L, "value4");
        dataStore.put(34, 150L, "value5");

        assertTrue(dataStore.hasHistory(34));
    }


}