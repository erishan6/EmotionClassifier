package com.teamlab.ss18.ec;

import java.io.IOException;

/**
 * Created by deniz on 09.05.18.
 */
public class MainNaiveBayes {
    public static void main(String[] args) throws IOException {
        /*
         ****************************
         * Set init parameters      *
         ****************************
         */
        String trainFilePath = "data/full/train.csv";
        String testFilePath = "data/full/test.csv";
//        double trainPercentage = 0.8;
        /*
         ****************************
         * Set init parameters      *
         ****************************
         */

//        DataSet dataSet = new DataSet();
//        dataSet.trainTestSplit(trainFilePath, trainPercentage, false);
//
//        Corpus trainCorpus = new Corpus("trainCorpus", "", 6);
//        trainCorpus.create(dataSet.train);
//        Corpus testCorpus = new Corpus("testCorpus", "", 6);
//        testCorpus.create(dataSet.test);

        Corpus trainCorpus = new Corpus("train",trainFilePath,6);
//        trainCorpus.create();
        Corpus testCorpus = new Corpus("test",testFilePath,6);
//        testCorpus.create();
        AbstractClassifier naiveBayesian = new NaiveBayesianClassifier(trainCorpus);
        naiveBayesian.train();
        naiveBayesian.predict(testCorpus);
        naiveBayesian.evaluate(testCorpus);
    }
}
