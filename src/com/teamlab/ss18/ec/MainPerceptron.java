package com.teamlab.ss18.ec;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by deniz on 09.05.18.
 */
public class MainPerceptron {

    public static void main(String[] args) throws IOException {
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
        trainCorpus.create();
        Corpus testCorpus = new Corpus("testCorpus", testFilePath, 6);
        testCorpus.create();






        DataSet dataSet = new DataSet();
        boolean writeToFile = false;
        //dataSet.trainTestSplit("data/full/data_original", 0.7, writeToFile);
        dataSet.trainTestSplit("data/small/trial_small.csv", 0.5, writeToFile);
        String[] trainSamples = dataSet.train;
        String[] testSamples = dataSet.test;

        Corpus tmpCorpus = new Corpus("tmp", "", 6);
        tmpCorpus.create(trainSamples);






        //Perceptron_ArrayWeights perceptron = new Perceptron_ArrayWeights();
        Perceptron perceptron = new Perceptron();

        //perceptron.init(); //all params

        perceptron.fit(trainCorpus, testCorpus, epochs, shuffle, 1, 10);
        perceptron.predict(testCorpus);


        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(trainCorpus.getTweets().values());

        System.out.println("-----------------");
        System.out.println("Evaluation on testset:");

        Evaluator evaluator = new Evaluator(testCorpus);

        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();

    }
}
