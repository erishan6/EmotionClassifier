package com.teamlab.ss18.ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by deniz on 18.04.18.
 */
public class Corpus {
    private String name = "";
    private LinkedHashMap<UUID, Tweet> tweets;
    private String filePath = "";
    public Corpus(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;

        tweets = createCorpus(this.filePath);
    }

    private LinkedHashMap createCorpus(String filePath) {
        //TODO: read file into hashMap (like in util)
        LinkedHashMap tweetMap = null;
        try {
            tweetMap = new LinkedHashMap();
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter("\n");
            while(scanner.hasNext()){
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String label = arr[0];
                String sentence = arr[1];

                Tweet currentTweet = new Tweet(sentence,label);

                if (tweetMap.containsKey(currentTweet.getId())) {
                    System.out.println("bug : tweet duplicate: "+sentence);
                }
                else {
                    tweetMap.put(currentTweet.getId(), currentTweet);
                }
            }
            scanner.close();

        }
        catch (FileNotFoundException filenotfound) {
            filenotfound.printStackTrace();
        }
        return tweetMap;
    }


}

