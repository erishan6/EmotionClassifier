package com.teamlab.ss18.ec;

/**
 * Created by deniz on 13.04.18.
 */

import java.util.HashMap;

/**
 * this class serves the sole purpose of having a main method to try small code snippets and see what they do
 *
 */
public class Scribble_Deniz {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        int count = 0;

        map.put("zero",++count);
        map.put("one", ++count);

        for (String key : map.keySet()) {
            int val = map.get(key);
            System.out.println(key+": "+val);
        }
    }
}
