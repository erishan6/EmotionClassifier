package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial.csv";
        String goldPath = "data/trial.labels";
        Corpus corpus = new Corpus("testCorpus", filepath, 6, goldPath);

        Evaluator evaluator = new Evaluator(corpus);
        System.out.println(evaluator.getRecallAverage());
        System.out.println(evaluator.getPrecisionAverage());
        System.out.println(evaluator.getFScoreAverage());
        /*
        Classifier.classify(corpus);

        Evaluator.
        */


    }
}
