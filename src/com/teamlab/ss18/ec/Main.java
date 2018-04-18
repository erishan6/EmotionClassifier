package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial.csv";

        Corpus corpus = new Corpus("testCorpus", filepath, 1);

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
