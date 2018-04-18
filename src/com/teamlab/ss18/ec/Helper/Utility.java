package com.teamlab.ss18.ec.Helper;

import com.teamlab.ss18.ec.Tweet;

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
                String label = arr[0];
                String sentence = arr[1];
//                System.out.println(label+"\nggggggggggggggggg\n"+sentence+"\n");
                if (lm.containsKey(sentence)) {
                    System.out.println("bug : "+sentence);
                }
                else {
                    lm.put(sentence, label);
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
    public static ArrayList<Tweet> rawDataMapAsArrayList(String filepath) {
        ArrayList<Tweet> arrayList = null;
        try {
            arrayList = new ArrayList();
            Scanner scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String label = arr[0];
                String sentence = arr[1];
//                System.out.println(label+"\nggggggggggggggggg\n"+sentence+"\n");
                Tweet dummy = new Tweet(sentence,label);
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
