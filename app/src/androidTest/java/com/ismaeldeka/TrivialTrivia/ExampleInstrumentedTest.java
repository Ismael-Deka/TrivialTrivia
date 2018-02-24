package com.ismaeldeka.TrivialTrivia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseBooleanArray;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals(0, getSparseBoolArraySize(9));
    }

    private int getSparseBoolArraySize(int initSize){
        SparseBooleanArray booleanArray = new SparseBooleanArray(initSize);
        return booleanArray.size();
    }
}
