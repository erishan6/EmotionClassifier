package com.teamlab.ss18.ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by deniz on 18.04.18.
 */
public class Corpus {
    private String name = "";
    private LinkedHashMap<UUID, Tweet> tweets;
    private String filePath = "";

    public Corpus(String name, String filePath, String goldPath) {
        this.name = name;
        this.filePath = filePath;

        tweets = createCorpus(this.filePath, goldPath);
    }


    private LinkedHashMap createCorpus(String filePath, String goldPath) {
        //TODO: read file into hashMap (like in util)
        LinkedHashMap tweetMap = null;
        try {
            tweetMap = new LinkedHashMap();
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter("\n");
            Scanner scanner2 = new Scanner(new File(goldPath));
            scanner2.useDelimiter("\n");
            while (scanner.hasNext() && scanner2.hasNext()) {
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String label = arr[0];
                String sentence = arr[1];
                String goldLabel = scanner2.next();
                Tweet currentTweet = new Tweet(sentence, goldLabel);
                currentTweet.setPredictedLabel(label);
                tweetMap.put(currentTweet.getId(), currentTweet);

            }
            scanner.close();
            scanner2.close();
            System.out.println(tweetMap.size());
        }
        catch (FileNotFoundException filenotfound) {
            filenotfound.printStackTrace();
        }
        return tweetMap;
    }


}

