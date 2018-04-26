package com.mysample.application;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Map<String, String> map = new HashMap<>();

    @Test
    public void addition_isCorrect() throws Exception {
        map.put("111", "111");
        map.put("222", "222");
        String value2=map.put("222","333");
        System.out.println("11111111111 value2 = " + value2);

        for (String value : map.values()) {
            System.out.println("11111111111 value = " + value);
        }
    }
}