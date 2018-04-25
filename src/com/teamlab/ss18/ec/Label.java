package com.teamlab.ss18.ec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deniz on 19.04.18.
 */
public class Label {

    private static final Map<String, Integer> LABELS_MAP;
    static{
        Map<String, Integer> otherMap = new HashMap<String, Integer>();
        otherMap.put( "joy", 0);
        otherMap.put( "surprise", 1);
        otherMap.put( "sad", 2);
        otherMap.put( "fear", 3);
        otherMap.put( "anger", 4);
        otherMap.put( "disgust", 5);
        LABELS_MAP = Collections.unmodifiableMap( otherMap );
    }
    private static final Map<Integer,String> LABELS_MAP_INT;
    static {
        Map<Integer, String> otherMap = new HashMap<Integer, String>();
        for (String key : LABELS_MAP.keySet()) {
            otherMap.put(LABELS_MAP.get(key), key);
        }
        LABELS_MAP_INT = Collections.unmodifiableMap(otherMap);
    }


    private String labelString = "";
    private int labelInt = -99;

    public Label(){
        labelString = "";
        labelInt = -1;
    }

    public Label(String label){
        labelString = label;
        labelInt = LABELS_MAP.get(label);
    }

    public static Map<String, Integer> getLabelsMap() {
        return LABELS_MAP;
    }

    public static Map<Integer, String> getLabelsMapInt() {
        return LABELS_MAP_INT;
    }

    public String getLabelString() {
        return labelString;
    }

    public int getLabelInt() {
        return labelInt;
    }
}
