package com.jeremysule.delorean;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Jeremy.
 */
public class UtilsTest {

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void validateID_InvID() throws Exception {
        int invID = -8;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_ID, invID));
        Utils.validateID(invID);

    }

    @Test
    public void validateID() throws Exception {
        Utils.validateID(15);
    }

    @Test
    public void validateTimestamp_InvID() throws Exception {
        long invTS = -8L;
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(String.format(Utils.INVALID_TS, invTS));
        Utils.validateTimestamp(invTS);

    }

    @Test
    public void validateTimestamp() throws Exception {
        Utils.validateTimestamp(34444443L);
    }

    @Test
    public void validateData() throws Exception {
        Utils.validateData("898sdjhf");
    }

    @Test
    public void validateData_null() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData(null);
    }

    @Test
    public void validateData_empty() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData("");
    }


    @Test
    public void validateData_tooLong() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData("0123456789abcdefg");
    }


    @Test
    public void validateData_space() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData("aaa aaa");
    }

    @Test
    public void validateData_space2() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData("aaa ");
    }

    @Test
    public void validateData_space3() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData(" aaa");
    }

    @Test
    public void validateData_tab() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage(Utils.INVALID_Data);
        Utils.validateData("aaa\taaa");
    }


}