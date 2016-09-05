import com.jeremysule.delorean.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Jeremy.
 */
public class TemporalDataStoreTest {

    TemporalDataStore dataStore;
    @Before
    public void setUp() throws Exception {
        dataStore = new TemporalDataStore();
    }

//    @After
//    public void tearDown() throws Exception {
//
//    }

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
        dataStore.put(24,223423L,"  lkjklj  ");

    }

    @Test
    public void testPut_validData() throws Exception {
        String actual = dataStore.put(23,98L, "data1");
        assertEquals("put should return inseterd data","data1", actual);
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
        String actual_put = dataStore.put(23,98L, expected);
        assertEquals("put should return inseterd data", expected, actual_put);
        assertEquals("get on newly added value should return same data", expected, dataStore.get(23, 98L).get());
        assertEquals("get on newly added value with ceiling", expected, dataStore.get(23, 100L).get());
    }

    @Test
    public void getWithIDAndTS_AfterPut_Early() throws Exception {
        ex.expect(NoSuchElementException.class);
        String expected = "mycooldata";
        String actual_put = dataStore.put(23,98L, expected);

        dataStore.get(23, 56L).get();
    }

    @Test
    public void getWithID_AfterTwoPut() throws Exception {

        dataStore.put(23,90L, "value1");
        dataStore.put(23,90L, "value2");
        dataStore.put(23,90L, "value3");
        fail();




    }




    @Test
    public void get1() throws Exception {
        fail();
    }

    @Test
    public void remove() throws Exception {
        fail();
    }

    @Test
    public void hasHistory() throws Exception {
        fail();
    }

}