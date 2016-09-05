package com.jeremysule.delorean;

import com.jeremysule.delorean.data.TemporalDataStore;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TDSServiceTest {

    public TDSService tdsService;

    public TemporalDataStore tds = new TemporalDataStore();
    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Before
    public void setUp(){
        tdsService = new TDSService(tds);
    }

    @Test
    public void create() throws Exception {
        tdsService.create(22,343434L,"data1");
        assertEquals("data1", tds.get(22,343434L).get());
    }

    @Test
    public void create_alreadyExists() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);
        tdsService.create(22,343434L,"data1");
        assertEquals("data1", tds.get(22).get().data);
        tdsService.create(22,34343L,"data2");
    }

    @Test
    public void update() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");

        assertEquals("data3", tds.get(22,110L).get());
        tdsService.update(22,115L,"data4");
        assertEquals("data3", tds.get(22,110L).get());
    }

    @Test
    public void update_NotExists() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);
        tdsService.update(22,343434L,"data1");
    }

    @Test
    public void delete_id_not_exists() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);
        tdsService.delete(22);
    }

    @Test
    public void delete_id() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        String actual = tdsService.delete(22);
        assertEquals("data3", actual);
        assertFalse(tds.hasHistory(22));
    }

    @Test
    public void delete_ts_not_exists() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);
        tdsService.delete(22,345L);
    }

    @Test
    public void delete_ts() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        String actual = tdsService.delete(22,115L);
        assertEquals("data3", actual);
        actual = tdsService.delete(22,110L);
        assertEquals("data1",actual);
        actual = tdsService.delete(22,55L);
        assertEquals("data1",actual);
        assertFalse(tds.hasHistory(22));
    }

    @Test
    public void delete_ts2() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        String actual = tdsService.delete(22,100L);
        assertEquals("data3", actual);
        assertFalse(tds.hasHistory(22));
    }

    @Test
    public void get_no_history() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);

        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");

        tdsService.get(34,56L);

    }

    @Test
    public void get_no_info() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);

        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");

        tdsService.get(22,23L);
    }

    @Test
    public void get_expected() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        assertEquals("data1",tdsService.get(22,105L));
        assertEquals("data3",tdsService.get(22,110L));
        assertEquals("data3",tdsService.get(22,113L));
    }

    @Test
    public void latest_no_hist() throws Exception {
        ex.expect(TDSService.TDSServiceException.class);

        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        tdsService.latest(222);
    }

    @Test
    public void latest_expected() throws Exception {
        tdsService.create(22,100L,"data1");
        tdsService.update(22,110L,"data2");
        tdsService.update(22,110L,"data3");
        assertEquals("data3",tdsService.latest(22).data);
    }


}