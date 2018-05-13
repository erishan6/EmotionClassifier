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





        //Corpus trainCorpus = new Corpus("trainCorpus", trainFilePath, 6);
        //trainCorpus.create();
        //Corpus testCorpus = new Corpus("testCorpus", testFilePath, 6);
        //testCorpus.create();


        //Perceptron_ArrayWeights perceptron = new Perceptron_ArrayWeights();
        Perceptron perceptron = new Perceptron(trainCorpus);

        //perceptron.init(); //all params

        perceptron.fit(testCorpus, epochs, shuffle, 1, 10);
        perceptron.predict(testCorpus);


        System.out.println("-----------------");
        System.out.println("Evaluation on testset:");

        Evaluator evaluator = new Evaluator(testCorpus);

        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();

    }
}
