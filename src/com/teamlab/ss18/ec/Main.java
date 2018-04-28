package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        String filepath = "data/trial.csv";
        String goldPath = "data/trial.labels";
        Corpus corpus = new Corpus("testCorpus", filepath, 6, goldPath);

        //Perceptron perceptron = new Perceptron();
        PerceptronMap perceptron = new PerceptronMap();
        perceptron.fit(corpus,100, false, 2, 10);
        perceptron.predict(corpus);


        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(corpus.getTweets().values());

        System.out.println("Evaluation on testset:");
        Evaluator evaluator = new Evaluator(corpus);

        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();

        //iter through corpus
        for (UUID uuid : corpus.getTweets().keySet()) {
            Tweet t = corpus.getTweets().get(uuid);
//            System.out.println(t.getGoldLabel().getLabelString());
        }
    }
}
