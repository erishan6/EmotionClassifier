package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial_small.csv";
        String goldPath = "data/trial_small.labels";
        Corpus corpus = new Corpus("testCorpus", filepath, 3, goldPath);

        Perceptron perceptron = new Perceptron();
        perceptron.train(corpus, 10);
        perceptron.predict(corpus);

        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(corpus.getTweets().values());

        /*
        System.out.println("\n\nPREDICTION");
        for (Tweet tweet : corpus.getTweets().values()) {
            System.out.println("---------");
            System.out.println(tweet.getSentence());
            System.out.println("gold: "+tweet.getGoldLabel().getLabelString());
            System.out.println("pred: "+tweet.getPredictedLabel().getLabelString());
            for (int val : tweet.getFeatures()) {
                System.out.print(val+", ");
            }
            System.out.println("\n---------");
        }
        System.out.println("\n\n");
*/
        Evaluator evaluator = new Evaluator(corpus);
        System.out.println("recall: "+evaluator.getRecallAverage());
        System.out.println("precision: "+evaluator.getPrecisionAverage());
        System.out.println("fscore: "+evaluator.getFScoreAverage());

        /*
        Classifier.classify(corpus);

        Evaluator.
        */


    }
}
