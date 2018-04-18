package com.teamlab.ss18.ec;

import com.teamlab.ss18.ec.Helper.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "/Users/erishan6/Study_WS17/sem2/CLTeamLab/emotionclassifier/data/iest/trial.csv";
        Map lm =  Utility.rawDataMap(filepath);
        System.out.println(lm.size());
        ArrayList<DataPojo> dataPojoArrayList = Utility.rawDataMapAsArrayList(filepath);
        System.out.println(dataPojoArrayList.size());

    }
}
