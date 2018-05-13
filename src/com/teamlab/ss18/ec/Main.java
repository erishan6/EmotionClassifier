package com.teamlab.ss18.ec;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

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
//        Perceptron perceptron = new Perceptron();
//        perceptron.fit(testCorpus, epochs, shuffle, 1, 10);
//        perceptron.predict(testCorpus);


        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(trainCorpus.getTweets().values());

        System.out.println("-----------------");
        System.out.println("Evaluation on testset:");

        Evaluator evaluator = new Evaluator(testCorpus);

        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();


        /*
         *
         * Bayesian section
         *
         * */


        String filepath2 = "data/train.csv";
        System.out.println("training on " + filepath2);
        Corpus trainCorpus2 = new Corpus("trainCorpus", filepath2, 6);
        AbstractClassifier naiveBayesian = new NaiveBayesianClassifier(trainCorpus2);
        naiveBayesian.train();
        // dev corpus to be passed for evaluation
        naiveBayesian.evaluate(naiveBayesian.trainingCorpus);
        // no dev set used
        String filepath3 = "data/train_trial.csv";
        Corpus testCorpus2 = new Corpus("testCorpus", filepath3, 6);
        naiveBayesian.predict(testCorpus2);
        naiveBayesian.evaluate(testCorpus2);
    }
}
