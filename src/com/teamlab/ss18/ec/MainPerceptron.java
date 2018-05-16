package com.teamlab.ss18.ec;

import java.io.IOException;

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
        //String testFilePath = "data/full/test/test.csv";
        double trainPercentage = 0.8;
        int epochs = 100;
        boolean shuffle = true;

        /*
        ****************************
        * Set init parameters      *
        ****************************
        */

        DataSet dataSet = new DataSet();
        dataSet.trainTestSplit(trainFilePath, trainPercentage, false);

        Corpus trainCorpus = new Corpus("trainCorpus", "", 6);
        trainCorpus.create(dataSet.train);
        Corpus testCorpus = new Corpus("testCorpus", "", 6);
        testCorpus.create(dataSet.test);

        AbstractClassifier perceptron = new Perceptron(trainCorpus, testCorpus, epochs, shuffle, 2, 10);
        perceptron.train();
        perceptron.predict(testCorpus);
        perceptron.evaluate(testCorpus);


    }
}
