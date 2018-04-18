package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial.csv";
        String goldPath = "data/trial.labels";
        Corpus corpus = new Corpus("testCorpus", filepath, goldPath);

        /*
        Classifier.classify(corpus);

        Evaluator.
        */


    }
}
