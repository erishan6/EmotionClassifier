package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial.csv";
        String goldPath = "data/trial.labels";
        Corpus corpus = new Corpus("testCorpus", filepath, 6, goldPath);

        Perceptron perceptron = new Perceptron();
        perceptron.train(corpus, 10);
        perceptron.predict(corpus);

        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(corpus.getTweets().values());


        Evaluator evaluator = new Evaluator(corpus);
        evaluator.printConfusionMatrix();
        evaluator.printEvalResults();


    }
}
