package com.teamlab.ss18.ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
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
    private int numberOfLabels;
    public Corpus(String name, String filePath, int numberOfLabels) {
        this.name = name;
        this.filePath = filePath;
        this.numberOfLabels = numberOfLabels;

        tweets = createCorpus(this.filePath);
    }

    public int getNumberOfLabels() {
        return numberOfLabels;
    }

    public LinkedHashMap<UUID, Tweet> getTweets() {
        return tweets;
    }

    public int size(){
        return tweets.size();
    }

    private LinkedHashMap createCorpus(String filePath) {
        LinkedHashMap tweetMap = null;
        try {
            tweetMap = new LinkedHashMap();
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String mystring = scanner.next();
                String arr[] = mystring.split("\t| ", 2);
                String goldLabel = arr[0];
                String sentence = arr[1];
//                String goldLabel = scanner2.next();
                Tweet currentTweet = new Tweet(sentence, goldLabel);
                //currentTweet.setPredictedLabel(label);
                tweetMap.put(currentTweet.getId(), currentTweet);

            }
            scanner.close();
//            System.out.println("Number of training samples: " + tweetMap.size());
        }
        catch (FileNotFoundException filenotfound) {
            filenotfound.printStackTrace();
        }
        return tweetMap;
    }

    // Adding new features in corpus
    /*
    *  Returns Hashmap of words in corpus along with word count
    */
    public HashMap<String, Integer> wordsList(){
        HashMap<String,Integer> corpusWordsList  = new HashMap<String, Integer>();
        for (Tweet singleTweet : tweets.values()) {
            String sentence = singleTweet.getSentence();
            //TODO update regex precedence for appropraite results and fix for extra space
            String regex = "\\W*(\\@USERNAME)|\\W*(\\[#TRIGGERWORD#])|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\"";
//            String regex = "\\s+|\\W*(\\@USERNAME)\\W*|\\W*(\\[#TRIGGERWORD#])\\W*|\\W*(\\[NEWLINE\\])\\W*|\\W*(http://url.removed)\\W*";
            String[] wordsList = sentence.replaceAll(regex, " ").split("\\s+");
            for (String word : wordsList) {
                if (corpusWordsList.containsKey(word)) {
                    int count = corpusWordsList.get(word);
                    corpusWordsList.put(word,count+1);
                }
                else {
                    corpusWordsList.put(word,1);
                }
            }
        }
//        System.out.println("Corpus size " + corpusWordsList.size());

        return corpusWordsList;
    }

    public HashMap<String, HashMap<String,Integer>> wordEmotionCount() {
        HashMap<String, HashMap<String,Integer>> wordEmotionCount = new HashMap<>();
        for (Tweet singleTweet : tweets.values()) {
            String sentence = singleTweet.getSentence();
            String[] wordsList = sentence.split("\\s+");
            String label = singleTweet.getGoldLabel().getLabelString();
            for (String word : wordsList) {
                HashMap<String, Integer> emotionCount;
                if (wordEmotionCount.containsKey(word)) {
                    emotionCount = wordEmotionCount.get(word);
                    if (emotionCount.containsKey(label)) {
                        int count  = emotionCount.get(label);
                        emotionCount.put(label,count+1);
                    }
                    else {
                        emotionCount.put(label,1);

                    }
                }
                else {
                    emotionCount = new HashMap<>();
                    emotionCount.put(label,1);
//                    corpusWordsList.put(word,1);
                }
                wordEmotionCount.put(word,emotionCount);
            }
        }
        return wordEmotionCount;
    }

    public void printWordEmotionCount(){
        try {
            PrintWriter fs = new PrintWriter("data/wordEmotionCount.txt");
            HashMap<String, HashMap<String,Integer>> wordEmotionCount = wordEmotionCount();
            for (String word : wordEmotionCount.keySet()) {
//                System.out.print(word+ " ");
                fs.print(word+" ");
                HashMap<String, Integer> emotionCount = wordEmotionCount.get(word);
                for (String label : emotionCount.keySet()) {
//                    System.out.print(label+ " : "+emotionCount.get(label)+" ");
                    fs.print(label+ " : "+emotionCount.get(label)+" ");
                }
//                System.out.println();
                fs.print("\n");
            }
            fs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getVocabularySize() {
        return wordsList().size();
    }

}

