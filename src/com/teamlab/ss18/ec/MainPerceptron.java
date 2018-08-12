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
        //String trainFilePath = "data/full/data_original";
        String trainFilePath = "data/iest/full/01_train/iest_train.csv";

        try{
            trainFilePath = args[0];
        } catch (Exception e){
            System.out.println("Please provide the filepath for the iest data!");
            System.out.println("Program terminated...");
            System.exit(0);
        }


        //String testFilePath = "data/full/test/test.csv";
        double trainPercentage = 0.7;
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
