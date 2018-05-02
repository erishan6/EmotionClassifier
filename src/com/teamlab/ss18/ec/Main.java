package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        /*
        ****************************
        * Set init parameters      *
        ****************************
        */
        String trainFilePath = "data/full/train/train.csv";
        String testFilePath = "data/full/test/test.csv";
        int epochs = 100;
        boolean shuffle = true;

        /*
        ****************************
        * Set init parameters      *
        ****************************
        */

        Corpus trainCorpus = new Corpus("trainCorpus", trainFilePath, 6);
        Corpus testCorpus = new Corpus("testCorpus", testFilePath, 6);


        //Perceptron perceptron = new Perceptron();
        PerceptronMap perceptron = new PerceptronMap();
        perceptron.fit(trainCorpus,epochs, shuffle, 2, 10);
        perceptron.predict(testCorpus);


        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(trainCorpus.getTweets().values());

        System.out.println("-----------------");
        System.out.println("Evaluation on testset:");


        Evaluator evaluator = new Evaluator(testCorpus); //TODO: pass testCorpus not trainCorpus

        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();
    }
}
