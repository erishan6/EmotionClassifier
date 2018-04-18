package com.teamlab.ss18.ec.Helper;

import com.teamlab.ss18.ec.DataPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Utility {
    public static Map<String,String> rawDataMap(String filepath) {
        Map lm = null;
        try {
            lm = new LinkedHashMap();
            Scanner scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String firstWord = arr[0];
                String theRest = arr[1];
//                System.out.println(firstWord+"\nggggggggggggggggg\n"+theRest+"\n");
                if (lm.containsKey(theRest)) {
                    System.out.println("bug : "+theRest);
                }
                else {
                    lm.put(theRest, firstWord);
                }
            }
            scanner.close();
//            System.out.println(lm.keySet());
//            System.out.println(lm.values());
        }
        catch (FileNotFoundException filenotfound) {
            filenotfound.printStackTrace();
        }
        return lm;
    }
    public static ArrayList<DataPojo> rawDataMapAsArrayList(String filepath) {
        ArrayList<DataPojo> arrayList = null;
        try {
            arrayList = new ArrayList();
            Scanner scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String firstWord = arr[0];
                String theRest = arr[1];
//                System.out.println(firstWord+"\nggggggggggggggggg\n"+theRest+"\n");
                DataPojo dummy = new DataPojo(firstWord,theRest);
                arrayList.add(dummy);
            }
            scanner.close();
//            System.out.println(arrayList.keySet());
//            System.out.println(arrayList.values());
        }
        catch (FileNotFoundException filenotfound) {
            filenotfound.printStackTrace();
        }
        return arrayList;
    }
}
